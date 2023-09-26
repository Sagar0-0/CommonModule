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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlin.math.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircularSlider(
    modifier: Modifier = Modifier,
    indicatorValue: Float = 2f,
    maxIndicatorValue: Float = 120f,
    bigTextColor: Color = MaterialTheme.colorScheme.onSurface,
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
    appliedAngleDurationValue: Float = 0f,
    appliedAngleDistanceValue: Float = 0f,
    onChangeDuration: ((Float) -> Unit)? = null,
    onChangeDistance: ((Float) -> Unit)? = null,
    onChangeAngleDuration: ((Float) -> Unit)? = null,
    onChangeAngelDistance: ((Float) -> Unit)? = null,
    onChangeType: () -> Unit,
    onScroll:(Boolean)->Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var width by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }
    var angleDuration by rememberSaveable { mutableStateOf(-35f) }
    var angleDistance by rememberSaveable { mutableStateOf(-35f) }
    var lastDuration by rememberSaveable { mutableStateOf(0f) }
    var lastDistance by rememberSaveable { mutableStateOf(0f) }
    var down by remember { mutableStateOf(false) }
    var radius by remember { mutableStateOf(0f) }
    var center by remember { mutableStateOf(Offset.Zero) }
    var appliedAngleDuration by remember(appliedAngleDurationValue) {
        mutableStateOf(appliedAngleDurationValue)
    }
    var appliedAngleDistance by remember(appliedAngleDistanceValue) {
        mutableStateOf(appliedAngleDistanceValue)
    }
    LaunchedEffect(key1 = angleDuration) {
        var a = angleDuration
        a += 35
        if (a <= 0f) {
            a += 360
        }
        a = a.coerceIn(0f, 250f)
        if (lastDuration < 145f && a == 250f) {
            a = 0f
        }
        lastDuration = a
        appliedAngleDuration = a
    }
    LaunchedEffect(key1 = appliedAngleDuration) {
        onChangeAngleDuration?.invoke(appliedAngleDuration)
        onChangeDuration?.invoke(
            range(
                value = appliedAngleDuration / 250f * 100,
                maxIndicatorValue = 120f
            )
        )
    }

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
        onChangeAngelDistance?.invoke(appliedAngleDistance)
        onChangeDistance?.invoke(
            range(
                value = appliedAngleDistance / 250f * 100,
                maxIndicatorValue = 3f
            )
        )
    }

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
        (allowedIndicatorValue / maxIndicatorValue)

    val sweepAngle by animateFloatAsState(
        targetValue = (250 * percentage),
        animationSpec = tween(1000)
    )

    val receivedValue by animateFloatAsState(
        targetValue = allowedIndicatorValue,
        animationSpec = tween(1000)
    )
    val animatedBigTextColor by animateColorAsState(
        targetValue = if (allowedIndicatorValue == 0f)
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
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
                            if (!isStarted && d >= radius - touchStroke / 2f && d <= radius + touchStroke / 2f && a !in -135f..-40f) {
                                down = true
                                if (isDuration) angleDuration = a
                                else angleDistance = a
                            } else {
                                down = false
                            }
                        }

                        MotionEvent.ACTION_MOVE -> {
                            if (isStarted) onScroll(true) else onScroll(false)
                            val a = angle(center, offset)
                            if (down && a !in -135f..-40f) {
                                if (isDuration) angleDuration = a
                                else angleDistance = a
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
                startAngle = 145f,
                sweepAngle = 250f,
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
                        width = stroke,
                        cap = cap
                    )
                )
                if (isDuration) {
                    drawCircle(
                        color = thumbColor,
                        radius = stroke,
                        center = center + Offset(
                            radius * cos((145 + appliedAngleDuration) * PI / 180f).toFloat(),
                            radius * sin((145 + appliedAngleDuration) * PI / 180f).toFloat()
                        )
                    )
                } else {
                    drawCircle(
                        color = thumbColor,
                        radius = stroke,
                        center = center + Offset(
                            radius * cos((145 + appliedAngleDistance) * PI / 180f).toFloat(),
                            radius * sin((145 + appliedAngleDistance) * PI / 180f).toFloat()
                        )
                    )
                }
            } else {
                if (isDuration) {
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
                        sweepAngle = appliedAngleDuration,
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
                            radius * cos((145 + appliedAngleDuration) * PI / 180f).toFloat(),
                            radius * sin((145 + appliedAngleDuration) * PI / 180f).toFloat()
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
                            width = stroke,
                            cap = cap
                        )
                    )
                    drawCircle(
                        color = thumbColor,
                        radius = stroke,
                        center = center + Offset(
                            radius * cos((145 + appliedAngleDistance) * PI / 180f).toFloat(),
                            radius * sin((145 + appliedAngleDistance) * PI / 180f).toFloat()
                        )
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)
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
                            range(appliedAngleDuration / 250f * 100f, maxIndicatorValue)
                        )
                    } else {
                        "%.1f $bigTextSuffix".format(
                            range(appliedAngleDistance / 250f * 100f, maxIndicatorValue)
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

