package fit.asta.health.feature.auth.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.getVideoUrl
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.onboarding.model.OnBoardingDataType
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.appShimmerAnimation
import fit.asta.health.designsystem.atomic.modifier.carouselTransition
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppGifImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.auth.screens.AuthUiEvent


/**
 * This function handles the On Boarding Data Pager UI Data states. It Determines which UI to show
 * according to the state of the API call.
 *
 * @param onboardingState This is the state variable for the on boarding api call
 * @param onUiEvent This is the event variable to set UI Events for the View Model Layers
 */
@Composable
fun BoxScope.AuthOnboardingControl(
    onboardingState: UiState<List<OnboardingData>>,
    onUiEvent: (AuthUiEvent) -> Unit
) {

    val context = LocalContext.current

    // on Boarding API call State
    when (onboardingState) {

        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .padding(horizontal = AppTheme.spacing.level3)
                    .fillMaxSize()
                    .clip(AppTheme.shape.level1)
                    .appShimmerAnimation(isVisible = true)
            )
        }

        is UiState.Success -> {
            OnBoardingSuccess(onboardingState.data)
        }

        is UiState.NoInternet -> {
            AppInternetErrorDialog {
                onUiEvent(AuthUiEvent.GetOnboardingData)
            }
        }

        is UiState.ErrorRetry -> {
            AppErrorScreen(text = onboardingState.resId.toStringFromResId()) {
                onUiEvent(AuthUiEvent.GetOnboardingData)
            }
        }

        is UiState.ErrorMessage -> {
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    onboardingState.resId.toStringFromResId(context),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        else -> {}
    }
}

/**
 * This function shows the onboarding Data Pager UI which is a sliding window which shows the
 * on boarding data.
 *
 * @param items This is the [OnboardingData] list which contains all the data items to be shown in the
 * Data Pager
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BoxScope.OnBoardingSuccess(items: List<OnboardingData>) {

    val pagerState = rememberPagerState { items.size }
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = AppTheme.spacing.level3),
        pageSpacing = AppTheme.spacing.level2,
    ) { page ->
        AppCard(
            modifier = Modifier
                .carouselTransition(page, pagerState)
                .fillMaxHeight(),
            shape = AppTheme.shape.level1
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
            ) {
                when (items[page].type) {
                    OnBoardingDataType.Image.type, OnBoardingDataType.GIF.type -> {
                        AppGifImage(
                            modifier = Modifier.fillMaxWidth(),
                            url = getImgUrl(url = items[page].url),
                            contentScale = ContentScale.FillWidth
                        )
                    }

                    OnBoardingDataType.Video.type -> {
                        VideoViewUI(
                            onPlay = page == pagerState.currentPage,
                            onPause = page != pagerState.currentPage,
                            mediaItem = MediaItem.Builder()
                                .setUri(getVideoUrl(url = items[page].url).toUri()).build()
                        )
                    }
                }

                val textModifier = Modifier.padding(horizontal = AppTheme.spacing.level2)

                HeadingTexts.Level2(
                    modifier = textModifier,
                    text = items[page].title,
                    textAlign = TextAlign.Center
                )
                BodyTexts.Level1(
                    modifier = textModifier,
                    text = items[page].desc,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    PagerIndicator(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(vertical = AppTheme.spacing.level6),
        size = items.size,
        currentPage = pagerState.currentPage
    )
}