package fit.asta.health.feature.auth.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppGifImage
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.auth.screens.AuthUiEvent

@Composable
@Preview
private fun AuthOnboardingSuccessPreview() {
    AppTheme {
        Box(Modifier.fillMaxSize()) {
            OnBoardingSuccess(
                items = listOf(
                    OnboardingData(
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
    AppHorizontalPager(
        pagerState = pagerState,
        contentPadding = PaddingValues(horizontal = AppTheme.spacing.level3),
        pageSpacing = AppTheme.spacing.level2,
        enableAutoAnimation = false,
        userScrollEnabled = true
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
                            url = getImgUrl(url = items[page].url),
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
                        style = AppTheme.customTypography.title.level1.copy(
                            shadow = Shadow(
                                color = DefaultShadowColor,
                                offset = Offset(4f, 4f),
                                blurRadius = 8f
                            )
                        ),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                    BodyTexts.Level3(
                        text = items[page].desc,
                        style = AppTheme.customTypography.body.level3.copy(
                            shadow = Shadow(
                                color = DefaultShadowColor,
                                offset = Offset(4f, 4f),
                                blurRadius = 8f
                            )
                        ),
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