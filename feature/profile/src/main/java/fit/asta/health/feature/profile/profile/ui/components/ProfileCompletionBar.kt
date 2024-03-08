package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Composable
fun ProfileCompletionBar(
    progress: Int
) {
    val progressBarColor = remember(progress) {
        if (progress < 30) {
            Color.Red
        } else if (progress < 65) {
            Color.Yellow
        } else {
            Color.Green
        }
    }
    val progressFloat = rememberSaveable(progress) {
        progress.toFloat() / 100f
    }
    val size by animateFloatAsState(
        targetValue = progressFloat, label = "",
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.boxSize.level3),
        contentAlignment = Alignment.Center
    ) {
        //Progressbar background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(AppTheme.shape.level1)
                .background(AppTheme.colors.background)
        )
        //Progress
        Box(
            modifier = Modifier
                .fillMaxWidth(size)
                .fillMaxHeight()
                .clip(AppTheme.shape.level1)
                .background(progressBarColor)
                .animateContentSize()
        )

        CaptionTexts.Level3(text = "${progress}% Completed")
    }
}