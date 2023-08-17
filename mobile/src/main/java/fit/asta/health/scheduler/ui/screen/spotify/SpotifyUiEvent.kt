package fit.asta.health.scheduler.ui.screen.spotify

import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.ToneUiState

sealed class SpotifyUiEvent {

    sealed class NetworkIO : SpotifyUiEvent() {

        object LoadCurrentUserRecentlyPlayedTracks : NetworkIO()

        object LoadUserTopTracks : NetworkIO()

        class SetSearchQueriesAndVariables(val query: String) : NetworkIO()

        object LoadSpotifySearchResult : NetworkIO()
    }

    sealed class HelperEvent : SpotifyUiEvent() {

        class PlaySpotifySong(val url: String) : HelperEvent()

        class OnApplyClick(val toneUiState: ToneUiState) : HelperEvent()
    }

    sealed class LocalIO : SpotifyUiEvent() {

        object LoadAllTracks : LocalIO()

        object LoadAllAlbums : LocalIO()
    }

}
