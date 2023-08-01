package fit.asta.health.tools.meditation.view.audio_meditation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.player.jetpack_audio.domain.utils.asFormattedString
import fit.asta.health.player.jetpack_audio.domain.utils.convertToProgress
import fit.asta.health.player.jetpack_audio.exo_player.common.MusicState
import fit.asta.health.player.jetpack_audio.presentation.screens.player.components.PlayerButtons
import fit.asta.health.player.jetpack_audio.presentation.screens.player.components.PlayerHeader
import fit.asta.health.player.jetpack_audio.presentation.screens.player.components.PlayerImage
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.LocalSpacing
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.MinContrastOfPrimaryVsSurface
import fit.asta.health.player.jetpack_audio.presentation.utils.DynamicThemePrimaryColorsFromImage
import fit.asta.health.player.jetpack_audio.presentation.utils.contrastAgainst
import fit.asta.health.player.jetpack_audio.presentation.utils.rememberDominantColorState
import fit.asta.health.player.jetpack_audio.presentation.utils.verticalGradientScrim


@Composable
fun AudioMeditationScreen(
    onBackPressed: () -> Unit,
    onAudioEvent:(AudioMeditationEvents)->Unit,
    musicState:MusicState,
    currentPosition:Long
) {

    val progress by animateFloatAsState(
        targetValue = convertToProgress(count = currentPosition, total = musicState.duration)
    )

    val spacing = LocalSpacing.current

    val surfaceColor = MaterialTheme.colorScheme.surface
    val dominantColorState = rememberDominantColorState { color ->
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }
    val img = getImgUrl(url = musicState.currentSong.artworkUri.toString())
    DynamicThemePrimaryColorsFromImage(dominantColorState) {

        LaunchedEffect(musicState.currentSong.artworkUri) {
            dominantColorState.updateColorsFromImageUrl(img)
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
            PlayerHeader(onBackPress = onBackPressed)
            Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
            PlayerImage(
                modifier = Modifier
                    .fillMaxWidth(),
                trackImageUrl = musicState.currentSong.artworkUri,
            )
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
                onValueChange = {onAudioEvent(AudioMeditationEvents.SkipTo(it)) }
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
                play = { onAudioEvent(AudioMeditationEvents.Play) },
                pause = { onAudioEvent(AudioMeditationEvents.Pause) },
                replay10 = {onAudioEvent(AudioMeditationEvents.SkipBack) },
                forward10 = { onAudioEvent(AudioMeditationEvents.SkipForward)},
                next = { onAudioEvent(AudioMeditationEvents.SkipNext) },
                previous = { onAudioEvent(AudioMeditationEvents.SkipPrevious) }
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        }
    }
}
