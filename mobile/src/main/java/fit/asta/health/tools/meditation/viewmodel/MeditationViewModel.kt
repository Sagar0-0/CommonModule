package fit.asta.health.tools.meditation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants
import fit.asta.health.player.jetpack_audio.domain.utils.convertToPosition
import fit.asta.health.player.jetpack_audio.exo_player.MusicServiceConnection
import fit.asta.health.tools.meditation.db.MeditationData
import fit.asta.health.tools.meditation.model.LocalRepo
import fit.asta.health.tools.meditation.model.MeditationRepo
import fit.asta.health.tools.meditation.model.domain.mapper.getMusicTool
import fit.asta.health.tools.meditation.model.network.PostRes
import fit.asta.health.tools.meditation.model.network.Prc
import fit.asta.health.tools.meditation.model.network.PutData
import fit.asta.health.tools.meditation.model.network.Value
import fit.asta.health.tools.meditation.view.audio_meditation.AudioMeditationEvents
import fit.asta.health.tools.meditation.view.home.HomeUiState
import fit.asta.health.tools.meditation.view.home.MEvent
import fit.asta.health.tools.meditation.view.music.MusicEvents
import fit.asta.health.tools.meditation.view.music.MusicViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class MeditationViewModel @Inject constructor(
    private val meditationRepo: MeditationRepo,
    private val musicServiceConnection: MusicServiceConnection,
    private val localRepo: LocalRepo
) : ViewModel() {

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
    val state: StateFlow<MusicViewState>
        get() = _state

    init {
        loadData()
        loadLocalData()
        loadMusicData()
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
                date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
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
                    Log.d("subhash", "putMeditationData:Error ${result.message}")
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
                                artistId = 333,
                                artworkUri = "/tags/Breathing+Tag.png".toUri(),//https://img2.asta.fit
                                album = "",
                                albumId = 5566,
                                duration = 4,
                                mediaUri = "https://stream1.asta.fit/${data.music.music_url}".toUri(),
                                title = data.music.music_name,
                            )
                            list.clear()
                            data.instructor.forEachIndexed { index, it ->
                                list.add(
                                    Song(
                                        id = index,
                                        artist = it.artist_name,
                                        artistId = index.toLong(),
                                        artworkUri = "/tags/Breathing+Tag.png".toUri(),
                                        album = it.music_name,
                                        albumId = index.toLong(),
                                        duration = duration(it.duration),
                                        mediaUri = "https://stream1.asta.fit${it.music_url}".toUri(),
                                        title = "Day $index",
                                    )
                                )
                            }

                            _state.value = _state.value.copy(
                                selectedAlbum = list,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        Log.d("subhash", "music:Error ${result.message}")
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
            backgroundPlayer?.run {
                setMediaItem(MediaItem.fromUri("https://stream1.asta.fit/audio/relaxing%201.mp3"))
                prepare()
                volume = 0.3f
                repeatMode=Player.REPEAT_MODE_ONE
                play()
            }
        }
    }


    val musicState = musicServiceConnection.musicState
    val currentPosition = musicServiceConnection.currentPosition.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = MediaConstants.DEFAULT_POSITION_MS
    )


    fun onAudioEvent(event: AudioMeditationEvents) {
        when (event) {
            is AudioMeditationEvents.Play -> play()
            is AudioMeditationEvents.Pause -> pause()
            is AudioMeditationEvents.SkipNext -> skipNext()
            is AudioMeditationEvents.SkipPrevious -> skipPrevious()
            is AudioMeditationEvents.SkipTo -> skipTo(event.value)
            is AudioMeditationEvents.SkipForward -> forward()
            is AudioMeditationEvents.SkipBack -> backward()
        }
    }

    fun setBackgroundSound(context: Context) {
        if (backgroundPlayer != null) return
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(USAGE_MEDIA)
            .build()

        backgroundPlayer = ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .setAudioAttributes(audioAttributes, false)
            .setHandleAudioBecomingNoisy(true)
            .build()
    }

    fun release(context: Context) {
        musicServiceConnection.release(context)
        backgroundPlayer?.release()
    }

    private fun skipPrevious() = musicServiceConnection.skipPrevious()
    fun play() {
        musicServiceConnection.play()
        backgroundPlayer?.play()
    }

    fun pause() {
        musicServiceConnection.pause()
        backgroundPlayer?.pause()
    }

    private fun skipNext() = musicServiceConnection.skipNext()
    private fun skipTo(position: Float) =
        musicServiceConnection.skipTo(convertToPosition(position, musicState.value.duration))

    private fun forward() = musicServiceConnection.forward()
    private fun backward() = musicServiceConnection.backward()

    fun duration(value: String = "00:15:33"): Int {
        val data = value.split(":")
        return data[1].toInt() + (data[2].toInt() / 60)
    }

    override fun onCleared() {
        super.onCleared()
        backgroundPlayer?.release()
    }
}