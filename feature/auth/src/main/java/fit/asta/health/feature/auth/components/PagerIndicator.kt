package fit.asta.health.feature.auth.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import fit.asta.health.designsystem.AppTheme

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    size: Int,
    currentPage: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(size) {
            Indicator(isSelected = it == currentPage)
            Spacer(modifier = Modifier.width(AppTheme.spacing.level0))
        }
    }
}

@Composable
private fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected)
            AppTheme.customSize.level3
        else
            AppTheme.customSize.level3,
        label = ""
    )

    Box(
        modifier = Modifier
            .height(AppTheme.customSize.level3)
            .padding(AppTheme.spacing.level3)
            .width(width.value)
            .clip(AppTheme.shape.level3)
            .background(
                if (isSelected) AppTheme.colors.primary else AppTheme.colors.onBackground.copy(
                    alpha = AppTheme.alphaValues.level2
                )
            )
    )
}