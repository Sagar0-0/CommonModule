package fit.asta.health.data.scheduler.db.entity


import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.scheduler.remote.net.scheduler.Info
import fit.asta.health.data.scheduler.remote.net.scheduler.Ivl
import fit.asta.health.data.scheduler.remote.net.scheduler.Meta
import fit.asta.health.data.scheduler.remote.net.scheduler.Time
import fit.asta.health.data.scheduler.remote.net.scheduler.Tone
import fit.asta.health.data.scheduler.remote.net.scheduler.Vib
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*

@Entity(tableName = "ALARM_TABLE")
@Parcelize
data class AlarmEntity(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("almId")
    var alarmId: Long,
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
    var interval: Ivl,//
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
    var time: Time,//
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
    var daysOfWeek: Weekdays = Weekdays.NONE,
    @ColumnInfo(name = "skipDate")
    var skipDate: Int = -1,
    @ColumnInfo(name = "startDate")
    @SerializedName("startDate")
    var selectedStartDateMillis: Long,
    @ColumnInfo(name = "endDate")
    @SerializedName("endDate")
    var selectedEndDateMillis: Long? = null,
    @SerializedName("missedAlarm")
    var isMissed: Boolean? = null,
    @SerializedName("currentAlarmDate")
    var currentAlarmDate: Int? = null,
) : Serializable, Parcelable {

    override fun hashCode(): Int {
        return java.lang.Long.valueOf(alarmId).hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is AlarmEntity) return false
        return alarmId == other.alarmId
    }

    fun getPreviousAlarmTime(currentTime: Calendar): Calendar? {
        val previousInstanceTime = Calendar.getInstance(currentTime.timeZone)
        previousInstanceTime[Calendar.YEAR] = currentTime[Calendar.YEAR]
        previousInstanceTime[Calendar.MONTH] = currentTime[Calendar.MONTH]
        previousInstanceTime[Calendar.DAY_OF_MONTH] = currentTime[Calendar.DAY_OF_MONTH]
        previousInstanceTime[Calendar.HOUR_OF_DAY] = time.hours
        previousInstanceTime[Calendar.MINUTE] = time.minutes
        previousInstanceTime[Calendar.SECOND] = 0
        previousInstanceTime[Calendar.MILLISECOND] = 0

        val subtractDays = daysOfWeek.getDistanceToPreviousDay(previousInstanceTime)
        return if (subtractDays > 0) {
            previousInstanceTime.add(Calendar.DAY_OF_WEEK, -subtractDays)
            previousInstanceTime
        } else {
            null
        }
    }

    fun getCurrentAlarmTime(currentTime: Calendar, state: State): Calendar {
        val nextInstanceTime = Calendar.getInstance(currentTime.timeZone)
        nextInstanceTime[Calendar.YEAR] = currentTime[Calendar.YEAR]
        nextInstanceTime[Calendar.MONTH] = currentTime[Calendar.MONTH]
        nextInstanceTime[Calendar.DAY_OF_MONTH] = currentTime[Calendar.DAY_OF_MONTH]
        nextInstanceTime[Calendar.SECOND] = 0
        nextInstanceTime[Calendar.MILLISECOND] = 0

        when (state) {
            State.PRE -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = time.hours
                nextInstanceTime[Calendar.MINUTE] = time.minutes
                nextInstanceTime.add(Calendar.MINUTE, -interval.advancedReminder.time)
            }

            State.CURRENT -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = time.hours
                nextInstanceTime[Calendar.MINUTE] = time.minutes
            }

            State.PREEND -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = interval.endAlarmTime.hours
                nextInstanceTime[Calendar.MINUTE] = interval.endAlarmTime.minutes
                nextInstanceTime.add(Calendar.MINUTE, -interval.advancedReminder.time)
            }

            State.END -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = interval.endAlarmTime.hours
                nextInstanceTime[Calendar.MINUTE] = interval.endAlarmTime.minutes
            }
        }

//        LogUtils.v("time ${nextInstanceTime.time}")

        return nextInstanceTime
    }

    fun getNextAlarmTime(
        currentTime: Calendar,
        state: State,
        skipToday: Boolean = false
    ): Calendar {
        val nextInstanceTime = Calendar.getInstance(currentTime.timeZone)
        nextInstanceTime[Calendar.YEAR] = currentTime[Calendar.YEAR]
        nextInstanceTime[Calendar.MONTH] = currentTime[Calendar.MONTH]
        nextInstanceTime[Calendar.DAY_OF_MONTH] = currentTime[Calendar.DAY_OF_MONTH]
        nextInstanceTime[Calendar.SECOND] = 0
        nextInstanceTime[Calendar.MILLISECOND] = 0

        when (state) {
            State.PRE -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = time.hours
                nextInstanceTime[Calendar.MINUTE] = time.minutes
                nextInstanceTime.add(Calendar.MINUTE, -interval.advancedReminder.time)
            }

            State.CURRENT -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = time.hours
                nextInstanceTime[Calendar.MINUTE] = time.minutes
            }

            State.PREEND -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = interval.endAlarmTime.hours
                nextInstanceTime[Calendar.MINUTE] = interval.endAlarmTime.minutes
                nextInstanceTime.add(Calendar.MINUTE, -interval.advancedReminder.time)
            }

            State.END -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = interval.endAlarmTime.hours
                nextInstanceTime[Calendar.MINUTE] = interval.endAlarmTime.minutes
            }
        }
        Log.d("AlarmClock", "time init ${nextInstanceTime.time}")
        if (nextInstanceTime.timeInMillis <= currentTime.timeInMillis || skipToday) {
            nextInstanceTime.add(Calendar.DAY_OF_YEAR, 1)
        }
        Log.d("AlarmClock", "time mid ${nextInstanceTime.time}")
        val addDays = daysOfWeek.getDistanceToNextDay(nextInstanceTime)
        if (addDays > 0) {
            nextInstanceTime.add(Calendar.DAY_OF_WEEK, addDays)
        }
        Log.d("AlarmClock", "time end ${nextInstanceTime.time}")

        return nextInstanceTime
    }


    companion object {
        fun createIntent(context: Context?, cls: Class<*>?, alarmId: Long): Intent {
            return Intent(context, cls).putExtra("id", alarmId)
        }
    }

    enum class State {
        PRE, CURRENT, PREEND, END
    }
}

