package fit.asta.health.feature.onboarding.components

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import fit.asta.health.designsystem.AppTheme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun rememberPagerState(
    @androidx.annotation.IntRange(from = 0) pageCount: Int,
    @androidx.annotation.IntRange(from = 0) initialPage: Int,
    @FloatRange(from = 0.0, to = 1.0) initialPageOffset: Float = 0f,
    @androidx.annotation.IntRange(from = 1) initialOffscreenLimit: Int = 1,
    infiniteLoop: Boolean = false,
): PagerState = rememberSaveable(saver = PagerState.Saver) {
    PagerState(
        pageCount = pageCount,
        currentPage = initialPage,
        currentPageOffset = initialPageOffset,
        offscreenLimit = initialOffscreenLimit,
        infiniteLoop = infiniteLoop
    )
}


@Composable
fun PagerIndicator(size: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(vertical = AppTheme.spacing.level6)
    ) {
        repeat(size) {
            Indicator(isSelected = it == currentPage)
            Spacer(modifier = Modifier.width(AppTheme.spacing.level0))
        }
    }
}


@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) AppTheme.customSize.level3 else AppTheme.customSize.level3,
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