package fit.asta.health.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateFormat(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}