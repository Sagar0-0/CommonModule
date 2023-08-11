package fit.asta.health.navigation.track.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.navigation.track.model.TrackingRepo
import fit.asta.health.navigation.track.model.net.breathing.BreathingResponse
import fit.asta.health.navigation.track.model.net.meditation.MeditationResponse
import fit.asta.health.navigation.track.model.net.menu.HomeMenuResponse
import fit.asta.health.navigation.track.model.net.sleep.SleepResponse
import fit.asta.health.navigation.track.model.net.step.StepsResponse
import fit.asta.health.navigation.track.model.net.sunlight.SunlightResponse
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import fit.asta.health.navigation.track.view.util.TrackOption
import fit.asta.health.navigation.track.view.util.TrackingNetworkCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrackViewModel @Inject constructor(
    private val trackingRepo: TrackingRepo
) : ViewModel() {


    // User Id For testing
    private val uid = "6309a9379af54f142c65fbfe"

    // This variable contains the Home Screen Menu Details
    private val _homeScreenDetails = MutableStateFlow<TrackingNetworkCall<HomeMenuResponse>>(
        TrackingNetworkCall.Initialized()
    )
    val homeScreenDetails = _homeScreenDetails.asStateFlow()

    /**
     * This function fetches the Home Screen Details from the Server
     */
    fun getHomeDetails() {

        if (_homeScreenDetails.value is TrackingNetworkCall.Loading)
            return

        val date = "2023-June-05"

        viewModelScope.launch {
            trackingRepo.getHomeDetails(
                uid = uid,
                date = date,
                location = "bangalore"
            ).collect {
                _homeScreenDetails.value = it
            }
        }
    }

    // This variable contains the water tracking details
    private val _waterDetails = MutableStateFlow<TrackingNetworkCall<WaterResponse>>(
        TrackingNetworkCall.Initialized()
    )
    val waterDetails = _waterDetails.asStateFlow()

    /**
     * This function fetches the Water Tracking Details from the Server
     */
    private fun getWaterDetails(status: String, date: String) {

        if (_waterDetails.value is TrackingNetworkCall.Loading)
            return

        viewModelScope.launch {
            trackingRepo.getWaterDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).collect {
                _waterDetails.value = it
            }
        }
    }


    // This variable contains the steps Tracking Details
    private val _stepsDetails = MutableStateFlow<TrackingNetworkCall<StepsResponse>>(
        TrackingNetworkCall.Initialized()
    )
    val stepsDetails = _stepsDetails.asStateFlow()

    /**
     * This function fetches the steps Tracking details from the Server
     */
    private fun getStepsDetails(status: String, date: String) {

        if (_stepsDetails.value is TrackingNetworkCall.Loading)
            return

        viewModelScope.launch {
            trackingRepo.getStepsDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).collect {
                _stepsDetails.value = it
            }
        }
    }


    // This variable contains Meditation Tracking Details
    private val _meditationDetails = MutableStateFlow<TrackingNetworkCall<MeditationResponse>>(
        TrackingNetworkCall.Initialized()
    )
    val meditationDetails = _meditationDetails.asStateFlow()

    private fun getMeditationDetails(status: String, date: String) {

        if (_meditationDetails.value is TrackingNetworkCall.Loading)
            return

        viewModelScope.launch {
            trackingRepo.getMeditationDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).collect {
                _meditationDetails.value = it
            }
        }
    }


    // This variable contains the Breathing Tracking Details
    private val _breathingDetails = MutableStateFlow<TrackingNetworkCall<BreathingResponse>>(
        TrackingNetworkCall.Initialized()
    )
    val breathingDetails = _breathingDetails.asStateFlow()

    /**
     * This function fetches the breathing tracking details from the Server
     */
    private fun getBreathingDetails(status: String, date: String) {

        if (_breathingDetails.value is TrackingNetworkCall.Loading)
            return

        viewModelScope.launch {
            trackingRepo.getBreathingDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).collect {
                _breathingDetails.value = it
            }
        }
    }

    // This variable contains the sleep tracking Details
    private val _sleepDetails = MutableStateFlow<TrackingNetworkCall<SleepResponse>>(
        TrackingNetworkCall.Initialized()
    )
    val sleepDetails = _sleepDetails.asStateFlow()

    /**
     * This function fetches the sleep details from the Server
     */
    private fun getSleepDetails(status: String, date: String) {

        if (_sleepDetails.value is TrackingNetworkCall.Loading)
            return

        viewModelScope.launch {
            trackingRepo.getSleepDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).collect {
                _sleepDetails.value = it
            }
        }
    }

    // This variable contains the Sunlight Tracking Details
    private val _sunlightDetails = MutableStateFlow<TrackingNetworkCall<SunlightResponse>>(
        TrackingNetworkCall.Initialized()
    )
    val sunlightDetails = _sunlightDetails.asStateFlow()

    /**
     * This function fetches the Sunlight Tracking Details from the server
     */
    private fun getSunlightDetails(status: String, date: String) {

        if (_sunlightDetails.value is TrackingNetworkCall.Loading)
            return

        viewModelScope.launch {
            trackingRepo.getSunlightDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).collect {
                _sunlightDetails.value = it
            }
        }
    }

    private var currentTrackOption: TrackOption = TrackOption.WaterOption
    fun setTrackOption(newTrackOption: TrackOption) {
        currentTrackOption = newTrackOption
    }

    fun setTrackStatus(chosenOption: Int) {
        when (chosenOption) {
            0 -> currentTrackOption.trackStatus = TrackOption.TrackStatus.StatusDaily
            1 -> currentTrackOption.trackStatus = TrackOption.TrackStatus.StatusWeekly
            2 -> currentTrackOption.trackStatus = TrackOption.TrackStatus.StatusMonthly
            3 -> currentTrackOption.trackStatus = TrackOption.TrackStatus.StatusYearly
        }
        handleTrackerOption()
    }

    private fun handleTrackerOption() {
        when (currentTrackOption) {
            is TrackOption.WaterOption -> {
                getWaterDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate()
                )
            }

            is TrackOption.StepsOption -> {
                getStepsDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate()
                )
            }

            is TrackOption.MeditationOption -> {
                getMeditationDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate()
                )
            }

            is TrackOption.BreathingOption -> {
                getBreathingDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate()
                )
            }

            is TrackOption.SleepOption -> {
                getSleepDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate()
                )
            }

            is TrackOption.SunlightOption -> {
                getSunlightDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate()
                )
            }
        }
    }

    private fun handleTrackerDate(): String {
        return when (currentTrackOption.trackStatus) {
            is TrackOption.TrackStatus.StatusDaily -> "2023-June-05"
            is TrackOption.TrackStatus.StatusWeekly -> "2023-June-05"
            is TrackOption.TrackStatus.StatusMonthly -> "June-2023"
            is TrackOption.TrackStatus.StatusYearly -> "2023"
        }
    }
}