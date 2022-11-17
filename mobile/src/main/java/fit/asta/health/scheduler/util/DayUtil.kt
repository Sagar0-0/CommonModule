package fit.asta.health.scheduler.util

import java.util.*

object DayUtil {
    @Throws(Exception::class)
    fun toDay(day: Int): String {
        when (day) {
            Calendar.SUNDAY -> return "Sunday"
            Calendar.MONDAY -> return "Monday"
            Calendar.TUESDAY -> return "Tuesday"
            Calendar.WEDNESDAY -> return "Wednesday"
            Calendar.THURSDAY -> return "Thursday"
            Calendar.FRIDAY -> return "Friday"
            Calendar.SATURDAY -> return "Saturday"
        }
        throw Exception("Could not locate day")
    }

    fun getDay(hour: Int, minute: Int): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        // if alarm time has already passed
        return if (calendar.timeInMillis <= System.currentTimeMillis()) {
            "Tomorrow"
        } else {
            "Today"
        }
    }
}