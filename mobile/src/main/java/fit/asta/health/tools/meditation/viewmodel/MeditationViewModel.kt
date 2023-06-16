package fit.asta.health.tools.meditation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.tools.meditation.model.MeditationRepo
import fit.asta.health.tools.meditation.model.domain.mapper.getMusicTool
import fit.asta.health.tools.meditation.model.domain.model.MusicTool
import fit.asta.health.tools.meditation.model.network.PostRes
import fit.asta.health.tools.meditation.model.network.Prc
import fit.asta.health.tools.meditation.model.network.PutData
import fit.asta.health.tools.meditation.model.network.Value
import fit.asta.health.tools.meditation.view.home.HomeUiState
import fit.asta.health.tools.meditation.view.home.MEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeditationViewModel @Inject constructor(
    private val meditationRepo: MeditationRepo
) : ViewModel() {

    private val _selectedLevel = MutableStateFlow("")
    val selectedLevel: StateFlow<String> = _selectedLevel

    private val _selectedLanguage = MutableStateFlow("")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    private val _selectedInstructor = MutableStateFlow("")
    val selectedInstructor: StateFlow<String> = _selectedInstructor

    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    private val musicTool = MutableStateFlow<MusicTool?>(null)

    init {
        loadData()
    }

    fun event(event: MEvent) {
        when (event) {
            is MEvent.setTarget -> {
                _uiState.value=_uiState.value.copy(targetAngle = event.target)
            }
            is MEvent.setTargetAngle -> {
                _uiState.value=_uiState.value.copy(angle = event.angle)
            }
            is MEvent.setLanguage -> { _selectedLanguage.value = event.language }
            is MEvent.setLevel -> { _selectedLevel.value = event.level }
            is MEvent.setInstructor -> { _selectedInstructor.value = event.instructor }
            is MEvent.Start->{putMeditationData()}
            is MEvent.End->{postData()}
            else -> {}
        }
    }

    fun loadData() {
        viewModelScope.launch {
            meditationRepo.getMeditationTool(
                uid = "6309a9379af54f142c65fbfe",
                date = "2023-03-27"
            ).collectLatest { result ->
                when (result) {
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        result.data?.data.let { mdata ->
                           _uiState.value= _uiState.value.copy(
                                target = mdata!!.meditationProgressData.tgt,
                                achieved =mdata.meditationProgressData.ach ,
                                recommended =mdata.meditationProgressData.rcm ,
                                remaining = mdata.meditationProgressData.rem,
                            )
                        }
                    }

                    is NetworkResult.Error -> {}
                    else -> {}
                }
            }

        }
    }
    fun loadMusicData(){
        viewModelScope.launch {
            meditationRepo.getMusicTool(
                uid = "6309a9379af54f142c65fbfe"
            ).collectLatest { result ->
                when (result) {
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                       result.data?.let {
                          musicTool.value= it.getMusicTool()
                       }
                    }

                    is NetworkResult.Error -> {}
                    else -> {}
                }
            }

        }
    }
    fun putMeditationData(){
        val music=Prc(
            id = "",
            code = "energy",
            title = "music",
            description = "music",
            type = 0,
            values = listOf(Value(
                id = "", name = "energy", value = "energy"
            ))
        )
        val target =Prc(
            id = "",
            code = "target",
            title = "target",
            description = "target",
            type = 0,
            values = listOf(Value(
                id = "", name = "20", value = "20"
            ))
        )
        val instructor =Prc(
            id = "",
            code = "richard",
            title = "instructor",
            description = "instructor",
            type = 0,
            values = listOf(Value(
                id = "", name = "richard", value = "richard"
            ))
        )
        val level =Prc(
            id = "",
            code = "beginner 1",
            title = "level",
            description = "level",
            type = 0,
            values = listOf(Value(
                id = "", name = "beginner 1", value = "beginner 1"
            ))
        )
        val language =Prc(
            id = "",
            code = "english",
            title = "language",
            description = "language",
            type = 0,
            values = listOf(Value(
                id = "", name = "english", value = "english"
            ))
        )
        val prc = mutableListOf<Prc>()
        prc.add(music)
        prc.add(target)
        prc.add(instructor)
        prc.add(level)
        prc.add(language)

        viewModelScope.launch {
            meditationRepo.putMeditationData(
                PutData(
                    code="meditation",
                    id = "",
                    prc = prc,
                    type =3 ,
                    uid = "6309a9379af54f142c65fbfe",
                    wea = true
                )
            )
            loadData()
        }
    }
    fun postData(){
        viewModelScope.launch {
            meditationRepo.postMeditationData(
                PostRes(
                    id = "",
                    uid = "",
                    duration = 35,
                    mode = "indoor",
                    exp = 40
                )
            )
        }
    }
}