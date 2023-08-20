package fit.asta.health.scheduler.ref.provider

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fit.asta.health.scheduler.ref.data.Weekdays
import kotlinx.parcelize.Parcelize
import java.util.Calendar
@Entity(tableName = "alarms")
@Parcelize
data class Alarm(
    @PrimaryKey(autoGenerate = false) var id: Long = 0,
    @ColumnInfo(name = "hour") var hour: Int,
    @ColumnInfo(name = "minutes") var minutes: Int,
    @ColumnInfo(name = "preEnabled") var preEnabled: Boolean,
    @ColumnInfo(name = "preNotification") var preNotification: Int?,
    @ColumnInfo(name = "endEnabled") var endEnabled: Boolean,
    @ColumnInfo(name = "endHour") var endHour: Int?,
    @ColumnInfo(name = "endMinutes") var endMinutes: Int?,
    @ColumnInfo(name = "snooze") var snooze: Int,
    @ColumnInfo(name = "daysOfWeek") var daysOfWeek: Weekdays = Weekdays.NONE,
    @ColumnInfo(name = "enabled") var enabled: Boolean,
    @ColumnInfo(name = "vibrate") var vibrate: Boolean = true,
    @ColumnInfo(name = "label") var label: String? = "hi",
    @ColumnInfo(name = "alert") var alert: Uri? = null,
    @ColumnInfo(name = "deleteAfterUse") var deleteAfterUse: Boolean = false
) : Parcelable {


    fun getPreviousAlarmTime(currentTime: Calendar): Calendar? {
        val previousInstanceTime = Calendar.getInstance(currentTime.timeZone)
        previousInstanceTime[Calendar.YEAR] = currentTime[Calendar.YEAR]
        previousInstanceTime[Calendar.MONTH] = currentTime[Calendar.MONTH]
        previousInstanceTime[Calendar.DAY_OF_MONTH] = currentTime[Calendar.DAY_OF_MONTH]
        previousInstanceTime[Calendar.HOUR_OF_DAY] = hour
        previousInstanceTime[Calendar.MINUTE] = minutes
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

    fun getNextAlarmTime(currentTime: Calendar, state: State): Calendar {
        val nextInstanceTime = Calendar.getInstance(currentTime.timeZone)
        nextInstanceTime[Calendar.YEAR] = currentTime[Calendar.YEAR]
        nextInstanceTime[Calendar.MONTH] = currentTime[Calendar.MONTH]
        nextInstanceTime[Calendar.DAY_OF_MONTH] = currentTime[Calendar.DAY_OF_MONTH]
        nextInstanceTime[Calendar.SECOND] = 0
        nextInstanceTime[Calendar.MILLISECOND] = 0

        when (state) {
            State.PRE -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = hour
                nextInstanceTime[Calendar.MINUTE] = minutes
                nextInstanceTime.add(Calendar.MINUTE, -preNotification!!)
            }

            State.CURRENT -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = hour
                nextInstanceTime[Calendar.MINUTE] = minutes
            }

            State.PREEND -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = endHour!!
                nextInstanceTime[Calendar.MINUTE] = endMinutes!!
                nextInstanceTime.add(Calendar.MINUTE, -preNotification!!)
            }

            State.END -> {
                nextInstanceTime[Calendar.HOUR_OF_DAY] = endHour!!
                nextInstanceTime[Calendar.MINUTE] = endMinutes!!
            }
        }
        if (nextInstanceTime.timeInMillis <= currentTime.timeInMillis) {
            nextInstanceTime.add(Calendar.DAY_OF_YEAR, 1)
        }

        val addDays = daysOfWeek.getDistanceToNextDay(nextInstanceTime)
        if (addDays > 0) {
            nextInstanceTime.add(Calendar.DAY_OF_WEEK, addDays)
        }

//        nextInstanceTime[Calendar.HOUR_OF_DAY] = hour
//        nextInstanceTime[Calendar.MINUTE] = minutes

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