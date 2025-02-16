package fit.asta.health.data.walking.local.utils

import androidx.room.TypeConverter
import java.time.LocalDate

@Suppress("unused")
class DateTimeConverter {

    @TypeConverter
    fun toTimestamp(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @TypeConverter
    fun toLocalDate(timestamp: Long): LocalDate {
        return LocalDate.ofEpochDay(timestamp)
    }
}