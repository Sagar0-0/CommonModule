package fit.asta.health.designsystem.molecular.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import fit.asta.health.designsystem.AppTheme

/**
 * This function makes a Dot indicator for the [AppHorizontalPager] which would indicate the pages
 * of the Pager.
 *
 * @param modifier This is for passing Modifications from the Parent Composable Function
 * @param pagerState This is the pagerState according to which we would show the indicator
 * @param dotHeight This is the height of the indicator composable
 * @param activeDotWidth This is the active width of the indicator composable
 * @param inactiveDotWidth This is the in-active width of the indicator composable
 * @param activeColor This is the active color of the indicator composable
 * @param inactiveColor This is the in-active color of the indicator composable
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppExpandingDotIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    dotHeight: Dp = AppTheme.customSize.level1,
    activeDotWidth: Dp = AppTheme.customSize.level3,
    inactiveDotWidth: Dp = AppTheme.customSize.level1,
    activeColor: Color = AppTheme.colors.primary,
    inactiveColor: Color = AppTheme.colors.onSurfaceVariant
) {

    // This is the parent composable
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        repeat(pagerState.pageCount) {

            // This keeps if the Element is the one being showed or not
            val selected = pagerState.currentPage == it

            Box(
                modifier = Modifier
                    .height(dotHeight)
                    .width(if (selected) activeDotWidth else inactiveDotWidth)
                    .clip(AppTheme.shape.level3)
                    .background(if (selected) activeColor else inactiveColor)
            )
        }
    }
}