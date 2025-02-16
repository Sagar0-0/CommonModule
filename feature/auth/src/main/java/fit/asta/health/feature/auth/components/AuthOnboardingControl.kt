package fit.asta.health.feature.auth.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.common.utils.getVideoUrl
import fit.asta.health.data.onboarding.remote.model.OnBoardingDataType
import fit.asta.health.data.onboarding.remote.model.Onboarding
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.appShimmerAnimation
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppGifImage
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.auth.screens.AuthUiEvent
import fit.asta.health.resources.drawables.R

@Composable
@Preview
private fun AuthOnboardingSuccessPreview() {
    AppTheme {
        Box(Modifier.fillMaxSize()) {
            OnBoardingSuccess(
                items = listOf(
                    Onboarding(
                        desc = "errem",
                        id = "litora",
                        title = "TITLE HERE",
                        type = 1,
                        url = "https://duckduckgo.com/?q=quisque",
                        ver = 9370,
                        vis = false
                    )
                )
            )
        }
    }
}

/**
 * This function handles the On Boarding Data Pager UI Data states. It Determines which UI to show
 * according to the state of the API call.
 *
 * @param onboardingState This is the state variable for the on boarding api call
 * @param onUiEvent This is the event variable to set UI Events for the View Model Layers
 */
@Composable
fun BoxScope.AuthOnboardingControl(
    onboardingState: UiState<List<Onboarding>>,
    onUiEvent: (AuthUiEvent) -> Unit
) {
    // on Boarding API call State
    AppUiStateHandler(
        uiState = onboardingState,
        onErrorRetry = {
            onUiEvent(AuthUiEvent.GetOnboardingData)
        },
        onLoading = {
            Box(
                modifier = Modifier
                    .padding(horizontal = AppTheme.spacing.level3)
                    .fillMaxSize()
                    .clip(AppTheme.shape.level1)
                    .appShimmerAnimation(isVisible = true)
            )
        },
        errorMessageUi = {
            Box(
                modifier = Modifier
                    .padding(horizontal = AppTheme.spacing.level3)
                    .fillMaxSize()
                    .clip(AppTheme.shape.level1)
                    .appShimmerAnimation(isVisible = false)
                    .clickable {
                        onUiEvent(AuthUiEvent.GetOnboardingData)
                    }
            ) {
                AppLocalImage(painter = painterResource(id = R.drawable.error_404))
            }
        }
    ) {
        OnBoardingSuccess(it)
    }
}

/**
 * This function shows the onboarding Data Pager UI which is a sliding window which shows the
 * on boarding data.
 *
 * @param items This is the [Onboarding] list which contains all the data items to be shown in the
 * Data Pager
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BoxScope.OnBoardingSuccess(items: List<Onboarding>) {

    val pagerState = rememberPagerState { items.size }
    AppHorizontalPager(
        pagerState = pagerState,
        contentPadding = PaddingValues(horizontal = AppTheme.spacing.level1),
        pageSpacing = AppTheme.spacing.level2,
        userScrollEnabled = true,
        enableAutoAnimation = true
    ) { page ->
        AppCard(
            modifier = Modifier.fillMaxSize(),
            shape = AppTheme.shape.level1
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (items[page].type) {
                    OnBoardingDataType.Image.type, OnBoardingDataType.GIF.type -> {
                        AppGifImage(
                            modifier = Modifier.fillMaxSize(),
                            url = getImageUrl(url = items[page].url),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    OnBoardingDataType.Video.type -> {
                        VideoViewUI(
                            modifier = Modifier.fillMaxSize(),
                            onPlay = page == pagerState.currentPage,
                            onPause = page != pagerState.currentPage,
                            mediaItem = MediaItem
                                .Builder()
                                .setUri(getVideoUrl(url = items[page].url).toUri()).build()
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                        .align(alignment = Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    AppTheme.colors.inverseOnSurface
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.spacing.level2,
                            vertical = AppTheme.spacing.level4
                        )
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
                ) {
                    TitleTexts.Level1(
                        text = items[page].title,
                        style = AppTheme.customTypography.title.level1,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                    BodyTexts.Level3(
                        text = items[page].desc,
                        style = AppTheme.customTypography.body.level3,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    // This function draws the Dot Indicator for the Pager
    AppExpandingDotIndicator(
        modifier = Modifier
            .padding(bottom = AppTheme.spacing.level2)
            .align(Alignment.BottomCenter),
        pagerState = pagerState
    )
}