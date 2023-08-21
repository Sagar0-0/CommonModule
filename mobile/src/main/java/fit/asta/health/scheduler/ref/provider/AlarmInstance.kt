package fit.asta.health.scheduler.ref.provider

import android.content.Context
import android.content.Intent
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "alarm_instances")
data class AlarmInstance(
    @PrimaryKey(autoGenerate = false) var mId: Long = 0,
    @ColumnInfo(name = "year") var mYear: Int = 0,
    @ColumnInfo(name = "month") var mMonth: Int = 0,
    @ColumnInfo(name = "day") var mDay: Int = 0,
    @ColumnInfo(name = "hour") var mHour: Int = 0,
    @ColumnInfo(name = "minute") var mMinute: Int = 0,
    @ColumnInfo(name = "label") var mLabel: String? = null,
    @ColumnInfo(name = "alarm_id") var mAlarmId: Long = 0,
    @ColumnInfo(name = "alarm_state") var mAlarmState: Int = 0
) {
    override fun hashCode(): Int {
        return java.lang.Long.valueOf(mId).hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is AlarmInstance) return false
        return mId == other.mId
    }

    companion object {

        fun createIntent(context: Context?, cls: Class<*>?, instanceId: Long): Intent {
            return Intent(context, cls).putExtra("id", instanceId)
        }
    }

    var alarmTime: Calendar
        get() {
            val calendar = Calendar.getInstance()
            calendar[Calendar.YEAR] = mYear
            calendar[Calendar.MONTH] = mMonth
            calendar[Calendar.DAY_OF_MONTH] = mDay
            calendar[Calendar.HOUR_OF_DAY] = mHour
            calendar[Calendar.MINUTE] = mMinute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            return calendar
        }
        set(calendar) {
            mYear = calendar[Calendar.YEAR]
            mMonth = calendar[Calendar.MONTH]
            mDay = calendar[Calendar.DAY_OF_MONTH]
            mHour = calendar[Calendar.HOUR_OF_DAY]
            mMinute = calendar[Calendar.MINUTE]
        }
    val timeout: Calendar
        get() {
            val timeoutMinutes = 10
            val calendar = alarmTime
            calendar.add(Calendar.MINUTE, timeoutMinutes)
            return calendar
        }
}