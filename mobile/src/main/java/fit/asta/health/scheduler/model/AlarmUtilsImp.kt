package fit.asta.health.scheduler.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import fit.asta.health.scheduler.AlarmBroadcastReceiver
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.model.net.scheduler.Time
import fit.asta.health.scheduler.util.Constants
import java.util.Calendar


class AlarmUtilsImp(
    private val alarmManager: AlarmManager,
    private val context: Context,
    private val calendar: Calendar
) : AlarmUtils {
    val today: Int = calendar.get(Calendar.DAY_OF_WEEK)

    override fun scheduleAlarm(alarmEntity: AlarmEntity) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Constants.ARG_ALARM_OBJET, alarmEntity)
        intent.putExtra(Constants.BUNDLE_ALARM_OBJECT, bundle)

        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = alarmEntity.time.hours.toInt()
        calendar[Calendar.MINUTE] = alarmEntity.time.minutes.toInt()
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        setAllAlarm(
            alarmEntity = alarmEntity,
            intent = intent,
            today = alarmEntity.alarmId,
            sunId = (alarmEntity.alarmId + Constants.sun),
            monId = (alarmEntity.alarmId + Constants.mon),
            tueId = (alarmEntity.alarmId + Constants.tue),
            wedId = (alarmEntity.alarmId + Constants.wed),
            thuId = (alarmEntity.alarmId + Constants.thu),
            friId = (alarmEntity.alarmId + Constants.fri),
            satId = (alarmEntity.alarmId + Constants.sat)
        )
        if (alarmEntity.interval.advancedReminder.status) {
            schedulerAlarmPreNotification(alarmEntity, false, null, alarmEntity.alarmId)
            if (alarmEntity.interval.status) {
                if (alarmEntity.interval.isVariantInterval) {
                    alarmEntity.interval.variantIntervals.forEach {
                        schedulerAlarmPreNotification(alarmEntity, true, it, it.id)
                    }
                } else {
                    alarmEntity.interval.staticIntervals.forEach {
                        schedulerAlarmPreNotification(alarmEntity, true, it, it.id)
                    }
                }
            }
        }
        if (alarmEntity.interval.status) {
            if (alarmEntity.interval.isVariantInterval) scheduleAlarmInterval(
                alarmEntity,
                alarmEntity.interval.variantIntervals
            )
            else scheduleAlarmInterval(alarmEntity, alarmEntity.interval.staticIntervals)
        }
    }

    override fun schedulerAlarmPostNotification(
        alarmEntity: AlarmEntity, isInterval: Boolean, interval: Stat?, id: Int
    ) {
        val postNotificationIntent = Intent(context, AlarmBroadcastReceiver::class.java)
        val bundle = Bundle()
        val importance: Boolean = alarmEntity.important
        val hour: Int
        val min: Int
        if (isInterval) {
            hour = interval!!.hours.toInt()
            min = interval.minutes.toInt() + alarmEntity.interval.duration
        } else {
            hour = alarmEntity.time.hours.toInt()
            min = alarmEntity.time.minutes.toInt() + alarmEntity.interval.duration
        }
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = min
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        val postNotificationAlarmEntity = alarmEntity.copy(
            time = Time(
                hours = calendar.get(Calendar.HOUR_OF_DAY).toString(),
                minutes = calendar.get(Calendar.MINUTE).toString(),
                midDay = calendar.get(Calendar.HOUR_OF_DAY) >= 12
            )
        )
        Log.d("TAGTAG", "schedulerAlarmPostNotification: $postNotificationAlarmEntity")
        bundle.putSerializable(Constants.ARG_POST_NOTIFICATION_OBJET, postNotificationAlarmEntity)
        bundle.putInt("id", (id + 4))
        postNotificationIntent.putExtra(Constants.BUNDLE_POST_NOTIFICATION_OBJECT, bundle)

        setTodayAlarm(postNotificationIntent, (id + 4), importance)
    }

    private fun schedulerAlarmPreNotification(
        alarmEntity: AlarmEntity, isInterval: Boolean, interval: Stat?, iD: Int
    ) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val bundle = Bundle()
        calendar.timeInMillis = System.currentTimeMillis()
        val hour: Int
        val min: Int
        if (isInterval) {
            hour = interval!!.hours.toInt()
            min = interval.minutes.toInt() - alarmEntity.interval.advancedReminder.time
        } else {
            hour = alarmEntity.time.hours.toInt()
            min = alarmEntity.time.minutes.toInt() - alarmEntity.interval.advancedReminder.time
        }
        if (min < 0) {
            calendar[Calendar.HOUR_OF_DAY] = hour - 1
            calendar[Calendar.MINUTE] = (60 + min)     // 60 +(-min)
        } else {
            calendar[Calendar.HOUR_OF_DAY] = hour
            calendar[Calendar.MINUTE] = min
        }
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        val preNotificationAlarmEntity = alarmEntity.copy(
            time = Time(
                hours = calendar[Calendar.HOUR_OF_DAY].toString(),
                minutes = calendar[Calendar.MINUTE].toString(),
                midDay = calendar[Calendar.HOUR_OF_DAY] >= 12
            )
        )
        Log.d("TAGTAG", "schedulerAlarmPreNotification: $preNotificationAlarmEntity")
        bundle.putSerializable(Constants.ARG_PRE_NOTIFICATION_OBJET, preNotificationAlarmEntity)
        bundle.putInt("id", (iD + 1))
        intent.putExtra(Constants.BUNDLE_PRE_NOTIFICATION_OBJECT, bundle)
        intent.putExtra("id", (iD + 1))

        setAllAlarm(
            alarmEntity = alarmEntity,
            intent = intent,
            today = iD + 1,
            sunId = (iD + Constants.sun + 1),
            monId = (iD + Constants.mon + 1),
            tueId = (iD + Constants.tue + 1),
            wedId = (iD + Constants.wed + 1),
            thuId = (iD + Constants.thu + 1),
            friId = (iD + Constants.fri + 1),
            satId = (iD + Constants.sat + 1)
        )
    }

    private fun scheduleAlarmInterval(
        alarmEntity: AlarmEntity, listOfVariantIntervals: List<Stat>?
    ) {
        listOfVariantIntervals?.forEach { variantInterval ->

            val intent = Intent(context, AlarmBroadcastReceiver::class.java)
            val bundle = Bundle()
            bundle.putSerializable(
                Constants.ARG_VARIANT_INTERVAL_ALARM_OBJECT, alarmEntity.copy(
                    time = Time(
                        hours = variantInterval.hours,
                        minutes = variantInterval.minutes,
                        midDay = variantInterval.midDay
                    )
                )
            )
            Log.d("TAGTAG", "scheduleAlarmInterval: $alarmEntity")
            bundle.putParcelable(Constants.ARG_VARIANT_INTERVAL_OBJECT, variantInterval)
            intent.putExtra(Constants.BUNDLE_VARIANT_INTERVAL_OBJECT, bundle)

            calendar[Calendar.HOUR_OF_DAY] = variantInterval.hours.toInt()
            calendar[Calendar.MINUTE] = variantInterval.minutes.toInt()
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            setAllAlarm(
                alarmEntity = alarmEntity,
                intent = intent,
                today = variantInterval.id,
                sunId = (variantInterval.id + Constants.sun),
                monId = (variantInterval.id + Constants.mon),
                tueId = (variantInterval.id + Constants.tue),
                wedId = (variantInterval.id + Constants.wed),
                thuId = (variantInterval.id + Constants.thu),
                friId = (variantInterval.id + Constants.fri),
                satId = (variantInterval.id + Constants.sat)
            )
        }
    }

    override fun cancelScheduleAlarm(
        alarmEntity: AlarmEntity, cancelAllIntervals: Boolean
    ) {

        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        removeAllAlarm(
            alarmEntity = alarmEntity,
            intent = intent,
            today = alarmEntity.alarmId,
            sunId = (alarmEntity.alarmId + Constants.sun),
            monId = (alarmEntity.alarmId + Constants.mon),
            tueId = (alarmEntity.alarmId + Constants.tue),
            wedId = (alarmEntity.alarmId + Constants.wed),
            thuId = (alarmEntity.alarmId + Constants.thu),
            friId = (alarmEntity.alarmId + Constants.fri),
            satId = (alarmEntity.alarmId + Constants.sat)
        )

        if (alarmEntity.interval.advancedReminder.status) {
            removeAllAlarm(
                alarmEntity = alarmEntity,
                intent = intent,
                today = alarmEntity.alarmId + 1,
                sunId = (alarmEntity.alarmId + Constants.sun + 1),
                monId = (alarmEntity.alarmId + Constants.mon + 1),
                tueId = (alarmEntity.alarmId + Constants.tue + 1),
                wedId = (alarmEntity.alarmId + Constants.wed + 1),
                thuId = (alarmEntity.alarmId + Constants.thu + 1),
                friId = (alarmEntity.alarmId + Constants.fri + 1),
                satId = (alarmEntity.alarmId + Constants.sat + 1)
            )
        }
        if (alarmEntity.interval.isRemainderAtTheEnd) {
            removeAlarms(alarmEntity.alarmId + 4, intent)
        }
        if (cancelAllIntervals && alarmEntity.interval.status) {
            if (alarmEntity.interval.isVariantInterval) {
                alarmEntity.interval.variantIntervals.forEach { variantInterval ->
                    removeAllAlarm(
                        alarmEntity = alarmEntity,
                        intent = intent,
                        today = variantInterval.id,
                        sunId = (variantInterval.id + Constants.sun),
                        monId = (variantInterval.id + Constants.mon),
                        tueId = (variantInterval.id + Constants.tue),
                        wedId = (variantInterval.id + Constants.wed),
                        thuId = (variantInterval.id + Constants.thu),
                        friId = (variantInterval.id + Constants.fri),
                        satId = (variantInterval.id + Constants.sat)
                    )
                    removeAllAlarm(
                        alarmEntity = alarmEntity,
                        intent = intent,
                        today = variantInterval.id + 1,
                        sunId = (variantInterval.id + Constants.sun + 1),
                        monId = (variantInterval.id + Constants.mon + 1),
                        tueId = (variantInterval.id + Constants.tue + 1),
                        wedId = (variantInterval.id + Constants.wed + 1),
                        thuId = (variantInterval.id + Constants.thu + 1),
                        friId = (variantInterval.id + Constants.fri + 1),
                        satId = (variantInterval.id + Constants.sat + 1)
                    )
                }
            } else {
                alarmEntity.interval.staticIntervals.forEach { variantInterval ->
                    removeAllAlarm(
                        alarmEntity = alarmEntity,
                        intent = intent,
                        today = variantInterval.id,
                        sunId = (variantInterval.id + Constants.sun),
                        monId = (variantInterval.id + Constants.mon),
                        tueId = (variantInterval.id + Constants.tue),
                        wedId = (variantInterval.id + Constants.wed),
                        thuId = (variantInterval.id + Constants.thu),
                        friId = (variantInterval.id + Constants.fri),
                        satId = (variantInterval.id + Constants.sat)
                    )
                    removeAllAlarm(
                        alarmEntity = alarmEntity,
                        intent = intent,
                        today = variantInterval.id + 1,
                        sunId = (variantInterval.id + Constants.sun + 1),
                        monId = (variantInterval.id + Constants.mon + 1),
                        tueId = (variantInterval.id + Constants.tue + 1),
                        wedId = (variantInterval.id + Constants.wed + 1),
                        thuId = (variantInterval.id + Constants.thu + 1),
                        friId = (variantInterval.id + Constants.fri + 1),
                        satId = (variantInterval.id + Constants.sat + 1)
                    )
                }
            }

        }
    }

    override fun snooze(alarmEntity: AlarmEntity) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Constants.ARG_ALARM_OBJET, alarmEntity)
        intent.putExtra(Constants.BUNDLE_ALARM_OBJECT, bundle)
        val importance = alarmEntity.important
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        val id = alarmEntity.alarmId + 999
        val snoozeTime = alarmEntity.interval.snoozeTime
        val min = alarmEntity.time.minutes.toInt() + if (snoozeTime < 5) 5 else snoozeTime

        if (min >= 60) {
            if (alarmEntity.time.hours.toInt() == 24) {
                calendar[Calendar.HOUR_OF_DAY] = 0
                calendar[Calendar.MINUTE] = (min % 60)
                setTodayAlarm(intent, id, importance, day = 1)
            } else {
                calendar[Calendar.HOUR_OF_DAY] = alarmEntity.time.hours.toInt()
                calendar[Calendar.MINUTE] = min
                setTodayAlarm(intent, id, importance)
            }
        }
    }

    private fun setAllAlarm(
        alarmEntity: AlarmEntity,
        intent: Intent,
        today: Int,
        sunId: Int,
        monId: Int,
        tueId: Int,
        wedId: Int,
        thuId: Int,
        friId: Int,
        satId: Int
    ) {
        val importance = alarmEntity.important
        if (!alarmEntity.week.recurring) {
            setTodayAlarm(intent, today, importance)
        } else {
            if (alarmEntity.week.sunday) {
                when (today) {
                    1 -> {
                        setTodayAlarm(intent, sunId, importance)
                    }

                    else -> { // day>1
                        val day = 7
                        setTodayAlarm(intent, sunId, importance, day)
                    }
                }
            }
            if (alarmEntity.week.monday) {
                when (today) {
                    1 -> {
                        val day = 2 - today
                        setTodayAlarm(intent, monId, importance, day)
                    }

                    2 -> {
                        setTodayAlarm(intent, monId, importance)
                    }

                    in 3..7 -> {
                        val day = 7 - (today - 2)
                        setTodayAlarm(intent, monId, importance, day)
                    }
                }
            }
            if (alarmEntity.week.tuesday) {
                when (today) {
                    in 1..2 -> {
                        val day = 3 - today
                        setTodayAlarm(intent, tueId, importance, day)
                    }

                    3 -> {
                        setTodayAlarm(intent, tueId, importance)
                    }

                    in 4..7 -> {
                        val day = 7 - (today - 3)
                        setTodayAlarm(intent, tueId, importance, day)
                    }
                }
            }
            if (alarmEntity.week.wednesday) {
                when (today) {
                    in 1..3 -> {
                        val day = 4 - today
                        setTodayAlarm(intent, wedId, importance, day)
                    }

                    4 -> {
                        setTodayAlarm(intent, wedId, importance)
                    }

                    in 5..7 -> {
                        val day = 7 - (today - 4)
                        setTodayAlarm(intent, wedId, importance, day)
                    }
                }
            }
            if (alarmEntity.week.thursday) {
                when (today) {
                    in 1..4 -> {
                        val day = 5 - today
                        setTodayAlarm(intent, thuId, importance, day)
                    }

                    5 -> {
                        setTodayAlarm(intent, thuId, importance)
                    }

                    in 6..7 -> {
                        val day = 7 - (today - 5)
                        setTodayAlarm(intent, thuId, importance, day)
                    }
                }
            }
            if (alarmEntity.week.friday) {
                when (today) {
                    in 1..5 -> {
                        val day = 6 - today
                        setTodayAlarm(intent, friId, importance, day)
                    }

                    6 -> {
                        setTodayAlarm(intent, friId, importance)
                    }

                    7 -> {
                        val day = 7 - (today - 6)
                        setTodayAlarm(intent, friId, importance, day)
                    }
                }
            }
            if (alarmEntity.week.saturday) {
                when (today) {
                    in 1..6 -> {
                        val day = 7 - today
                        setTodayAlarm(intent, satId, importance, day)
                    }

                    7 -> {
                        setTodayAlarm(intent, satId, importance)
                    }
                }
            }
        }
    }

    private fun setTodayAlarm(intent: Intent, id: Int, importance: Boolean, day: Int? = null) {
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH).plus(day ?: 0))
        Log.d(
            "TAG",
            "setTodayAlarm: time:${calendar.time} date:${calendar.get(Calendar.DAY_OF_MONTH)}"
        )
        if (importance) {
            AlarmManagerCompat.setAlarmClock(
                alarmManager,
                calendar.timeInMillis,
                alarmPendingIntent,
                alarmPendingIntent,
            )
        } else {
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager, AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmPendingIntent
            )
        }
    }

    private fun removeAllAlarm(
        alarmEntity: AlarmEntity,
        intent: Intent,
        today: Int,
        sunId: Int,
        monId: Int,
        tueId: Int,
        wedId: Int,
        thuId: Int,
        friId: Int,
        satId: Int
    ) {
        if (!alarmEntity.week.recurring) {
            removeAlarms(today, intent)
        } else {
            if (alarmEntity.week.sunday) removeAlarms(sunId, intent)
            if (alarmEntity.week.monday) removeAlarms(monId, intent)
            if (alarmEntity.week.tuesday) removeAlarms(tueId, intent)
            if (alarmEntity.week.wednesday) removeAlarms(wedId, intent)
            if (alarmEntity.week.thursday) removeAlarms(thuId, intent)
            if (alarmEntity.week.friday) removeAlarms(friId, intent)
            if (alarmEntity.week.saturday) removeAlarms(satId, intent)
        }
    }

    private fun removeAlarms(
        id: Int, intent: Intent
    ) {
        val alarmPendingIntent =
            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(alarmPendingIntent)
    }

}