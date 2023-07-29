package fit.asta.health.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import java.util.Calendar

class AlarmBroadcastReceiver : BroadcastReceiver() {

    var alarmEntity: AlarmEntity? = null
    var preNotificationAlarmEntity: AlarmEntity? = null
    var postNotificationAlarmEntity: AlarmEntity? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent!!.action || Intent.ACTION_LOCKED_BOOT_COMPLETED == intent.action
        ) {
            val toastText = String.format("Alarm Reboot")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            startRescheduleAlarmsService(context!!)
        }
        else {
            val bundleForAlarm = intent.getBundleExtra(BUNDLE_ALARM_OBJECT)
            if (bundleForAlarm != null) {
                alarmEntity = bundleForAlarm.serializable(ARG_ALARM_OBJET)
                Log.d("TAGTAGTAG", "onReceive:alarm $alarmEntity")
                val toastText = String.format("Alarm Received")
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                if (alarmEntity != null) {
//                    val notificationIntent = Intent(context, AlarmScreenActivity::class.java)
//                    notificationIntent.putExtra(BUNDLE_ALARM_OBJECT, bundleForAlarm)
//                    notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    context?.startActivity(notificationIntent)

                    if (!alarmEntity?.week!!.recurring) {
                        startAlarmService(
                            context!!, /*alarmEntity!!, null,*/
                            bundleForAlarm,
                            isNotification = false,
                            isPre = false,
                            isVariantInterval = false
                        )
                    } else {
                        if (isAlarmToday(alarmEntity!!)) {
                            startAlarmService(
                                context!!, /*alarmEntity!!, null,*/
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
                            context!!,
                            bundleForVariantInterval,
                            isNotification = false,
                            isPre = false,
                            isVariantInterval = true
                        )
                    } else {
                        if (isAlarmToday(alarmEntity!!)) {
                            startAlarmService(
                                context!!,
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
                val id = bundleForPreNotification.getInt(
                    "id", 1
                )
                Log.d("TAGTAGTAG", "onReceive:prenotification $preNotificationAlarmEntity $id")
                val toastText = String.format("Pre Notification Received")
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                if (preNotificationAlarmEntity != null) {
                    startAlarmService(
                        context!!, /*preNotificationAlarmEntity!!, null,*/
                        bundleForPreNotification,
                        isNotification = true,
                        isPre = true,
                        isVariantInterval = false
                    )
                }
            }
            val bundleForPostNotification = intent.getBundleExtra(BUNDLE_POST_NOTIFICATION_OBJECT)
            if (bundleForPostNotification != null) {
                postNotificationAlarmEntity = bundleForPostNotification.serializable(ARG_POST_NOTIFICATION_OBJET)
                Log.d("TAGTAGTAG", "onReceive:postnotification $postNotificationAlarmEntity")
                val toastText = String.format("Post Notification Received")
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                if (postNotificationAlarmEntity != null) {
                    startAlarmService(
                        context!!, /*postNotificationAlarmEntity!!, null,*/
                        bundleForPostNotification,
                        isNotification = true,
                        isPre = false,
                        isVariantInterval = false
                    )
                }
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
        val intentService = Intent(context, AlarmService::class.java)
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
        ContextCompat.startForegroundService(context, intentService)
    }

    private fun startRescheduleAlarmsService(context: Context) {
        val intentService = Intent(context, RescheduleAlarmService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
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