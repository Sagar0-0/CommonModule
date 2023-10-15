package fit.asta.health.designsystem.molecular.animations

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import fit.asta.health.designsystem.AppTheme


/**
 *  @param modifier (optional) - A set of modifiers to customize the layout and behavior
 *  of the [AppDivider].
 *  @param thickness This is the thickness of the Divider Line which would be created
 *  @param color This is the Color of the App Divider Line
 */
@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = AppTheme.spacing.level0,
    color: Color = AppTheme.colors.primary,
) {
    Divider(
        color = color, thickness = thickness, modifier = modifier.clip(AppTheme.shape.level0)
    )
}


/**[AppCircularProgressIndicator] is a custom composable function in Jetpack Compose, which provides a circular
 * progress indicator for the app.
 * @param modifier (optional) - A set of modifiers to customize the layout and behavior of the AppProgressArc.
 * @param strokeWidth (optional) - The width of the circular progress indicator stroke.
 * */
@Composable
fun AppCircularProgressIndicator(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
) {
    CircularProgressIndicator(
        modifier = modifier, color = AppTheme.colors.primary, strokeWidth = strokeWidth
    )
}


/**[AppHorizontalPagerIndicator] function is a Composable function that displays a horizontal pager
 * indicator.
 * @param modifier An optional parameter that allows you to apply styling modifications to
 * the HorizontalPagerIndicator.
 * @param pagerState A required parameter that represents the state of the pager. It holds
 * information about the current page, the number of items, and allows controlling the pager's behavior.
 * */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppHorizontalPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = modifier,
        activeColor = AppTheme.colors.onSurface,
        inactiveColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level3),
        indicatorWidth = AppTheme.spacing.level1,
        indicatorHeight = AppTheme.spacing.level1,
        spacing = AppTheme.spacing.level1,
        indicatorShape = CircleShape
    )
}


