package fit.asta.health.designsystem.components.generic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

/**[carouselTransition] is a custom Modifier, used for creating carousel-like transitions for paged
 * elements. It allows you to add a smooth and visually appealing transition effect to elements that
 * are controlled by a PagerState.
 * @param page An integer representing the target page for which the transition should be applied.
 * This is typically the index of the page that you want to create a transition effect for.
 * @param pagerState A PagerState object that controls the pager's state. This is necessary to
 * retrieve the current page and its offset for computing the transition effect.
 * @return [carouselTransition] function returns a [Modifier] that can be applied to Compose UI elements.
 * */

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.carouselTransition(page: Int, pagerState: PagerState) = graphicsLayer {
    val pageOffset =
        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

    val transformation = lerp(
        start = 0.7f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
    alpha = transformation
    scaleY = transformation
}