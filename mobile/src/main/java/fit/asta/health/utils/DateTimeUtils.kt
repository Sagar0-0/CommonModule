package fit.asta.health.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


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