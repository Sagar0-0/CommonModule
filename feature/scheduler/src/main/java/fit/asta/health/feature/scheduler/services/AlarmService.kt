package fit.asta.health.feature.scheduler.services


import android.app.Notification
import android.app.NotificationManager
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
import fit.asta.health.common.utils.Constants.CHANNEL_ID
import fit.asta.health.common.utils.Constants.deepLinkUrl
import fit.asta.health.common.utils.Constants.goToTool
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.scheduler.db.AlarmDao
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.feature.scheduler.ui.AlarmScreenActivity
import fit.asta.health.feature.scheduler.util.Constants
import fit.asta.health.feature.scheduler.util.StateManager
import fit.asta.health.feature.scheduler.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import fit.asta.health.resources.drawables.R as DrawR


@AndroidEntryPoint
class AlarmService : Service() {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Inject
    lateinit var alarmDao: AlarmDao

    @Inject
    lateinit var stateManager: StateManager

    @Inject
    lateinit var notificationManager: NotificationManager

    private var alarmEntity: AlarmEntity? = null
    private lateinit var ringtone: Uri
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private lateinit var partialWakeLock: PowerManager.WakeLock
    private var mTimer: Timer? = null
    private val missedNotificationId = 999
    private val skipNotificationId = 777

    @Inject
    lateinit var player: Player

    override fun onCreate() {
        super.onCreate()
        mTimer = Timer()
        mTimer?.schedule(object : TimerTask() {
            override fun run() {
                if (alarmEntity != null) {
                    notification(
                        message = "Alarm is skipped ",
                        alarmName = alarmEntity!!.info.name,
                        tag = alarmEntity!!.info.tag,
                        nId = skipNotificationId
                    )
                    stopService(Intent(applicationContext, AlarmScreenActivity::class.java))
                    stateManager.missedAlarm(applicationContext, alarmEntity!!)
                }
                stopSelf()
            }
        }, 3 * 60 * 1000)
        partialWakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmService::WakeLock").apply {
                acquire(3 * 60 * 1000L /*3 minutes*/)
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
                    if (alarmEntity != null) {
                        notification(
                            message = "Alarm is missed ",
                            alarmName = alarmEntity!!.info.name,
                            tag = alarmEntity!!.info.tag,
                            nId = missedNotificationId
                        )
                        stateManager.dismissAlarm(applicationContext, alarm.alarmId)
                    } else {
                        alarmEntity = alarm
                        if (alarm.mode == "Notification") notificationAlarm(alarm)
                        else splashAlarm(alarm)
                    }
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
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(DrawR.drawable.ic_round_access_alarm_24).setSound(null)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCategory(NotificationCompat.CATEGORY_ALARM).setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC).setOngoing(true).setWhen(0)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .addAction(0, "Snooze", pendingIntentSnooze).addAction(0, "Stop", pendingIntentStop)
        val notificationLayout = RemoteViews(packageName, DrawR.layout.notification_small)
        val notificationLayoutExpanded = RemoteViews(packageName, DrawR.layout.notification_large)
        notificationLayout.apply {
            setTextViewText(DrawR.id.title, alarmName)
            setTextViewText(DrawR.id.tag, alarm.info.tag)
        }
        notificationLayoutExpanded.apply {
            setTextViewText(DrawR.id.title, alarmName)
            setTextViewText(DrawR.id.tag, alarm.info.tag)
        }

        Glide.with(this).asBitmap().load(getImgUrl(url = alarm.info.url))
            .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(DrawR.drawable.weatherimage)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap, transition: Transition<in Bitmap?>?
                ) {
                    notificationLayout.setImageViewBitmap(DrawR.id.image, resource)
                    notificationLayoutExpanded.setImageViewBitmap(DrawR.id.image, resource)

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
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
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
            NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle(alarmName)
                .setContentText(alarm.info.tag)
                .setSmallIcon(DrawR.drawable.ic_round_access_alarm_24)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX) // set base on important in alarm entity
                .setFullScreenIntent(pendingIntent, true).setStyle(bigTextStyle).setWhen(0)
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
        alarmEntity = null
        mTimer?.cancel()
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

    private fun notification(message: String, alarmName: String, tag: String, nId: Int) {
        val bigTextStyle = NotificationCompat.BigTextStyle().bigText(message)
        val builder =
            NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle(alarmName)
                .setContentText(tag)
                .setSmallIcon(DrawR.drawable.ic_round_access_alarm_24)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX) // set base on important in alarm entity
                .setStyle(bigTextStyle)
                .setAutoCancel(true)
        notificationManager.notify(nId, builder.build())
    }
}