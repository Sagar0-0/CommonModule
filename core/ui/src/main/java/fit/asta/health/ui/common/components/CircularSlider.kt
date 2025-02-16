package fit.asta.health.ui.common.components

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.*


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun CircularSlider(
    modifier: Modifier = Modifier,
    padding: Float = 50f,
    stroke: Float = 32f,
    cap: StrokeCap = StrokeCap.Round,
    touchStroke: Float = 100f,
    thumbColor: Color = Color.Blue,
    progressColor: Color = Color(0xff0277BD),
    backgroundColor: Color = Color.LightGray,
    debug: Boolean = false,
    angle1: Float = -60f,
    onChange: ((Float) -> Unit)? = null,
) {
    var width by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }
    var angle by remember { mutableStateOf(angle1) }
    var last by remember { mutableStateOf(0f) }
    var down by remember { mutableStateOf(false) }
    var radius by remember { mutableStateOf(0f) }
    var center by remember { mutableStateOf(Offset.Zero) }
    var appliedAngle by remember { mutableStateOf(0f) }
    var progresscolor by remember {
        mutableStateOf(progressColor)
    }

    LaunchedEffect(key1 = angle) {
        var a = angle
        a += 60
        if (a <= 0f) {
            a += 360
        }
        a = a.coerceIn(0f, 300f)
        if (last < 150f && a == 300f) {
            a = 0f
        }
        last = a
        appliedAngle = a
    }
    LaunchedEffect(key1 = appliedAngle) {
        onChange?.invoke(appliedAngle / 300f)
        progresscolor = if (appliedAngle / 300f < 0.25f) {
            Color.Red
        } else if (appliedAngle / 300f < 0.5f) {
            Color(0xFFFFA000)
        } else if (appliedAngle / 300f < 0.75f) {
            Color(0xFF0288D1)
        } else {
            Color(0xFF689F38)
        }
    }

    Canvas(modifier = modifier
        .onGloballyPositioned {
            width = it.size.width
            height = it.size.height
            center = Offset(width / 2f, height / 2f)
            radius = min(width.toFloat(), height.toFloat()) / 2f - padding - stroke / 2f
        }
        .pointerInteropFilter {
            val x = it.x
            val y = it.y
            val offset = Offset(x, y)
            when (it.action) {

                MotionEvent.ACTION_DOWN -> {
                    val d = distance(offset, center)
                    val a = angle(center, offset)
                    if (d >= radius - touchStroke / 2f && d <= radius + touchStroke / 2f && a !in -120f..-60f) {
                        down = true
                        angle = a
                    } else {
                        down = false
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (down) {
                        angle = angle(center, offset)
                    }
                }

                MotionEvent.ACTION_UP -> {
                    down = false
                }

                else -> return@pointerInteropFilter false
            }
            return@pointerInteropFilter true
        }) {
        drawArc(
            color = backgroundColor,
            startAngle = -240f,
            sweepAngle = 300f,
            topLeft = center - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            useCenter = false,
            style = Stroke(width = stroke, cap = cap)
        )
        drawArc(
            color = progresscolor,
            startAngle = 120f,
            sweepAngle = appliedAngle,
            topLeft = center - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            useCenter = false,
            style = Stroke(width = stroke, cap = cap)
        )
        drawCircle(
            color = thumbColor,
            radius = stroke,
            center = center + Offset(
                radius * cos((120 + appliedAngle) * PI / 180f).toFloat(),
                radius * sin((120 + appliedAngle) * PI / 180f).toFloat()
            )
        )
        if (debug) {
            drawRect(
                color = Color.Green,
                topLeft = Offset.Zero,
                size = Size(width.toFloat(), height.toFloat()),
                style = Stroke(4f)
            )
            drawRect(
                color = Color.Red,
                topLeft = Offset(padding, padding),
                size = Size(width.toFloat() - padding * 2, height.toFloat() - padding * 2),
                style = Stroke(4f)
            )
            drawRect(
                color = Color.Blue,
                topLeft = Offset(padding, padding),
                size = Size(width.toFloat() - padding * 2, height.toFloat() - padding * 2),
                style = Stroke(4f)
            )
            drawCircle(
                color = Color.Red,
                center = center,
                radius = radius + stroke / 2f,
                style = Stroke(2f)
            )
            drawCircle(
                color = Color.Red,
                center = center,
                radius = radius - stroke / 2f,
                style = Stroke(2f)
            )
        }
    }
}

fun angle(center: Offset, offset: Offset): Float {
    val rad = atan2(center.y - offset.y, center.x - offset.x)
    val deg = Math.toDegrees(rad.toDouble())
    return deg.toFloat()
}

fun distance(first: Offset, second: Offset): Float {
    return sqrt((first.x - second.x).square() + (first.y - second.y).square())
}

fun Float.square(): Float {
    return this * this
}