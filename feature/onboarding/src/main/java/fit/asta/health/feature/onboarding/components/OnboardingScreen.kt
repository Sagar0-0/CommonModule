package fit.asta.health.feature.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.designsystem.components.GifImage
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.components.generic.carouselTransition
import fit.asta.health.designsystem.theme.LocalSpacing
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.designsystemx.AstaThemeX
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    state: UiState<List<OnboardingData>>,
    onReload: () -> Unit,
    onFinish: () -> Unit
) {
    when (state) {
        UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation()
            }
        }

        is UiState.ErrorMessage -> {
            AppErrorScreen(
                onTryAgain = onReload,
                desc = state.resId.toStringFromResId()
            )
        }

        is UiState.Success -> {
            val items = state.data
            val coroutine = rememberCoroutineScope()
            val pagerState = rememberPagerState(pageCount = { items.size })

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    modifier = Modifier.weight(1f),
                    state = pagerState,
                    contentPadding = PaddingValues(LocalSpacing.current.small),
                    pageSpacing = LocalSpacing.current.medium,
                ) { page ->
                    Card(
                        modifier = Modifier
                            .carouselTransition(page, pagerState)
                            .fillMaxHeight()
                            .padding(LocalSpacing.current.small)
                            .clip(AstaThemeX.shapeX.large)
                    ) {
                        GifImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = spacing.medium),
                            url = getImgUrl(url = items[page].url),
                            contentScale = ContentScale.FillWidth
                        )

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
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