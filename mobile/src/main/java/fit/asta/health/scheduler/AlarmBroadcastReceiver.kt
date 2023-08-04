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
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.HealthCareApp
import fit.asta.health.MainActivity
import fit.asta.health.R
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.AlarmUtils
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.services.AlarmService
import fit.asta.health.scheduler.services.RescheduleAlarmService
import fit.asta.health.scheduler.util.Constants
import fit.asta.health.scheduler.util.Constants.Companion.ARG_ALARM_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_POST_NOTIFICATION_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_PRE_NOTIFICATION_OBJET
import fit.asta.health.scheduler.util.Constants.Companion.ARG_VARIANT_INTERVAL_ALARM_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.ARG_VARIANT_INTERVAL_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_ALARM_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_POST_NOTIFICATION_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_PRE_NOTIFICATION_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_VARIANT_INTERVAL_OBJECT
import fit.asta.health.scheduler.util.Constants.Companion.BUNDLE_VARIANT_INTERVAL_OBJECT_NOTIFICATION
import fit.asta.health.scheduler.util.SerializableAndParcelable.parcelable
import fit.asta.health.scheduler.util.SerializableAndParcelable.serializable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBroadcastReceiver : BroadcastReceiver() {

    var alarmEntity: AlarmEntity? = null
    private var preNotificationAlarmEntity: AlarmEntity? = null
    private var postNotificationAlarmEntity: AlarmEntity? = null
    var variantInterval: Stat? = null

    @Inject
    lateinit var alarmLocalRepo: AlarmLocalRepo

    @Inject
    lateinit var alarmUtils: AlarmUtils

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundleForVariantIntervalNotification =
            intent!!.getBundleExtra(BUNDLE_VARIANT_INTERVAL_OBJECT_NOTIFICATION)
        if (bundleForVariantIntervalNotification != null) {
            alarmEntity =
                bundleForVariantIntervalNotification.serializable(ARG_VARIANT_INTERVAL_ALARM_OBJECT)
            variantInterval =
                bundleForVariantIntervalNotification.parcelable(ARG_VARIANT_INTERVAL_OBJECT)
        }
        val bundle = intent.getBundleExtra(Constants.BUNDLE_ALARM_OBJECT_NOTIFICATION)
        if (bundle != null) {
            alarmEntity = bundle.serializable(ARG_ALARM_OBJET)
        }
        when (intent.action) {
            "snooze" -> {
                alarmEntity?.let {
                    setNextAlarm(it, variantInterval)
                    it.info.name = "Snooze ${it.info.name}"
                    alarmUtils.snooze(it)
                }
                val intentService = Intent(context, AlarmService::class.java)
                context?.stopService(intentService)
            }

            "stop" -> {
                alarmEntity?.let { alarm ->
                    if (alarm.interval.isRemainderAtTheEnd) {
                        setPostNotification(alarm, variantInterval)
                    }
                    if (!alarm.week.recurring) {
                        GlobalScope.launch(Dispatchers.Default) { updateState(alarm) }
                    }
                    Log.d("TAGTAGTAG", "onSwipedRight: ")
                }
                val intentService =
                    Intent(context, AlarmService::class.java)
                context?.stopService(intentService)
            }
        }
        if (Intent.ACTION_BOOT_COMPLETED == intent.action || Intent.ACTION_LOCKED_BOOT_COMPLETED == intent.action
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
                if (alarmEntity != null) {
                    startAlarmService(
                        context,
                        bundleForAlarm,
                        isVariantInterval = false
                    )
                }
            }
            val bundleForVariantInterval = intent.getBundleExtra(BUNDLE_VARIANT_INTERVAL_OBJECT)
            if (bundleForVariantInterval != null) {
                alarmEntity =
                    bundleForVariantInterval.serializable(ARG_VARIANT_INTERVAL_ALARM_OBJECT)
                Log.d("TAGTAGTAG", "onReceive:variant $alarmEntity")
                if (alarmEntity != null) {
                    startAlarmService(
                        context,
                        bundleForVariantInterval,
                        isVariantInterval = true
                    )
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
        }
    }

    private fun startAlarmService(
        context: Context,
        bundle: Bundle,
        isVariantInterval: Boolean
    ) {
        val intentService = Intent(context.applicationContext, AlarmService::class.java)
        if (isVariantInterval) {
            intentService.putExtra(BUNDLE_VARIANT_INTERVAL_OBJECT, bundle)
        } else {
            intentService.putExtra(BUNDLE_ALARM_OBJECT, bundle)
        }
        Log.d("alarmtest", "broadcastReceiver start:${context.applicationContext}")
        ContextCompat.startForegroundService(context.applicationContext, intentService)
    }

    private fun startRescheduleAlarmsService(context: Context) {
        val intentService = Intent(context, RescheduleAlarmService::class.java)
        ContextCompat.startForegroundService(context.applicationContext, intentService)
    }

    suspend fun updateState(alarm: AlarmEntity) {
        alarmLocalRepo.updateAlarm(alarm.copy(status = false))
    }

    private fun setNextAlarm(alarm: AlarmEntity, variantInt: Stat?) {
        if (alarm.alarmId == 999) {
            return
        } // alarm is snooze in past so no need to reschedule
        if (variantInt == null) {
            alarmUtils.scheduleNextAlarm(alarm)
            alarmUtils.schedulerAlarmNextPreNotification(alarm, false, null, alarm.alarmId)
        } else {
            alarmUtils.scheduleNextIntervalAlarm(alarm, variantInt)
            alarmUtils.schedulerAlarmNextPreNotification(alarm, true, variantInt, variantInt.id)
        }
    }

    private fun setPostNotification(alarm: AlarmEntity, variantInt: Stat?) {
        if (alarm.alarmId == 999) {
            return
        }
        if (variantInt == null) {
            alarmUtils.schedulerAlarmPostNotification(
                alarm,
                isInterval = true,
                interval = null,
                alarm.alarmId
            )
        } else {
            alarmUtils.schedulerAlarmPostNotification(
                alarm,
                isInterval = true,
                variantInt,
                variantInt.id
            )
        }
    }
}