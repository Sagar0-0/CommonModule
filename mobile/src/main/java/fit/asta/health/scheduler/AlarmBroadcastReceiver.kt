package fit.asta.health.scheduler

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import fit.asta.health.HealthCareApp
import fit.asta.health.MainActivity
import fit.asta.health.R
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.services.AlarmService
import fit.asta.health.scheduler.services.RescheduleAlarmService
import fit.asta.health.scheduler.util.Constants.Companion.ARG_ALARM_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_POST_NOTIFICATION_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_PRE_NOTIFICATION_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_VARIANT_INTERVAL_ALARM_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_ALARM_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_POST_NOTIFICATION_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_PRE_NOTIFICATION_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_VARIANT_INTERVAL_OBJECT
import fit.asta.health.scheduler.util.SerializableAndParcelable.serializable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Calendar

class AlarmBroadcastReceiver : BroadcastReceiver() {

    var alarmEntity: AlarmEntity? = null
    private var preNotificationAlarmEntity: AlarmEntity? = null
    private var postNotificationAlarmEntity: AlarmEntity? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent!!.action || Intent.ACTION_LOCKED_BOOT_COMPLETED == intent.action
        ) {
            val toastText = String.format("Alarm Reboot")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            startRescheduleAlarmsService(context!!)
        } else {
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val bundleForAlarm = intent.getBundleExtra(BUNDLE_ALARM_OBJECT)
            if (bundleForAlarm != null) {
                alarmEntity = bundleForAlarm.serializable(ARG_ALARM_OBJET)
                Log.d("alarmtest", "broadcastReceiver onReceive:alarm $alarmEntity")
                val toastText = String.format("Alarm Received")
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                if (alarmEntity != null) {
                    if (!alarmEntity?.week!!.recurring) {
                        startAlarmService(
                            context, /*alarmEntity!!, null,*/
                            bundleForAlarm,
                            isNotification = false,
                            isPre = false,
                            isVariantInterval = false
                        )
                    } else {
                        if (isAlarmToday(alarmEntity!!)) {
                            startAlarmService(
                                context, /*alarmEntity!!, null,*/
                                bundleForAlarm,
                                isNotification = false,
                                isPre = false,
                                isVariantInterval = false
                            )
                        }
                    }
                }
            }
            val bundleForVariantInterval = intent.getBundleExtra(BUNDLE_VARIANT_INTERVAL_OBJECT)
            if (bundleForVariantInterval != null) {
                alarmEntity =
                    bundleForVariantInterval.serializable(ARG_VARIANT_INTERVAL_ALARM_OBJECT)
                Log.d("TAGTAGTAG", "onReceive:variant $alarmEntity")
                val toastText = String.format("Variant Received")
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                if (alarmEntity != null) {
                    if (!alarmEntity?.week!!.recurring) {
                        startAlarmService(
                            context,
                            bundleForVariantInterval,
                            isNotification = false,
                            isPre = false,
                            isVariantInterval = true
                        )
                    } else {
                        if (isAlarmToday(alarmEntity!!)) {
                            startAlarmService(
                                context,
                                /*  alarmEntity!!,
                                  bundleForVariantInterval.getParcelable(ARG_VARIANT_INTERVAL_OBJECT) as AlarmTimeItem?,*/
                                bundleForVariantInterval,
                                isNotification = false,
                                isPre = false,
                                isVariantInterval = true
                            )
                        }
                    }

                }
            }
            val bundleForPreNotification = intent.getBundleExtra(BUNDLE_PRE_NOTIFICATION_OBJECT)
            if (bundleForPreNotification != null) {
                preNotificationAlarmEntity = bundleForPreNotification.serializable(ARG_PRE_NOTIFICATION_OBJET)
                val id = bundleForPreNotification.getInt("id", 1)
                if (preNotificationAlarmEntity != null) {
                    val notificationIntent = Intent(context, MainActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(
                        context,
                        bundleForPreNotification.getInt("id", 1),
                        notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    var alarmName = context.getString(R.string.notification)
                    if (alarmEntity != null) {
                        alarmName = alarmEntity?.info?.name!!
                    }

                    val notification = NotificationCompat.Builder(context, HealthCareApp.CHANNEL_ID)
                        .setContentTitle("Post Notification")
                        .setContentText(alarmName + id)
                        .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(pendingIntent)

                    notificationManager.notify(123, notification.build())

                }
            }
            val bundleForPostNotification = intent.getBundleExtra(BUNDLE_POST_NOTIFICATION_OBJECT)
            if (bundleForPostNotification != null) {
                postNotificationAlarmEntity =
                    bundleForPostNotification.serializable(ARG_POST_NOTIFICATION_OBJET)
                val notificationIntent = Intent(context, MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(
                    context,
                    bundleForPostNotification.getInt("id", 1),
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                var alarmName = context.getString(R.string.notification)
                if (alarmEntity != null) {
                    alarmName = alarmEntity?.info?.name!!
                }
                val notification = NotificationCompat.Builder(context, HealthCareApp.CHANNEL_ID)
                    .setContentTitle("Post Notification")
                    .setContentText(
                        alarmName + bundleForPostNotification.getInt("id", 1),
                    )
                    .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                notificationManager.notify(124, notification.build())
            }

            val toastText = String.format("Alarm Received but not available")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startAlarmService(
        context: Context,
        bundle: Bundle,
        isNotification: Boolean,
        isPre: Boolean,
        isVariantInterval: Boolean
    ) {
        val intentService = Intent(context.applicationContext, AlarmService::class.java)
        if (isNotification) {
            if (isPre) {
                intentService.putExtra(BUNDLE_PRE_NOTIFICATION_OBJECT, bundle)
            } else {
                intentService.putExtra(BUNDLE_POST_NOTIFICATION_OBJECT, bundle)
                Log.d("TAGTAGTAG", "startAlarmService: inside else")
            }
        } else {
            if (isVariantInterval) {
                intentService.putExtra(BUNDLE_VARIANT_INTERVAL_OBJECT, bundle)
            } else {
                intentService.putExtra(BUNDLE_ALARM_OBJECT, bundle)
            }
        }
        Log.d("alarmtest", "broadcastReceiver start:${context.applicationContext}")
        ContextCompat.startForegroundService(context.applicationContext, intentService)
    }

    private fun startRescheduleAlarmsService(context: Context) {
        val intentService = Intent(context, RescheduleAlarmService::class.java)
        ContextCompat.startForegroundService(context.applicationContext, intentService)
    }

    private fun isAlarmToday(alarm1: AlarmEntity): Boolean {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val today: Int = calendar.get(Calendar.DAY_OF_WEEK)
        when (today) {
            Calendar.MONDAY -> {
                return alarm1.week.monday
            }

            Calendar.TUESDAY -> {
                return alarm1.week.tuesday
            }

            Calendar.WEDNESDAY -> {
                return alarm1.week.wednesday
            }

            Calendar.THURSDAY -> {
                return alarm1.week.thursday
            }

            Calendar.FRIDAY -> {
                return alarm1.week.friday
            }

            Calendar.SATURDAY -> {
                return alarm1.week.saturday
            }

            Calendar.SUNDAY -> {
                return alarm1.week.sunday
            }
        }
        return false
    }
}