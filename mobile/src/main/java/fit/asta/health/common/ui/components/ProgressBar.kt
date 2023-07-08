package fit.asta.health.common.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.spacing


@Composable
fun ProgressBarInt(
    modifier: Modifier,
    progress: Float,
    targetDistance: Float,
    name: String,
    postfix:String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "%.0f $postfix".format(targetDistance),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        val animatedProgress = animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        ).value

        LinearProgressIndicator(
            modifier = Modifier.clip(RoundedCornerShape(6.dp)),
            progress = animatedProgress,
            backgroundColor = Color.LightGray,
            color = Color.Magenta
        )
        Text(
            text = name, fontSize = 12.sp, fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProgressBarFloat(
    modifier: Modifier,
    progress: Float,
    targetDistance: Float,
    name: String,
    postfix:String="Litres"
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "%.1f $postfix".format(targetDistance),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        val animatedProgress = animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        ).value

        LinearProgressIndicator(
            modifier = Modifier.clip(RoundedCornerShape(6.dp)),
            progress = animatedProgress,
            backgroundColor = Color.LightGray,
            color = Color.Magenta
        )
        Text(
            text = name, fontSize = 12.sp, fontWeight = FontWeight.Bold
        )
    }
}
