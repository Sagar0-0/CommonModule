package fit.asta.health.tools.walking.view.component


import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.spacing
import kotlin.math.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircularSlider(
    modifier: Modifier = Modifier,
    indicatorValue: Float = 2f,
    maxIndicatorValue: Float = 120f,
    bigTextColor: Color = MaterialTheme.colors.onSurface,
    bigTextSuffix: String = "min",
    padding: Float = 50f,
    stroke: Float = 20f,
    cap: StrokeCap = StrokeCap.Round,
    touchStroke: Float = 50f,
    thumbColor: Color = Color.Blue,
    progressColor: Color = Color.Red,
    backgroundColor: Color = Color.LightGray,
    isDuration: Boolean,
    isStarted: Boolean,
    onChange: ((Float) -> Unit)? = null,
    onChangeType: () -> Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var width by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }
    var angle by remember { mutableStateOf(-60f) }
    var last by remember { mutableStateOf(0f) }
    var down by remember { mutableStateOf(false) }
    var radius by remember { mutableStateOf(0f) }
    var center by remember { mutableStateOf(Offset.Zero) }
    var appliedAngle by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = angle) {
        var a = angle
        a += 40
        if (a <= 0f) {
            a += 360
        }
        a = a.coerceIn(0f, 250f)
        if (last < 150f && a == 250f) {
            a = 0f
        }
        last = a
        appliedAngle = a
    }
    LaunchedEffect(key1 = appliedAngle) {
        onChange?.invoke(
            range(
                appliedAngle / 250f * 100,
                maxIndicatorValue
            )
        )
    }


    //new code
    var allowedIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue) {
        indicatorValue
    } else {
        maxIndicatorValue
    }

    var animatedIndicatorValue by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue
    }

    val percentage =
        (animatedIndicatorValue / maxIndicatorValue)

    val sweepAngle by animateFloatAsState(
        targetValue = (250 * percentage),
        animationSpec = tween(1000)
    )

    val receivedValue by animateFloatAsState(
        targetValue = allowedIndicatorValue,
        animationSpec = tween(1000)
    )
    LaunchedEffect(key1 = receivedValue) {
        onChange?.invoke(receivedValue)
    }
    val animatedBigTextColor by animateColorAsState(
        targetValue = if (allowedIndicatorValue == 0f)
            MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
        else
            bigTextColor,
        animationSpec = tween(1000)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onSizeChanged {
                size = it
            }
    ) {
        Canvas(
            modifier = modifier
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
                }
        ) {

            drawArc(
                color = backgroundColor,
                startAngle = 145f, //120,-240
                sweepAngle = 250f,  // 300
                topLeft = center - Offset(radius, radius),
                size = Size(radius * 2, radius * 2),
                useCenter = false,
                style = Stroke(
                    width = stroke,
                    cap = cap
                )
            )
            if (isStarted) {
                drawArc(
                    color = progressColor,
                    startAngle = 145f,
                    sweepAngle = sweepAngle,
                    topLeft = center - Offset(radius, radius),
                    size = Size(radius * 2, radius * 2),
                    useCenter = false,
                    style = Stroke(
                        width = stroke,
                        cap = cap
                    )
                )
            } else {
                drawArc(
                    color = progressColor,
                    startAngle = 145f,
                    sweepAngle = appliedAngle,
                    topLeft = center - Offset(radius, radius),
                    size = Size(radius * 2, radius * 2),
                    useCenter = false,
                    style = Stroke(
                        width = stroke,
                        cap = cap
                    )
                )
                drawCircle(
                    color = thumbColor,
                    radius = stroke,
                    center = center + Offset(
                        radius * cos((145 + appliedAngle) * PI / 180f).toFloat(),
                        radius * sin((145 + appliedAngle) * PI / 180f).toFloat()
                    )
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Text(
                modifier = Modifier.clickable { onChangeType() },
                text = if (isDuration) "Duration" else "Distance", fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = bigTextColor
            )
            AnimatedVisibility(visible = isStarted) {
                Text(
                    text = if (isDuration) {
                        "%.0f $bigTextSuffix".format(
                            receivedValue
                        )
                    } else {
                        "%.1f $bigTextSuffix".format(
                            receivedValue
                        )
                    },
                    color = animatedBigTextColor,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            AnimatedVisibility(visible = !isStarted) {
                Text(
                    text = if (isDuration) {
                        "%.0f $bigTextSuffix".format(
                            range(appliedAngle / 250f * 100f, maxIndicatorValue)
                        )
                    } else {
                        "%.1f $bigTextSuffix".format(
                            range(appliedAngle / 250f * 100f, maxIndicatorValue)
                        )
                    },
                    color = animatedBigTextColor,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }

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
fun range(value: Float, maxIndicatorValue: Float): Float {
    val old_value = value
    val old_min = 0f
    val old_max = 100f
    val new_min = 0f
    val new_max = maxIndicatorValue
    return (((old_value - old_min) * (new_max - new_min)) / (old_max - old_min)) + new_min
}

