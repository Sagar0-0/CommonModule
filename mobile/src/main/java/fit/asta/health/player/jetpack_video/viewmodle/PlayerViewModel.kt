package fit.asta.health.player.jetpack_video.viewmodle

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_video.data.model.VideoItem
import fit.asta.health.player.jetpack_video.video.UiEvent
import fit.asta.health.player.jetpack_video.video.UiState
import fit.asta.health.tools.meditation.model.MeditationRepo
import fit.asta.health.tools.meditation.model.domain.mapper.getMusicTool
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val meditationRepo: MeditationRepo,
    val player: Player
) : ViewModel() {

    companion object {
        private const val TAG = "PlayerViewModel"
    }

    val listener = object : Player.Listener {
        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
            // recover the remembered state if media id matched
            rememberedState
                ?.let { (id, index, position) ->
                    if (!timeline.isEmpty
                        && timeline.windowCount > index
                        && id == timeline.getWindow(index, window).mediaItem.mediaId
                    ) {
                        player.seekTo(index, position)
                    }
                }
                ?.also { rememberedState = null }
        }
    }

    private val _videoList = mutableStateListOf<VideoItem>()
    val videoList = MutableStateFlow(_videoList)
    private val list = mutableListOf<Song>()
    private val _uiState = mutableStateOf(UiState())
    val uiState: State<UiState> = _uiState

    val currentVideo: MutableState<VideoItem?> = mutableStateOf(null)
    init {
        player.apply {
            playWhenReady = _uiState.value.playWhenReady
            loadMusicData()
            prepare()
            addListener(listener)
            Log.d("devil", "prepare")
        }
    }

    fun player(): Player = player
    private var rememberedState: Triple<String, Int, Long>? = null
    private val window: Timeline.Window = Timeline.Window()

    fun play() = player.play()

    fun saveState() {
        player.currentMediaItem?.let { mediaItem ->
            rememberedState = Triple(
                mediaItem.mediaId,
                player.currentMediaItemIndex,
                player.currentPosition
            )
        }
    }

    fun pause() = player.pause()
    fun loadMusicData() {
        viewModelScope.launch {
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
                                    artistId = 333,
                                    artworkUri = "https://img2.asta.fit${data.music.artist_url}".toUri(),
                                    album = "",
                                    albumId = 5566,
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
                                        artistId = index.toLong(),
                                        artworkUri = "https://img2.asta.fit/tags/Breathing+Tag.png".toUri(),
                                        album = "hi",
                                        albumId = index.toLong(),
                                        duration = 4,
                                        mediaUri = "https://stream1.asta.fit${it.music_url}".toUri(),
                                        title = it.music_name,
                                    )
                                )
                            }
                            val fakeVideos = mutableListOf<VideoItem>()
                            val music ="https://stream1.asta.fit/video/Day02.mp4"
                            list.forEachIndexed { index, song ->
                                fakeVideos.add(
                                    VideoItem(
                                        contentUri = song.mediaUri, name = song.title,
                                        mediaItem = MediaItem.Builder().setMediaId(song.title).setUri(song.mediaUri).build()
                                    )
                                )
                            }
                            _videoList.addAll(fakeVideos)
                            currentVideo.value = fakeVideos.first()
                            player.addMediaItems(fakeVideos.map { it.mediaItem })
                            Log.d("subhash", "loadMusicData: ${_videoList.toList()}")
                        }
                    }

                    is NetworkResult.Error -> {}
                    else -> {}
                }
            }

        }
    }
    fun event(event: UiEvent) {
        when (event) {
            is UiEvent.Start -> play()
            is UiEvent.Stop -> {
                saveState()
                pause()
            }

            is UiEvent.SetPlayer -> {
                _uiState.value = _uiState.value.copy(setPlayer = event.value)
            }

            is UiEvent.SetUrl -> {
                Log.d("subhash", "event: ${event.value}")
                _uiState.value = _uiState.value.copy(url = event.value)
                playVideo(event.value.toUri())
            }

            is UiEvent.SetControllerType -> {
                _uiState.value = _uiState.value.copy(controllerType = event.value)
            }

            is UiEvent.SetShowBuffering -> {
                _uiState.value = _uiState.value.copy(showBuffering = event.value)
            }

            is UiEvent.SetSurfaceType -> {
                _uiState.value = _uiState.value.copy(surfaceType = event.value)
            }

            is UiEvent.SetUseArtwork -> {
                _uiState.value = _uiState.value.copy(useArtwork = event.value)
            }

            is UiEvent.SetResizeMode -> {
                _uiState.value = _uiState.value.copy(resizeMode = event.value)
            }

            is UiEvent.ControllerAutoShow -> {
                _uiState.value = _uiState.value.copy(controllerAutoShow = event.value)
            }

            is UiEvent.ControllerHideOnTouchType -> {
                _uiState.value = _uiState.value.copy(controllerHideOnTouchType = event.value)
            }

            is UiEvent.PlayWhenReady -> {
                _uiState.value = _uiState.value.copy(playWhenReady = event.value)
                player.playWhenReady = event.value
            }

            is UiEvent.KeepContentOnPlayerReset -> {
                _uiState.value = _uiState.value.copy(keepContentOnPlayerReset = event.value)
            }
        }
    }



    fun playVideo(uri: Uri) {
        player.setMediaItem(
            MediaItem.fromUri(uri)
        )
        currentVideo.value = _videoList.find { it.contentUri == uri } ?: return
    }

    override fun onCleared() {
        super.onCleared()
        player.removeListener(listener)
        player.release()
        Log.d(TAG, "onCleared: ")
    }


}