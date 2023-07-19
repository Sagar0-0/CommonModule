package fit.asta.health.tools.breathing.viewmodel

import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import fit.asta.health.player.jetpack_audio.domain.utils.convertToProgress
import fit.asta.health.tools.breathing.db.BreathingData
import fit.asta.health.tools.breathing.model.BreathingRepo
import fit.asta.health.tools.breathing.model.LocalRepo
import fit.asta.health.tools.breathing.model.domain.mapper.getBreathingTool
import fit.asta.health.tools.breathing.model.domain.mapper.getMusicTool
import fit.asta.health.tools.breathing.model.domain.mapper.toPutData
import fit.asta.health.tools.breathing.model.domain.model.BreathingTool
import fit.asta.health.tools.breathing.model.network.request.CustomRatioPost
import fit.asta.health.tools.breathing.model.network.request.NetPost
import fit.asta.health.tools.breathing.view.home.UiEvent
import fit.asta.health.tools.breathing.view.home.UiState
import fit.asta.health.tools.exercise.model.domain.model.VideoItem
import fit.asta.health.tools.exercise.view.video.VideoEvent
import fit.asta.health.tools.exercise.view.video.VideoState
import fit.asta.health.tools.exercise.view.video_player.VideoPlayerEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BreathingViewModel @Inject constructor(
    private val breathingRepo: BreathingRepo,
    private val localRepo: LocalRepo,
    private val player: Player
) : ViewModel() {

    private val date = LocalDate.now().dayOfMonth
    private val _selectedExercise = MutableStateFlow(listOf("ex"))
    val selectedExercise: StateFlow<List<String>> = _selectedExercise

    private val _selectedGoals = MutableStateFlow(listOf("goals"))
    val selectedGoals: StateFlow<List<String>> = _selectedGoals

    private val _selectedPace = MutableStateFlow("Beginner 1")
    val selectedPace: StateFlow<String> = _selectedPace

    private val _selectedBreakTime = MutableStateFlow("Beginner 1")
    val selectedBreakTime: StateFlow<String> = _selectedBreakTime

    private val _selectedLevel = MutableStateFlow("Beginner 1")
    val selectedLevel: StateFlow<String> = _selectedLevel

    private val _selectedMusic = MutableStateFlow("relaxing")
    val selectedMusic: StateFlow<String> = _selectedMusic

    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    private val _selectedInstructor = MutableStateFlow("Darlene Robertson")
    val selectedInstructor: StateFlow<String> = _selectedInstructor

    private val _homeUiState = mutableStateOf(UiState())
    val homeUiState: State<UiState> = _homeUiState

    init {
        loadData()
        loadLocalData()
    }

    fun loadData() {
        viewModelScope.launch {
            breathingRepo.getBreathingTool(
                userId = "6309a9379af54f142c65fbfe",
                date = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()).toString(),
            ).collectLatest {
                when (it) {
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        val data = it.data?.getBreathingTool()
                        if (data != null) {
                            _homeUiState.value = _homeUiState.value.copy(
                                target = data.target,
                                achieved = if (data.achieved < 1) 1 else data.achieved,
                                recommended = data.recommend,
                                weather = data.weather)
                            _selectedLanguage.value = data.Language
                            _selectedMusic.value = data.Music
                            _selectedInstructor.value = data.instructor
                            _selectedLevel.value = data.Level
                            _selectedGoals.value = data.Goal
                            _selectedExercise.value = data.exercise
                            _selectedBreakTime.value = data.Break
                            _selectedPace.value = data.Pace
                        }
                    }

                    is NetworkResult.Error -> {}
                }
            }
        }
    }

    fun loadLocalData() {
        viewModelScope.launch {
            val result = localRepo.getBreathingData(date)
            if (result != null) {
                _homeUiState.value = _homeUiState.value.copy(
                    start = result.start,
                    targetAngle = result.appliedAngleDistance,
                    progress_angle = result.appliedAngleDistance,
                    consume = result.time.toFloat()
                )
            } else {
                localRepo.insert(
                    BreathingData(
                        date = date,
                        start = false,
                        appliedAngleDistance = 0f,
                        time = 0,
                    )
                )
            }
        }
    }

    fun loadMusicAllData() {
        viewModelScope.launch {
            breathingRepo.getAllBreathingData(userId = "6309a9379af54f142c65fbfe").collectLatest {
                when (it) {
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        val data = it.data
                        if (data != null) {

                        }
                    }

                    is NetworkResult.Error -> {}
                }
            }
        }
    }

    fun loadSessionData() {
        viewModelScope.launch {
            breathingRepo.getStart(userId = "6309a9379af54f142c65fbfe").collectLatest {
                when (it) {
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        val data = it.data?.getMusicTool()
                        if (data != null) {
                            val fakeVideos = mutableListOf<VideoItem>()
                            _videoList.clear()
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
                            data.instructor.forEachIndexed { index, it ->
                                fakeVideos.add(
                                    VideoItem(
                                        mediaUri = "https://stream1.asta.fit${it.music_url}".toUri(),
                                        mediaItem = MediaItem.Builder()
                                            .setMediaId(it.music_name)
                                            .setUri("https://stream1.asta.fit${it.music_url}".toUri())
                                            .build(),
                                        title = it.music_name,
                                        artworkUri = "https://img2.asta.fit/tags/Breathing+Tag.png".toUri(),
                                        artist = data.music.artist_name,
                                        duration = data.music.duration
                                    )
                                )

                            }

                            val music = "https://stream1.asta.fit/video/Day02.mp4"
                            _videoList.addAll(fakeVideos)
                        }
                    }

                    is NetworkResult.Error -> {}
                }
            }
        }
    }

    fun updateServer() {
        viewModelScope.launch {
            val breathingTool = BreathingTool(
                id = "63b26d14f04e0e613d0c443d",
                uid = "6309a9379af54f142c65fbfe",
                weather = false,
                target = 0,
                achieved = 0,
                recommend = 0,
                exercise = _selectedExercise.value,
                instructor = _selectedInstructor.value,
                Music = _selectedMusic.value,
                Target = _homeUiState.value.targetValue.toInt().toString(),
                Level = _selectedLevel.value,
                Language = _selectedLanguage.value,
                Goal = _selectedGoals.value,
                Pace = _selectedPace.value,
                Break = _selectedBreakTime.value
            )
            when (breathingRepo.putBreathingData(breathingTool.toPutData())) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    loadData()
                    loadSessionData()
                    viewModelScope.launch {
                        localRepo.updateAngle(
                            date = date,
                            appliedAngleDistance = _homeUiState.value.targetAngle
                        )
                        localRepo.updateState(
                            date = date,
                            start = true
                        )
                        localRepo.updateTime(
                            date = date,
                            time = 0
                        )
                    }
                    _homeUiState.value = _homeUiState.value.copy(start = true)
                }

                is NetworkResult.Error -> {}
            }
        }
    }

    fun endSession() {
        viewModelScope.launch {
            val result = breathingRepo.postBreathingData(
                NetPost(
                    id = "",
                    calories = 100,
                    exp = 20,
                    ex = _selectedExercise.value,
                    uid = "6309a9379af54f142c65fbfe",
                    duration = _homeUiState.value.consume.toInt(),
                    level = _selectedLevel.value,
                    breath = 200,
                    inX = 200
                )
            )
            when (result) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    _homeUiState.value = UiState()
                    viewModelScope.launch {
                        localRepo.updateState(date, false)
                    }
                }

                is NetworkResult.Error -> {}
            }
        }
    }

    fun updateRatio() {
        viewModelScope.launch {
            val result = breathingRepo.postRatioData(
                CustomRatioPost(
                    id = "",
                    inhale = 1,
                    inhaleH = 1,
                    out = 1,
                    outH = 1,
                    type = 1,
                    uid = "6309a9379af54f142c65fbfe",
                    name = "Nadi Shodhana"
                )
            )
            when (result) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {

                }

                is NetworkResult.Error -> {}
            }
        }
    }

    fun deleteRatio(ratioId: String) {
        viewModelScope.launch {
            when (breathingRepo.deleteRatioData(ratioId = ratioId)) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {

                }

                is NetworkResult.Error -> {}
            }
        }
    }

    fun event(event: UiEvent) {
        when (event) {
            is UiEvent.SetExercise -> {
                _selectedExercise.update { event.exercise }
            }

            is UiEvent.SetGoals -> {
                _selectedGoals.update { event.goals }
            }

            is UiEvent.SetBreakTime -> {
                _selectedBreakTime.update { event.time }
            }

            is UiEvent.SetLevel -> {
                _selectedLevel.update { event.level }
            }

            is UiEvent.SetPace -> {
                _selectedLevel.update { event.pace }
            }

            is UiEvent.SetInstructor -> {
                _selectedInstructor.update { event.instructor }
            }

            is UiEvent.SetMusic -> {
                _selectedMusic.update { event.music }
            }

            is UiEvent.SetLanguage -> {
                _selectedLanguage.update { event.language }
            }

            is UiEvent.SetTarget -> {
                _homeUiState.value = _homeUiState.value.copy(targetValue = event.target)
            }

            is UiEvent.SetTargetAngle -> {
                _homeUiState.value = _homeUiState.value.copy(targetAngle = event.angle)
            }

            is UiEvent.Start -> {
                if (_homeUiState.value.targetValue < 1f) {
                    Toast.makeText(event.context, "select target", Toast.LENGTH_SHORT).show()
                } else {
                    updateServer()
                    setPlayer()
                }
            }

            is UiEvent.End -> {
                endSession()
            }
        }
    }


    val listener = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)
            Log.d(
                "subhash",
                "onEvents: $player.duration  progress ${
                    convertToProgress(
                        count = player.currentPosition,
                        total = 1000
                    )
                } "
            )

        }

        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
            // recover the remembered state if media id matched
            Log.d("subhash", "onTimelineChanged: ${timeline.windowCount},$reason")
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
    private val _uiState = mutableStateOf(VideoState())
    val uiState: State<VideoState> = _uiState

    fun setPlayer() {
        player.apply {
            playWhenReady = true
            prepare()
            addListener(listener)
        }
    }

    fun player(): Player = player
    private var rememberedState: Triple<String, Int, Long>? = null
    private val window: Timeline.Window = Timeline.Window()

    fun play() = player.play()

    private fun saveState() {
        player.currentMediaItem?.let { mediaItem ->
            rememberedState = Triple(
                mediaItem.mediaId,
                player.currentMediaItemIndex,
                player.currentPosition
            )
        }
    }

    fun pause() = player.pause()
    fun eventVideo(event: VideoEvent) {
        when (event) {
            is VideoEvent.Start -> play()
            is VideoEvent.Stop -> {
                saveState()
                pause()
            }

            is VideoEvent.UpdateProgress -> {
                viewModelScope.launch {
                    localRepo.updateTime(date, time = event.value.toLong())
                }
                _homeUiState.value = _homeUiState.value.copy(
                    consume = event.value
                )
            }

            is VideoEvent.SetControllerType -> {
                _uiState.value = _uiState.value.copy(controllerType = event.value)
            }

            is VideoEvent.SetResizeMode -> {
                _uiState.value = _uiState.value.copy(resizeMode = event.value)
            }
        }
    }

    fun eventVideoPlayer(event: VideoPlayerEvent) {
        when (event) {
            is VideoPlayerEvent.PlaySound -> {
                playVideo(uri = event.uri)
            }
        }

    }

    private fun playVideo(uri: Uri) {
        player.setMediaItem(
            MediaItem.fromUri(uri)
        )
    }

    override fun onCleared() {
        super.onCleared()
        player.removeListener(listener)
        player.release()
    }
}