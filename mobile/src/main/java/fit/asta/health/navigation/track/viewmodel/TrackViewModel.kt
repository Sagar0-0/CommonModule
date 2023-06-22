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
import fit.asta.health.navigation.track.model.network.water.NetWaterDailyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterMonthlyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterWeeklyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterYearlyRes
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

    private val _waterDailyData = MutableStateFlow<NetWaterDailyRes?>(null)
    val waterDailyData = _waterDailyData.asStateFlow()

    private val _waterWeeklyData = MutableStateFlow<NetWaterWeeklyRes?>(null)
    val waterWeeklyData = _waterWeeklyData.asStateFlow()

    private val _waterMonthlyData = MutableStateFlow<NetWaterMonthlyRes?>(null)
    val waterMonthlyData = _waterMonthlyData.asStateFlow()

    private val _waterYearlyData = MutableStateFlow<NetWaterYearlyRes?>(null)
    val waterYearlyData = _waterYearlyData.asStateFlow()

    private lateinit var currentSelectedTrackingOption: TrackingOptions

    fun changeTrackingOption(newOption: TrackingOptions) {
        currentSelectedTrackingOption = newOption

        /* TODO :-
            1. Make the repository object
         */
    }

    fun getDailyData() {
        when (currentSelectedTrackingOption) {
            TrackingOptions.WaterTrackingOption -> getWaterDaily()
            TrackingOptions.BreathingTrackingOption -> getBreathingDaily()
            else -> {}
        }
    }


    fun getWeeklyData() {
        when (currentSelectedTrackingOption) {
            TrackingOptions.WaterTrackingOption -> getWaterWeekly()
            else -> {}
        }
    }

    fun getMonthlyData() {
        when (currentSelectedTrackingOption) {
            TrackingOptions.WaterTrackingOption -> getWaterMonthly()
            else -> {}
        }
    }

    fun getYearlyData() {
        when (currentSelectedTrackingOption) {
            TrackingOptions.WaterTrackingOption -> getWaterYearly()
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
                _waterDailyData.value = it
                d("View Model", _waterDailyData.value.toString())
            }
        }
    }

    private fun getWaterWeekly() {
        viewModelScope.launch {

            waterRepository.getWeeklyData(
                uid = "6309a9379af54f142c65fbfe",
                date = "2023-June-10",
            ).catch { exception ->
                d("View Model", exception.toString())
            }.collect {
                _waterWeeklyData.value = it
                d("View Model", _waterWeeklyData.value.toString())
            }
        }
    }

    private fun getWaterMonthly() {
        viewModelScope.launch {

            waterRepository.getMonthlyData(
                uid = "6309a9379af54f142c65fbfe",
                month = "June-2023",
            ).catch { exception ->
                d("View Model", exception.toString())
            }.collect {
                _waterMonthlyData.value = it
                d("View Model", _waterMonthlyData.value.toString())
            }
        }
    }

    private fun getWaterYearly() {
        viewModelScope.launch {

            waterRepository.getYearlyData(
                uid = "6309a9379af54f142c65fbfe",
                year = "2023"
            ).catch { exception ->
                d("View Model", exception.toString())
            }.collect {
                _waterYearlyData.value = it
                d("View Model", _waterYearlyData.value.toString())
            }
        }
    }

    private fun getBreathingDaily() {
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
}