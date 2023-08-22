package fit.asta.health.tools.sunlight.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.auth.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.getCurrentDate
import fit.asta.health.tools.sunlight.model.SunlightToolRepo
import fit.asta.health.tools.sunlight.model.network.response.ResponseData
import fit.asta.health.tools.sunlight.view.home.SunlightHomeScreenEvents
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class SunlightViewModel
@Inject constructor(
    private val sunlightToolRepo: SunlightToolRepo,
    private val authRepo: AuthRepo
) : ViewModel() {
    private val mutableState = MutableStateFlow<SunlightState>(SunlightState.Loading)
    val state = mutableState.asStateFlow()
    private val _apiState = mutableStateOf<ResponseData.SunlightToolData?>(null)
    val apiState: State<ResponseData.SunlightToolData?> = _apiState
    val startState = mutableStateOf(false)

    init {
        loadSunlightToolData()
    }

    private fun loadSunlightToolData() {
        viewModelScope.launch {

            authRepo.getUser()?.let {
                sunlightToolRepo.getSunlightTool(
                    userId = "62fcd8c098eb9d5ed038b563",
                    latitude = "28.6353",
                    longitude = "77.2250",
                    location = "bangalore",
                    date = getCurrentDate()
                ).catch { exception ->
                    mutableState.value = SunlightState.Error(exception)
                }.collect {
                    mutableState.value = SunlightState.Success(it)
                }
            }
        }
    }


    private val _selectedAgeSelection = MutableStateFlow("")
    val selectedAgeSelection: StateFlow<String> = _selectedAgeSelection

    private val _selectedSkinExposure = MutableStateFlow("")
    val selectedSkinExposure: StateFlow<String> = _selectedSkinExposure

    private val _selectedSpfSelection = MutableStateFlow("")
    val seletedSpfSelection = _selectedSpfSelection

    private val _selectedSkincolorSelection = MutableStateFlow("")
    val selectedSkincolorSelection = _selectedSkincolorSelection

    fun onAgeSelection(ageSelected: String) {
        _selectedAgeSelection.value = ageSelected
    }

    fun onSkinExposureSelection(skinExposureSelected: String) {
        _selectedSkinExposure.value = skinExposureSelected
    }

    fun onSpfSelection(spfSelected: String) {
        _selectedSpfSelection.value = spfSelected
    }

    fun onSkinColorSelection(skinColorSelected: String) {
        _selectedSkincolorSelection.value = skinColorSelected
    }

    fun onUiEvent(event: SunlightHomeScreenEvents) {

        when (event) {
            is SunlightHomeScreenEvents.OnStartClick -> {
                startState.value = true
            }

            is SunlightHomeScreenEvents.OnStopClick -> {
                startState.value = false
            }
        }
    }
}