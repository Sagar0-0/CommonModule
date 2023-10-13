package fit.asta.health.feature.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.getVideoUrl
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.carouselTransition
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppRetryCard
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppGifImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.player.media.Media
import fit.asta.health.player.media.ResizeMode
import fit.asta.health.player.media.rememberMediaState
import fit.asta.health.player.presentation.ControllerType
import fit.asta.health.player.presentation.component.VideoState
import fit.asta.health.player.presentation.component.rememberManagedExoPlayer
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    state: UiState<List<OnboardingData>>,
    onReload: () -> Unit,
    onFinish: () -> Unit
) {
    when (state) {
        is UiState.ErrorRetry -> {
            AppRetryCard(text = state.resId.toStringFromResId()) {
                onReload()
            }
        }

        UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AppCircularProgressIndicator()
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
                    contentPadding = PaddingValues(AppTheme.spacing.level2),
                    pageSpacing = AppTheme.spacing.level3,
                ) { page ->
                    AppCard(
                        modifier = Modifier
                            .carouselTransition(page, pagerState)
                            .fillMaxHeight()
                            .padding(AppTheme.spacing.level2)
                            .clip(AppTheme.shape.level3)
                    ) {
                        when (state.data[page].type) {
                            1, 2 -> {
                                AppGifImage(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = AppTheme.spacing.level3),
                                    url = getImgUrl(url = items[page].url),
                                    contentScale = ContentScale.FillWidth
                                )
                            }

                            3 -> {
                                VideoView(
                                    url = getVideoUrl(url = items[page].url),
                                )
                            }
                        }


                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TitleTexts.Level2(
                                modifier = Modifier.padding(horizontal = AppTheme.spacing.level4),
                                text = items[page].title,
                                textAlign = TextAlign.Center
                            )
                            TitleTexts.Level2(
                                modifier = Modifier.padding(horizontal = AppTheme.spacing.level4),
                                text = items[page].desc,
                                textAlign = TextAlign.Center
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

@Composable
fun VideoView(
    url: String,
    uiState: VideoState = VideoState(
        controllerType = ControllerType.None,
        resizeMode = ResizeMode.FixedWidth,
        useArtwork = true
    )
) {
    val player by rememberManagedExoPlayer()
    val mediaItem = remember {
        MediaItem.Builder().setUri(url.toUri()).setMediaId(url).build()
    }
    val state = rememberMediaState(player = player)
    LaunchedEffect(mediaItem, player) {
        player?.run {
            setMediaItem(mediaItem)
            playWhenReady = false
            prepare()
            stop()
        }
    }

    Box(
        modifier = Modifier.background(Color.Black), contentAlignment = Alignment.Center
    ) {
        Media(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(AppTheme.aspectRatio.wideScreen)
                .background(Color.Black),
            surfaceType = uiState.surfaceType,
            resizeMode = uiState.resizeMode,
            keepContentOnPlayerReset = uiState.keepContentOnPlayerReset,
            useArtwork = uiState.useArtwork,
            showBuffering = uiState.showBuffering,
            buffering = {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    AppCircularProgressIndicator()
                }
            },
            errorMessage = { error ->
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    BodyTexts.Level1(text = error.message ?: "", color = AppTheme.colors.error)
                }
            },
            controllerHideOnTouch = uiState.controllerHideOnTouchType,
            controllerAutoShow = uiState.controllerAutoShow,
            controller = null
        )
    }
}