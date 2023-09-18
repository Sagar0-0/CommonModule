package fit.asta.health.navigation.track.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.navigation.track.data.repo.TrackingRepo
import fit.asta.health.navigation.track.data.remote.model.breathing.BreathingResponse
import fit.asta.health.navigation.track.data.remote.model.exercise.ExerciseResponse
import fit.asta.health.navigation.track.data.remote.model.meditation.MeditationResponse
import fit.asta.health.navigation.track.data.remote.model.menu.HomeMenuResponse
import fit.asta.health.navigation.track.data.remote.model.sleep.SleepResponse
import fit.asta.health.navigation.track.data.remote.model.step.StepsResponse
import fit.asta.health.navigation.track.data.remote.model.sunlight.SunlightResponse
import fit.asta.health.navigation.track.data.remote.model.water.WaterResponse
import fit.asta.health.navigation.track.ui.util.TrackOption
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class TrackViewModel @Inject constructor(
    private val trackingRepo: TrackingRepo
) : ViewModel() {

    /**
     * These variables contains the current date and calendar data
     */
    private val _calendarData: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val calendarData: StateFlow<LocalDate> = _calendarData.asStateFlow()

    /**
     * This function changes the current calendar date
     */
    private fun onDateChanged(localDate: LocalDate) {
        _calendarData.value = localDate
    }

    /**
     * This function executes when the previous button is hit
     */
    private fun onPreviousCalendarButtonClick() {

        _calendarData.value = when (currentTrackOption.trackStatus) {
            is TrackOption.TrackStatus.StatusDaily -> _calendarData.value.minusDays(1)
            is TrackOption.TrackStatus.StatusWeekly -> _calendarData.value.minusWeeks(1)
            is TrackOption.TrackStatus.StatusMonthly -> _calendarData.value.minusMonths(1)
            is TrackOption.TrackStatus.StatusYearly -> _calendarData.value.minusYears(1)
        }
    }

    /**
     * This function is executed when the next calendar button is hit
     */
    private fun onNextCalendarButtonClick() {

        _calendarData.value = when (currentTrackOption.trackStatus) {
            is TrackOption.TrackStatus.StatusDaily -> _calendarData.value.plusDays(1)
            is TrackOption.TrackStatus.StatusWeekly -> _calendarData.value.plusWeeks(1)
            is TrackOption.TrackStatus.StatusMonthly -> _calendarData.value.plusMonths(1)
            is TrackOption.TrackStatus.StatusYearly -> _calendarData.value.plusYears(1)
        }
    }

    // User Id For testing
    // TODO : To be changed according to the user
    private val uid = "6309a9379af54f142c65fbfe"

    // This variable contains the Home Screen Menu Details
    private val _homeScreenDetails = MutableStateFlow<UiState<HomeMenuResponse>>(UiState.Idle)
    val homeScreenDetails = _homeScreenDetails.asStateFlow()

    /**
     * This function fetches the Home Screen Details from the Server
     */
    private fun getHomeDetails(status: String, date: String) {

        if (_homeScreenDetails.value is UiState.Loading)
            return

        _homeScreenDetails.value = UiState.Loading

        viewModelScope.launch {
            _homeScreenDetails.value = trackingRepo.getHomeDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).toUiState()
        }
    }

    // This variable contains the water tracking details
    private val _waterDetails = MutableStateFlow<UiState<WaterResponse>>(UiState.Idle)
    val waterDetails = _waterDetails.asStateFlow()

    /**
     * This function fetches the Water Tracking Details from the Server
     */
    private fun getWaterDetails(status: String, date: String) {

        if (_waterDetails.value is UiState.Loading)
            return

        _waterDetails.value = UiState.Loading

        viewModelScope.launch {
            _waterDetails.value = trackingRepo.getWaterDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).toUiState()
        }
    }


    // This variable contains the steps Tracking Details
    private val _stepsDetails = MutableStateFlow<UiState<StepsResponse>>(UiState.Idle)
    val stepsDetails = _stepsDetails.asStateFlow()

    /**
     * This function fetches the steps Tracking details from the Server
     */
    private fun getStepsDetails(status: String, date: String) {

        if (_stepsDetails.value is UiState.Loading)
            return

        _stepsDetails.value = UiState.Loading

        viewModelScope.launch {
            _stepsDetails.value = trackingRepo.getStepsDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).toUiState()
        }
    }


    // This variable contains Meditation Tracking Details
    private val _meditationDetails = MutableStateFlow<UiState<MeditationResponse>>(UiState.Idle)
    val meditationDetails = _meditationDetails.asStateFlow()

    private fun getMeditationDetails(status: String, date: String) {

        if (_meditationDetails.value is UiState.Loading)
            return

        _meditationDetails.value = UiState.Loading

        viewModelScope.launch {
            _meditationDetails.value = trackingRepo.getMeditationDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).toUiState()
        }
    }


    // This variable contains the Breathing Tracking Details
    private val _breathingDetails = MutableStateFlow<UiState<BreathingResponse>>(UiState.Idle)
    val breathingDetails = _breathingDetails.asStateFlow()

    /**
     * This function fetches the breathing tracking details from the Server
     */
    private fun getBreathingDetails(status: String, date: String) {

        if (_breathingDetails.value is UiState.Loading)
            return

        _breathingDetails.value = UiState.Loading

        viewModelScope.launch {
            _breathingDetails.value = trackingRepo.getBreathingDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).toUiState()
        }
    }

    // This variable contains the sleep tracking Details
    private val _sleepDetails = MutableStateFlow<UiState<SleepResponse>>(UiState.Idle)
    val sleepDetails = _sleepDetails.asStateFlow()

    /**
     * This function fetches the sleep details from the Server
     */
    private fun getSleepDetails(status: String, date: String) {

        if (_sleepDetails.value is UiState.Loading)
            return

        _sleepDetails.value = UiState.Loading

        viewModelScope.launch {
            _sleepDetails.value = trackingRepo.getSleepDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).toUiState()
        }
    }

    // This variable contains the Sunlight Tracking Details
    private val _sunlightDetails = MutableStateFlow<UiState<SunlightResponse>>(UiState.Idle)
    val sunlightDetails = _sunlightDetails.asStateFlow()

    /**
     * This function fetches the Sunlight Tracking Details from the server
     */
    private fun getSunlightDetails(status: String, date: String) {

        if (_sunlightDetails.value is UiState.Loading)
            return

        _sunlightDetails.value = UiState.Loading

        viewModelScope.launch {
            _sunlightDetails.value = trackingRepo.getSunlightDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                status = status
            ).toUiState()
        }
    }


    // This variable contains the Sunlight Tracking Details
    private val _exerciseDetails = MutableStateFlow<UiState<ExerciseResponse>>(UiState.Idle)
    val exerciseDetails = _exerciseDetails.asStateFlow()

    /**
     * This function fetches the Sunlight Tracking Details from the server
     */
    private fun getExerciseDetails(status: String, date: String, exercise: String) {

        if (_exerciseDetails.value is UiState.Loading)
            return

        _exerciseDetails.value = UiState.Loading

        viewModelScope.launch {
            _exerciseDetails.value = trackingRepo.getExerciseDetails(
                uid = uid,
                date = date,
                location = "bangalore",
                exercise = exercise,
                status = status
            ).toUiState()
        }
    }

    private var currentTrackOption: TrackOption = TrackOption.HomeMenuOption

    fun uiEventListener(event: TrackUiEvent) {
        when (event) {
            is TrackUiEvent.SetTrackOption -> {
                currentTrackOption = event.trackOption
            }

            is TrackUiEvent.SetTrackStatus -> {
                when (event.chosenOption) {
                    0 -> currentTrackOption.trackStatus = TrackOption.TrackStatus.StatusDaily
                    1 -> currentTrackOption.trackStatus = TrackOption.TrackStatus.StatusWeekly
                    2 -> currentTrackOption.trackStatus = TrackOption.TrackStatus.StatusMonthly
                    3 -> currentTrackOption.trackStatus = TrackOption.TrackStatus.StatusYearly
                }
                handleTrackerOption()
            }

            is TrackUiEvent.SetNewDate -> {
                onDateChanged(event.newDate)
            }

            is TrackUiEvent.ClickedPreviousDateButton -> {
                onPreviousCalendarButtonClick()
            }

            is TrackUiEvent.ClickedNextDateButton -> {
                onNextCalendarButtonClick()
            }
        }
    }

    private fun handleTrackerOption() {
        when (currentTrackOption) {
            is TrackOption.HomeMenuOption -> {
                getHomeDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate(),
                )
            }

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

            is TrackOption.YogaOption -> {
                getExerciseDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate(),
                    exercise = "yoga"
                )
            }

            is TrackOption.DanceOption -> {
                getExerciseDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate(),
                    exercise = "dance"
                )
            }

            is TrackOption.WorkoutOption -> {
                getExerciseDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate(),
                    exercise = "workout"
                )
            }

            is TrackOption.HiitOption -> {
                getExerciseDetails(
                    status = currentTrackOption.trackStatus.status,
                    date = handleTrackerDate(),
                    exercise = "hiit"
                )
            }
        }
    }

    private fun handleTrackerDate(): String {

        // TODO :- Clean up the code
//        return when (currentTrackOption.trackStatus) {
//            is TrackOption.TrackStatus.StatusDaily -> "2023-June-05"
//            is TrackOption.TrackStatus.StatusWeekly -> "2023-June-05"
//            is TrackOption.TrackStatus.StatusMonthly -> "June-2023"
//            is TrackOption.TrackStatus.StatusYearly -> "2023"
//        }

        return when (currentTrackOption.trackStatus) {
            is TrackOption.TrackStatus.StatusDaily -> {
//                "2023-June-05"
                val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd")
                _calendarData.value.format(dateTimeFormatter)
            }

            is TrackOption.TrackStatus.StatusWeekly -> {
//                "2023-June-05"
                val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd")
                _calendarData.value.format(dateTimeFormatter)
            }

            is TrackOption.TrackStatus.StatusMonthly -> {
//                "June-2023"
                val dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM-yyyy")
                _calendarData.value.format(dateTimeFormatter)
            }

            is TrackOption.TrackStatus.StatusYearly -> {
//                "2023"
                val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy")
                _calendarData.value.format(dateTimeFormatter)
            }
        }
    }
}