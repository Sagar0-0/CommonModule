package fit.asta.health.sunlight.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object ChartUtils {
    private fun String.toDate(pattern: String = "yyyy-MM-dd'T'HH:mm"): Date? {
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return format.parse(this)
    }

    fun calculateAngle(start: String, end: String): Double {
        val startDate = start.toDate()?.time?.toDouble() ?: 0.0
        val endDate = end.toDate()?.time?.toDouble() ?: 0.0
        val currentDate = Date().time.toDouble() ?: 0.0

        return when {
            currentDate < (startDate ?: 0.0) -> 0.0
            currentDate > (endDate ?: 0.0) -> 180.0
            else -> {
                val totalDuration = endDate - startDate
                val elapsedDuration = currentDate - startDate
                (elapsedDuration / totalDuration) * 180
            }
        }
    }
    fun isHourMatchingWithCurrentTime(inputTime: String): Boolean {
        val inputHour = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault()).parse(inputTime)?.hours
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return inputHour == currentHour
    }

}