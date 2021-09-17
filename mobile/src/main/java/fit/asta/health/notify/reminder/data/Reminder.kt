package fit.asta.health.notify.reminder.data

import android.os.Parcelable
import android.text.format.DateFormat
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.*


object DateConverter {

    @TypeConverter
    @JvmStatic
    fun toDate(timestamp: Long?): Date? {

        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    @JvmStatic
    fun toTimestamp(date: Date?): Long? {

        return date?.time
    }

    fun getDayMonth(date: Date?): String {

        val day = DateFormat.format("dd", date) as String
        val monthString = DateFormat.format("MMM", date) as String //Jan
        return day + monthString
    }
}

object ListConverter {

    @TypeConverter
    @JvmStatic
    fun toString(list: List<Int>?): String? {

        //Replacing array symbols
        return list?.toString()?.replace(Regex("""[$ \[\]]"""), "")
    }

    @TypeConverter
    @JvmStatic
    fun toList(list: String?): List<Int>? {

        return if (list == null || list.isEmpty()) null
        else list.split(",").map { it.trim().toInt() }
    }
}

@Parcelize
@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    var type: Int = OTHER,
    @ColumnInfo
    var title: String? = null,
    @ColumnInfo
    var desc: String? = null,
    @ColumnInfo
    var hour: Int = 0,
    @ColumnInfo(name = "min")
    var minute: Int = 0,
    @ColumnInfo
    var status: Boolean = true,
    @ColumnInfo
    var isRepeat: Boolean = true,
    @field:TypeConverters(ListConverter::class)
    var days: List<Int>? = null,
    @ColumnInfo
    var audio: String? = null,
    @ColumnInfo
    var isVibrate: Boolean = true,
    @ColumnInfo
    var linkedId: String? = null
    /*@field:TypeConverters(DateConverter::class)
    var createdAt: Date? = null,*/
) : Parcelable {

    companion object {

        const val OTHER = 0
        const val EXERCISE = 1
        const val WATER = 2
        const val VITAMIN_D = 3
    }
}