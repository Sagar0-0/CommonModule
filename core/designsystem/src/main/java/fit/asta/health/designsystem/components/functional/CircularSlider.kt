package fit.asta.health.designsystem.components.functional


import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
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
import fit.asta.health.designsystemx.AstaThemeX
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircularSliderInt(
    // progress in 1 2 3 4 range
    modifier: Modifier = Modifier,
    indicatorValue: Float = 2f,
    maxIndicatorValue: Float = 6f,
    bigTextColor: Color = MaterialTheme.colorScheme.onSurface,
    bigTextSuffix: String = "Min",
    padding: Float = 50f,
    stroke: Float = 20f,
    cap: StrokeCap = StrokeCap.Round,
    touchStroke: Float = 50f,
    thumbColor: Color = Color.Blue,
    backgroundColor: Color = Color.LightGray,
    isStarted: Boolean,
    appliedAngleDistanceValue: Float = 0f,
    onChangeDistance: ((Float) -> Unit)? = null,
    onChangeAngleDistance: ((Float) -> Unit)? = null,
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var width by remember { mutableIntStateOf(0) }
    var height by remember { mutableIntStateOf(0) }
    var angleDistance by rememberSaveable { mutableFloatStateOf(-35f) }
    var lastDistance by rememberSaveable { mutableFloatStateOf(0f) }
    var down by remember { mutableStateOf(false) }
    var radius by remember { mutableFloatStateOf(0f) }
    var center by remember { mutableStateOf(Offset.Zero) }
    var appliedAngleDistance by remember(appliedAngleDistanceValue) {
        mutableFloatStateOf(appliedAngleDistanceValue)
    }

    if (!isStarted) {
        LaunchedEffect(key1 = angleDistance) {
            var a = angleDistance
            a += 35
            if (a <= 0f) {
                a += 360
            }
            a = a.coerceIn(0f, 250f)
            if (lastDistance < 145f && a == 250f) {
                a = 0f
            }
            lastDistance = a
            appliedAngleDistance = a
        }
        LaunchedEffect(key1 = appliedAngleDistance) {
            onChangeAngleDistance?.invoke(appliedAngleDistance)
            onChangeDistance?.invoke(
                range(
                    value = appliedAngleDistance / 250f * 100, maxIndicatorValue = maxIndicatorValue
                )
            )
        }
    }

    var allowedIndicatorValue by remember {
        mutableFloatStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue) {
        indicatorValue
    } else {
        maxIndicatorValue
    }

    var animatedIndicatorValue by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue
    }

    val percentage = (allowedIndicatorValue / maxIndicatorValue)

    val sweepAngle by animateFloatAsState(
        targetValue = (250 * percentage), animationSpec = tween(1000), label = ""
    )

    val receivedValue by animateFloatAsState(
        targetValue = allowedIndicatorValue, animationSpec = tween(1000), label = ""
    )
    val animatedBigTextColor by animateColorAsState(
        targetValue = if (allowedIndicatorValue == 0f) MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.3f
        )
        else bigTextColor, animationSpec = tween(1000), label = ""
    )

    Box(contentAlignment = Alignment.Center, modifier = modifier.onSizeChanged {
        size = it
    }) {
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
                        if (!isStarted && d >= radius - touchStroke / 2f && d <= radius + touchStroke / 2f && a !in -135f..-40f) {
                            down = true
                            angleDistance = a
                        } else {
                            down = false
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val a = angle(center, offset)
                        if (down && a !in -135f..-40f) {
                            angleDistance = a
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
                startAngle = 145f,
                sweepAngle = 250f,
                topLeft = center - Offset(radius, radius),
                size = Size(radius * 2, radius * 2),
                useCenter = false,
                style = Stroke(
                    width = stroke, cap = cap
                )
            )
            if (isStarted) {
                drawArc(
                    brush = Brush.linearGradient(
                        0f to Color.Yellow,
                        0.2f to Color.Green,
                        0.3f to Color.Red,
                        0.4f to Color.Magenta,
                        0.5f to Color.Blue,
                        0.6f to Color.Yellow,
                        0.7f to Color.Green,
                        0.8f to Color.Red,
                        0.9f to Color.Magenta,
                        1f to Color(0x00EF7B7B)
                    ),
                    startAngle = 145f,
                    sweepAngle = sweepAngle,
                    topLeft = center - Offset(radius, radius),
                    size = Size(radius * 2, radius * 2),
                    useCenter = false,
                    style = Stroke(
                        width = stroke, cap = cap
                    )
                )
                drawCircle(
                    color = thumbColor, radius = stroke, center = center + Offset(
                        radius * cos((145 + appliedAngleDistance) * PI / 180f).toFloat(),
                        radius * sin((145 + appliedAngleDistance) * PI / 180f).toFloat()
                    )
                )
            } else {
                drawArc(
                    brush = Brush.linearGradient(
                        0f to Color.Yellow,
                        0.2f to Color.Green,
                        0.3f to Color.Red,
                        0.4f to Color.Magenta,
                        0.5f to Color.Blue,
                        0.6f to Color.Yellow,
                        0.7f to Color.Green,
                        0.8f to Color.Red,
                        0.9f to Color.Magenta,
                        1f to Color(0x00EF7B7B)
                    ),
                    startAngle = 145f,
                    sweepAngle = appliedAngleDistance,
                    topLeft = center - Offset(radius, radius),
                    size = Size(radius * 2, radius * 2),
                    useCenter = false,
                    style = Stroke(
                        width = stroke, cap = cap
                    )
                )
                drawCircle(
                    color = thumbColor, radius = stroke, center = center + Offset(
                        radius * cos((145 + appliedAngleDistance) * PI / 180f).toFloat(),
                        radius * sin((145 + appliedAngleDistance) * PI / 180f).toFloat()
                    )
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)
        ) {
            Text(
                text = "Total", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = bigTextColor
            )
            AnimatedVisibility(visible = isStarted) {
                Text(
                    text = "%.0f $bigTextSuffix".format(
                        receivedValue
                    ),
                    color = animatedBigTextColor,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            AnimatedVisibility(visible = !isStarted) {
                Text(
                    text = "%.0f $bigTextSuffix".format(
                        range(appliedAngleDistance / 250f * 100f, maxIndicatorValue)
                    ),
                    color = animatedBigTextColor,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }


}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircularSliderFloat(
    // progress in 1.3 2.6 6.2 range
    modifier: Modifier = Modifier,
    indicatorValue: Float = 2f,
    maxIndicatorValue: Float = 6f,
    bigTextColor: Color = MaterialTheme.colorScheme.onSurface,
    bigTextSuffix: String = "Litres",
    padding: Float = 50f,
    stroke: Float = 20f,
    cap: StrokeCap = StrokeCap.Round,
    touchStroke: Float = 50f,
    thumbColor: Color = Color.Blue,
    backgroundColor: Color = Color.LightGray,
    isStarted: Boolean,
    appliedAngleDistanceValue: Float = 0f,
    onChangeDistance: ((Float) -> Unit)? = null,
    onChangeAngleDistance: ((Float) -> Unit)? = null,
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var width by remember { mutableIntStateOf(0) }
    var height by remember { mutableIntStateOf(0) }
    var angleDistance by rememberSaveable { mutableFloatStateOf(-35f) }
    var lastDistance by rememberSaveable { mutableFloatStateOf(0f) }
    var down by remember { mutableStateOf(false) }
    var radius by remember { mutableFloatStateOf(0f) }
    var center by remember { mutableStateOf(Offset.Zero) }
    var appliedAngleDistance by remember(appliedAngleDistanceValue) {
        mutableFloatStateOf(appliedAngleDistanceValue)
    }

    if (!isStarted) {
        LaunchedEffect(key1 = angleDistance) {
            var a = angleDistance
            a += 35
            if (a <= 0f) {
                a += 360
            }
            a = a.coerceIn(0f, 250f)
            if (lastDistance < 145f && a == 250f) {
                a = 0f
            }
            lastDistance = a
            appliedAngleDistance = a
        }
        LaunchedEffect(key1 = appliedAngleDistance) {
            onChangeAngleDistance?.invoke(appliedAngleDistance)
            onChangeDistance?.invoke(
                range(
                    value = appliedAngleDistance / 250f * 100, maxIndicatorValue = maxIndicatorValue
                )
            )
        }
    }

    var allowedIndicatorValue by remember {
        mutableFloatStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue) {
        indicatorValue
    } else {
        maxIndicatorValue
    }

    var animatedIndicatorValue by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue
    }

    val percentage = (allowedIndicatorValue / maxIndicatorValue)

    val sweepAngle by animateFloatAsState(
        targetValue = (250 * percentage),
        animationSpec = tween(1000), label = "",
    )

    val receivedValue by animateFloatAsState(
        targetValue = allowedIndicatorValue, animationSpec = tween(1000), label = ""
    )
    val animatedBigTextColor by animateColorAsState(
        targetValue = if (allowedIndicatorValue == 0f) MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.3f
        )
        else bigTextColor, animationSpec = tween(1000), label = ""
    )

    Box(contentAlignment = Alignment.Center, modifier = modifier.onSizeChanged {
        size = it
    }) {
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
                        if (!isStarted && d >= radius - touchStroke / 2f && d <= radius + touchStroke / 2f && a !in -135f..-40f) {
                            down = true
                            angleDistance = a
                        } else {
                            down = false
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val a = angle(center, offset)
                        if (down && a !in -135f..-40f) {
                            angleDistance = a
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
                startAngle = 145f,
                sweepAngle = 250f,
                topLeft = center - Offset(radius, radius),
                size = Size(radius * 2, radius * 2),
                useCenter = false,
                style = Stroke(
                    width = stroke, cap = cap
                )
            )
            if (isStarted) {
                drawArc(
                    brush = Brush.linearGradient(
                        0f to Color.Yellow,
                        0.2f to Color.Green,
                        0.3f to Color.Red,
                        0.4f to Color.Magenta,
                        0.5f to Color.Blue,
                        0.6f to Color.Yellow,
                        0.7f to Color.Green,
                        0.8f to Color.Red,
                        0.9f to Color.Magenta,
                        1f to Color(0x00EF7B7B)
                    ),
                    startAngle = 145f,
                    sweepAngle = sweepAngle,
                    topLeft = center - Offset(radius, radius),
                    size = Size(radius * 2, radius * 2),
                    useCenter = false,
                    style = Stroke(
                        width = stroke, cap = cap
                    )
                )
                drawCircle(
                    color = thumbColor, radius = stroke, center = center + Offset(
                        radius * cos((145 + appliedAngleDistance) * PI / 180f).toFloat(),
                        radius * sin((145 + appliedAngleDistance) * PI / 180f).toFloat()
                    )
                )
            } else {
                drawArc(
                    brush = Brush.linearGradient(
                        0f to Color.Yellow,
                        0.2f to Color.Green,
                        0.3f to Color.Red,
                        0.4f to Color.Magenta,
                        0.5f to Color.Blue,
                        0.6f to Color.Yellow,
                        0.7f to Color.Green,
                        0.8f to Color.Red,
                        0.9f to Color.Magenta,
                        1f to Color(0x00EF7B7B)
                    ),
                    startAngle = 145f,
                    sweepAngle = appliedAngleDistance,
                    topLeft = center - Offset(radius, radius),
                    size = Size(radius * 2, radius * 2),
                    useCenter = false,
                    style = Stroke(
                        width = stroke, cap = cap
                    )
                )
                drawCircle(
                    color = thumbColor, radius = stroke, center = center + Offset(
                        radius * cos((145 + appliedAngleDistance) * PI / 180f).toFloat(),
                        radius * sin((145 + appliedAngleDistance) * PI / 180f).toFloat()
                    )
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)
        ) {
            Text(
                text = "Total", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = bigTextColor
            )
            AnimatedVisibility(visible = isStarted) {
                Text(
                    text = "%.1f $bigTextSuffix".format(
                        receivedValue
                    ),
                    color = animatedBigTextColor,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            AnimatedVisibility(visible = !isStarted) {
                Text(
                    text = "%.1f $bigTextSuffix".format(
                        range(appliedAngleDistance / 250f * 100f, maxIndicatorValue)
                    ),
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
    val oldMin = 0f
    val oldMax = 100f
    val newMin = 0f
    return (((value - oldMin) * (maxIndicatorValue - newMin)) / (oldMax - oldMin)) + newMin
}

