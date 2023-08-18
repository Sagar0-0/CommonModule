package fit.asta.health.navigation.track.view.util

sealed class TrackUiEvent {

    class SetTrackOption(val trackOption: TrackOption) : TrackUiEvent()

    class SetTrackStatus(val chosenOption : Int) : TrackUiEvent()

}