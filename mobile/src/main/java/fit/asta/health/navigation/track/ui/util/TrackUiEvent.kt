package fit.asta.health.navigation.track.ui.util

import java.time.LocalDate

sealed class TrackUiEvent {

    class SetTrackOption(val trackOption: TrackOption) : TrackUiEvent()

    class SetTrackStatus(val chosenOption: Int) : TrackUiEvent()

    class SetNewDate(val newDate : LocalDate) : TrackUiEvent()

    data object ClickedPreviousDateButton : TrackUiEvent()

    data object ClickedNextDateButton : TrackUiEvent()
}