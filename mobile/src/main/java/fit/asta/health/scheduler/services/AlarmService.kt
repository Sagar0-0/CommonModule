package fit.asta.health.scheduler.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import fit.asta.health.HealthCareApp.Companion.CHANNEL_ID
import fit.asta.health.R
import fit.asta.health.scheduler.SchedulerHomeActivity
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
import fit.asta.health.scheduler.view.alarmsplashscreen.AlarmScreenActivity
import kotlin.random.Random


class AlarmService : Service() {

    var alarmEntity: AlarmEntity? = null
    var variantInterval: Stat? = null
    var preNotificationAlarmEntity: AlarmEntity? = null
    var postNotificationAlarmEntity: AlarmEntity? = null
    lateinit var ringtone: Uri
    lateinit var mediaPlayer: MediaPlayer
    lateinit var vibrator: Vibrator

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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var notification: Notification? = null
//         getting bundle from intent
        val bundle = intent?.getBundleExtra(BUNDLE_ALARM_OBJECT)
        if (bundle != null) {
            // getting alarm item data from bundle
            alarmEntity = bundle.getSerializable(ARG_ALARM_OBJET) as AlarmEntity
            // creating notification-pending intent to redirect on notification click
            val notificationIntent = Intent(this, AlarmScreenActivity::class.java)
            notificationIntent.putExtra(BUNDLE_ALARM_OBJECT, bundle)
            val pendingIntent = PendingIntent.getActivity(
                this,
                if (alarmEntity != null) alarmEntity!!.alarmId else Random.nextInt(999999999),
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            var alarmName = getString(R.string.app_name)
            if (alarmEntity != null) {
                alarmName = alarmEntity?.info?.name!!
                try {
                    mediaPlayer.setDataSource(
                        this.baseContext,
                        Uri.parse(alarmEntity?.tone!!.uri)
                    )
                    mediaPlayer.prepareAsync()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            } else {
                try {
                    mediaPlayer.setDataSource(this.baseContext, ringtone)
                    mediaPlayer.prepareAsync()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }

            // creating notification with different modes
            when (alarmEntity?.mode) {
                "Notification" -> {
                    notification = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("Scheduler")
                        .setContentText(alarmName)
                        .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                        .setSound(null)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setFullScreenIntent(pendingIntent, true)
                        .build()
                }
                "Splash" -> {
                    notification = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("Scheduler")
                        .setContentText(alarmName)
                        .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()

                    try {
                        pendingIntent.send()
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }

                }
            }

            mediaPlayer.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }

            if (alarmEntity?.vibration!!.status) {
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

            startForeground(
                if (alarmEntity != null) alarmEntity!!.alarmId else Random.nextInt(
                    999999999
                ), notification
            )
        }

        val bundleForVariantInterval = intent?.getBundleExtra(BUNDLE_VARIANT_INTERVAL_OBJECT)
        if (bundleForVariantInterval != null) {
            // getting alarm item data from bundle
            alarmEntity =
                bundleForVariantInterval.getSerializable(ARG_VARIANT_INTERVAL_ALARM_OBJECT) as AlarmEntity
            variantInterval =
                bundleForVariantInterval.getParcelable(ARG_VARIANT_INTERVAL_OBJECT) as Stat?
            Log.d("TAGTAGTAG", "onStartCommand: $alarmEntity \n $variantInterval")
            // creating notification-pending intent to redirect on notification click
            val notificationIntent = Intent(this, AlarmScreenActivity::class.java)
            notificationIntent.putExtra(BUNDLE_VARIANT_INTERVAL_OBJECT, bundleForVariantInterval)
            val pendingIntent = PendingIntent.getActivity(
                this,
                if (variantInterval != null) variantInterval!!.id else Random.nextInt(
                    999999999
                ),
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            var alarmName = getString(R.string.app_name)
            if (alarmEntity != null) {
                alarmName =
                    if (variantInterval != null) variantInterval?.name!! else getString(R.string.app_name)
                try {
                    mediaPlayer.setDataSource(
                        this.baseContext,
                        Uri.parse(alarmEntity?.tone!!.uri)
                    )
                    mediaPlayer.prepareAsync()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            } else {
                try {
                    mediaPlayer.setDataSource(this.baseContext, ringtone)
                    mediaPlayer.prepareAsync()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }

            // creating notification with different modes
            when (alarmEntity?.mode) {
                "Notification" -> {
                    notification = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("Scheduler")
                        .setContentText(alarmName)
                        .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                        .setSound(null)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setFullScreenIntent(pendingIntent, true)
                        .build()
                }
                "Splash" -> {
                    notification = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("Scheduler")
                        .setContentText(alarmName)
                        .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()

                    try {
                        pendingIntent.send()
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }

                }
            }

            mediaPlayer.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }

            if (alarmEntity?.vibration!!.status) {
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

            startForeground(
                if (variantInterval != null) variantInterval!!.id else Random.nextInt(
                    999999999
                ), notification
            )
        }


        val bundleForPreNotification = intent?.getBundleExtra(BUNDLE_PRE_NOTIFICATION_OBJECT)
        Log.d("TAGTAGTAG", "onStartCommand: $bundleForPreNotification")
        if (bundleForPreNotification != null) {
            // getting alarm item data from bundle
            preNotificationAlarmEntity =
                bundleForPreNotification.getSerializable(ARG_PRE_NOTIFICATION_OBJET) as AlarmEntity
            Log.d("TAGTAGTAG", "onStartCommand: $preNotificationAlarmEntity")
            // creating notification-pending intent to redirect on notification click
            val notificationIntent = Intent(this, SchedulerHomeActivity::class.java)
            notificationIntent.putExtra(BUNDLE_PRE_NOTIFICATION_OBJECT, bundleForPreNotification)
            val pendingIntent = PendingIntent.getActivity(
                this,
                bundleForPreNotification.getInt("id", 1),
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
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
//            val mNotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            mNotificationManager.notify(bundleForPreNotification.getInt("id"), notification)
            startForeground(bundleForPreNotification.getInt("id", 1), notification)
            stopForeground(false)
            return START_STICKY
        }

        val bundleForPostNotification = intent?.getBundleExtra(BUNDLE_POST_NOTIFICATION_OBJECT)
        if (bundleForPostNotification != null) {
            // getting alarm item data from bundle
            postNotificationAlarmEntity =
                bundleForPostNotification.getSerializable(ARG_POST_NOTIFICATION_OBJET) as AlarmEntity
            // creating notification-pending intent to redirect on notification click
            val notificationIntent = Intent(this, SchedulerHomeActivity::class.java)
            notificationIntent.putExtra(BUNDLE_POST_NOTIFICATION_OBJECT, bundleForPostNotification)
            val pendingIntent = PendingIntent.getActivity(
                this,
                bundleForPostNotification.getInt("id", 1),
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
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
//            val mNotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            mNotificationManager.notify(
//                bundleForPostNotification.getInt("id", 1), notification
//            )
            startForeground(
                bundleForPostNotification.getInt("id", 1),
                notification
            )
            stopForeground(false)
            return START_STICKY
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        vibrator.cancel()
//        if (vibrator.hasVibrator()) {
//        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}