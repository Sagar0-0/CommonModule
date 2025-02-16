package fit.asta.health.common.utils

import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

//"2022-10-03%2012%20pm"
fun getCurrentTime(format: String = "yyyy-MM-dd HH:mm:ss"): String {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val ldt: LocalDateTime = LocalDateTime.now()
        ldt.format(DateTimeFormatter.ofPattern(format))
    } else {

        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.format(Date())
    }
}

fun formatTime(timeMillis: Long, format: String = "hh:mm a"): String {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeMillis
    return sdf.format(calendar.time)
}

fun convertMillisToDate(milliseconds: Long, format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val sdf =
        SimpleDateFormat(format, Locale.getDefault())
    val resultDate = Date(milliseconds)
    return sdf.format(resultDate)
}

fun getNextNthDayMillis(n: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0) // Set to midnight
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    calendar.add(Calendar.DATE, n)
    return calendar.timeInMillis
}

fun getCurrentDateTime(): Long = System.currentTimeMillis()

fun getCurrentDate(format: String = "yyyy-MM-dd"): String {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val startDate: LocalDate = LocalDate.now()
        startDate.format(DateTimeFormatter.ofPattern(format))
    } else {

        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.format(Date())
    }
}

fun getNextDate(days: Int, format: String = "yyyy-MM-dd"): String {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val startDate: LocalDate = LocalDate.now()
        val newDate: LocalDate = startDate.plusDays(days.toLong())
        newDate.format(DateTimeFormatter.ofPattern(format))
    } else {

        val calendar = GregorianCalendar()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.DAY_OF_MONTH, 2)
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.format(Date(calendar.timeInMillis))
    }
}

fun convertToMilliseconds(hour: Int, minute: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun isCurrentTimeLaterThan(inputTime: String?, format: String = "yyyy-MM-dd'T'HH:mm"): Boolean {
    if (inputTime.isNullOrEmpty()) return true
    Log.d("inputTime", "isCurrentTimeLaterThan:$inputTime ")
    val inputDateTime = SimpleDateFormat(format, Locale.getDefault()).parse(inputTime)
    val currentTime = Calendar.getInstance().time
    Log.d("inputTime", "isCurrentTimeLaterThan:${currentTime.after(inputDateTime)} ")
    return currentTime.after(inputDateTime)
}