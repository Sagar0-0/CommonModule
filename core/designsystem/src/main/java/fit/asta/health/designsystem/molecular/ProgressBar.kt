package fit.asta.health.designsystem.molecular

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.CaptionTexts


@Composable
fun ProgressBarInt(
    modifier: Modifier,
    progress: Float,
    targetDistance: Float,
    name: String,
    postfix: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CaptionTexts.Level1(
            text = "%.0f $postfix".format(targetDistance),
        )
        val animatedProgress = animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
        ).value

        LinearProgressIndicator(
            modifier = Modifier.clip(RoundedCornerShape(6.dp)),
            progress = animatedProgress,
            color = Color.Magenta,
            trackColor = Color.LightGray,
        )
        CaptionTexts.Level1(text = name)
    }
}

@Composable
fun ProgressBarFloat(
    modifier: Modifier,
    progress: Float,
    targetDistance: Float,
    name: String,
    postfix: String = "Litres"
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CaptionTexts.Level1(
            text = "%.1f $postfix".format(targetDistance),
        )
        val animatedProgress = animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
        ).value

        LinearProgressIndicator(
            modifier = Modifier.clip(RoundedCornerShape(6.dp)),
            progress = animatedProgress,
            color = Color.Magenta,
            trackColor = Color.LightGray,
        )
        CaptionTexts.Level1(
            text = name
        )
    }
}
