package fit.asta.health.player.jetpack_audio.presentation.screens.player

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.palette.graphics.Palette
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_POSITION_MS
import fit.asta.health.player.jetpack_audio.domain.utils.convertToPosition
import fit.asta.health.player.jetpack_audio.exo_player.MusicServiceConnection
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject



@HiltViewModel
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class PlayerViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection,
    private val player: Player
) : ViewModel() {

    fun getPlayer()=player
    val musicState = musicServiceConnection.musicState
    val currentPosition = musicServiceConnection.currentPosition.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = DEFAULT_POSITION_MS
    )

    fun onEvent(event: PlayerEvent) {
        when (event) {
            is PlayerEvent.Play -> play()
            is PlayerEvent.Pause -> pause()
            is PlayerEvent.SkipNext -> skipNext()
            is PlayerEvent.SkipPrevious -> skipPrevious()
            is PlayerEvent.SkipTo -> skipTo(event.value)
            is PlayerEvent.SkipForward -> forword()
            is PlayerEvent.SkipBack -> backword()
        }
    }

    fun skipPrevious() = musicServiceConnection.skipPrevious()
    fun play() = musicServiceConnection.play()
    fun pause() = musicServiceConnection.pause()
    fun skipNext() = musicServiceConnection.skipNext()
    fun skipTo(position: Float) =
        musicServiceConnection.skipTo(convertToPosition(position, musicState.value.duration))

    fun  forword()= musicServiceConnection.forword()
    fun backword()= musicServiceConnection.backword()
    fun calculateColorPalette(drawable: Bitmap, onFinish: (Color) -> Unit) {
        Palette.from(drawable).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}