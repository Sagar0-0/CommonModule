package fit.asta.health.tools.meditation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.getVideoUrl
import fit.asta.health.datastore.PrefManager
import fit.asta.health.network.utils.NetworkResult
import fit.asta.health.player.audio.MusicServiceConnection
import fit.asta.health.player.domain.mapper.asMediaItem
import fit.asta.health.player.domain.model.Song
import fit.asta.health.player.domain.utils.MediaConstants
import fit.asta.health.player.domain.utils.convertToPosition
import fit.asta.health.player.presentation.screens.player.PlayerEvent
import fit.asta.health.tools.meditation.db.MeditationData
import fit.asta.health.tools.meditation.model.LocalRepo
import fit.asta.health.tools.meditation.model.MeditationRepo
import fit.asta.health.tools.meditation.model.domain.mapper.getMusicTool
import fit.asta.health.tools.meditation.model.network.PostRes
import fit.asta.health.tools.meditation.model.network.Prc
import fit.asta.health.tools.meditation.model.network.PutData
import fit.asta.health.tools.meditation.model.network.Value
import fit.asta.health.tools.meditation.view.home.HomeUiState
import fit.asta.health.tools.meditation.view.home.MEvent
import fit.asta.health.tools.meditation.view.music.MusicEvents
import fit.asta.health.tools.meditation.view.music.MusicViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
@androidx.annotation.OptIn(UnstableApi::class)
class MeditationViewModel @Inject constructor(
    private val meditationRepo: MeditationRepo,
    private val musicServiceConnection: MusicServiceConnection,
    private val localRepo: LocalRepo,
    private val player: Player,
    private val prefManager: PrefManager,
    @UID private val uId: String
) : ViewModel() {
    fun getPlayer() = player
    private var backgroundPlayer: Player? = null
    private val date = LocalDate.now().dayOfMonth
    private val _selectedLevel = MutableStateFlow("Beginner 1")
    val selectedLevel: StateFlow<String> = _selectedLevel

    private val _selectedMusic = MutableStateFlow("relaxing")
    val selectedMusic: StateFlow<String> = _selectedMusic

    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    private val _selectedInstructor = MutableStateFlow("Darlene Robertson")
    val selectedInstructor: StateFlow<String> = _selectedInstructor

    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    private val music = MutableStateFlow<Song?>(null)
    private val _state = MutableStateFlow(MusicViewState())
    private val list = mutableListOf<Song>()
    private var rememberedState: Triple<String, Int, Long>? = null
    private val window: Timeline.Window = Timeline.Window()
    private val _trackList = mutableStateListOf("")
    val trackList = MutableStateFlow(_trackList)
    val track = prefManager.userData
        .map { it.trackLanguage }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = "hi",
        )
    val state: StateFlow<MusicViewState>
        get() = _state

    private val listener = object : Player.Listener {
        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
            // recover the remembered state if media id matched
            Log.d("TAG", "onTimelineChanged: change")
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
    val listSong = listOf(
        Song(
            id = 1,
            artist = "it.artist_name",
            artworkUri = getImgUrl("/tags/Breathing+Tag.png").toUri(),
            duration = duration(),
            mediaUri = "https://storage.googleapis.com/downloads.webmproject.org/av1/exoplayer/bbb-av1-480p.mp4".toUri(),
            title = "Day 1",
        ),
        Song(
            id = 2,
            artist = "it.artist_name",
            artworkUri = getImgUrl("/tags/Water+Tag.png").toUri(),
            duration = duration(),
            mediaUri = "https://d28fbw0qer0joz.cloudfront.net/video/Multilanguage+Video.mp4".toUri(),
            title = "Day 2",
        ),
        Song(
            id = 3,
            artist = "it.artist_name",
            artworkUri = getImgUrl("/tags/Sleep+Tag.png").toUri(),
            duration = duration(),
            mediaUri = "https://d28fbw0qer0joz.cloudfront.net/video/Multilanguage+Video.mp4".toUri(),
            title = "Day 3",
        ),
        Song(
            id = 4,
            artist = "it.artist_name",
            artworkUri = getImgUrl("/tags/Walking+Tag.png").toUri(),
            duration = duration(),
            mediaUri = "https://d28fbw0qer0joz.cloudfront.net/video/Multilanguage+Video.mp4".toUri(),
            title = "Day 2",
        )
    )

    init {
        player.apply {
            addListener(listener)
        }
        loadData()
        loadLocalData()
        loadMusicData()
    }


    private fun saveState() {
        player.currentMediaItem?.let { mediaItem ->
            rememberedState = Triple(
                mediaItem.mediaId,
                player.currentMediaItemIndex,
                player.currentPosition
            )
        }
    }

    fun event(event: MEvent) {
        when (event) {
            is MEvent.SetTarget -> {
                _uiState.value = _uiState.value.copy(targetValue = event.target)
            }

            is MEvent.SetTargetAngle -> {
                _uiState.value = _uiState.value.copy(targetAngle = event.angle)
            }

            is MEvent.SetLanguage -> {
                _selectedLanguage.value = event.language
            }

            is MEvent.SetLevel -> {
                _selectedLevel.value = event.level
            }

            is MEvent.SetInstructor -> {
                _selectedInstructor.value = event.instructor
            }

            is MEvent.Start -> {
                if (_uiState.value.targetValue < 1f) {
                    Toast.makeText(event.context, "select target", Toast.LENGTH_SHORT).show()
                } else {
                    putMeditationData()
                    setBackgroundSound(context = event.context)
                }
            }

            is MEvent.End -> {
                postData()
                release(context = event.context)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            meditationRepo.getMeditationTool(
                uid = "6309a9379af54f142c65fbfe",
                date = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
                    .toString()//"2023-03-27"
            ).collectLatest { result ->
                when (result) {
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        result.data?.data.let { mdata ->
                            _uiState.value = _uiState.value.copy(
                                target = mdata!!.meditationProgressData.tgt,
                                achieved = mdata.meditationProgressData.ach,
                                recommended = mdata.meditationProgressData.rcm,
                                remaining = mdata.meditationProgressData.rem,
                            )
                        }
                    }

                    is NetworkResult.Error -> {}
                }
            }

        }
    }

    private fun loadLocalData() {
        viewModelScope.launch {
            val data = localRepo.getWaterData(date = LocalDate.now().dayOfMonth)
            if (data != null) {
                _uiState.value = _uiState.value.copy(
                    start = data.start,
                    targetAngle = data.appliedAngleDistance,
                    progress_angle = data.appliedAngleDistance,
                    consume = data.time.toFloat()
                )
            } else {// val time = ((System.nanoTime() - data.time) / 1_000_000_000L / 60L).toInt()
                localRepo.insert(
                    MeditationData(
                        date = date,
                        appliedAngleDistance = 0f, start = false, time = 0
                    )
                )
            }
            musicServiceConnection.currentProgress.collect { progress ->
                val result = localRepo.getWaterData(date = date)
                if (result != null) {
                    var pro: Long = 0
                    pro = if (progress >= result.time) progress else result.time + 1
                    localRepo.updateTime(date = date, time = pro)
                    _uiState.value = _uiState.value.copy(
                        consume = pro.toFloat()
                    )
                }
            }
        }
    }

    private fun putMeditationData() {
        val music = Prc(
            id = "",
            code = "music",
            title = "music",
            description = "music",
            type = 1,
            values = listOf(
                Value(
                    id = "", name = _selectedMusic.value, value = _selectedMusic.value
                )
            )
        )
        val target = Prc(
            id = "",
            code = "target",
            title = "target",
            description = "target",
            type = 1,
            values = listOf(
                Value(
                    id = "",
                    name = _uiState.value.targetValue.toInt().toString(),
                    value = _uiState.value.targetValue.toInt().toString()
                )
            )
        )
        val instructor = Prc(
            id = "",
            code = "instructor",
            title = "instructor",
            description = "instructor",
            type = 1,
            values = listOf(
                Value(
                    id = "", name = _selectedInstructor.value, value = _selectedInstructor.value
                )
            )
        )
        val level = Prc(
            id = "",
            code = "level",
            title = "level",
            description = "level",
            type = 1,
            values = listOf(
                Value(
                    id = "", name = _selectedLevel.value, value = _selectedLevel.value
                )
            )
        )
        val language = Prc(
            id = "",
            code = "language",
            title = "language",
            description = "language",
            type = 1,
            values = listOf(
                Value(
                    id = "", name = _selectedLanguage.value, value = _selectedLanguage.value
                )
            )
        )
        val prc = mutableListOf<Prc>()
        prc.add(music)
        prc.add(target)
        prc.add(instructor)
        prc.add(level)
        prc.add(language)

        viewModelScope.launch {
            val result = meditationRepo.putMeditationData(
                PutData(
                    code = "meditation",
                    id = "",
                    prc = prc,
                    type = 3,
                    uid = "6309a9379af54f142c65fbfe",
                    wea = true
                )
            )
            when (result) {
                is NetworkResult.Success -> {
                    Log.d("subhash", "putMeditationData:Success ${result.data}")
                    loadMusicData()
                    localRepo.updateAngle(
                        date = LocalDate.now().dayOfMonth,
                        appliedAngleDistance = _uiState.value.targetAngle
                    )
                    localRepo.updateState(date = LocalDate.now().dayOfMonth, start = true)
                    localRepo.updateTime(date = LocalDate.now().dayOfMonth, time = 0)
                    loadData()
                    _uiState.value = _uiState.value.copy(start = true)
                }

                is NetworkResult.Error -> {
                    Log.d("subhash", "putMeditationData:ErrorMessage ${result.message}")
                }

                is NetworkResult.Loading -> {}
            }
        }
    }

    private fun postData() {
        viewModelScope.launch {
            val result = meditationRepo.postMeditationData(
                PostRes(
                    id = "",
                    uid = "6309a9379af54f142c65fbfe",
                    duration = _uiState.value.consume.toInt(),
                    mode = "indoor",
                    exp = 40
                )
            )
            when (result) {
                is NetworkResult.Success -> {
                    localRepo.updateState(date = LocalDate.now().dayOfMonth, start = false)
                    _uiState.value = _uiState.value.copy(start = false)
                }

                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }
    }


    private fun loadMusicData() {
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
                        Log.d("subhash", "music:Success ${result.data}")
                        result.data?.let { netMusicRes ->
                            val data = netMusicRes.getMusicTool()
                            music.value = Song(
                                id = 55,
                                artist = data.music.artist_name,
                                artworkUri = getImgUrl(data.music.imgUrl).toUri(),
                                duration = 4,
                                mediaUri = getVideoUrl(data.music.music_url).toUri(),
                                title = data.music.music_name,
                                audioList = data.music.language
                            )
                            list.clear()
                            data.instructor.forEachIndexed { index, it ->
                                val subUrl = it.music_url.split(".")
                                val url = subUrl[0] + "_hi." + subUrl[1]
                                Log.d("TAG", "loadMusicData: $url")
                                list.add(
                                    Song(
                                        id = index,
                                        artist = it.artist_name,
                                        artworkUri = getImgUrl(it.imgUrl).toUri(),
                                        duration = duration(it.duration),
                                        mediaUri = getVideoUrl(if (index > 17) url else it.music_url).toUri(),
                                        title = "Day $index",
                                        audioList = it.language
                                    )
                                )
                            }
                            list.reverse()
                            _state.value = _state.value.copy(
                                selectedAlbum = list,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        Log.d("subhash", "music:ErrorMessage ${result.message}")
                        _state.value = _state.value.copy(
                            selectedAlbum = list,
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }

        }
    }

    fun onMusicEvent(event: MusicEvents) {
        when (event) {
            is MusicEvents.PlaySound -> {
                playPauseList(
                    isRunning = event.isRunning,
                    playWhenReady = event.playWhenReady,
                    startIndex = event.idx,
                    list = state.value.selectedAlbum

                )
            }
        }
    }

    private fun playPauseList(
        list: List<Song>,
        startIndex: Int = MediaConstants.DEFAULT_INDEX,
        isRunning: Boolean = false,
        playWhenReady: Boolean = false
    ) {
        if (isRunning) {
            if (!playWhenReady) {
                musicServiceConnection.play()
                backgroundPlayer?.play()
            } else {
                musicServiceConnection.pause()
                backgroundPlayer?.pause()
            }
        } else {
            musicServiceConnection.playSongs(
                songs = list,
                startIndex = startIndex
            )
            music.value?.let {
                backgroundPlayer?.run {
                    setMediaItem(MediaItem.fromUri(it.mediaUri))
                    prepare()
                    volume = 0.3f
                    repeatMode = Player.REPEAT_MODE_ONE
                    play()
                }
            }
            _trackList.clear()
            _trackList.addAll(list[startIndex].audioList)
        }
    }


    val musicState = musicServiceConnection.musicState
    val currentPosition = musicServiceConnection.currentPosition.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = MediaConstants.DEFAULT_POSITION_MS
    )
    private val _visibility = MutableStateFlow(false)
    val visibility: StateFlow<Boolean> = _visibility

    fun setVisibility() {
        _visibility.value = !_visibility.value
    }

    fun onAudioEvent(event: PlayerEvent) {
        when (event) {
            is PlayerEvent.Play -> play()
            is PlayerEvent.Pause -> pause()
            is PlayerEvent.SkipNext -> skipNext()
            is PlayerEvent.SkipPrevious -> skipPrevious()
            is PlayerEvent.SkipTo -> skipTo(event.value)
            is PlayerEvent.SkipForward -> forward()
            is PlayerEvent.SkipBack -> backward()
        }
    }

    fun setBackgroundSound(context: Context) {
        if (backgroundPlayer != null) return
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(USAGE_MEDIA)
            .build()

        backgroundPlayer = ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, false)
            .setHandleAudioBecomingNoisy(true)
            .build()
    }

    fun release(context: Context) {
        musicServiceConnection.release(context)
        backgroundPlayer?.release()
    }

    private fun skipPrevious() {
        musicServiceConnection.skipPrevious()
        _trackList.clear()
        _trackList.addAll(list[player.previousMediaItemIndex].audioList)
    }


    fun play() {
        musicServiceConnection.play()
        backgroundPlayer?.play()
    }

    fun pause() {
        musicServiceConnection.pause()
        backgroundPlayer?.pause()
    }

    private fun skipNext() {
        musicServiceConnection.skipNext()
        _trackList.clear()
        _trackList.addAll(list[player.nextMediaItemIndex].audioList)
    }

    private fun skipTo(position: Float) =
        musicServiceConnection.skipTo(convertToPosition(position, musicState.value.duration))

    private fun forward() = musicServiceConnection.forward()
    private fun backward() = musicServiceConnection.backward()

    fun duration(value: String = "00:15:33"): Int {
        val data = value.split(":")
        return data[1].toInt() + (data[2].toInt() / 60)
    }

    fun onTrackChange(language: String) {
        saveState()
        viewModelScope.launch { prefManager.setTrackLanguage(language) }
        val subUrl = list[player.currentMediaItemIndex].mediaUri.toString().split("_")
        val url = subUrl.first() + "_" + language + "." + subUrl.last().split(".").last()
        Log.d("TAG", "onTrackChange: $url")
        musicServiceConnection.changeTrack(
            list[player.currentMediaItemIndex]
                .copy(mediaUri = url.toUri()).asMediaItem()
        )
    }

    override fun onCleared() {
        super.onCleared()
        player.removeListener(listener)
        player.release()
        backgroundPlayer?.release()
    }
}