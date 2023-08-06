package fit.asta.health.navigation.track.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.navigation.track.model.TrackingRepo
import fit.asta.health.navigation.track.model.net.breathing.BreathingResponse
import fit.asta.health.navigation.track.model.net.meditation.MeditationResponse
import fit.asta.health.navigation.track.model.net.sleep.SleepResponse
import fit.asta.health.navigation.track.model.net.step.StepsResponse
import fit.asta.health.navigation.track.model.net.sunlight.SunlightResponse
import fit.asta.health.navigation.track.model.net.water.WaterResponse
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


    // This variable contains the water tracking details
    private val _waterDetails = MutableStateFlow<TrackingNetworkCall<WaterResponse>>(
        TrackingNetworkCall.Initialized()
    )
    val waterDetails = _waterDetails.asStateFlow()

    /**
     * This function fetches the Water Tracking Details from the Server
     */
    fun getWaterDetails(status: String) {
        viewModelScope.launch {
            trackingRepo.getWaterDetails(
                uid = uid,
                date = "2023",
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
    fun getStepsDetails() {
        viewModelScope.launch {
            trackingRepo.getStepsDetails(
                uid = uid,
                date = "2023",
                location = "bangalore",
                status = "yearly"
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

    fun getMeditationDetails() {
        viewModelScope.launch {
            trackingRepo.getMeditationDetails(
                uid = uid,
                date = "2023",
                location = "bangalore",
                status = "yearly"
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
    fun getBreathingDetails() {
        viewModelScope.launch {
            trackingRepo.getBreathingDetails(
                uid = uid,
                date = "2023",
                location = "bangalore",
                status = "yearly"
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
    fun getSleepDetails() {
        viewModelScope.launch {
            trackingRepo.getSleepDetails(
                uid = uid,
                date = "2023",
                location = "bangalore",
                status = "yearly"
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
    fun getSunlightDetails() {
        viewModelScope.launch {
            trackingRepo.getSunlightDetails(
                uid = uid,
                date = "2023",
                location = "bangalore",
                status = "yearly"
            ).collect {
                _sunlightDetails.value = it
            }
        }
    }
}