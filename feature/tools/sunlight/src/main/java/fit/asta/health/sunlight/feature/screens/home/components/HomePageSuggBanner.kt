package fit.asta.health.sunlight.feature.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.sunlight.feature.utils.getBannerDrawable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageSugBanner(tips: ArrayList<String>?) {
    val state = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tips?.size ?: 0
    }
    val scope = rememberCoroutineScope()
    val onPageChange: (Int) -> Unit = { no ->
        if (no in 0..6) {
            scope.launch { state.animateScrollToPage(no) }
        }
    }
    LaunchedEffect(state.currentPage) {
        delay(4000)
        val nextPage = if (state.currentPage < state.pageCount - 1) state.currentPage + 1 else 0
        onPageChange.invoke(nextPage)
    }
    AppCard(modifier = Modifier.padding(vertical = AppTheme.spacing.level2)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            AppHorizontalPager(
                pagerState = state
            ) { index ->
                AppLocalImage(
                    painter = painterResource(id = index.getBannerDrawable()),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.BottomCenter)
                        .alpha(0.6f)
                )
                HeadingTexts.Level3(
                    text = "“" + tips?.getOrNull(index) + "”",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(AppTheme.spacing.level2),
                    textAlign = TextAlign.Center
                )
            }
            AppExpandingDotIndicator(
                pagerState = state,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(AppTheme.spacing.level1)
            )
        }
    }
}