package fit.asta.health.navigation.track.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.navigation.track.model.TrackingRepo
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrackViewModel @Inject constructor(
    private val trackingRepo: TrackingRepo
) : ViewModel() {

    fun getWaterDetails() {
        viewModelScope.launch {
            trackingRepo.getWaterDetails(
                uid = "6309a9379af54f142c65fbfe",
                date = "2023",
                location = "bangalore",
                status = "yearly"
            )
        }
    }

    fun getStepsDetails() {
        viewModelScope.launch {
            trackingRepo.getStepsDetails(
                uid = "6309a9379af54f142c65fbfe",
                date = "2023-June-05",
                location = "bangalore",
                status = "yearly"
            )
        }
    }

    fun getMeditationDetails() {
        viewModelScope.launch {
            trackingRepo.getMeditationDetails(
                uid = "6309a9379af54f142c65fbfe",
                date = "2023",
                location = "bangalore",
                status = "yearly"
            )
        }
    }

    fun getBreathingDetails() {
        viewModelScope.launch {
            trackingRepo.getBreathingDetails(
                uid = "6309a9379af54f142c65fbfe",
                date = "2023",
                location = "bangalore",
                status = "yearly"
            )
        }
    }

    fun getSleepDetails() {
        viewModelScope.launch {
            trackingRepo.getSleepDetails(
                uid = "6309a9379af54f142c65fbfe",
                date = "2023",
                location = "bangalore",
                status = "yearly"
            )
        }
    }
}