package fit.asta.health.designsystem.molecular.animations

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import fit.asta.health.designsystem.AppTheme
import kotlinx.coroutines.delay


// Preview Function
@Preview("Light Button")
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        Surface {
            AppDotTypingAnimation()
        }
    }
}


/**
 * This function provides a 3 Dot Animation which is equivalent ot the typing Animation which we get
 * in different Social Media like Instagram
 *
 * @param modifier This is for [Modifier] modifications from the parent function
 * @param circleSize This is the size of the circle
 * @param spaceBetween This the space between each Circles
 * @param travelDistance This is the distance each circle would travel
 * @param contentAlignment This is the Alignment of the whole loading Animation
 */
@Composable
fun AppDotTypingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = AppTheme.iconSize.level3,
    spaceBetween: Dp = AppTheme.spacing.level1,
    travelDistance: Dp = AppTheme.customSize.level3,
    contentAlignment: Alignment = Alignment.Center,
) {

    val circles = listOf(remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) })

    circles.forEachIndexed { index, animationTable ->
        LaunchedEffect(key1 = animationTable) {
            delay(index * 100L)
            animationTable.animateTo(
                targetValue = 1f, animationSpec = infiniteRepeatable(animation = keyframes {
                    durationMillis = 1200
                    0.0f at 0 with LinearOutSlowInEasing
                    1.0f at 300 with LinearOutSlowInEasing
                    0.0f at 600 with LinearOutSlowInEasing
                    0.0f at 1200 with LinearOutSlowInEasing
                }, repeatMode = RepeatMode.Restart)
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }

    Box(contentAlignment = contentAlignment, modifier = modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spaceBetween)
        ) {
            circleValues.forEach { value ->
                Box(modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        color = AppTheme.colors.primary,
                        shape = CircleShape
                    )
                )
            }
        }
    }
}