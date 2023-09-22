package fit.asta.health.player.jetpack_audio.presentation.screens.home.discover

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.network.utils.NetworkResult
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants
import fit.asta.health.player.jetpack_audio.exo_player.MusicServiceConnection
import fit.asta.health.tools.meditation.model.MeditationRepo
import fit.asta.health.tools.meditation.model.domain.mapper.getMusicTool
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
@androidx.annotation.OptIn(UnstableApi::class)
class DiscoverViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection,
    private val meditationRepo: MeditationRepo
) : ViewModel() {

    private val _state = MutableStateFlow(DiscoverViewState())
    private val list = mutableListOf<Song>()
    val state: StateFlow<DiscoverViewState>
        get() = _state

    init {
        loadMusicData()
    }

    fun loadMusicData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                selectedAlbum = list,
                isLoading = true,
                errorMessage = null
            )
            meditationRepo.getMusicTool(
                uid = "6309a9379af54f142c65fbfe"
            ).collectLatest { result ->
                when (result) {
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        result.data?.let { netMusicRes ->
                            val data = netMusicRes.getMusicTool()
                            list.add(
                                Song(
                                    id = 55,
                                    artist = data.music.artist_name,
                                    artworkUri = "https://img2.asta.fit${data.music.artist_url}".toUri(),
                                    duration = 4,
                                    mediaUri = "https://stream1.asta.fit/${data.music.music_url}".toUri(),
                                    title = data.music.music_name,
                                )
                            )
                            data.instructor.forEachIndexed {index,it->
                                list.add(
                                    Song(
                                        id =index ,
                                        artist = it.artist_name,
                                        artworkUri = "https://img2.asta.fit/tags/Breathing+Tag.png".toUri(),
                                        duration = 4,
                                        mediaUri = "https://stream1.asta.fit${it.music_url}".toUri(),
                                        title = it.music_name,
                                    )
                                )
                            }
                            Log.d("subhash", "loadMusicData: ${list}")
                            _state.value = _state.value.copy(
                                selectedAlbum = list,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }

                    is NetworkResult.Error -> {}
                    else -> {}
                }
            }

        }
    }

    fun onEvent(event: DiscoverEvents) {
        when (event) {
            is DiscoverEvents.PlaySound -> {
                playPauseList(
                    isRunning = event.isRunning,
                    playWhenReady = event.playWhenReady,
                    startIndex = event.idx,
                    list = state.value.selectedAlbum
                )
            }
        }
    }

    fun playPauseList(
        list: List<Song>,
        startIndex: Int = MediaConstants.DEFAULT_INDEX,
        isRunning: Boolean = false,
        playWhenReady: Boolean = false
    ) {
        if (isRunning) {
            if (!playWhenReady) {
                musicServiceConnection.play()
            } else {
                musicServiceConnection.pause()
            }
        } else {
            musicServiceConnection.playSongs(
                songs = list,
                startIndex = startIndex
            )
        }
    }

}