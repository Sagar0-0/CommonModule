package fit.asta.health.onboarding.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import fit.asta.health.R
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.onboarding.modal.OnboardingData
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingPager(
    state: ResponseState<List<OnboardingData>>,
    onReload: () -> Unit,
    onFinish: () -> Unit
) {
    when (state) {
        ResponseState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation()
            }
        }

        ResponseState.NoInternet -> {
            AppErrorScreen(onTryAgain = onReload)
        }
        is ResponseState.Error -> {
            AppErrorScreen(
                onTryAgain = onReload,
                desc = state.error.message ?: "ERROR 404",
                imgID = (R.drawable.error_404)
            )
        }

        is ResponseState.Success -> {
            val items = state.data
            val coroutine = rememberCoroutineScope()

            val pagerState = rememberPagerState(
                pageCount = items.size,
                initialPage = 0,
                initialOffscreenLimit = items.size - 1,
                infiniteLoop = false
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (items[page].type == 1) {
                            GifImage(
                                url = getImgUrl(url = items[page].imgUrl),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = spacing.medium),
                                contentScale = ContentScale.FillWidth
                            )
                        } else {
                            AsyncImage(
                                model = getImgUrl(url = items[page].imgUrl),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Text(
                            text = items[page].title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = spacing.extraMedium),
                            textAlign = TextAlign.Center,
                            text = items[page].desc,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                PagerIndicator(size = items.size, currentPage = pagerState.currentPage)

                BottomNavigationSection(
                    lastPage = pagerState.currentPage == items.size - 1,
                    onNextClick = {
                        coroutine.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    onSkipClick = {
                        onFinish()
                    }
                )
            }
        }

        else -> {}
    }

}