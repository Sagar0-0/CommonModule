package fit.asta.health.onboarding.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import fit.asta.health.R
import fit.asta.health.common.ui.components.GifImage
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.navigation.home.view.component.ErrorScreenLayout
import fit.asta.health.onboarding.vm.OnboardingGetState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingPager(
    state: OnboardingGetState,
    onReload: () -> Unit,
    onFinish: () -> Unit
) {
    when (state) {
        OnboardingGetState.Loading -> {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
        }

        OnboardingGetState.NoInternet -> {
            ErrorScreenLayout(onTryAgain = onReload)
        }

        OnboardingGetState.Empty -> {
            ErrorScreenLayout(
                onTryAgain = onReload,
                desc = "Nothing found",
                imgID = (R.drawable.error_404)
            )
        }

        is OnboardingGetState.Error -> {
            ErrorScreenLayout(
                onTryAgain = onReload,
                desc = state.error.message ?: "ERROR 404",
                imgID = (R.drawable.error_404)
            )
        }

        is OnboardingGetState.Success -> {
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
                                url = getImageUrl(url = items[page].imgUrl),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = spacing.medium),
                                contentScale = ContentScale.FillWidth
                            )
                        } else {
                            AsyncImage(
                                model = getImageUrl(url = items[page].imgUrl),
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

    }

}


@Preview
@Composable
fun PreviewOnBoard() {
    OnBoardingPager(
        OnboardingGetState.Loading,
        {

        }
    ) {

    }
}