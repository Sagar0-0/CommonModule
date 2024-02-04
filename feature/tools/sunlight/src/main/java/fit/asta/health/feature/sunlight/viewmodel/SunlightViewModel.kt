package fit.asta.health.feature.sunlight.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getCurrentDate
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.sunlight.model.SunlightToolRepo
import fit.asta.health.data.sunlight.model.network.response.SunlightToolData
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.sunlight.view.home.SunlightHomeScreenEvents
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class SunlightViewModel
@Inject constructor(
    private val sunlightToolRepo: SunlightToolRepo,
    private val prefManager: PrefManager,
    @UID private val uid: String
) : ViewModel() {

    private val _sunlightToolDataState =
        MutableStateFlow<UiState<SunlightToolData>>(UiState.Loading)
    val sunlightToolDataState = _sunlightToolDataState.asStateFlow()

    val startState = mutableStateOf(false)

    init {
        loadSunlightToolData()
    }

    private fun loadSunlightToolData() {
        viewModelScope.launch {
            prefManager.address.collectLatest {
                _sunlightToolDataState.value = sunlightToolRepo.getSunlightTool(
                    userId = uid,
                    latitude = it.lat.toString(),
                    longitude = it.long.toString(),
                    location = it.currentAddress,
                    date = getCurrentDate()
                ).toUiState()
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