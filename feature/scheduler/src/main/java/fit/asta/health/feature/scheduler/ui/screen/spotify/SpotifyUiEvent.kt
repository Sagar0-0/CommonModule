package fit.asta.health.feature.scheduler.ui.screen.spotify

import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ToneUiState

sealed class SpotifyUiEvent {

    sealed class NetworkIO : SpotifyUiEvent() {

        data object LoadCurrentUserRecentlyPlayedTracks : NetworkIO()

        data object LoadUserTopTracks : NetworkIO()

        class SetSearchQueriesAndVariables(val query: String) : NetworkIO()

        data object LoadSpotifySearchResult : NetworkIO()

        data object LoadLikedSongs : NetworkIO()
    }

    sealed class HelperEvent : SpotifyUiEvent() {

        class PlaySpotifySong(val url: String) : HelperEvent()

        class OnApplyClick(val toneUiState: ToneUiState) : HelperEvent()
    }

    sealed class LocalIO : SpotifyUiEvent() {

        data object LoadAllTracks : LocalIO()

        data object LoadAllAlbums : LocalIO()
    }

}
