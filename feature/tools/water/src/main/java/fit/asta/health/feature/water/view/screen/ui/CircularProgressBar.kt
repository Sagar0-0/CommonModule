package fit.asta.health.feature.water.view.screen.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun CircularProgressBar(
    percentage: Float,
    number : Int,
    fontSize : TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration:Int = 1000,
    animDelay:Int = 0
) {
    var animationPlayed by remember{
        mutableStateOf(false)
    }
    val percent = try {
        percentage
    } catch (e: ArithmeticException) {
        0f
    }
    val currPercent = animateFloatAsState(
        targetValue = if(animationPlayed) percent else 0f, label = "",
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )
    val remPercent = 1f - percentage
    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    Box(modifier = Modifier.size(radius*2f),
        contentAlignment = Alignment.Center){
        Canvas(modifier = Modifier.size(radius*2f)){
            if(remPercent > currPercent.value) {
                drawArc(
                    color = Color(0xffF4AF1B),
                    -90f,
                    sweepAngle = 360 * remPercent,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = Color(0xff6b9bd1),
                    -90f,
                    sweepAngle = 360 * currPercent.value,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
            else{
                drawArc(
                    color = Color(0xff6b9bd1),
                    -90f,
                    sweepAngle = 360 * currPercent.value,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = Color(0xffF4AF1B),
                    -90f,
                    sweepAngle = 360 * remPercent,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        val text = when(number){
            1 -> "Goal"
            else -> (currPercent.value * number).toInt().toString()
        }

        BodyTexts.Level1(
            text = text,
        )
//        Text(
//            text = valueText,
//            color = Color.Black,
//            fontSize = fontSize
//        )
    }
}