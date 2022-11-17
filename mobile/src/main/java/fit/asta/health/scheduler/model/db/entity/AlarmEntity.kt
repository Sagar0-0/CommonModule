package fit.asta.health.scheduler.model.db.entity


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.AlarmBroadcastReceiver
import fit.asta.health.scheduler.model.net.scheduler.*
import fit.asta.health.scheduler.util.Constants
import fit.asta.health.scheduler.util.DayUtil
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*

@Entity(tableName = "ALARM_TABLE")
@Parcelize
data class AlarmEntity(
    @ColumnInfo(name = "idFromServer")
    @SerializedName("id")
    var idFromServer: String,
    @ColumnInfo(name = "important")
    @SerializedName("imp")
    var important: Boolean,
    @ColumnInfo(name = "info")
    @SerializedName("info")
    var info: Info,
    @ColumnInfo(name = "interval")
    @SerializedName("ivl")
    var interval: Ivl,
    @ColumnInfo(name = "meta")
    @SerializedName("meta")
    var meta: Meta,
    @ColumnInfo(name = "mode")
    @SerializedName("mode")
    var mode: String,
    @ColumnInfo(name = "status")
    @SerializedName("sts")
    var status: Boolean,
    @ColumnInfo(name = "time")
    @SerializedName("time")
    var time: Time,
    @ColumnInfo(name = "tone")
    @SerializedName("tone")
    var tone: Tone,
    @ColumnInfo(name = "userId")
    @SerializedName("uid")
    var userId: String,
    @ColumnInfo(name = "vibration")
    @SerializedName("vib")
    var vibration: Vib,
    @ColumnInfo(name = "week")
    @SerializedName("wk")
    var week: Wk,
    @PrimaryKey(autoGenerate = true)
    @SerializedName("almId")
    val alarmId: Int = 0
) : Serializable, Parcelable {

    private fun schedulerPreNotification(
        context: Context,
        isInterval: Boolean,
        interval: Stat?,
        id: Int
    ) {
        val preNotificationManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val preNotificationIntent = Intent(context, AlarmBroadcastReceiver::class.java)
        val bundle = Bundle()
        val hour = if (isInterval) {
            if (interval!!.midDay) {
                interval.hours.toInt() + 12
            } else {
                interval.hours.toInt()
            }
        } else {
            if (this.time.midDay) {
                this.time.hours.toInt() + 12
            } else {
                this.time.hours.toInt()
            }
        }
        val millie = if (isInterval) {
            (hour * 60 * 60000) + (interval!!.minutes.toInt() * 60000) - (this.interval.advancedReminder.time * 60000)
        } else {
            (hour * 60 * 60000) + (this.time.minutes.toInt() * 60000) - (this.interval.advancedReminder.time * 60000)
        }

        val millieToHour = ((millie / (1000 * 60 * 60)) % 24)
        val millieToMinute = ((millie / (1000 * 60)) % 60)
        val preNotificationAlarmEntity = this.copy(
            time = Time(
                hours = (if (millieToHour >= 12) millieToHour - 12 else millieToHour).toString(),
                minutes = millieToMinute.toString(),
                midDay = millieToHour >= 12
            )
        )
        Toast.makeText(
            context,
            ": ${((millie / (1000 * 60 * 60)) % 24) >= 12}",
            Toast.LENGTH_SHORT
        ).show()
        bundle.putSerializable(Constants.ARG_PRE_NOTIFICATION_OBJET, preNotificationAlarmEntity)
        bundle.putInt("id", (id + 1))
        preNotificationIntent.putExtra(Constants.BUNDLE_PRE_NOTIFICATION_OBJECT, bundle)
        preNotificationIntent.putExtra("id", (id + 1))
        val alarmPendingIntent =
            PendingIntent.getBroadcast(
                context,
                (id + 1),
                preNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        if (preNotificationAlarmEntity.time.midDay) {
            calendar[Calendar.HOUR_OF_DAY] = preNotificationAlarmEntity.time.hours.toInt() + 12
        } else {
            calendar[Calendar.HOUR_OF_DAY] = preNotificationAlarmEntity.time.hours.toInt()
        }
        calendar[Calendar.MINUTE] = preNotificationAlarmEntity.time.minutes.toInt()
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        val toastText =
            "Pre Notification at ${preNotificationAlarmEntity.time.hours}:${preNotificationAlarmEntity.time.minutes}"

        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
        preNotificationManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmPendingIntent
        )
        preNotificationAlarmEntity.status = true

    }

    fun schedulerPostNotification(
        context: Context,
        isInterval: Boolean,
        interval: Stat?,
        id: Int
    ) {
        val postNotificationManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val postNotificationIntent = Intent(context, AlarmBroadcastReceiver::class.java)
        val bundle = Bundle()
        val hour = if (isInterval) {
            if (interval!!.midDay) {
                interval.hours.toInt() + 12
            } else {
                interval.hours.toInt()
            }
        } else {
            if (this.time.midDay) {
                this.time.hours.toInt() + 12
            } else {
                this.time.hours.toInt()
            }
        }
        val millie = if (isInterval) {
            (hour * 60 * 60000) + (interval!!.minutes.toInt() * 60000) + (this.interval.duration * 60000)
        } else {
            (hour * 60 * 60000) + (this.time.minutes.toInt() * 60000) + (this.interval.duration * 60000)
        }

        val millieToHour = ((millie / (1000 * 60 * 60)) % 24)
        val millieToMinute = ((millie / (1000 * 60)) % 60)
        val postNotificationAlarmEntity = this.copy(
            time = Time(
                hours = (if (millieToHour >= 12) millieToHour - 12 else millieToHour).toString(),
                minutes = millieToMinute.toString(),
                midDay = millieToHour >= 12
            )
        )
        Toast.makeText(
            context,
            ": ${((millie / (1000 * 60 * 60)) % 24) >= 12}",
            Toast.LENGTH_SHORT
        ).show()
        bundle.putSerializable(Constants.ARG_POST_NOTIFICATION_OBJET, postNotificationAlarmEntity)
        bundle.putInt("id", (id + 4))
        postNotificationIntent.putExtra(Constants.BUNDLE_POST_NOTIFICATION_OBJECT, bundle)
        val alarmPendingIntent =
            PendingIntent.getBroadcast(
                context,
                (id + 4),
                postNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        if (postNotificationAlarmEntity.time.midDay) {
            calendar[Calendar.HOUR_OF_DAY] = postNotificationAlarmEntity.time.hours.toInt() + 12
        } else {
            calendar[Calendar.HOUR_OF_DAY] = postNotificationAlarmEntity.time.hours.toInt()
        }
        calendar[Calendar.MINUTE] = postNotificationAlarmEntity.time.minutes.toInt()
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        val toastText =
            "Post Notification at ${postNotificationAlarmEntity.time.hours}:${postNotificationAlarmEntity.time.minutes}"

        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
        postNotificationManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmPendingIntent
        )
        postNotificationAlarmEntity.status = true

    }

    fun schedule(context: Context) {
        var isAlarmHasAlreadyPassed = false
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Constants.ARG_ALARM_OBJET, this)
        intent.putExtra(Constants.BUNDLE_ALARM_OBJECT, bundle)
        val alarmPendingIntent =
            PendingIntent.getBroadcast(
                context, this.alarmId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        if (this.time.midDay) {
            calendar[Calendar.HOUR_OF_DAY] = this.time.hours.toInt() + 12
        } else {
            calendar[Calendar.HOUR_OF_DAY] = this.time.hours.toInt()
        }
        calendar[Calendar.MINUTE] = this.time.minutes.toInt()
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        // if alarm time has already passed, increment day by 1
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
            isAlarmHasAlreadyPassed = true
        }

        Log.d("TAGTAGTAG", "schedule: $isAlarmHasAlreadyPassed")

        if (!week.recurring) {
            var toastText: String? = null
            try {
                toastText = java.lang.String.format(
                    Locale.getDefault(),
                    "One Time Alarm %s scheduled for %s at %02d:%02d",
                    this.info.name,
                    DayUtil.toDay(
                        calendar[Calendar.DAY_OF_WEEK]
                    ),
                    this.time.hours.toInt(),
                    this.time.minutes.toInt()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent
            )
        } else {
            val toastText = java.lang.String.format(
                Locale.getDefault(),
                "Recurring Alarm %s scheduled for %s at %02d:%02d",
                this.info.name,
                Constants.getRecurringDaysText(this.week),
                this.time.hours.toInt(), this.time.minutes.toInt()
            )
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            val runDaily = (24 * 60 * 60 * 1000).toLong()
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                runDaily,
                alarmPendingIntent
            )
        }

        if (this.interval.status) {
            if (this.interval.isVariantInterval) {
                if (this.interval.staticIntervals.isNotEmpty()) {
                    this.interval.staticIntervals.forEach {
                        cancelInterval(context, it)
                    }
                }
                scheduleInterval(
                    context,
                    this.interval.variantIntervals,
                    isAlarmHasAlreadyPassed
                )
            } else {
                if (this.interval.variantIntervals.isNotEmpty()) {
                    this.interval.variantIntervals.forEach {
                        cancelInterval(context, it)
                    }
                }
                scheduleInterval(
                    context,
                    this.interval.staticIntervals,
                    isAlarmHasAlreadyPassed
                )
            }
        } else {
            if (this.interval.isVariantInterval) {
                if (this.interval.variantIntervals.isNotEmpty()) {
                    this.interval.variantIntervals.forEach {
                        cancelInterval(context, it)
                    }
                }
            } else {
                if (this.interval.staticIntervals.isNotEmpty()) {
                    this.interval.staticIntervals.forEach {
                        cancelInterval(context, it)
                    }
                }
            }
        }

        if (this.interval.advancedReminder.status) {
            schedulerPreNotification(context, false, null, this.alarmId)
        }
//        if (this.alarmInterval!!.isRemainderAtTheEnd) {
//            schedulerPostNotification(context, false, null, this.alarmId)
//        }
        this.status = true
    }

    fun scheduleInterval(
        context: Context,
        listOfVariantIntervals: List<Stat>?,
        isAlarmHasAlreadyPassed: Boolean
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val bundle = Bundle()
        listOfVariantIntervals?.forEach { variantInterval ->
            bundle.putSerializable(
                Constants.ARG_VARIANT_INTERVAL_ALARM_OBJECT, this.copy(
                    time = Time(
                        hours = variantInterval.hours,
                        minutes = variantInterval.minutes,
                        midDay = variantInterval.midDay
                    )
                )
            )
            bundle.putParcelable(Constants.ARG_VARIANT_INTERVAL_OBJECT, variantInterval)
            intent.putExtra(Constants.BUNDLE_VARIANT_INTERVAL_OBJECT, bundle)
            val alarmPendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    variantInterval.id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            if (variantInterval.midDay) {
                calendar[Calendar.HOUR_OF_DAY] = variantInterval.hours.toInt() + 12
            } else {
                calendar[Calendar.HOUR_OF_DAY] = variantInterval.hours.toInt()
            }
            calendar[Calendar.MINUTE] = variantInterval.minutes.toInt()
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0


            // if alarm time has already passed, increment day by 1
            if (isAlarmHasAlreadyPassed) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
            }

            if (!this.week.recurring) {
                var toastText: String? = null
                try {
                    toastText = java.lang.String.format(
                        Locale.getDefault(),
                        "One Time Alarm %s scheduled for %s at %02d:%02d",
                        variantInterval.name,
                        DayUtil.toDay(
                            calendar[Calendar.DAY_OF_WEEK]
                        ),
                        variantInterval.hours.toInt(),
                        variantInterval.minutes.toInt()
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    alarmPendingIntent
                )
            } else {
                val toastText = java.lang.String.format(
                    Locale.getDefault(),
                    "Recurring Alarm %s scheduled for %s at %02d:%02d",
                    variantInterval.name,
                    Constants.getRecurringDaysText(this.week),
                    variantInterval.hours.toInt(), variantInterval.minutes.toInt()
                )
                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                val runDaily = (24 * 60 * 60 * 1000).toLong()
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    runDaily,
                    alarmPendingIntent
                )
            }
            if (this.interval.advancedReminder.status) {
                schedulerPreNotification(
                    context,
                    true,
                    variantInterval,
                    variantInterval.id
                )
            }
//            if (this.alarmInterval!!.isRemainderAtTheEnd) {
//                schedulerPostNotification(
//                    context,
//                    true,
//                    variantInterval,
//                    variantInterval.alarmTimeId
//                )
//            }
        }

//        this.alarmStatus = true
    }

    fun cancelAlarm(context: Context, id: Int, cancelAllIntervals: Boolean) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent =
            PendingIntent.getBroadcast(context, id, intent, 0)
        alarmManager.cancel(alarmPendingIntent)
        this.status = false
        @SuppressLint("DefaultLocale") val toastText =
            String.format(
                "Alarm cancelled for %02d:%02d",
                this.time.hours.toInt(),
                this.time.minutes.toInt()
            )
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        if (cancelAllIntervals) {
            if (this.interval.isVariantInterval) {
                this.interval.variantIntervals.forEach {
                    cancelNotification(context, it.id)
                    cancelInterval(context, it)
                }
            }
            if (this.interval.staticIntervals.isNotEmpty()) {
                this.interval.staticIntervals.forEach {
                    cancelNotification(context, it.id)
                    cancelInterval(context, it)
                }
            }
        }
        Log.i("cancel", toastText)
    }

    fun cancelInterval(context: Context, variantInterval: Stat) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent =
            PendingIntent.getBroadcast(context, variantInterval.id, intent, 0)
        alarmManager.cancel(alarmPendingIntent)
//        this.alarmStatus = false
        @SuppressLint("DefaultLocale") val toastText =
            String.format(
                "Alarm cancelled for %02d:%02d",
                variantInterval.hours.toInt(),
                variantInterval.hours.toInt()
            )
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        Log.i("cancel", toastText)
    }

    fun cancelNotification(context: Context, id: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent =
            PendingIntent.getBroadcast(context, id + 4, intent, 0)
        alarmManager.cancel(alarmPendingIntent)
//        this.alarmStatus = false
        val toastText = "Notification Canceled"
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        Log.i("cancel", toastText)
    }
}