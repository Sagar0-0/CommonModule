package fit.asta.health.scheduler.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import fit.asta.health.HealthCareApp.Companion.CHANNEL_ID
import fit.asta.health.R
import fit.asta.health.scheduler.AlarmBroadcastReceiver
import fit.asta.health.scheduler.compose.AlarmScreenActivity
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.util.Constants.Companion.ARG_ALARM_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_VARIANT_INTERVAL_ALARM_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.ARG_VARIANT_INTERVAL_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_ALARM_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_VARIANT_INTERVAL_OBJECT
import fit.asta.health.scheduler.util.SerializableAndParcelable.parcelable
import fit.asta.health.scheduler.util.SerializableAndParcelable.serializable
import kotlinx.coroutines.ExperimentalCoroutinesApi


class AlarmService : Service() {

    var alarmEntity: AlarmEntity? = null
    private var variantInterval: Stat? = null
    private lateinit var ringtone: Uri
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private lateinit var partialWakeLock: PowerManager.WakeLock
    private val TAG = this.javaClass.simpleName

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer.isLooping = true
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        ringtone = RingtoneManager.getActualDefaultRingtoneUri(
            this.baseContext,
            RingtoneManager.TYPE_ALARM
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //         getting bundle from intent
        val bundle = intent?.getBundleExtra(BUNDLE_ALARM_OBJECT)
        if (bundle != null) {
            alarmEntity = bundle.serializable(ARG_ALARM_OBJET)
            Log.d("alarmtest", "onStartCommand: alarm $alarmEntity")
            when (alarmEntity?.mode) {
                "Notification" -> {
                    notificationAlarm(alarmEntity!!, bundle, BUNDLE_ALARM_OBJECT)
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
                        BUNDLE_VARIANT_INTERVAL_OBJECT,
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
        alarmEntity: AlarmEntity,
        bundle: Bundle,
        putString: String,
        variantInterval: Stat? = null
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
        setMediaData()
        createWakeLock().acquire(9000)

        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(alarmEntity.info.description)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(alarmName)
            .setContentText(alarmEntity.info.tag)
            .setSmallIcon(R.drawable.ic_round_access_alarm_24)
            .setSound(null)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setWhen(0)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setStyle(bigTextStyle)
            .addAction(0, "Snooze", pendingIntentSnooze)
            .addAction(0, "Stop", pendingIntentStop)

        mediaPlayer.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }
        startForGroundService(
            notification = builder.build(),
            status = alarmEntity.vibration.status,
            id = alarmEntity.alarmId
        )

    }

    private fun splashAlarm(
        alarmEntity: AlarmEntity,
        bundle: Bundle,
        putString: String,
        variantInterval: Stat? = null
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
        val alarmName: String = variantInterval?.name ?: alarmEntity.info.name
        setMediaData()
        createWakeLock().acquire(9000)
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(alarmEntity.info.description)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(alarmName)
            .setContentText(alarmEntity.info.tag)
            .setSmallIcon(R.drawable.ic_round_access_alarm_24)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_MAX) // set base on important in alarm entity
            .setFullScreenIntent(pendingIntent, true)
            .setStyle(bigTextStyle)
            .setWhen(0)
            .setAutoCancel(true)

        mediaPlayer.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }
        startForGroundService(
            notification = builder.build(),
            status = alarmEntity.vibration.status,
            id = alarmEntity.alarmId
        )

    }

    private fun createWakeLock(): PowerManager.WakeLock {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        partialWakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            TAG
        )
        return partialWakeLock

    }

    private fun startForGroundService(notification: Notification?, status: Boolean, id: Int) {
        if (status) {
            val pattern = longArrayOf(0, 100, 1000)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createWaveform(
                        pattern,
                        0
                    )
                )
            } else {
                vibrator.vibrate(pattern, 0)
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
                this.baseContext,
                (alarmEntity?.tone?.uri ?: ringtone) as Uri
            )
            mediaPlayer.prepareAsync()
        } catch (exception: Exception) {
            exception.printStackTrace()
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
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}