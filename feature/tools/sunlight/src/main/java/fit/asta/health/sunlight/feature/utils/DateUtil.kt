package fit.asta.health.sunlight.feature.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

object DateUtil {
    fun getCurrentDateFormatted(): String {
        return try {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            currentDate.format(formatter)
        } catch (e: Exception) {
            "-"
        }

    }

    fun convertIsoToTime(isoTimestamp: String): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val dateTime = LocalDateTime.parse(isoTimestamp, DateTimeFormatter.ISO_DATE_TIME)
            dateTime.format(formatter)
        } catch (e: Exception) {
            "-"
        }

    }

    fun getDayNameForToday(): String {
        val today = LocalDate.now()
        val dayOfWeek = today.dayOfWeek
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    fun isCurrentTimeLaterThan(inputTime: String): Boolean {
        Log.d("inputTime", "isCurrentTimeLaterThan:$inputTime ")
        if (inputTime.isEmpty()) return true
        val inputDateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault()).parse(inputTime)
        val currentTime = Calendar.getInstance().time
        Log.d("inputTime", "isCurrentTimeLaterThan:${currentTime.after(inputDateTime)} ")
        return currentTime.after(inputDateTime)
    }
}