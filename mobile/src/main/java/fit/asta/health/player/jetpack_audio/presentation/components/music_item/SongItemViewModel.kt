package fit.asta.health.player.jetpack_audio.presentation.components.music_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_POSITION_MS
import fit.asta.health.player.jetpack_audio.exo_player.MusicServiceConnection
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject



@HiltViewModel
class SongItemViewModel @Inject constructor(
    musicServiceConnection: MusicServiceConnection,
) : ViewModel() {
    val musicState = musicServiceConnection.musicState
    val currentPosition = musicServiceConnection.currentPosition.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = DEFAULT_POSITION_MS,
    )
}
