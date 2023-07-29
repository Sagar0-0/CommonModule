@file:OptIn(ExperimentalPagerApi::class)

package fit.asta.health.common.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <T> AppBanner(
    bannerList: List<T>,
    content: @Composable BoxScope.(page: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    val pagerState = rememberPagerState(pageCount = bannerList.size)
    val scope = rememberCoroutineScope()

    val bannerListSize = bannerList.size
    val animationDelay = 2500L
    val minScale = 0.85f
    val maxScale = 1f

    LaunchedEffect(pagerState.currentPage) {
        scope.launch {
            delay(animationDelay)
            var newPosition = pagerState.currentPage + 1
            if (newPosition > (bannerListSize - 1)) newPosition = 0
            pagerState.animateScrollToPage(newPosition)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { page ->
            Box(modifier = modifier
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
                .fillMaxWidth()) {
                content(page)
            }
            //Horizontal dot indicator
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }

}