package fit.asta.health.common.utils

import java.text.SimpleDateFormat
import java.util.Date

fun Long.toDateFormat(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(date)
}