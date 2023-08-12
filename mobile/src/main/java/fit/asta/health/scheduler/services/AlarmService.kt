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
import android.os.*
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.HealthCareApp.Companion.CHANNEL_ID
import fit.asta.health.R
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.main.deepLinkUrl
import fit.asta.health.navigation.today.view.goToTool
import fit.asta.health.scheduler.AlarmBroadcastReceiver
import fit.asta.health.scheduler.compose.AlarmScreenActivity
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.util.Constants.Companion.ARG_ALARM_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_VARIANT_INTERVAL_ALARM_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.ARG_VARIANT_INTERVAL_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_ALARM_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_ALARM_OBJECT_NOTIFICATION
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_VARIANT_INTERVAL_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_VARIANT_INTERVAL_OBJECT_NOTIFICATION
import fit.asta.health.scheduler.util.Constants.Companion.getVibrationPattern
import fit.asta.health.scheduler.util.SerializableAndParcelable.parcelable
import fit.asta.health.scheduler.util.SerializableAndParcelable.serializable
import javax.inject.Inject

@AndroidEntryPoint
class AlarmService : Service() {

    var alarmEntity: AlarmEntity? = null
    private var variantInterval: Stat? = null
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
        val bundle = intent?.getBundleExtra(BUNDLE_ALARM_OBJECT)
        if (bundle != null) {
            alarmEntity = bundle.serializable(ARG_ALARM_OBJET)
            Log.d("alarmtest", "onStartCommand: alarm $alarmEntity")
            when (alarmEntity?.mode) {
                "Notification" -> {
                    notificationAlarm(alarmEntity!!, bundle, BUNDLE_ALARM_OBJECT_NOTIFICATION)
                }

                "Splash" -> {
                    splashAlarm(alarmEntity!!, bundle, BUNDLE_ALARM_OBJECT)
                }
            }
        }

        val bundleForVariantInterval = intent?.getBundleExtra(BUNDLE_VARIANT_INTERVAL_OBJECT)
        if (bundleForVariantInterval != null) {
            alarmEntity = bundleForVariantInterval.serializable(ARG_VARIANT_INTERVAL_ALARM_OBJECT)
            variantInterval = bundleForVariantInterval.parcelable(ARG_VARIANT_INTERVAL_OBJECT)
            Log.d("TAGTAGTAG", "onStartCommand:variant $alarmEntity \n $variantInterval")

            when (alarmEntity?.mode) {
                "Notification" -> {
                    notificationAlarm(
                        alarmEntity!!,
                        bundleForVariantInterval,
                        BUNDLE_VARIANT_INTERVAL_OBJECT_NOTIFICATION,
                        variantInterval
                    )
                }

                "Splash" -> {
                    splashAlarm(
                        alarmEntity!!,
                        bundleForVariantInterval,
                        BUNDLE_VARIANT_INTERVAL_OBJECT,
                        variantInterval
                    )
                }
            }
        }
        return START_STICKY
    }

    private fun notificationAlarm(
        alarmEntity: AlarmEntity, bundle: Bundle, putString: String, variantInterval: Stat? = null
    ) {
        val stopIntent = Intent(this, AlarmBroadcastReceiver::class.java).apply {
            action = "stop"
            putExtra(putString, bundle)
        }
        val pendingIntentStop = PendingIntent.getBroadcast(
            this,
            variantInterval?.id ?: alarmEntity.alarmId,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val snoozeIntent = Intent(this, AlarmBroadcastReceiver::class.java).apply {
            action = "snooze"
            putExtra(putString, bundle)
        }
        val pendingIntentSnooze = PendingIntent.getBroadcast(
            this,
            variantInterval?.id ?: alarmEntity.alarmId,
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmName: String = variantInterval?.name ?: alarmEntity.info.name

        player.apply {
            setMediaItem(MediaItem.fromUri(alarmEntity.tone.uri))
            prepare()
        }
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse("$deepLinkUrl/${goToTool(alarmEntity.info.tag)}")
        )
        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_round_access_alarm_24).setSound(null)
            .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC).setOngoing(true).setWhen(0)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .addAction(0, "Snooze", pendingIntentSnooze).addAction(0, "Stop", pendingIntentStop)
        val notificationLayout = RemoteViews(packageName, R.layout.notification_small)
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_large)
        notificationLayout.apply {
            setTextViewText(R.id.title, alarmName)
            setTextViewText(R.id.tag, alarmEntity.info.tag)
        }
        notificationLayoutExpanded.apply {
            setTextViewText(R.id.title, alarmName)
            setTextViewText(R.id.tag, alarmEntity.info.tag)
        }

        Glide.with(this).asBitmap().load(getImgUrl(url = alarmEntity.info.url))
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
                        status = alarmEntity.vibration.status,
                        id = alarmEntity.alarmId,
                        vibrationPattern = getVibrationPattern(alarmEntity.vibration.pattern)
                    )
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

    }

    private fun splashAlarm(
        alarmEntity: AlarmEntity, bundle: Bundle, putString: String, variantInterval: Stat? = null
    ) {
        val splashIntent = Intent(this, AlarmScreenActivity::class.java).apply {
            putExtra(putString, bundle)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            variantInterval?.id ?: alarmEntity.alarmId,
            splashIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        player.apply {
            setMediaItem(MediaItem.fromUri(alarmEntity.tone.uri))
            prepare()
        }
        val alarmName: String = variantInterval?.name ?: alarmEntity.info.name
        setMediaData()
        val bigTextStyle = NotificationCompat.BigTextStyle().bigText(alarmEntity.info.description)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle(alarmName)
            .setContentText(alarmEntity.info.tag).setSmallIcon(R.drawable.ic_round_access_alarm_24)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_MAX) // set base on important in alarm entity
            .setFullScreenIntent(pendingIntent, true).setStyle(bigTextStyle).setWhen(0)
            .setAutoCancel(true)
        player.play()
        startForGroundService(
            notification = builder.build(),
            status = alarmEntity.vibration.status,
            id = alarmEntity.alarmId,
            vibrationPattern = getVibrationPattern(alarmEntity.vibration.pattern)
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

    private fun setMediaDataDef() {
        try {
            mediaPlayer.setDataSource(this.baseContext, ringtone)
            mediaPlayer.prepareAsync()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private fun setMediaData() {
        try {
            mediaPlayer.setDataSource(
                this.baseContext, (alarmEntity?.tone?.uri ?: ringtone) as Uri
            )
            mediaPlayer.prepareAsync()
        } catch (exception: Exception) {
            exception.printStackTrace()
            Log.d("tone", "setMediaData: ${exception.message}")
            setMediaDataDef()
        }
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