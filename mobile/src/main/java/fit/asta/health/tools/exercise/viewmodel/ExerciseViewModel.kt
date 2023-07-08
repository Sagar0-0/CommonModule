package fit.asta.health.tools.exercise.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.tools.exercise.db.ExerciseData
import fit.asta.health.tools.exercise.model.ExerciseLocalRepo
import fit.asta.health.tools.exercise.model.ExerciseRepo
import fit.asta.health.tools.exercise.model.domain.mapper.getExerciseTool
import fit.asta.health.tools.exercise.model.domain.mapper.getMusicTool
import fit.asta.health.tools.exercise.model.network.NetPost
import fit.asta.health.tools.exercise.model.network.NetPutRes
import fit.asta.health.tools.exercise.model.network.PrcX
import fit.asta.health.tools.exercise.model.network.Value
import fit.asta.health.tools.exercise.view.home.ExerciseUiState
import fit.asta.health.tools.exercise.view.home.HomeEvent
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
    private val exerciseLocalRepo: ExerciseLocalRepo
) : ViewModel() {
    private var screen = "dance"
    private val date = LocalDate.now().dayOfMonth
    private val _selectedLevel = MutableStateFlow("Beginner 1")
    val selectedLevel: StateFlow<String> = _selectedLevel

    private val _selectedMusic = MutableStateFlow("Quieting the Mind")
    val selectedMusic: StateFlow<String> = _selectedMusic

    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    private val _selectedInstructor = MutableStateFlow("john")
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


    private fun loadMusicData() {
        viewModelScope.launch {
            exerciseRepo.getStart(uid = "").collectLatest { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        networkResult.data?.let {
                            val data = it.getMusicTool()

                        }
                    }

                    is NetworkResult.Error -> {}
                }
            }
        }
    }

    private fun putData(code: String) {
        val target = PrcX(
            dsc = "target",
            title = "target",
            values = listOf(Value(id = "", name = "20", value = "20")),
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
        viewModelScope.launch {
            val result = exerciseRepo.putExerciseData(
                netPutRes = NetPutRes(
                    id = "",
                    code = "",
                    prc = prcList,
                    type = 6,
                    uid = "6309a9379af54f142c65fbfe",
                    weather = true
                ), name = code
            )
            when (result) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    loadData()
                    loadMusicData()
                    updateAnge(screen)
                    updateState(start = true, code = screen)
                    updateTime(time = 0, code = screen)
                    _exerciseUiState.value = _exerciseUiState.value.copy(start = true)
                }

                is NetworkResult.Error -> {}
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

    val bodyParts = listOf("Core", "Full Body", "Arms", "Legs", "Back", "Buttocks")
    val bodyStretch = listOf(
        "Neck roll",
        "Shoulder roll",
        "Behind-head tricep stretch",
        "Standing hamstring stretch",
        "Quadriceps stretch",
        "Ankle roll",
        "Child's Pose"
    )
}
