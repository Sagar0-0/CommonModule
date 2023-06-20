package fit.asta.health.navigation.track.viewmodel

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.navigation.track.TrackingOptions
import kotlinx.coroutines.launch
import fit.asta.health.navigation.track.model.TrackingBreathingRepo
import fit.asta.health.navigation.track.model.TrackingWaterRepo
import fit.asta.health.navigation.track.model.network.breathing.NetBreathingRes
import fit.asta.health.navigation.track.model.network.water.NetWaterRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject


@HiltViewModel
class TrackViewModel @Inject constructor(
    private val breathingRepository: TrackingBreathingRepo,
    private val waterRepository: TrackingWaterRepo
) : ViewModel() {

    private val _breathingData = MutableStateFlow<NetBreathingRes?>(null)
    val breathingData = _breathingData.asStateFlow()

    private val _waterData = MutableStateFlow<NetWaterRes?>(null)
    val waterData = _waterData.asStateFlow()

    private lateinit var currentSelectedTrackingOption: TrackingOptions

    fun changeTrackingOption(newOption: TrackingOptions) {
        currentSelectedTrackingOption = newOption

        /* TODO :-
            1. Make the repository object
         */
    }

    fun getDailyData() {
        viewModelScope.launch {
            breathingRepository.getDailyData(
                uid = "6309a9379af54f142c65fbfe",
                date = "2023-June-11",
                location = "bangalore"
            ).catch { exception ->
                d("View Model", exception.toString())
            }.collect {
                _breathingData.value = it
                d("View Model", _breathingData.value.toString())
            }
        }
    }

    fun getDaily() {
        when (currentSelectedTrackingOption) {
            TrackingOptions.WaterTrackingOption -> getWaterDaily()
            else -> {}
        }
    }

    private fun getWaterDaily() {
        viewModelScope.launch {

            waterRepository.getDailyData(
                uid = "6309a9379af54f142c65fbfe",
                date = "2023-June-11",
                location = "bangalore"
            ).catch { exception ->
                d("View Model", exception.toString())
            }.collect {
                _waterData.value = it
                d("View Model", _waterData.value.toString())
            }
        }
    }

}