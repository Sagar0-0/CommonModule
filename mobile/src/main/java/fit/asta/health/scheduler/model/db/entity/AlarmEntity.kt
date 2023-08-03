package fit.asta.health.scheduler.model.db.entity


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.model.net.scheduler.*
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
    var alarmId: Int = 0,
    @ColumnInfo(name = "skipDate")
    var skipDate:Int=-1
) : Serializable, Parcelable {
//
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    fun scheduleAlarm(context: Context) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val intentActivity = Intent(context, MainActivity::class.java)
//        val bundle = Bundle()
//        bundle.putSerializable(Constants.ARG_ALARM_OBJET, this)
//        intent.putExtra(Constants.BUNDLE_ALARM_OBJECT, bundle)
//
//
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        calendar[Calendar.HOUR_OF_DAY] = this.time.hours.toInt()
//        calendar[Calendar.MINUTE] = this.time.minutes.toInt()
//        calendar[Calendar.SECOND] = 0
//        calendar[Calendar.MILLISECOND] = 0
//        val today: Int = calendar.get(Calendar.DAY_OF_WEEK)
//
//        if (!this.week.recurring) {
//            val alarmPendingIntent =
//                PendingIntent.getBroadcast(
//                    context, this.alarmId, intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//                )
////            alarmManager.setExact(
////                AlarmManager.RTC_WAKEUP,
////                calendar.timeInMillis,
////                alarmPendingIntent
////            )
//            AlarmManagerCompat.setAlarmClock(
//                alarmManager,
//                calendar.timeInMillis,
//                alarmPendingIntent,
//                alarmPendingIntent,
//            )
////            AlarmManagerCompat.setExactAndAllowWhileIdle(
////                alarmManager,
////                AlarmManager.RTC_WAKEUP,
////                calendar.timeInMillis,
////                alarmPendingIntent
////            )
//        } else {
//            if (this.week.sunday) {
//                when (today) {
//                    1 -> {
//                        val id = (this.alarmId + Constants.sun)
//                        setTodayAlarm(calendar, alarmManager, context, intent, id)
//                    }
//                    else -> { // day>1
//                        val day = 7
//                        val id = (this.alarmId + Constants.sun)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                }
//            }
//            if (this.week.monday) {
//                when (today) {
//                    1 -> {
//                        val day = 2 - today
//                        val id = (this.alarmId + Constants.mon)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                    2 -> {
//                        val id = (this.alarmId + Constants.mon)
//                        setTodayAlarm(calendar, alarmManager, context, intent, id)
//                    }
//                    in 3..7 -> {
//                        val day = 7 - (today - 2)
//                        val id = (this.alarmId + Constants.mon)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                }
//            }
//            if (this.week.tuesday) {
//                when (today) {
//                    in 1..2 -> {
//                        val day = 3 - today
//                        val id = (this.alarmId + Constants.tue)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                    3 -> {
//                        val id = (this.alarmId + Constants.tue)
//                        setTodayAlarm(calendar, alarmManager, context, intent, id)
//                    }
//                    in 4..7 -> {
//                        val day = 7 - (today - 3)
//                        val id = (this.alarmId + Constants.tue)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                }
//            }
//            if (this.week.wednesday) {
//                when (today) {
//                    in 1..3 -> {
//                        val day = 4 - today
//                        val id = (this.alarmId + Constants.wed)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                    4 -> {
//                        val id = (this.alarmId + Constants.wed)
//                        setTodayAlarm(calendar, alarmManager, context, intent, id)
//                    }
//                    in 5..7 -> {
//                        val day = 7 - (today - 4)
//                        val id = (this.alarmId + Constants.wed)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                }
//            }
//            if (this.week.thursday) {
//                when (today) {
//                    in 1..4 -> {
//                        val day = 5 - today
//                        val id = (this.alarmId + Constants.thu)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                    5 -> {
//                        val id = (this.alarmId + Constants.thu)
//                        setTodayAlarm(calendar, alarmManager, context, intent, id)
//                    }
//                    in 6..7 -> {
//                        val day = 7 - (today - 5)
//                        val id = (this.alarmId + Constants.thu)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                }
//            }
//            if (this.week.friday) {
//                when (today) {
//                    in 1..5 -> {
//                        val day = 6 - today
//                        val id = (this.alarmId + Constants.fri)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                    6 -> {
//                        val id = (this.alarmId + Constants.fri)
//                        setTodayAlarm(calendar, alarmManager, context, intent, id)
//                    }
//                    7 -> {
//                        val day = 7 - (today - 6)
//                        val id = (this.alarmId + Constants.fri)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                }
//            }
//            if (this.week.saturday) {
//                when (today) {
//                    in 1..6 -> {
//                        val day = 7 - today
//                        val id = (this.alarmId + Constants.sat)
//                        setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                    }
//                    7 -> {
//                        val id = (this.alarmId + Constants.sat)
//                        setTodayAlarm(calendar, alarmManager, context, intent, id)
//                    }
//                }
//            }
//        }
//        if (this.interval.status){
//            if (this.interval.isVariantInterval) {
//                scheduleAlarmInterval(
//                    context,
//                    this.interval.variantIntervals
//                )
//            } else {
//                scheduleAlarmInterval(
//                    context,
//                    this.interval.staticIntervals
//                )
//            }
//        }
//        if (this.interval.advancedReminder.status) {
//            schedulerAlarmPreNotification(context, false, null, this.alarmId)
//        }
//        this.status = true
//    }
//
//    fun schedulerAlarmPostNotification(
//        context: Context,
//        isInterval: Boolean,
//        interval: Stat?,
//        id: Int
//    ) {
//        val postNotificationManager =
//            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val postNotificationIntent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val bundle = Bundle()
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        val hour: Int
//        val min: Int
//        if (isInterval) {
//            hour = interval!!.hours.toInt()
//            min = interval.minutes.toInt() + this.interval.duration
//        } else {
//            hour = this.time.hours.toInt()
//            min = this.time.minutes.toInt() + this.interval.duration
//        }
//        calendar[Calendar.HOUR_OF_DAY] = hour
//        calendar[Calendar.MINUTE] = min
//        calendar[Calendar.SECOND] = 0
//        calendar[Calendar.MILLISECOND] = 0
//
//        val postNotificationAlarmEntity = this.copy(
//            time = Time(
//                hours = calendar.get(Calendar.HOUR_OF_DAY).toString(),
//                minutes = calendar.get(Calendar.MINUTE).toString(),
//                midDay = calendar.get(Calendar.HOUR_OF_DAY) >= 12
//            )
//        )
//        Log.d("TAGTAG", "schedulerAlarmPostNotification: $postNotificationAlarmEntity")
//        bundle.putSerializable(Constants.ARG_POST_NOTIFICATION_OBJET, postNotificationAlarmEntity)
//        bundle.putInt("id", (id + 4))
//        postNotificationIntent.putExtra(Constants.BUNDLE_POST_NOTIFICATION_OBJECT, bundle)
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(
//                context,
//                (id + 4),
//                postNotificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//
//        val toastText =
//            "Post Notification at ${postNotificationAlarmEntity.time.hours}:${postNotificationAlarmEntity.time.minutes}"
//
//        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
//        postNotificationManager.setExact(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            alarmPendingIntent
//        )
//        postNotificationAlarmEntity.status = true
//
//    }
//
//    fun cancelPreNotification(context: Context, id: Int) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//
//        if (!this.week.recurring) {
//            val uid = id + 1
//            removeAlarm(context, uid, intent, alarmManager)
//        } else {
//            if (this.week.sunday) {
//                val uid = (id + Constants.sun + 1)
//                removeAlarm(context, uid, intent, alarmManager)
//            }
//            if (this.week.monday) {
//                val uid = (id + Constants.mon + 1)
//                removeAlarm(context, uid, intent, alarmManager)
//            }
//            if (this.week.tuesday) {
//                val uid = (id + Constants.tue + 1)
//                removeAlarm(context, uid, intent, alarmManager)
//            }
//            if (this.week.wednesday) {
//                val uid = (id + Constants.wed + 1)
//                removeAlarm(context, uid, intent, alarmManager)
//            }
//            if (this.week.thursday) {
//                val uid = (id + Constants.thu + 1)
//                removeAlarm(context, uid, intent, alarmManager)
//            }
//            if (this.week.friday) {
//                val uid = (id + Constants.fri + 1)
//                removeAlarm(context, uid, intent, alarmManager)
//            }
//            if (this.week.saturday) {
//                val uid = (id + Constants.sat + 1)
//                removeAlarm(context, uid, intent, alarmManager)
//            }
//        }
//    }
//
//    private fun schedulerAlarmPreNotification(
//        context: Context,
//        isInterval: Boolean,
//        interval: Stat?,
//        id: Int
//    ) {
//        val preNotificationManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val preNotificationIntent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val bundle = Bundle()
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        val hour: Int
//        val min: Int
//        if (isInterval) {
//            hour = interval!!.hours.toInt()
//            min = interval.minutes.toInt() - this.interval.advancedReminder.time
//        } else {
//            hour = this.time.hours.toInt()
//            min = this.time.minutes.toInt() - this.interval.advancedReminder.time
//        }
//        if (min < 0) {
//            calendar[Calendar.HOUR_OF_DAY] = hour - 1
//            calendar[Calendar.MINUTE] = (60 + min)     // 60 +(-min)
//        } else {
//            calendar[Calendar.HOUR_OF_DAY] = hour
//            calendar[Calendar.MINUTE] = min
//        }
//        calendar[Calendar.SECOND] = 0
//        calendar[Calendar.MILLISECOND] = 0
//        val today: Int = calendar.get(Calendar.DAY_OF_WEEK)
//
//        val preNotificationAlarmEntity = this.copy(
//            time = Time(
//                hours = calendar[Calendar.HOUR_OF_DAY].toString(),
//                minutes = calendar[Calendar.MINUTE].toString(),
//                midDay = calendar[Calendar.HOUR_OF_DAY] >= 12
//            )
//        )
//        Log.d("TAGTAG", "schedulerAlarmPreNotification: $preNotificationAlarmEntity")
//        bundle.putSerializable(Constants.ARG_PRE_NOTIFICATION_OBJET, preNotificationAlarmEntity)
//        bundle.putInt("id", (id + 1))
//        preNotificationIntent.putExtra(Constants.BUNDLE_PRE_NOTIFICATION_OBJECT, bundle)
//        preNotificationIntent.putExtra("id", (id + 1))
//
//        if (!this.week.recurring) {
//            val alarmPendingIntent =
//                PendingIntent.getBroadcast(
//                    context,
//                    (id + 1),
//                    preNotificationIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//                )
//            preNotificationManager.setExact(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                alarmPendingIntent
//            )
//        } else {
//            if (this.week.sunday) {
//                when (today) {
//                    1 -> {
//                        val uId = (id + Constants.sun + 1)
//                        setTodayAlarm(
//                            calendar,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    else -> { // day>1
//                        val day = 7
//                        val uId = (id + Constants.sun + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                }
//            }
//            if (this.week.monday) {
//                when (today) {
//                    1 -> {
//                        val day = 2 - today
//                        val uId = (id + Constants.mon + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    2 -> {
//                        val uId = (id + Constants.mon + 1)
//                        setTodayAlarm(
//                            calendar,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    in 3..7 -> {
//                        val day = 7 - (today - 2)
//                        val uId = (id + Constants.mon + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                }
//            }
//            if (this.week.tuesday) {
//                when (today) {
//                    in 1..2 -> {
//                        val day = 3 - today
//                        val uId = (id + Constants.tue + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    3 -> {
//                        val uId = (id + Constants.tue + 1)
//                        setTodayAlarm(
//                            calendar,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    in 4..7 -> {
//                        val day = 7 - (today - 3)
//                        val uId = (id + Constants.tue + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                }
//            }
//            if (this.week.wednesday) {
//                when (today) {
//                    in 1..3 -> {
//                        val day = 4 - today
//                        val uId = (id + Constants.wed + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    4 -> {
//                        val uId = (id + Constants.wed + 1)
//                        setTodayAlarm(
//                            calendar,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    in 5..7 -> {
//                        val day = 7 - (today - 4)
//                        val uId = (id + Constants.wed + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                }
//            }
//            if (this.week.thursday) {
//                when (today) {
//                    in 1..4 -> {
//                        val day = 5 - today
//                        val uId = (id + Constants.thu + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    5 -> {
//                        val uId = (id + Constants.thu + 1)
//                        setTodayAlarm(
//                            calendar,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    in 6..7 -> {
//                        val day = 7 - (today - 5)
//                        val uId = (id + Constants.thu + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                }
//            }
//            if (this.week.friday) {
//                when (today) {
//                    in 1..5 -> {
//                        val day = 6 - today
//                        val uId = (id + Constants.fri + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    6 -> {
//                        val uId = (id + Constants.fri + 1)
//                        setTodayAlarm(
//                            calendar,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    7 -> {
//                        val day = 7 - (today - 6)
//                        val uId = (id + Constants.fri + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                }
//            }
//            if (this.week.saturday) {
//                when (today) {
//                    in 1..6 -> {
//                        val day = 7 - today
//                        val uId = (id + Constants.sat + 1)
//                        setAlarmOther(
//                            calendar,
//                            day,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                    7 -> {
//                        val uId = (id + Constants.sat + 1)
//                        setTodayAlarm(
//                            calendar,
//                            preNotificationManager,
//                            context,
//                            preNotificationIntent,
//                            uId
//                        )
//                    }
//                }
//            }
//        }
//
//        preNotificationAlarmEntity.status = true
//
//    }
//
//
//    fun scheduleAlarmInterval(
//        context: Context,
//        listOfVariantIntervals: List<Stat>?
//    ) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val bundle = Bundle()
//        listOfVariantIntervals?.forEach { variantInterval ->
//            bundle.putSerializable(
//                Constants.ARG_VARIANT_INTERVAL_ALARM_OBJECT, this.copy(
//                    time = Time(
//                        hours = variantInterval.hours,
//                        minutes = variantInterval.minutes,
//                        midDay = variantInterval.midDay
//                    )
//                )
//            )
//            Log.d("TAGTAG", "scheduleAlarmInterval: $this")
//            bundle.putParcelable(Constants.ARG_VARIANT_INTERVAL_OBJECT, variantInterval)
//            intent.putExtra(Constants.BUNDLE_VARIANT_INTERVAL_OBJECT, bundle)
//
//
//            val calendar = Calendar.getInstance()
//            calendar.timeInMillis = System.currentTimeMillis()
//            calendar[Calendar.HOUR_OF_DAY] = variantInterval.hours.toInt()
//            calendar[Calendar.MINUTE] = variantInterval.minutes.toInt()
//            calendar[Calendar.SECOND] = 0
//            calendar[Calendar.MILLISECOND] = 0
//            val today: Int = calendar.get(Calendar.DAY_OF_WEEK)
//
//            if (!this.week.recurring) {
//                val alarmPendingIntent =
//                    PendingIntent.getBroadcast(
//                        context,
//                        variantInterval.id,
//                        intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//                    )
//                alarmManager.setExact(
//                    AlarmManager.RTC_WAKEUP,
//                    calendar.timeInMillis,
//                    alarmPendingIntent
//                )
//            } else {
//                if (this.week.sunday) {
//                    when (today) {
//                        1 -> {
//                            val id = (variantInterval.id + Constants.sun)
//                            setTodayAlarm(calendar, alarmManager, context, intent, id)
//                        }
//                        else -> { // day>1
//                            val day = 7
//                            val id = (variantInterval.id + Constants.sun)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                    }
//                }
//                if (this.week.monday) {
//                    when (today) {
//                        1 -> {
//                            val day = 2 - today
//                            val id = (variantInterval.id + Constants.mon)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                        2 -> {
//                            val id = (variantInterval.id + Constants.mon)
//                            setTodayAlarm(calendar, alarmManager, context, intent, id)
//                        }
//                        in 3..7 -> {
//                            val day = 7 - (today - 2)
//                            val id = (variantInterval.id + Constants.mon)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                    }
//                }
//                if (this.week.tuesday) {
//                    when (today) {
//                        in 1..2 -> {
//                            val day = 3 - today
//                            val id = (variantInterval.id + Constants.tue)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                        3 -> {
//                            val id = (variantInterval.id + Constants.tue)
//                            setTodayAlarm(calendar, alarmManager, context, intent, id)
//                        }
//                        in 4..7 -> {
//                            val day = 7 - (today - 3)
//                            val id = (variantInterval.id + Constants.tue)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                    }
//                }
//                if (this.week.wednesday) {
//                    when (today) {
//                        in 1..3 -> {
//                            val day = 4 - today
//                            val id = (variantInterval.id + Constants.wed)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                        4 -> {
//                            val id = (variantInterval.id + Constants.wed)
//                            setTodayAlarm(calendar, alarmManager, context, intent, id)
//                        }
//                        in 5..7 -> {
//                            val day = 7 - (today - 4)
//                            val id = (variantInterval.id + Constants.wed)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                    }
//                }
//                if (this.week.thursday) {
//                    when (today) {
//                        in 1..4 -> {
//                            val day = 5 - today
//                            val id = (variantInterval.id + Constants.thu)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                        5 -> {
//                            val id = (variantInterval.id + Constants.thu)
//                            setTodayAlarm(calendar, alarmManager, context, intent, id)
//                        }
//                        in 6..7 -> {
//                            val day = 7 - (today - 5)
//                            val id = (variantInterval.id + Constants.thu)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                    }
//                }
//                if (this.week.friday) {
//                    when (today) {
//                        in 1..5 -> {
//                            val day = 6 - today
//                            val id = (variantInterval.id + Constants.fri)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                        6 -> {
//                            val id = (variantInterval.id + Constants.fri)
//                            setTodayAlarm(calendar, alarmManager, context, intent, id)
//                        }
//                        7 -> {
//                            val day = 7 - (today - 6)
//                            val id = (variantInterval.id + Constants.fri)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                    }
//                }
//                if (this.week.saturday) {
//                    when (today) {
//                        in 1..6 -> {
//                            val day = 7 - today
//                            val id = (variantInterval.id + Constants.sat)
//                            setAlarmOther(calendar, day, alarmManager, context, intent, id)
//                        }
//                        7 -> {
//                            val id = (variantInterval.id + Constants.sat)
//                            setTodayAlarm(calendar, alarmManager, context, intent, id)
//                        }
//                    }
//                }
//            }
//
//
//            if (this.interval.advancedReminder.status) {
//                schedulerAlarmPreNotification(
//                    context,
//                    true,
//                    variantInterval,
//                    variantInterval.id
//                )
//            }
//        }
//    }
//
//    fun cancelAlarmInterval(context: Context, variantInterval: Stat) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        if (!this.week.recurring) {
//            val id = variantInterval.id
//            removeAlarm(context, id, intent, alarmManager)
//        } else {
//            if (this.week.sunday) {
//                val id = (variantInterval.id + Constants.sun)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.monday) {
//                val id = (variantInterval.id + Constants.mon)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.tuesday) {
//                val id = (variantInterval.id + Constants.tue)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.wednesday) {
//                val id = (variantInterval.id + Constants.wed)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.thursday) {
//                val id = (variantInterval.id + Constants.thu)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.friday) {
//                val id = (variantInterval.id + Constants.fri)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.saturday) {
//                val id = (variantInterval.id + Constants.sat)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//        }
//    }
//
//    fun cancelScheduleAlarm(context: Context, alarmId: Int, cancelAllIntervals: Boolean) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        if (!this.week.recurring) {
//            val id = alarmId
//            removeAlarm(context, id, intent, alarmManager)
//        } else {
//            if (this.week.sunday) {
//                val id = (alarmId + Constants.sun)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.monday) {
//                val id = (alarmId + Constants.mon)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.tuesday) {
//                val id = (alarmId + Constants.tue)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.wednesday) {
//                val id = (alarmId + Constants.wed)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.thursday) {
//                val id = (alarmId + Constants.thu)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.friday) {
//                val id = (alarmId + Constants.fri)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//            if (this.week.saturday) {
//                val id = (alarmId + Constants.sat)
//                removeAlarm(context, id, intent, alarmManager)
//            }
//        }
//
//        this.status = false
//        if (this.interval.advancedReminder.status){
//        cancelPreNotification(context, alarmId)
//        }
//        if (this.interval.isRemainderAtTheEnd) {
//            cancelPostNotification(context, alarmId) // post notification one time
//        }
//        if (cancelAllIntervals&&this.interval.status) {
//            if (this.interval.isVariantInterval) {
//                if (this.interval.variantIntervals.isNotEmpty()) {
//                    this.interval.variantIntervals.forEach {
//                        cancelPreNotification(context, it.id)
//                        cancelAlarmInterval(context, it)
//                    }
//                }
//            } else {
//                if (this.interval.staticIntervals.isNotEmpty()) {
//                    this.interval.staticIntervals.forEach {
//                        cancelPreNotification(context, it.id)
//                        cancelAlarmInterval(context, it)
//                    }
//                }
//            }
//
//        }
//    }
//
//    private fun setAlarmOther(
//        calendar: Calendar,
//        day: Int,
//        alarmManager: AlarmManager,
//        context: Context,
//        intent: Intent,
//        id: Int
//    ) {
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(
//                context, id, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day)
//        val runEverySevenDays = (7 * 24 * 60 * 60 * 1000).toLong()
//
////        Log.d("TAGTAG", "setAlarmOther: ${calendar.time}")
//        Log.d("alarm", "setAlarmOther:id $id,time ${calendar.time}")
//        alarmManager.setInexactRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            runEverySevenDays,
//            alarmPendingIntent
//        )
//    }
//
//    private fun setTodayAlarm(
//        calendar: Calendar,
//        alarmManager: AlarmManager,
//        context: Context,
//        intent: Intent,
//        id: Int
//    ) {
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(
//                context, id, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//        if (calendar.timeInMillis <= System.currentTimeMillis()) {
//            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 7)
//        }
//        val runEverySevenDays = (7 * 24 * 60 * 60 * 1000).toLong()
//        Log.d("alarm", "setTodayAlarm:id $id,time ${calendar.time}")
//        alarmManager.setInexactRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            runEverySevenDays,
//            alarmPendingIntent
//        )
//    }
//
//    fun snooze(context: Context) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val bundle = Bundle()
//        bundle.putSerializable(Constants.ARG_ALARM_OBJET, this)
//        intent.putExtra(Constants.BUNDLE_ALARM_OBJECT, bundle)
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        calendar[Calendar.SECOND] = 0
//        calendar[Calendar.MILLISECOND] = 0
//        val id = this.alarmId + 999
//        val snoozeTime = this.interval.snoozeTime
//        var min = this.time.minutes.toInt() + if (snoozeTime < 5) 5 else snoozeTime
//        min = if (min > 60) 60 else min
//        when (this.time.hours.toInt()) {
//            in 1..23 -> {
//                if (min == 60) {
//                    calendar[Calendar.HOUR_OF_DAY] = 24 // change for 1 to 22 hours
//                    calendar[Calendar.MINUTE] = 0
//                } else {
//                    calendar[Calendar.HOUR_OF_DAY] = this.time.hours.toInt()
//                    calendar[Calendar.MINUTE] = min
//                }
//            }
//            24 -> {
//                calendar[Calendar.HOUR_OF_DAY] = 0
//                calendar[Calendar.MINUTE] = min
//                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
//            }
//        }
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(
//                context, id, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//        alarmManager.setExact(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            alarmPendingIntent
//        )
//    }
//
//    fun cancelPostNotification(context: Context, id: Int) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(context, id + 4, intent, PendingIntent.FLAG_IMMUTABLE)
//        alarmManager.cancel(alarmPendingIntent)
//        val toastText = "Notification Canceled"
//        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
//        Log.i("cancel", toastText)
//    }
//
//    private fun removeAlarm(
//        context: Context,
//        id: Int,
//        intent: Intent,
//        alarmManager: AlarmManager
//    ) {
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE)
//        alarmManager.cancel(alarmPendingIntent)
//    }

}