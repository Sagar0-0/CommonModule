package fit.asta.health.feature.scheduler.ui.screen.spotify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.spotify.remote.model.common.Album
import fit.asta.health.data.spotify.remote.model.common.Track
import fit.asta.health.data.spotify.remote.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.data.spotify.remote.model.saved.SpotifyLikedSongsResponse
import fit.asta.health.data.spotify.remote.model.search.TrackList
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.feature.scheduler.ui.components.SpotifyHomeHeader
import fit.asta.health.feature.scheduler.ui.screen.spotify.components.favouriteAlbums
import fit.asta.health.feature.scheduler.ui.screen.spotify.components.favouriteTracks
import fit.asta.health.feature.scheduler.ui.screen.spotify.components.likedSongs
import fit.asta.health.feature.scheduler.ui.screen.spotify.components.recentlyPlayed
import fit.asta.health.feature.scheduler.ui.screen.spotify.components.topMixes

@Composable
fun SpotifyHomeScreen(
    recentlyData: UiState<SpotifyUserRecentlyPlayedModel>,
    topMixData: UiState<TrackList>,
    likedSongs: UiState<SpotifyLikedSongsResponse>,
    favouriteTracks: UiState<List<Track>>,
    favouriteAlbums: UiState<List<Album>>,
    setEvent: (SpotifyUiEvent) -> Unit,
    navSearch: () -> Unit,
) {

    // Fetching both the Top Mix and the Users Recently Played Songs
    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserRecentlyPlayedTracks)
        setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopTracks)
        setEvent(SpotifyUiEvent.NetworkIO.LoadLikedSongs)
        setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
        setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
    }
    AppScaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SpotifyHomeHeader(onSearchIconClicked = navSearch)
        }) { padding ->
        LazyColumn(
            modifier = Modifier
                .height(LocalConfiguration.current.screenHeightDp.dp)
                .padding(
                    start = AppTheme.spacing.level2,
                    end = AppTheme.spacing.level2,
                    bottom = AppTheme.spacing.level2,
                    top = padding.calculateTopPadding()
                )
                .width(LocalConfiguration.current.screenWidthDp.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            recentlyPlayed(recentlyData, setEvent)
            topMixes(topMixData, setEvent)
            likedSongs(likedSongs, setEvent)
            favouriteTracks(favouriteTracks, setEvent)
            favouriteAlbums(favouriteAlbums, setEvent)
        }
    }
}








