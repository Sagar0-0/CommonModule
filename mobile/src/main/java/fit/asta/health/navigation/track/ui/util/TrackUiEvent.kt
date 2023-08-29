package fit.asta.health.navigation.track.ui.util

sealed class TrackUiEvent {

    class SetTrackOption(val trackOption: TrackOption) : TrackUiEvent()

    class SetTrackStatus(val chosenOption: Int) : TrackUiEvent()

    class SetNewDate(val date: Int, val month: Int, val year: Int) : TrackUiEvent()

    data object ClickedPreviousDateButton : TrackUiEvent()

    data object ClickedNextDateButton : TrackUiEvent()
}