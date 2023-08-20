package fit.asta.health.navigation.track.ui.util

sealed class TrackUiEvent {

    class SetTrackOption(val trackOption: TrackOption) : TrackUiEvent()

    class SetTrackStatus(val chosenOption : Int) : TrackUiEvent()

}