@file:OptIn(ExperimentalPagerApi::class)

package fit.asta.health.designsystem.molecular.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import fit.asta.health.designsystem.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**The [AppHorizontalPager] composable function is a custom component in Jetpack Compose, a declarative UI
 *  framework for Android. This component provides a horizontally scrollable banner or carousel
 *  with animation and dot indicators. It takes a list of items (bannerList) and displays them
 *  one by one, allowing users to swipe horizontally to view different items.
 *  @param modifier: (Optional) A Compose Modifier that allows you to customize the appearance and
 *  behavior of the AppBanner component.
 *  @param bannerList: A list of items (List<T>) that you want to display in the banner. Replace
 *  <T> with the type of items you are using in the list.
 *  @param content: A lambda function (@Composable BoxScope.(page: Int) -> Unit) that defines the
 *  content to be displayed for each page in the banner. It takes the current page index as
 *  an argument, allowing you to customize the content for each item in the list.
 * */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <T> AppHorizontalPager(
    bannerList: List<T>,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(page: Int) -> Unit,
) {

    val pagerState = rememberPagerState(pageCount = bannerList.size)
    val scope = rememberCoroutineScope()

    val animationDelay = 2500L
    val minScale = 0.85f
    val maxScale = 1f

    LaunchedEffect(pagerState.currentPage) {
        scope.launch {
            delay(animationDelay)
            val newPosition = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(newPosition)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { page ->
            Box(
                modifier = modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = minScale,
                            stop = maxScale,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                    }
                    .fillMaxWidth()
            ) {
                content(page)
            }

            //Horizontal dot indicator
            AppHorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = AppTheme.spacing.level2)
            )
        }
    }
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