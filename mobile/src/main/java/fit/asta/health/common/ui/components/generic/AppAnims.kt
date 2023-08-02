package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import fit.asta.health.common.ui.theme.spacing
import kotlin.math.absoluteValue

/** [AppDivider] is a compose method, which creates a horizontal divider line.

 * [AppDividerLineWidth] define an object containing custom divider widths.
 */


object AppDividerLineWidth {
    val TstDividerWidth = 71.dp
}

/** @param lineWidth (required): The width of the divider line. */

@Composable
fun AppDivider(lineWidth: Dp) {
    Divider(
        color = MaterialTheme.colorScheme.primary,
        thickness = spacing.extraSmall,
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .width(width = lineWidth)
    )
}


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

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

        val transformation =
            lerp(
                start = 0.7f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        alpha = transformation
        scaleY = transformation
    }


