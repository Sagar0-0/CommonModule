package fit.asta.health.scheduler.services


import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.HealthCareApp
import fit.asta.health.R
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.main.deepLinkUrl
import fit.asta.health.navigation.today.ui.view.goToTool
import fit.asta.health.scheduler.data.db.AlarmDao
import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import fit.asta.health.scheduler.ui.AlarmScreenActivity
import fit.asta.health.scheduler.util.Constants
import fit.asta.health.scheduler.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmService : Service() {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Inject
    lateinit var alarmDao: AlarmDao

    var alarmEntity: AlarmEntity? = null
    private lateinit var ringtone: Uri
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private lateinit var partialWakeLock: PowerManager.WakeLock

    @Inject
    lateinit var player: Player

    override fun onCreate() {
        super.onCreate()
        partialWakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmService::WakeLock").apply {
                acquire(60 * 1000L /*1 minutes*/)
            }
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer.isLooping = true
        player.apply {
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ONE
        }
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION") getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        ringtone = RingtoneManager.getActualDefaultRingtoneUri(
            this.baseContext, RingtoneManager.TYPE_ALARM
        )
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //         getting bundle from intent
        if (intent != null) {
            val id: Long = intent.getLongExtra("id", -1)
            scope.launch {
                alarmDao.getAlarm(id)?.let { alarm ->
                    if (alarm.mode == "Notification") notificationAlarm(alarm)
                    else splashAlarm(alarm)
                }
            }
        }
        return START_STICKY
    }

    private fun notificationAlarm(
        alarm: AlarmEntity
    ) {
        val stopIntent = Intent(this, AlarmBroadcastReceiver::class.java).apply {
            action = Utils.DISMISS_ACTION
            putExtra("id", alarm.alarmId)
        }
        val pendingIntentStop = PendingIntent.getBroadcast(
            this, 555, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val snoozeIntent = Intent(this, AlarmBroadcastReceiver::class.java).apply {
            action = Utils.SNOOZE_ACTION
            putExtra("id", alarm.alarmId)
        }
        val pendingIntentSnooze = PendingIntent.getBroadcast(
            this,
            666,
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmName: String = alarm.info.name

        player.apply {
            setMediaItem(MediaItem.fromUri(alarm.tone.uri))
            prepare()
        }
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse("$deepLinkUrl/${goToTool(alarm.info.tag)}")
        )
        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        val builder = NotificationCompat.Builder(this, HealthCareApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_round_access_alarm_24).setSound(null)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCategory(NotificationCompat.CATEGORY_ALARM).setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC).setOngoing(true).setWhen(0)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .addAction(0, "Snooze", pendingIntentSnooze).addAction(0, "Stop", pendingIntentStop)
        val notificationLayout = RemoteViews(packageName, R.layout.notification_small)
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_large)
        notificationLayout.apply {
            setTextViewText(R.id.title, alarmName)
            setTextViewText(R.id.tag, alarm.info.tag)
        }
        notificationLayoutExpanded.apply {
            setTextViewText(R.id.title, alarmName)
            setTextViewText(R.id.tag, alarm.info.tag)
        }

        Glide.with(this).asBitmap().load(getImgUrl(url = alarm.info.url))
            .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.weatherimage)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap, transition: Transition<in Bitmap?>?
                ) {
                    notificationLayout.setImageViewBitmap(R.id.image, resource)
                    notificationLayoutExpanded.setImageViewBitmap(R.id.image, resource)

                    builder.setCustomContentView(notificationLayout)
                        .setCustomBigContentView(notificationLayoutExpanded)

                    player.play()
                    startForGroundService(
                        notification = builder.build(),
                        status = alarm.vibration.status,
                        id = alarm.hashCode(),
                        vibrationPattern = Constants.getVibrationPattern(alarm.vibration.pattern)
                    )
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

    }

    private fun splashAlarm(
        alarm: AlarmEntity
    ) {
        val splashIntent = Intent(this, AlarmScreenActivity::class.java).apply {
            putExtra("id", alarm.alarmId)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, splashIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        player.apply {
            setMediaItem(MediaItem.fromUri(alarm.tone.uri))
            prepare()
        }
        val alarmName: String = alarm.info.name
        val bigTextStyle = NotificationCompat.BigTextStyle().bigText(alarm.info.description)
        val builder =
            NotificationCompat.Builder(this, HealthCareApp.CHANNEL_ID).setContentTitle(alarmName)
                .setContentText(alarm.info.tag).setSmallIcon(R.drawable.ic_round_access_alarm_24)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX) // set base on important in alarm entity
                .setFullScreenIntent(pendingIntent, true).setStyle(bigTextStyle).setWhen(0)
                .setAutoCancel(true)
        player.play()
        startForGroundService(
            notification = builder.build(),
            status = alarm.vibration.status,
            id = alarm.hashCode(),
            vibrationPattern = Constants.getVibrationPattern(alarm.vibration.pattern)
        )

    }


    private fun startForGroundService(
        notification: Notification?, status: Boolean, id: Int, vibrationPattern: LongArray
    ) {
        if (status) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createWaveform(
                        vibrationPattern, 0
                    )
                )
            } else {
                vibrator.vibrate(vibrationPattern, 0)
            }
        }
        Log.d("alarmtest", "startForGroundService")
        startForeground(id, notification)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        vibrator.cancel()
        partialWakeLock.release()
        player.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}