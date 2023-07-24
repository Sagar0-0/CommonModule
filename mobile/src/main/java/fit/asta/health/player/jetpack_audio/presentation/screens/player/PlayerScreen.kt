package fit.asta.health.player.jetpack_audio.presentation.screens.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.player.jetpack_audio.domain.utils.asFormattedString
import fit.asta.health.player.jetpack_audio.domain.utils.convertToProgress
import fit.asta.health.player.jetpack_audio.presentation.screens.player.components.PlayerButtons
import fit.asta.health.player.jetpack_audio.presentation.screens.player.components.PlayerHeader
import fit.asta.health.player.jetpack_audio.presentation.screens.player.components.PlayerImage
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.LocalSpacing
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.MinContrastOfPrimaryVsSurface
import fit.asta.health.player.jetpack_audio.presentation.utils.DynamicThemePrimaryColorsFromImage
import fit.asta.health.player.jetpack_audio.presentation.utils.contrastAgainst
import fit.asta.health.player.jetpack_audio.presentation.utils.rememberDominantColorState
import fit.asta.health.player.jetpack_audio.presentation.utils.verticalGradientScrim
import fit.asta.health.player.jetpack_video.media.SurfaceType
import fit.asta.health.player.jetpack_video.media.TestTag_VideoSurface
import fit.asta.health.player.jetpack_video.media.VideoSurface
import fit.asta.health.player.jetpack_video.media.rememberMediaState


@ExperimentalPagerApi
@Composable
fun PlayerScreen(
    onBackPressed: () -> Unit,
    addToPlayList: () -> Unit,
    more: () -> Unit,
    viewModel: PlayerViewModel = hiltViewModel(),
) {

    val musicState by viewModel.musicState.collectAsStateWithLifecycle()
    val currentPosition by viewModel.currentPosition.collectAsStateWithLifecycle()
    val state = rememberMediaState(player = viewModel.getPlayer().takeIf { musicState.playWhenReady })

    val progress by animateFloatAsState(
        targetValue = convertToProgress(count = currentPosition, total = musicState.duration)
    )

    val spacing = LocalSpacing.current

    val surfaceColor = MaterialTheme.colorScheme.surface
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }
    DynamicThemePrimaryColorsFromImage(dominantColorState) {

        LaunchedEffect(musicState.currentSong.artworkUri) {
            dominantColorState.updateColorsFromImageUrl(musicState.currentSong.artworkUri.toString())
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalGradientScrim(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
                    startYPercentage = 1f,
                    endYPercentage = 0f
                )
        ) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
            )
            PlayerHeader(onBackPress = onBackPressed, addToPlayList = addToPlayList, more = more)
            Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
            var visibility by remember {
                mutableStateOf(true)
            }
            AnimatedVisibility(visible =visibility ) {
                Box(modifier = Modifier.clickable { visibility=!visibility }
                    .fillMaxWidth()
                    .padding(16.dp)
                    .aspectRatio(16 / 9f)){
                    VideoSurface(
                        state = state,
                        surfaceType = SurfaceType.SurfaceView,
                        modifier = Modifier
                            .testTag(TestTag_VideoSurface)
                            .fillMaxWidth()
                    )
                }
            }
            AnimatedVisibility(visible = !visibility) {
                PlayerImage(
                    modifier = Modifier.clickable { visibility=!visibility }
                        .fillMaxWidth(),
                    trackImageUrl = musicState.currentSong.artworkUri,
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
            Text(
                text = musicState.currentSong.title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceSmall)
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Text(
                text = musicState.currentSong.album,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceSmall)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
            Slider(
                modifier = Modifier.padding(horizontal = spacing.spaceMedium),
                value = progress,
                onValueChange = {viewModel.onEvent(PlayerEvent.SkipTo(it)) }
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            ) {
                Text(text = currentPosition.asFormattedString())
                Spacer(modifier = Modifier.weight(1f))
                Text(musicState.duration.asFormattedString())
            }
            Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
            PlayerButtons(
                modifier = Modifier.fillMaxWidth(),
                playWhenReady = musicState.playWhenReady,
                play = { viewModel.onEvent(PlayerEvent.Play) },
                pause = { viewModel.onEvent(PlayerEvent.Pause) },
                replay10 = {viewModel.onEvent(PlayerEvent.SkipBack) },
                forward10 = { viewModel.onEvent(PlayerEvent.SkipForward)},
                next = { viewModel.onEvent(PlayerEvent.SkipNext) },
                previous = { viewModel.onEvent(PlayerEvent.SkipPrevious) }
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        }
    }
}
