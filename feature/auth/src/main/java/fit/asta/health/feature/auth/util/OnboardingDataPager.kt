package fit.asta.health.feature.auth.util

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.getVideoUrl
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.carouselTransition
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ColumnScope.OnboardingDataPager(items: List<OnboardingData>) {
    val pagerState = rememberPagerState(pageCount = { items.size })
    Column(modifier = Modifier.weight(1f)) {
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
                when (items[page].type) {
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
                            onPlay = page == pagerState.currentPage,
                            onPause = page != pagerState.currentPage,
                            mediaItem = MediaItem.Builder()
                                .setUri(getVideoUrl(url = items[page].url).toUri()).build()
                        )
                    }
                }


                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleTexts.Level2(
                        modifier = Modifier.padding(horizontal = AppTheme.spacing.level3),
                        text = items[page].title,
                        textAlign = TextAlign.Center
                    )
                    TitleTexts.Level2(
                        modifier = Modifier.padding(horizontal = AppTheme.spacing.level3),
                        text = items[page].desc,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        PagerIndicator(size = items.size, currentPage = pagerState.currentPage)
    }
}


@Composable
fun VideoView(
    mediaItem: MediaItem,
    uiState: VideoState = VideoState(
        controllerType = ControllerType.None,
        resizeMode = ResizeMode.FixedWidth,
        useArtwork = true
    ),
    onPlay: Boolean,
    onPause: Boolean
) {
    val player by rememberManagedExoPlayer()

    val state = rememberMediaState(player = player)
    LaunchedEffect(player) {
        Log.d("TAG", "VideoView: ")
        player?.run {
            setMediaItem(mediaItem)
            playWhenReady = false
            repeatMode = Player.REPEAT_MODE_ONE
            prepare()
        }
    }
    LaunchedEffect(onPlay) {
        if (onPlay) {
            player?.play()
        }
    }
    LaunchedEffect(onPause) {
        if (onPause) {
            player?.pause()
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

@Composable
fun PagerIndicator(size: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(vertical = AppTheme.spacing.level6)
    ) {
        repeat(size) {
            Indicator(isSelected = it == currentPage)
            Spacer(modifier = Modifier.width(AppTheme.spacing.level0))
        }
    }
}


@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) AppTheme.customSize.level3 else AppTheme.customSize.level3,
        label = ""
    )

    Box(
        modifier = Modifier
            .height(AppTheme.customSize.level3)
            .padding(AppTheme.spacing.level3)
            .width(width.value)
            .clip(AppTheme.shape.level3)
            .background(
                if (isSelected) AppTheme.colors.primary else AppTheme.colors.onBackground.copy(
                    alpha = AppTheme.alphaValues.level2
                )
            )
    )
}