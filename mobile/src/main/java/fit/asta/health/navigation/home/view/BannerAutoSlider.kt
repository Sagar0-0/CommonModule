package fit.asta.health.navigation.home.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import fit.asta.health.navigation.home.model.domain.Banner
import fit.asta.health.navigation.home.view.component.BannerLayout
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


@ExperimentalPagerApi
@Composable
fun BannerAutoSlider(bannerList: List<Banner>) {

    val pagerState = rememberPagerState(pageCount = bannerList.size)

    LaunchedEffect(key1 = pagerState.currentPage) {
        delay(2500)
        var newPosition = pagerState.currentPage + 1
        if (newPosition > (bannerList.size - 1)) newPosition = 0
        pagerState.animateScrollToPage(newPosition)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        HorizontalPager(state = pagerState,
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally) { page ->
            Box(modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                }
                .fillMaxWidth()
                .height(236.dp)) {
                val sliderDataPages = bannerList[page]
                BannerLayout(sliderDataPages, pagerState)
            }
        }
    }
}

