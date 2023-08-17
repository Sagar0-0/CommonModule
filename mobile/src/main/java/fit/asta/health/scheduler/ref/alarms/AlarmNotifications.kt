/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fit.asta.health.scheduler.ref.alarms

import android.Manifest
import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.service.notification.StatusBarNotification
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import fit.asta.health.R
import fit.asta.health.scheduler.ref.AlarmUtils
import fit.asta.health.scheduler.ref.LogUtils
import fit.asta.health.scheduler.ref.provider.Alarm
import fit.asta.health.scheduler.ref.provider.AlarmInstance
import fit.asta.health.scheduler.ref.provider.ClockContract.InstancesColumns
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

internal object AlarmNotifications {
    val alarmStateManager = AlarmStateManager()
    const val EXTRA_NOTIFICATION_ID = "extra_notification_id"

    /**
     * Notification channel containing all low priority notifications.
     */
    private const val ALARM_LOW_PRIORITY_NOTIFICATION_CHANNEL_ID = "alarmLowPriorityNotification"

    /**
     * Notification channel containing all high priority notifications.
     */
    private const val ALARM_HIGH_PRIORITY_NOTIFICATION_CHANNEL_ID = "alarmHighPriorityNotification"

    /**
     * Notification channel containing all snooze notifications.
     */
    private const val ALARM_SNOOZE_NOTIFICATION_CHANNEL_ID = "alarmSnoozeNotification"

    /**
     * Notification channel containing all missed notifications.
     */
    private const val ALARM_MISSED_NOTIFICATION_CHANNEL_ID = "alarmMissedNotification"

    /**
     * Notification channel containing all alarm notifications.
     */
    private const val ALARM_NOTIFICATION_CHANNEL_ID = "alarmNotification"

    /**
     * Formats times such that chronological order and lexicographical order agree.
     */
    private val SORT_KEY_FORMAT: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)

    /**
     * This value is coordinated with group ids from
     * [com.android.deskclock.data.NotificationModel]
     */
    private const val UPCOMING_GROUP_KEY = "1"

    /**
     * This value is coordinated with group ids from
     * [com.android.deskclock.data.NotificationModel]
     */
    private const val MISSED_GROUP_KEY = "4"

    /**
     * This value is coordinated with notification ids from
     * [com.android.deskclock.data.NotificationModel]
     */
    private const val ALARM_GROUP_NOTIFICATION_ID = Int.MAX_VALUE - 4

    /**
     * This value is coordinated with notification ids from
     * [com.android.deskclock.data.NotificationModel]
     */
    private const val ALARM_GROUP_MISSED_NOTIFICATION_ID = Int.MAX_VALUE - 5

    /**
     * This value is coordinated with notification ids from
     * [com.android.deskclock.data.NotificationModel]
     */
    private const val ALARM_FIRING_NOTIFICATION_ID = Int.MAX_VALUE - 7

    @JvmStatic
    @Synchronized
    fun showLowPriorityNotification(
        context: Context,
        instance: AlarmInstance
    ) {
        LogUtils.v("Displaying low priority notification for alarm instance: " + instance.mId)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context, ALARM_LOW_PRIORITY_NOTIFICATION_CHANNEL_ID
        )
            .setShowWhen(false)
            .setContentTitle(
                context.getString(
                    R.string.alarm_alert_predismiss_title
                )
            )
            .setContentText(
                AlarmUtils.getAlarmText(
                    context, instance, true /* includeLabel */
                )
            )
            .setColor(ContextCompat.getColor(context, R.color.colorWindowBackground))
            .setSmallIcon(R.drawable.ic_round_access_alarm_24)
            .setAutoCancel(false)
            .setSortKey(createSortKey(instance))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_EVENT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLocalOnly(true)

        builder.setGroup(UPCOMING_GROUP_KEY)

        // Setup up hide notification
        val hideIntent: Intent = alarmStateManager.createStateChangeIntent(
            context,
            alarmStateManager.ALARM_DELETE_TAG, instance,
            InstancesColumns.HIDE_NOTIFICATION_STATE
        )
        val id = instance.hashCode()
        builder.setDeleteIntent(
            PendingIntent.getService(
                context, id,
                hideIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        // Setup up dismiss action
        val dismissIntent: Intent = alarmStateManager.createStateChangeIntent(
            context,
            alarmStateManager.ALARM_DISMISS_TAG, instance, InstancesColumns.PREDISMISSED_STATE
        )
        builder.addAction(
            R.drawable.ic_notifications_off,
            context.getString(R.string.alarm_alert_dismiss_text),
            PendingIntent.getService(
                context, id,
                dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        // Setup content action if instance is owned by alarm
        val viewAlarmIntent: Intent = createViewAlarmIntent(context, instance)
        builder.setContentIntent(
            PendingIntent.getActivity(
                context, id,
                viewAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        val nm: NotificationManagerCompat = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ALARM_LOW_PRIORITY_NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.default_label),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            nm.createNotificationChannel(channel)
        }
        val notification: Notification = builder.build()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        nm.notify(id, notification)
        updateUpcomingAlarmGroupNotification(context, -1, notification)
    }

    @JvmStatic
    @Synchronized
    fun showHighPriorityNotification(
        context: Context,
        instance: AlarmInstance
    ) {
//        LogUtils.v("Displaying high priority notification for alarm instance: " + instance.mId)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context, ALARM_HIGH_PRIORITY_NOTIFICATION_CHANNEL_ID
        )
            .setShowWhen(false)
            .setContentTitle(
                context.getString(
                    R.string.alarm_alert_predismiss_title
                )
            )
            .setContentText(
                AlarmUtils.getAlarmText(
                    context, instance, true /* includeLabel */
                )
            )
            .setColor(ContextCompat.getColor(context, R.color.colorWindowBackground))
            .setSmallIcon(R.drawable.ic_round_access_alarm_24)
            .setAutoCancel(false)
            .setSortKey(createSortKey(instance))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_EVENT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLocalOnly(true)

        builder.setGroup(UPCOMING_GROUP_KEY)


        // Setup up dismiss action
        val dismissIntent: Intent = alarmStateManager.createStateChangeIntent(
            context,
            alarmStateManager.ALARM_DISMISS_TAG, instance, InstancesColumns.PREDISMISSED_STATE
        )
        val id = instance.hashCode()
        builder.addAction(
            R.drawable.ic_notifications_off,
            context.getString(R.string.alarm_alert_dismiss_text),
            PendingIntent.getService(
                context, id,
                dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        // Setup content action if instance is owned by alarm
        val viewAlarmIntent: Intent = createViewAlarmIntent(context, instance)
        builder.setContentIntent(
            PendingIntent.getActivity(
                context, id,
                viewAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        val nm: NotificationManagerCompat = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ALARM_HIGH_PRIORITY_NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.default_label),
                NotificationManager.IMPORTANCE_HIGH
            )
            nm.createNotificationChannel(channel)
        }
        val notification: Notification = builder.build()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        nm.notify(id, notification)
        updateUpcomingAlarmGroupNotification(context, -1, notification)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun isGroupSummary(n: Notification): Boolean {
        return n.flags and Notification.FLAG_GROUP_SUMMARY == Notification.FLAG_GROUP_SUMMARY
    }

    /**
     * Method which returns the first active notification for a given group. If a notification was
     * just posted, provide it to make sure it is included as a potential result. If a notification
     * was just canceled, provide the id so that it is not included as a potential result. These
     * extra parameters are needed due to a race condition which exists in
     * [NotificationManager.getActiveNotifications].
     *
     * @param context Context from which to grab the NotificationManager
     * @param group The group key to query for notifications
     * @param canceledNotificationId The id of the just-canceled notification (-1 if none)
     * @param postedNotification The notification that was just posted
     * @return The first active notification for the group
     */
    @TargetApi(Build.VERSION_CODES.N)
    private fun getFirstActiveNotification(
        context: Context,
        group: String,
        canceledNotificationId: Int,
        postedNotification: Notification?
    ): Notification? {
        val nm: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifications: Array<StatusBarNotification> = nm.activeNotifications
        var firstActiveNotification: Notification? = postedNotification
        for (statusBarNotification in notifications) {
            val n: Notification = statusBarNotification.notification
            if (!isGroupSummary(n) && group == n.group &&
                statusBarNotification.id != canceledNotificationId
            ) {
                if (firstActiveNotification == null ||
                    n.sortKey.compareTo(firstActiveNotification.sortKey) < 0
                ) {
                    firstActiveNotification = n
                }
            }
        }
        return firstActiveNotification
    }

    private fun getActiveGroupSummaryNotification(context: Context, group: String): Notification? {
        val nm: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifications: Array<StatusBarNotification> = nm.activeNotifications
        for (statusBarNotification in notifications) {
            val n: Notification = statusBarNotification.notification
            if (isGroupSummary(n) && group == n.group) {
                return n
            }
        }
        return null
    }

    private fun updateUpcomingAlarmGroupNotification(
        context: Context,
        canceledNotificationId: Int,
        postedNotification: Notification?
    ) {

        val nm: NotificationManagerCompat = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ALARM_NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.default_label),
                NotificationManager.IMPORTANCE_HIGH
            )
            nm.createNotificationChannel(channel)
        }

        val firstUpcoming: Notification? = getFirstActiveNotification(
            context, UPCOMING_GROUP_KEY,
            canceledNotificationId, postedNotification
        )
        if (firstUpcoming == null) {
            nm.cancel(ALARM_GROUP_NOTIFICATION_ID)
            return
        }

        var summary: Notification? = getActiveGroupSummaryNotification(context, UPCOMING_GROUP_KEY)
        if (summary == null ||
            summary.contentIntent != firstUpcoming.contentIntent
        ) {
            summary = NotificationCompat.Builder(context, ALARM_NOTIFICATION_CHANNEL_ID)
                .setShowWhen(false)
                .setContentIntent(firstUpcoming.contentIntent)
                .setColor(ContextCompat.getColor(context, R.color.colorWindowBackground))
                .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                .setGroup(UPCOMING_GROUP_KEY)
                .setGroupSummary(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setLocalOnly(true)
                .build()
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            nm.notify(ALARM_GROUP_NOTIFICATION_ID, summary)
        }
    }

    private fun updateMissedAlarmGroupNotification(
        context: Context,
        canceledNotificationId: Int,
        postedNotification: Notification?
    ) {


        val nm: NotificationManagerCompat = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ALARM_NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.default_label),
                NotificationManager.IMPORTANCE_HIGH
            )
            nm.createNotificationChannel(channel)
        }

        val firstMissed: Notification? = getFirstActiveNotification(
            context, MISSED_GROUP_KEY,
            canceledNotificationId, postedNotification
        )
        if (firstMissed == null) {
            nm.cancel(ALARM_GROUP_MISSED_NOTIFICATION_ID)
            return
        }

        var summary: Notification? = getActiveGroupSummaryNotification(context, MISSED_GROUP_KEY)
        if (summary == null ||
            summary.contentIntent != firstMissed.contentIntent
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    ALARM_MISSED_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.default_label),
                    NotificationManager.IMPORTANCE_HIGH
                )
                nm.createNotificationChannel(channel)
            }
            summary = NotificationCompat.Builder(context, ALARM_NOTIFICATION_CHANNEL_ID)
                .setShowWhen(false)
                .setContentIntent(firstMissed.contentIntent)
                .setColor(ContextCompat.getColor(context, R.color.colorWindowBackground))
                .setSmallIcon(R.drawable.ic_round_access_alarm_24)
                .setGroup(MISSED_GROUP_KEY)
                .setGroupSummary(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setLocalOnly(true)
                .build()
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            nm.notify(ALARM_GROUP_MISSED_NOTIFICATION_ID, summary)
        }
    }

    @JvmStatic
    @Synchronized
    fun showSnoozeNotification(
        context: Context,
        instance: AlarmInstance
    ) {
//        LogUtils.v("Displaying snoozed notification for alarm instance: " + instance.mId)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context, ALARM_SNOOZE_NOTIFICATION_CHANNEL_ID
        )
            .setShowWhen(false)
            .setContentTitle(instance.mLabel)
            .setColor(ContextCompat.getColor(context, R.color.colorWindowBackground))
            .setSmallIcon(R.drawable.ic_round_access_alarm_24)
            .setAutoCancel(false)
            .setSortKey(createSortKey(instance))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_EVENT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLocalOnly(true)

        builder.setGroup(UPCOMING_GROUP_KEY)


        // Setup up dismiss action
        val dismissIntent: Intent = alarmStateManager.createStateChangeIntent(
            context,
            alarmStateManager.ALARM_DISMISS_TAG, instance, InstancesColumns.DISMISSED_STATE
        )
        val id = instance.hashCode()
        builder.addAction(
            R.drawable.ic_notifications_off,
            context.getString(R.string.alarm_alert_dismiss_text),
            PendingIntent.getService(
                context, id,
                dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        // Setup content action if instance is owned by alarm
        val viewAlarmIntent: Intent = createViewAlarmIntent(context, instance)
        builder.setContentIntent(
            PendingIntent.getActivity(
                context, id,
                viewAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        val nm: NotificationManagerCompat = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ALARM_SNOOZE_NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.default_label),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            nm.createNotificationChannel(channel)
        }
        val notification: Notification = builder.build()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        nm.notify(id, notification)
        updateUpcomingAlarmGroupNotification(context, -1, notification)
    }

    @JvmStatic
    @Synchronized
    fun showMissedNotification(
        context: Context,
        instance: AlarmInstance
    ) {
//        LogUtils.v("Displaying missed notification for alarm instance: " + instance.mId)

        val label = instance.mLabel
        val alarmTime: String = AlarmUtils.getFormattedTime(context, instance.alarmTime)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context, ALARM_MISSED_NOTIFICATION_CHANNEL_ID
        )
            .setShowWhen(false)
            .setContentTitle(context.getString(R.string.alarm_missed_title))
            .setContentText(alarmTime)
            .setColor(ContextCompat.getColor(context, R.color.colorWindowBackground))
            .setSortKey(createSortKey(instance))
            .setSmallIcon(R.drawable.ic_round_access_alarm_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_EVENT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLocalOnly(true)

        builder.setGroup(MISSED_GROUP_KEY)


        val id = instance.hashCode()

        // Setup dismiss intent
        val dismissIntent: Intent = alarmStateManager.createStateChangeIntent(
            context,
            alarmStateManager.ALARM_DISMISS_TAG, instance, InstancesColumns.DISMISSED_STATE
        )
        builder.setDeleteIntent(
            PendingIntent.getService(
                context, id,
                dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        // Setup content intent
        val showAndDismiss: Intent = AlarmInstance.createIntent(
            context,
            AlarmStateManager::class.java, instance.mId
        )
        showAndDismiss.putExtra(EXTRA_NOTIFICATION_ID, id)
        showAndDismiss.action = alarmStateManager.SHOW_AND_DISMISS_ALARM_ACTION
        builder.setContentIntent(
            PendingIntent.getBroadcast(
                context, id,
                showAndDismiss, PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        val nm: NotificationManagerCompat = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ALARM_MISSED_NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.default_label),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            nm.createNotificationChannel(channel)
        }
        val notification: Notification = builder.build()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        nm.notify(id, notification)
        updateMissedAlarmGroupNotification(context, -1, notification)
    }

    @Synchronized
    fun showAlarmNotification(service: Service, instance: AlarmInstance) {
//        LogUtils.v("Displaying alarm notification for alarm instance: " + instance.mId)

        val resources: Resources = service.resources
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(
            service, ALARM_NOTIFICATION_CHANNEL_ID
        )
            .setContentTitle(instance.mLabel)
            .setContentText(
                AlarmUtils.getFormattedTime(
                    service, instance.alarmTime
                )
            )
            .setColor(ContextCompat.getColor(service, R.color.colorWindowBackground))
            .setSmallIcon(R.drawable.ic_round_access_alarm_24)
            .setOngoing(true)
            .setAutoCancel(false)
            .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
            .setWhen(0)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLocalOnly(true)

        // Setup Snooze Action
        val snoozeIntent: Intent = alarmStateManager.createStateChangeIntent(
            service,
            alarmStateManager.ALARM_SNOOZE_TAG, instance, InstancesColumns.SNOOZE_STATE
        )
        snoozeIntent.putExtra(alarmStateManager.FROM_NOTIFICATION_EXTRA, true)
        val snoozePendingIntent: PendingIntent = PendingIntent.getService(
            service,
            ALARM_FIRING_NOTIFICATION_ID, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        notification.addAction(
            R.drawable.ic_round_access_alarm_24,
            resources.getString(R.string.alarm_alert_snooze_text), snoozePendingIntent
        )

        // Setup Dismiss Action
        val dismissIntent: Intent = alarmStateManager.createStateChangeIntent(
            service,
            alarmStateManager.ALARM_DISMISS_TAG, instance, InstancesColumns.DISMISSED_STATE
        )
        dismissIntent.putExtra(alarmStateManager.FROM_NOTIFICATION_EXTRA, true)
        val dismissPendingIntent: PendingIntent = PendingIntent.getService(
            service,
            ALARM_FIRING_NOTIFICATION_ID, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        notification.addAction(
            R.drawable.ic_notifications_off,
            resources.getString(R.string.alarm_alert_dismiss_text),
            dismissPendingIntent
        )

        // Setup Content Action
        val contentIntent: Intent = AlarmInstance.createIntent(
            service, AlarmActivity::class.java,
            instance.mId
        )
        notification.setContentIntent(
            PendingIntent.getActivity(
                service,
                ALARM_FIRING_NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        // Setup fullscreen intent
        val fullScreenIntent: Intent =
            AlarmInstance.createIntent(service, AlarmActivity::class.java, instance.mId)
        // set action, so we can be different then content pending intent
        fullScreenIntent.action = "fullscreen_activity"
        fullScreenIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_NO_USER_ACTION
        notification.setFullScreenIntent(
            PendingIntent.getActivity(
                service,
                ALARM_FIRING_NOTIFICATION_ID, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT
            ),
            true
        )
        notification.priority = NotificationCompat.PRIORITY_MAX

        clearNotification(service, instance)
        service.startForeground(ALARM_FIRING_NOTIFICATION_ID, notification.build())
    }

    @JvmStatic
    @Synchronized
    fun clearNotification(context: Context, instance: AlarmInstance) {
//        LogUtils.v("Clearing notifications for alarm instance: " + instance.mId)
        val nm: NotificationManagerCompat = NotificationManagerCompat.from(context)
        val id = instance.hashCode()
        nm.cancel(id)
        updateUpcomingAlarmGroupNotification(context, id, null)
        updateMissedAlarmGroupNotification(context, id, null)
    }

    /**
     * Updates the notification for an existing alarm. Use if the label has changed.
     */
    @JvmStatic
    fun updateNotification(context: Context, instance: AlarmInstance) {
        when (instance.mAlarmState) {
            InstancesColumns.LOW_NOTIFICATION_STATE -> {
                showLowPriorityNotification(context, instance)
            }

            InstancesColumns.HIGH_NOTIFICATION_STATE -> {
                showHighPriorityNotification(context, instance)
            }

            InstancesColumns.SNOOZE_STATE -> showSnoozeNotification(context, instance)
            InstancesColumns.MISSED_STATE -> showMissedNotification(context, instance)
            else -> {} //LogUtils.d("No notification to update")
        }
    }

    @JvmStatic
    fun createViewAlarmIntent(context: Context?, instance: AlarmInstance): Intent {
        val alarmId = instance.mAlarmId ?: -1
        return Alarm.createIntent(context, AlarmActivity::class.java, alarmId)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    /**
     * Alarm notifications are sorted chronologically. Missed alarms are sorted chronologically
     * **after** all upcoming/snoozed alarms by including the "MISSED" prefix on the
     * sort key.
     *
     * @param instance the alarm instance for which the notification is generated
     * @return the sort key that specifies the order of this alarm notification
     */
    private fun createSortKey(instance: AlarmInstance): String {
        val timeKey = SORT_KEY_FORMAT.format(instance.alarmTime.time)
        val missedAlarm = instance.mAlarmState == InstancesColumns.MISSED_STATE
        return if (missedAlarm) "MISSED $timeKey" else timeKey
    }
}