package fit.asta.health.sunlight.feature.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import fit.asta.health.resources.drawables.R as DrawR

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(messageId: Int) {
    Toast.makeText(this, this.getString(messageId), Toast.LENGTH_SHORT).show()
}

fun String.contains(vararg strings: String, ignoreCase: Boolean = true): Boolean {
    return strings.all { this.contains(it, ignoreCase) }
}

fun LazyListScope.gridItems(
    count: Int,
    nColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(Int) -> Unit,
) {
    gridItems(
        data = List(count) { it },
        nColumns = nColumns,
        horizontalArrangement = horizontalArrangement,
        itemContent = itemContent,
    )
}

fun <T> LazyListScope.gridItems(
    data: List<T>,
    nColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val rows = if (data.isEmpty()) 0 else 1 + (data.count() - 1) / nColumns
    items(rows) { rowIndex ->
        Row(horizontalArrangement = horizontalArrangement) {
            for (columnIndex in 0 until nColumns) {
                val itemIndex = rowIndex * nColumns + columnIndex
                if (itemIndex < data.count()) {
                    val item = data[itemIndex]
                    androidx.compose.runtime.key(key?.invoke(item)) {
                        Box(
                            modifier = Modifier.weight(1f, fill = true),
                            propagateMinConstraints = true
                        ) {
                            itemContent.invoke(this, item)
                        }
                    }
                } else {
                    Spacer(Modifier.weight(1f, fill = true))
                }
            }
        }
    }
}

fun Int.getBannerDrawable(): Int {
    return when (this % 3) {
        0 -> {
            DrawR.drawable.ic_walking_in_sun
        }

        1 -> {
            DrawR.drawable.ic_sun_nature

        }

        2 -> {
            DrawR.drawable.ic_sun_beach_day
        }

        else -> {
            DrawR.drawable.ic_walking_in_sun
        }
    }
}

fun String?.toAmPmFormat(pattern:String="hh:mm a"): String? {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat(pattern, Locale.getDefault())

    try {
        val date = inputFormat.parse(this ?: "")
        return outputFormat.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this
}
fun Canvas.drawTextMultiline(text: String?, x: Float, y: Float, paint: Paint) {
    if (text.isNullOrEmpty()) {
        return
    }
    val lines = text.split("\n")
    var yPos = y
    for (line in lines) {
        drawText(line, x, yPos, paint)
        yPos += paint.descent() - paint.ascent()
    }
}
fun String?.splitAmPm(): String? {
    if (this.isNullOrEmpty()) {
        return null
    }

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())

    return try {
        val date = inputFormat.parse(this)
        val hour = SimpleDateFormat("h", Locale.getDefault()).format(date!!)
        val amPm = SimpleDateFormat("a", Locale.getDefault()).format(date)
        "$hour\n$amPm"
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Long?.toAmPmTime(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this?:0
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return dateFormat.format(calendar.time)?:""
}
fun Long?.toMinutes(): Long {
    return (this?:0 )/ 60000
}
fun Int.toUVIndexColor(): Color {
    return when (this) {
        in 0..2 -> Color(0xFF038303)  // Low
        in 3..5 -> Color(0xFFEBA315) // Dark Yellow
        in 6..7 -> Color(0xFFCF7405) // Orange for High
        in 8..10 -> Color.Red // Very High
        else -> Color(0xFF800080) // Purple for ExtremeU
    }
}
fun Int.toUVIndexColorPartGrad(): Color {
    return when (this) {
        in 0..2 -> Color(0xFF02B302)  // Low
        in 3..5 -> Color(0xFFFDDB21) // Dark Yellow
        in 6..7 -> Color(0xFFFA9F30) // Orange for High
        in 8..10 -> Color.Red // Very High
        else -> Color(0xFFD302D3) // Purple for ExtremeU
    }
}

