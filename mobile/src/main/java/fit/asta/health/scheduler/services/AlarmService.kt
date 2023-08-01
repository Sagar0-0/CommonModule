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
import fit.asta.health.MainActivity
import fit.asta.health.R
import fit.asta.health.scheduler.compose.AlarmScreenActivity
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.util.Constants.Companion.ARG_ALARM_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_POST_NOTIFICATION_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_PRE_NOTIFICATION_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_VARIANT_INTERVAL_ALARM_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.ARG_VARIANT_INTERVAL_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_ALARM_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_POST_NOTIFICATION_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_PRE_NOTIFICATION_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_VARIANT_INTERVAL_OBJECT
import fit.asta.health.scheduler.util.SerializableAndParcelable.parcelable
import fit.asta.health.scheduler.util.SerializableAndParcelable.serializable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.random.Random


class AlarmService : Service() {

    var alarmEntity: AlarmEntity? = null
    var variantInterval: Stat? = null
    var preNotificationAlarmEntity: AlarmEntity? = null
    var postNotificationAlarmEntity: AlarmEntity? = null
    lateinit var ringtone: Uri
    lateinit var mediaPlayer: MediaPlayer
    lateinit var vibrator: Vibrator
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
        var notification: Notification? = null
//         getting bundle from intent
        val bundle = intent?.getBundleExtra(BUNDLE_ALARM_OBJECT)
        if (bundle != null) {
            // getting alarm item data from bundle
            alarmEntity = bundle.serializable(ARG_ALARM_OBJET)
            Log.d("alarmtest", "onStartCommand: alarm $alarmEntity")
            // creating notification-pending intent to redirect on notification click
            val notificationIntent = Intent(this, AlarmScreenActivity::class.java)
            notificationIntent.putExtra(BUNDLE_ALARM_OBJECT, bundle)
            val pendingIntent = PendingIntent.getActivity(
                this,
                if (alarmEntity != null) alarmEntity!!.alarmId else Random.nextInt(999999999),
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            var alarmName = getString(R.string.app_name)
            if (alarmEntity != null) {
                alarmName = alarmEntity?.info?.name!!
                setMediaData()
            } else {
                setMediaDataDef()
            }
            createWakeLock().acquire(9000)
            // creating notification with different modes
            val bigTextStyle = NotificationCompat.BigTextStyle()
                .bigText(alarmName)
            notification =
                createNotification(
                    notification,
                    pendingIntent,
                    bigTextStyle,
                    message = alarmEntity!!.info.tag
                )

            mediaPlayer.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }
            startForGroundService(
                notification = notification,
                status = alarmEntity?.vibration!!.status,
                id = alarmEntity!!.alarmId
            )
        }

        val bundleForVariantInterval = intent?.getBundleExtra(BUNDLE_VARIANT_INTERVAL_OBJECT)
        if (bundleForVariantInterval != null) {
            // getting alarm item data from bundle
            alarmEntity =
                bundleForVariantInterval.serializable(ARG_VARIANT_INTERVAL_ALARM_OBJECT)
            variantInterval =
                bundleForVariantInterval.parcelable(ARG_VARIANT_INTERVAL_OBJECT)
            Log.d("TAGTAGTAG", "onStartCommand:variant $alarmEntity \n $variantInterval")
            // creating notification-pending intent to redirect on notification click
            val notificationIntent = Intent(this, AlarmScreenActivity::class.java)
            notificationIntent.putExtra(BUNDLE_VARIANT_INTERVAL_OBJECT, bundleForVariantInterval)
            val pendingIntent = PendingIntent.getActivity(
                this,
                if (variantInterval != null) variantInterval!!.id else Random.nextInt(999999999),
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            var alarmName = getString(R.string.app_name)
            if (alarmEntity != null) {
                alarmName =
                    if (variantInterval != null) variantInterval?.name!! else getString(R.string.app_name)
                setMediaData()
            } else {
                setMediaDataDef()
            }

            // creating notification with different modes
            val bigTextStyle = NotificationCompat.BigTextStyle()
                .bigText(alarmName)
            notification =
                createNotification(
                    notification,
                    pendingIntent,
                    bigTextStyle,
                    message = alarmEntity!!.info.tag
                )

            mediaPlayer.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }
            startForGroundService(
                notification = notification,
                status = alarmEntity?.vibration!!.status,
                id =  variantInterval!!.id
            )
        }


        val bundleForPreNotification = intent?.getBundleExtra(BUNDLE_PRE_NOTIFICATION_OBJECT)
        if (bundleForPreNotification != null) {
            // getting alarm item data from bundle
            preNotificationAlarmEntity =
                bundleForPreNotification.serializable(ARG_PRE_NOTIFICATION_OBJET)
            Log.d("TAGTAGTAG", "onStartCommand:preNotification $preNotificationAlarmEntity")
            // creating notification-pending intent to redirect on notification click
            val notificationIntent = Intent(this, MainActivity::class.java)
            notificationIntent.putExtra(BUNDLE_PRE_NOTIFICATION_OBJECT, bundleForPreNotification)
            val pendingIntent = PendingIntent.getActivity(
                this,
                bundleForPreNotification.getInt("id", 1),
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            var alarmName = getString(R.string.notification)
            if (alarmEntity != null) {
                alarmName = alarmEntity?.info?.name!!
            }
            notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Scheduler")
                .setContentText(
                    alarmName + bundleForPreNotification.getInt("id", 1),
                )
                .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .build()
            startForeground(bundleForPreNotification.getInt("id", 1), notification)
            stopForeground(STOP_FOREGROUND_REMOVE)
            return START_STICKY
        }

        val bundleForPostNotification = intent?.getBundleExtra(BUNDLE_POST_NOTIFICATION_OBJECT)
        if (bundleForPostNotification != null) {
            // getting alarm item data from bundle
            postNotificationAlarmEntity =
                bundleForPostNotification.serializable(ARG_POST_NOTIFICATION_OBJET)
            // creating notification-pending intent to redirect on notification click
            Log.d("TAGTAGTAG", "onStartCommand:postNotification $postNotificationAlarmEntity")

            val notificationIntent = Intent(this, MainActivity::class.java)
            notificationIntent.putExtra(BUNDLE_POST_NOTIFICATION_OBJECT, bundleForPostNotification)
            val pendingIntent = PendingIntent.getActivity(
                this,
                bundleForPostNotification.getInt("id", 1),
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            var alarmName = getString(R.string.notification)
            if (alarmEntity != null) {
                alarmName = alarmEntity?.info?.name!!
            }
            notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Post Notification")
                .setContentText(
                    alarmName + bundleForPostNotification.getInt("id", 1),
                )
                .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .build()
            startForeground(
                bundleForPostNotification.getInt("id", 1),
                notification
            )
            stopForeground(STOP_FOREGROUND_REMOVE)
            return START_STICKY
        }
        return START_STICKY
    }
    private fun createWakeLock(): PowerManager.WakeLock {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        partialWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG)
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

    private fun createNotification(
        notification: Notification?,
        pendingIntent: PendingIntent,
        bigTextStyle: NotificationCompat.BigTextStyle,
        message: String
    ): Notification? {
        var notification1 = notification
        when (alarmEntity?.mode) {
            "Notification" -> {
                notification1 = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Scheduler")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                    .setSound(null)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setOngoing(true)
                    .setWhen(0)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setFullScreenIntent(
                        pendingIntent,
                        true
                    ) // set base on important in alarm entity
                    .setStyle(bigTextStyle)
                    .build()
            }

            "Splash" -> {
                notification1 = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Scheduler")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(NotificationCompat.PRIORITY_MAX) // set base on important in alarm entity
                    .setFullScreenIntent(pendingIntent, true)
                    .setStyle(bigTextStyle)
                    .setWhen(0)
                    .setAutoCancel(true)
                    .build()

                try {
                    pendingIntent.send()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }

            }
        }
        return notification1
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
                Uri.parse(alarmEntity?.tone!!.uri)
            )
            mediaPlayer.prepareAsync()
        } catch (exception: Exception) {
            exception.printStackTrace()
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