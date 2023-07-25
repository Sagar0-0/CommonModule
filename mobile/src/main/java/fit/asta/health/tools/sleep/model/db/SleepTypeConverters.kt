package fit.asta.health.tools.sleep.model.db

import androidx.room.TypeConverter
import java.time.LocalDateTime

class SleepTypeConverters {

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun fromString(text: String): LocalDateTime {
        return LocalDateTime.parse(text)
    }
}