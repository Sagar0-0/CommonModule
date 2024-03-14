package fit.asta.health.sunlight.feature.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Composable
fun DurationRecommendationCard(
    time: MutableState<String>? = null,
    constTime: String? = null,
    tool: String,
    title: String
) {
    val color = Color(247, 125, 32, 255)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CaptionTexts.Level4(
            text = time?.value ?: constTime ?: "",
        )
        AppDivider(
            modifier = Modifier
                .width(100.dp),
            thickness = 1.dp,
            color = AppTheme.colors.onSurface
        )
        CaptionTexts.Level4(
            text = tool,
        )
        CaptionTexts.Level4(
            text = title,
        )
    }
}