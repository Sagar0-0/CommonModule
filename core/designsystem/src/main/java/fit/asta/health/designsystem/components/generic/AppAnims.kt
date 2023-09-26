package fit.asta.health.designsystem.components.generic

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import fit.asta.health.designsystemx.AstaThemeX

/** [AppDivider] is a compose method, which creates a horizontal divider line.
 * [AppDividerLineWidth] define an object containing custom divider widths.
 */


object AppDividerLineWidth {
    val TstDividerWidth = 71.dp
}

/** @param modifier (optional) - A set of modifiers to customize the layout and behavior of the [AppDivider].
 * @param lineWidth (required): The width of the divider line. */

@Composable
fun AppDivider(
    lineWidth: Dp,
    modifier: Modifier = Modifier,
) {
    Divider(
        color = MaterialTheme.colorScheme.primary,
        thickness = AstaThemeX.appSpacing.extraSmall,
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .width(width = lineWidth)
    )
}

/**[AppProgressArc] is a custom composable function in Jetpack Compose, which provides a circular
 * progress indicator for the app.
 * @param modifier (optional) - A set of modifiers to customize the layout and behavior of the AppProgressArc.
 * @param strokeWidth (optional) - The width of the circular progress indicator stroke.
 * */

@Composable
fun AppProgressArc(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = strokeWidth,
        trackColor = ProgressIndicatorDefaults.circularTrackColor,
        strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
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
        activeColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
        inactiveColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        indicatorWidth = AstaThemeX.appSpacing.small,
        indicatorHeight = AstaThemeX.appSpacing.small,
        spacing = AstaThemeX.appSpacing.small,
        indicatorShape = CircleShape
    )
}


