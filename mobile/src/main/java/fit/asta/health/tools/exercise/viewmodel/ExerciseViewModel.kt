package fit.asta.health.tools.exercise.viewmodel

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
import fit.asta.health.network.utils.NetworkResult
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_audio.domain.utils.convertToProgress
import fit.asta.health.player.jetpack_video.video.component.VideoState
import fit.asta.health.tools.exercise.db.ExerciseData
import fit.asta.health.tools.exercise.model.ExerciseLocalRepo
import fit.asta.health.tools.exercise.model.ExerciseRepo
import fit.asta.health.tools.exercise.model.domain.mapper.getExerciseTool
import fit.asta.health.tools.exercise.model.domain.mapper.getMusicTool
import fit.asta.health.tools.exercise.model.domain.model.VideoItem
import fit.asta.health.tools.exercise.model.network.NetPost
import fit.asta.health.tools.exercise.model.network.NetPutRes
import fit.asta.health.tools.exercise.model.network.PrcX
import fit.asta.health.tools.exercise.model.network.Value
import fit.asta.health.tools.exercise.view.home.ExerciseUiState
import fit.asta.health.tools.exercise.view.home.HomeEvent
import fit.asta.health.tools.exercise.view.video.VideoEvent
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
class ExerciseViewModel @Inject constructor(
    private val exerciseRepo: ExerciseRepo,
    private val exerciseLocalRepo: ExerciseLocalRepo,
    private val player: Player
) : ViewModel() {
    private var screen = "dance"
    private val date = LocalDate.now().dayOfMonth
    private val _selectedLevel = MutableStateFlow("Beginner 1")
    val selectedLevel: StateFlow<String> = _selectedLevel

    private val _selectedMusic = MutableStateFlow("relaxing")
    val selectedMusic: StateFlow<String> = _selectedMusic

    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    private val _selectedInstructor = MutableStateFlow("Darlene Robertson")
    val selectedInstructor: StateFlow<String> = _selectedInstructor

    private val _selectedQuick = MutableStateFlow("Full Body")
    val selectedQuick: StateFlow<String> = _selectedQuick

    private val _selectedBodyStretch = MutableStateFlow("Foot Stretch")
    val selectedBodyStretch: StateFlow<String> = _selectedBodyStretch

    private val _selectedChallenges = MutableStateFlow("Daily")
    val selectedChallenges: StateFlow<String> = _selectedChallenges

    private val _selectedBodyParts = MutableStateFlow("Arms")
    val selectedBodyParts: StateFlow<String> = _selectedBodyParts

    private val _selectedDuration = MutableStateFlow("10 - 15 min")
    val selectedDuration: StateFlow<String> = _selectedDuration

    private val _selectedStyle = MutableStateFlow("Hatha Yoga")
    val selectedStyle: StateFlow<String> = _selectedStyle

    private val _selectedEquipments = MutableStateFlow("Dumbbell")
    val selectedEquipments: StateFlow<String> = _selectedEquipments

    private val _exerciseUiState = mutableStateOf(ExerciseUiState())
    val exerciseUiState: State<ExerciseUiState> = _exerciseUiState

    fun setScreen(value: String) {
        screen = value
        loadData()
        loadLocalData(value)
    }

    init {

    }

    fun event(event: HomeEvent) {
        when (event) {
            is HomeEvent.SetStyle -> {
                _selectedStyle.update { event.style }
                Log.d("subhash", "event: ${_selectedStyle.value}")
            }

            is HomeEvent.SetDuration -> {
                _selectedDuration.update { event.duration }
            }

            is HomeEvent.SetBodyParts -> {
                _selectedBodyParts.update { event.parts }
            }

            is HomeEvent.SetChallenge -> {
                _selectedChallenges.update { event.challenge }
            }

            is HomeEvent.SetBodyStretch -> {
                _selectedBodyStretch.update { event.stretch }
            }

            is HomeEvent.SetQuick -> {
                _selectedQuick.update { event.quick }
            }

            is HomeEvent.SetLevel -> {
                _selectedLevel.update { event.level }
            }

            is HomeEvent.SetEquipment -> {
                _selectedEquipments.update { event.equipment }
            }

            is HomeEvent.SetInstructor -> {
                _selectedInstructor.update { event.instructor }
            }

            is HomeEvent.SetMusic -> {}
            is HomeEvent.SetLanguage -> {}
            is HomeEvent.SetTarget -> {
                _exerciseUiState.value = _exerciseUiState.value.copy(targetValue = event.target)
            }

            is HomeEvent.SetTargetAngle -> {
                _exerciseUiState.value = _exerciseUiState.value.copy(targetAngle = event.angle)
            }

            is HomeEvent.Start -> {
                if (_exerciseUiState.value.targetValue < 1f) {
                    Toast.makeText(event.context, "select target", Toast.LENGTH_SHORT).show()
                } else {
                    putData(screen)
                    setPlayer()
                }
            }

            is HomeEvent.End -> {
                postData(code = screen)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            exerciseRepo.getExerciseTool(
                uid = "6309a9379af54f142c65fbfe",
                date = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()).toString(),
                name = screen
            ).collectLatest { it ->
                when (it) {
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        it.data?.let {
                            val data = it.getExerciseTool()
                            _exerciseUiState.value = _exerciseUiState.value.copy(
                                target = data.target,
                                achieved = data.achieved,
                                bp = data.bp,
                                bpm = data.bpm,
                                recommended = data.recommend,
                                calories = data.calories,
                                duration = data.duration,
                                vit = data.vit,
                                uid = data.uid,
                                weather = data.weather,
                            )
                            _selectedStyle.value = data.style
                            _selectedDuration.value = data.durationPrc
                            _selectedBodyParts.value = data.bodyParts
                            _selectedChallenges.value = data.challenge
                            _selectedBodyStretch.value = data.bodyStretch
                            _selectedQuick.value = data.quick
                            _selectedLevel.value = data.level
                            _selectedInstructor.value = data.instructor
                            _selectedMusic.value = data.music
                            _selectedLanguage.value = data.language
                            _selectedEquipments.value = data.equipments
                        }
                    }

                    is NetworkResult.Error -> {}
                }
            }
        }
    }

    private fun loadLocalData(code: String) {
        viewModelScope.launch {
            val result = exerciseLocalRepo.getExerciseData(date)
            if (result != null) {
                when (code) {
                    "dance" -> {
                        _exerciseUiState.value = _exerciseUiState.value.copy(
                            start = result.startDance,
                            targetAngle = result.appliedAngleDistanceDance,
                            progress_angle = result.appliedAngleDistanceDance,
                            consume = result.timeDance.toFloat()
                        )
                    }

                    "yoga" -> {
                        _exerciseUiState.value = _exerciseUiState.value.copy(
                            start = result.startYoga,
                            targetAngle = result.appliedAngleDistanceYoga,
                            progress_angle = result.appliedAngleDistanceYoga,
                            consume = result.timeYoga.toFloat()
                        )
                    }

                    "workout" -> {
                        _exerciseUiState.value = _exerciseUiState.value.copy(
                            start = result.startWorkout,
                            targetAngle = result.appliedAngleDistanceWorkout,
                            progress_angle = result.appliedAngleDistanceWorkout,
                            consume = result.timeWorkout.toFloat()
                        )
                    }

                    "HIIT" -> {
                        _exerciseUiState.value = _exerciseUiState.value.copy(
                            start = result.startHiit,
                            targetAngle = result.appliedAngleDistanceHiit,
                            progress_angle = result.appliedAngleDistanceHiit,
                            consume = result.timeHiit.toFloat()
                        )
                    }
                }

            } else {
                exerciseLocalRepo.insert(
                    ExerciseData(
                        date = date,
                        appliedAngleDistanceDance = 0f,
                        appliedAngleDistanceHiit = 0f,
                        appliedAngleDistanceWorkout = 0f,
                        appliedAngleDistanceYoga = 0f,
                        startDance = false,
                        startHiit = false,
                        startWorkout = false,
                        startYoga = false,
                        timeDance = 0,
                        timeHiit = 0,
                        timeWorkout = 0,
                        timeYoga = 0
                    )
                )
            }
        }
    }


    private fun loadMusicData(code: String) {
        viewModelScope.launch {
            exerciseRepo.getStart(uid = "6309a9379af54f142c65fbfe", name = code)
                .collectLatest { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Loading -> {}
                        is NetworkResult.Success -> {
                            networkResult.data?.let {
                                val data = it.getMusicTool()
                                Log.d("subhash", "loadMusicData: $data")
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
//                            currentVideo.value = fakeVideos.first()
//                            player.addMediaItems(fakeVideos.map { it.mediaItem })
                                Log.d("subhash", "loadMusicData: ${_videoList.toList()}")
                            }
                        }

                        is NetworkResult.Error -> {}
                    }
                }
        }
    }

    private fun putData(code: String) {
        Log.d("subhash", "putData: ${_exerciseUiState.value}")
        val target = PrcX(
            dsc = "target",
            title = "target",
            values = listOf(
                Value(
                    id = "",
                    name = _exerciseUiState.value.targetValue.toInt().toString(),
                    value = _exerciseUiState.value.targetValue.toInt().toString()
                )
            ),
            id = ""
        )
        val style = PrcX(
            dsc = "style", title = "style", values = listOf(
                Value(
                    id = "", name = _selectedStyle.value, value = _selectedStyle.value
                )
            ), id = ""
        )
        val duration = PrcX(
            dsc = "Duration", title = "Duration", values = listOf(
                Value(
                    id = "", name = _selectedDuration.value, value = _selectedDuration.value
                )
            ), id = ""
        )
        val bodyParts = PrcX(
            dsc = "Body Parts", title = "Body Parts", values = listOf(
                Value(
                    id = "", name = _selectedBodyParts.value, value = _selectedBodyParts.value
                )
            ), id = ""
        )
        val challenge = PrcX(
            dsc = "Challenges", title = "Challenges", values = listOf(
                Value(
                    id = "", name = _selectedChallenges.value, value = _selectedChallenges.value
                )
            ), id = ""
        )
        val bodyStretch = PrcX(
            dsc = "Body Stretch", title = "Body Stretch", values = listOf(
                Value(
                    id = "", name = _selectedBodyStretch.value, value = _selectedBodyStretch.value
                )
            ), id = ""
        )
        val goals = PrcX(
            dsc = "Quick", title = "Quick", values = listOf(
                Value(
                    id = "", name = _selectedQuick.value, value = _selectedQuick.value
                )
            ), id = ""
        )
        val level = PrcX(
            dsc = "Level", title = "Level", values = listOf(
                Value(
                    id = "", name = _selectedLevel.value, value = _selectedLevel.value
                )
            ), id = ""
        )
        val equipment = PrcX(
            dsc = "Equipments", title = "Equipments", values = listOf(
                Value(
                    id = "", name = _selectedEquipments.value, value = _selectedEquipments.value
                )
            ), id = ""
        )
        val instructor = PrcX(
            dsc = "instructor", title = "instructor", values = listOf(
                Value(
                    id = "", name = _selectedInstructor.value, value = _selectedInstructor.value
                )
            ), id = ""
        )
        val music = PrcX(
            dsc = "Music", title = "Music", values = listOf(
                Value(
                    id = "", name = _selectedMusic.value, value = _selectedMusic.value
                )
            ), id = ""
        )
        val language = PrcX(
            dsc = "language", title = "language", values = listOf(
                Value(
                    id = "", name = _selectedLanguage.value, value = _selectedLanguage.value
                )
            ), id = ""
        )
        val prcList = mutableListOf<PrcX>()
        prcList.add(target)
        prcList.add(style)
        if (code != "dance") prcList.add(duration)
        prcList.add(bodyParts)
        prcList.add(challenge)
        prcList.add(bodyStretch)
        prcList.add(goals)
        prcList.add(level)
        if (code == "HIIT") prcList.add(equipment)
        prcList.add(instructor)
        prcList.add(music)
        if (code != "HIIT") prcList.add(language)
        Log.d("subhash", "putData: $prcList")
        viewModelScope.launch {
            val result = exerciseRepo.putExerciseData(
                netPutRes = NetPutRes(
                    id = "",
                    code = code,
                    prc = prcList,
                    type = 6,
                    uid = "6309a9379af54f142c65fbfe",
                    weather = true
                ), name = code
            )
            Log.d("subhash", "putData: $result")
            when (result) {
                is NetworkResult.Loading -> {
                    Log.d("subhash", "putData: $result")
                }

                is NetworkResult.Success -> {
                    loadData()
                    loadMusicData(code)
                    updateAnge(screen)
                    updateState(start = true, code = screen)
                    updateTime(time = 0, code = screen)
                    _exerciseUiState.value = _exerciseUiState.value.copy(start = true)
                }

                is NetworkResult.Error -> {
                    Log.d("subhash", "putData: ${result.message}")
                }
            }
        }

    }

    private fun updateAnge(code: String) {
        viewModelScope.launch {
            when (code) {
                "dance" -> {
                    exerciseLocalRepo.updateAngleDance(
                        date = date,
                        appliedAngleDistance = _exerciseUiState.value.targetAngle
                    )
                }

                "yoga" -> {
                    exerciseLocalRepo.updateAngleYoga(
                        date = date,
                        appliedAngleDistance = _exerciseUiState.value.targetAngle
                    )
                }

                "workout" -> {
                    exerciseLocalRepo.updateAngleWorkout(
                        date = date,
                        appliedAngleDistance = _exerciseUiState.value.targetAngle
                    )
                }

                "HIIT" -> {
                    exerciseLocalRepo.updateAngleHiit(
                        date = date,
                        appliedAngleDistance = _exerciseUiState.value.targetAngle
                    )
                }
            }
        }
    }

    private fun updateState(start: Boolean, code: String) {
        viewModelScope.launch {
            when (code) {
                "dance" -> {
                    exerciseLocalRepo.updateStateDance(date, start)
                }

                "yoga" -> {
                    exerciseLocalRepo.updateStateYoga(date, start)
                }

                "workout" -> {
                    exerciseLocalRepo.updateStateWorkout(date, start)
                }

                "HIIT" -> {
                    exerciseLocalRepo.updateStateHiit(date, start)
                }
            }
        }
    }

    private fun updateTime(time: Long, code: String) {
        viewModelScope.launch {
            when (code) {
                "dance" -> {
                    exerciseLocalRepo.updateTimeDance(date, time)
                }

                "yoga" -> {
                    exerciseLocalRepo.updateTimeYoga(date, time)
                }

                "workout" -> {
                    exerciseLocalRepo.updateTimeWorkout(date, time)
                }

                "HIIT" -> {
                    exerciseLocalRepo.updateTimeHiit(date, time)
                }
            }
        }
    }

    private fun postData(code: String) {
        viewModelScope.launch {
            val result = exerciseRepo.postExerciseData(
                netPost = NetPost(
                    bp = 10,
                    bpm = 10,
                    calories = 10,
                    duration = 10,
                    id = "",
                    part = emptyList(),
                    style = emptyList(),
                    uid = "6309a9379af54f142c65fbfe",
                    vit = 10
                ), name = code
            )
            when (result) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    updateState(start = false, code = screen)
                    _exerciseUiState.value = ExerciseUiState()
                }

                is NetworkResult.Error -> {}
            }
        }
    }


    fun getStyleList(code: String): List<String> {
        return when (code) {
            "yoga" -> styleListYoga
            "dance" -> styleListDance
            "workout" -> styleListWorkout
            "HIIT" -> styleListHiit
            else -> styleListDance
        }
    }

    fun getGoalsList(code: String): List<String> {
        return when (code) {
            "yoga" -> goalsListYogaHiit
            "dance" -> goalsListDanceWorkout
            "workout" -> goalsListDanceWorkout
            "HIIT" -> goalsListYogaHiit
            else -> goalsListDanceWorkout
        }
    }

    private val styleListDance = listOf("Zumba", "Hip-Hop", "Aerobics", "Bollywood", "Kiat Jud Dai")
    private val styleListYoga =
        listOf("Ashtanga yoga", "Hatha yoga", "Hot yoga", "Iyengar yoga", "Vinyasa yoga")
    private val styleListWorkout = listOf(
        "Strength Training",
        "Weight-Lifting",
        "Aerobics",
        "Cardio",
        "Jump rope",
        "Sit up",
        "Push up"
    )
    private val styleListHiit = listOf("Tabata", "Emom", "Ladders", "Complexes", "Amrap")

    private val goalsListDanceWorkout = listOf(
        "Get Toned",
        "Loose Weight",
        "Improve Heart Health",
        "Boost Metabolism",
        "Gain Weight",
        "Develope Felxibility"
    )
    private val goalsListYogaHiit = listOf("De-Stress", "Fall Asleep", "Anxiety", "Clear your Mind")

    val equipmentList = listOf(
        "Jump rope",
        "kettlebells",
        "Dumbbells",
        "Power Loop Bands",
        "Sliders",
        "Yoga & Exercise Mat",
        "Foam Roller"
    )
    val challengeList = listOf("Daily", "Weekly", "Monthly", "6 Months")
    val durationList = listOf(
        "5-10 min",
        "15-20 min",
        "25-30 min",
        "35-40 min",
        "45-50 min",
        "55-60 min",
        "65-70 min",
    )
    val levelList = listOf("Beginner 1", "Beginner 2", "Intermediate ", "Advanced", "Expert")

    val bodyParts = listOf("Core", "Full Body", "Arms", "Legs", "OnPutSuccess", "Buttocks")
    val bodyStretch = listOf(
        "Neck roll",
        "Shoulder roll",
        "Behind-head tricep stretch",
        "Standing hamstring stretch",
        "Quadriceps stretch",
        "Ankle roll",
        "Child's Pose"
    )

    val listener = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)
            Log.d("subhash", "onEvents: $player.duration  progress ${ convertToProgress(count = player.currentPosition, total = 1000) } ")

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
            Log.d("devil", "prepare")
        }
    }

    fun player(): Player = player
    private var rememberedState: Triple<String, Int, Long>? = null
    private val window: Timeline.Window = Timeline.Window()

    fun play() = { player.play() }

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
            is VideoEvent.UpdateProgress->{
                  updateTime(time = event.value.toLong(),code=screen)
                _exerciseUiState.value=_exerciseUiState.value.copy(
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
