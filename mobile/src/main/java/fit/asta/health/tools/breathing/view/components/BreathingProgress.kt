package fit.asta.health.tools.breathing.view.components

import android.util.Log
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import kotlin.math.min

@Composable
fun BreathingProgress(
    modifier: Modifier = Modifier,
    start: Boolean,
    color: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
    bgColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
    stroke: Float = 5f,
    cap: StrokeCap = StrokeCap.Round,
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var width by remember { mutableIntStateOf(0) }
    var height by remember { mutableIntStateOf(0) }
    var radius by remember { mutableFloatStateOf(0f) }
    var smallRadius by remember { mutableFloatStateOf(0f) }
    var changeRadius by remember { mutableFloatStateOf(0f) }
    var center by remember { mutableStateOf(Offset.Zero) }
    var startCount by remember { mutableIntStateOf(4) }
    var endCount by remember { mutableIntStateOf(4) }

    val angle by animateFloatAsState(
        targetValue = changeRadius,
        animationSpec = tween(4000, easing = FastOutLinearInEasing)
    )

//    val angle = remember {
//        Animatable(smallRadius)
//    }
//    LaunchedEffect(key1 = startCount) {
//        val a = (radius- smallRadius)/4f
//        delay(1000)
//        if (startCount>0) startCount--
//        changeRadius +=a
//    }
//    LaunchedEffect(angle) {
//        launch {
//            angle.animateTo(radius, animationSpec = tween(4000, easing = FastOutLinearInEasing))
//        }
////        if (angle.value==smallRadius){
////            launch {
////                angle.animateTo(radius, animationSpec = tween(4000, easing = FastOutLinearInEasing))
////            }
////        } else{
////            launch {
////                angle.animateTo(smallRadius, animationSpec = tween(4000, easing = FastOutLinearInEasing))
////            }
////        }
//    }
    val infiniteTransition = rememberInfiniteTransition()
    val sizeBig by infiniteTransition.animateValue(
        initialValue = 200.dp,
        targetValue = 190.dp,
        Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val smallCircle by infiniteTransition.animateValue(
        initialValue = smallRadius,
        targetValue = radius,
        Float.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
//    val animatedBigTextColor by animateColorAsState(
//        targetValue = if (allowedIndicatorValue == 0f)
//            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
//        else
//            bigTextColor,
//        animationSpec = tween(1000)
//    )

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
                    radius = min(width.toFloat(), height.toFloat()) / 2f
//                    changeRadius = min(width.toFloat(), height.toFloat()) / 4f
                    smallRadius = min(width.toFloat(), height.toFloat()) / 4f
                }
        ) {
            drawArc(
                color = Color.Green,
                startAngle = 0f,
                sweepAngle = 360f,
                topLeft = center - Offset(radius, radius),
                size = Size(radius * 2, radius * 2),
                useCenter = false,
                style = Stroke(
                    width = stroke,
                    cap = cap
                )
            )
            drawCircle(
                color = bgColor,
                center = center,
                radius = radius
            )
            drawCircle(
                color = color,
                center = center,
                radius = angle
            )
            drawCircle(
                color = Color.White,
                center = center,
                radius = smallRadius
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small)
        ) {
            Text(text = "$startCount", modifier = Modifier.clickable {
                changeRadius = if (changeRadius == 300f) smallRadius
                else radius
                Log.d("subhash", "BreathingProgress: $changeRadius")
            })
        }
    }


}

@Composable
@Preview
fun Test() {
    BreathingProgress(start = true, modifier = Modifier.size(200.dp))
//    PulsatingCircles("Compose")
}

@Composable
fun PulsatingCircles(text: String) {
    Column {
        val infiniteTransition = rememberInfiniteTransition()
        val size by infiniteTransition.animateValue(
            initialValue = 200.dp,
            targetValue = 190.dp,
            Dp.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(500, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        val smallCircle by infiniteTransition.animateValue(
            initialValue = 150.dp,
            targetValue = 200.dp,
            Dp.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            SimpleCircleShape2(
                size = 200.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
            )
            SimpleCircleShape2(
                size = smallCircle,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
            )
            SimpleCircleShape2(
                size = 130.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = text,
                        style = TextStyle().copy(color = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleCircleShape2(
    size: Dp,
    color: Color = Color.White,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.LightGray.copy(alpha = 0.0f)
) {
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(
                    color
                )
                .border(borderWidth, borderColor)
        )
    }
}