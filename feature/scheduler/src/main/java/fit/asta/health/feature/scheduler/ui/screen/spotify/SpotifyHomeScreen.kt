package fit.asta.health.feature.scheduler.ui.screen.spotify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.data.spotify.model.common.Track
import fit.asta.health.data.spotify.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.data.spotify.model.saved.SpotifyLikedSongsResponse
import fit.asta.health.data.spotify.model.search.TrackList
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.feature.scheduler.ui.components.SpotifyHomeHeader
import fit.asta.health.feature.scheduler.ui.components.SpotifyMusicItem
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ToneUiState
import fit.asta.health.resources.strings.R as StringR

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

    LazyColumn(
        modifier = Modifier
            .height(LocalConfiguration.current.screenHeightDp.dp)
            .padding(16.dp)
            .width(LocalConfiguration.current.screenWidthDp.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {

        // Search Option And Spotify Title
        item {
            SpotifyHomeHeader(onSearchIconClicked = navSearch)
        }

        // Recently Played Text
        item {
            Text(
                text = stringResource(StringR.string.recently_played),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Showing the user's recently played Data
        when (recentlyData) {

            // Idle State
            is UiState.Idle -> {
//                setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserRecentlyPlayedTracks)
            }

            // Loading State
            is UiState.Loading -> {
                item {
                    LoadingAnimation()
                }
            }

            // Success State
            is UiState.Success -> {
                recentlyData.data.trackList.let { trackList ->
                    items(trackList.size) {
                        val currentItem = trackList[it]

                        val textToShow = currentItem.track.artists
                            .map { artist -> artist.name }
                            .toString()
                            .filterNot { char ->
                                char == '[' || char == ']'
                            }.trim()

                        SpotifyMusicItem(
                            imageUri = currentItem.track.album.images.firstOrNull()?.url,
                            name = currentItem.track.name,
                            secondaryText = textToShow,
                            onCardClick = {
                                setEvent(
                                    SpotifyUiEvent.HelperEvent.PlaySpotifySong(
                                        currentItem.track.uri
                                    )
                                )
                            }
                        ) {

                            setEvent(
                                SpotifyUiEvent.HelperEvent.OnApplyClick(
                                    ToneUiState(
                                        name = currentItem.track.name,
                                        type = 1,
                                        uri = currentItem.track.previewUrl ?: "hi"
                                    )
                                )
                            )
                        }
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                item {
                    AppErrorScreen(desc = recentlyData.resId.toStringFromResId()) {
                        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserRecentlyPlayedTracks)
                    }
                }
            }

            else -> {}
        }

        // Top Mixes Text
        item {
            Text(
                text = stringResource(StringR.string.top_mixes),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Showing the user's top mix Data
        when (topMixData) {

            // Idle State
            is UiState.Idle -> {
//                setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopTracks)
            }

            // Loading State
            is UiState.Loading -> {
                item {
                    LoadingAnimation()
                }
            }

            // Success State
            is UiState.Success -> {
                topMixData.data.trackList.let { trackList ->
                    items(trackList.size) {

                        val currentItem = trackList[it]

                        val textToShow = currentItem.artists
                            .map { artist -> artist.name }
                            .toString()
                            .filterNot { char ->
                                char == '[' || char == ']'
                            }.trim()

                        SpotifyMusicItem(
                            imageUri = currentItem.album.images.firstOrNull()?.url,
                            name = currentItem.name,
                            secondaryText = textToShow,
                            onCardClick = {
                                setEvent(
                                    SpotifyUiEvent.HelperEvent.PlaySpotifySong(
                                        currentItem.uri
                                    )
                                )
                            }
                        ) {
                            setEvent(
                                SpotifyUiEvent.HelperEvent.OnApplyClick(
                                    ToneUiState(
                                        name = currentItem.name,
                                        type = 1,
                                        uri = currentItem.previewUrl ?: "hi"
                                    )
                                )
                            )
                        }
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                item {
                    AppErrorScreen(desc = topMixData.resId.toStringFromResId()) {
                        setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopTracks)
                    }
                }
            }

            else -> {}
        }


        // Liked Songs Text
        item {
            Text(
                text = stringResource(StringR.string.liked_songs),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Showing the user's Spotify Liked Songs
        when (likedSongs) {

            // Idle state
            is UiState.Idle -> {
//                setEvent(SpotifyUiEvent.NetworkIO.LoadLikedSongs)
            }

            // Loading State
            is UiState.Loading -> {
                item {
                    LoadingAnimation()
                }
            }

            is UiState.Success -> {
                items(likedSongs.data.trackList.size) {

                    // Current Item
                    val currentItem = likedSongs.data.trackList[it]

                    val textToShow = currentItem.track.artists
                        .map { artist -> artist.name }
                        .toString()
                        .filterNot { char ->
                            char == '[' || char == ']'
                        }.trim()

                    SpotifyMusicItem(
                        imageUri = currentItem.track.album.images.firstOrNull()?.url,
                        name = currentItem.track.name,
                        secondaryText = textToShow,
                        onCardClick = {
                            setEvent(
                                SpotifyUiEvent.HelperEvent.PlaySpotifySong(
                                    currentItem.track.uri
                                )
                            )
                        }
                    ) {
                        setEvent(
                            SpotifyUiEvent.HelperEvent.OnApplyClick(
                                ToneUiState(
                                    name = currentItem.track.name,
                                    type = 1,
                                    uri = currentItem.track.previewUrl ?: ""
                                )
                            )
                        )
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                item {
                    AppErrorScreen(desc = likedSongs.resId.toStringFromResId()) {
                        setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
                    }
                }
            }

            else -> {}
        }

        // Favourite Tracks Text
        item {
            Text(
                text = stringResource(StringR.string.favourite_tracks),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Showing the users favourite tracks
        when (favouriteTracks) {

            // Idle state
            is UiState.Idle -> {
//                setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
            }

            // Loading State
            is UiState.Loading -> {
                item {
                    LoadingAnimation()
                }
            }

            // Success State
            is UiState.Success -> {

                items(favouriteTracks.data.size) {
                    // Current Item
                    val currentItem = favouriteTracks.data[it]

                    val textToShow = currentItem.artists
                        .map { artist -> artist.name }
                        .toString()
                        .filterNot { char ->
                            char == '[' || char == ']'
                        }.trim()

                    SpotifyMusicItem(
                        imageUri = currentItem.album.images.firstOrNull()?.url,
                        name = currentItem.name,
                        secondaryText = textToShow,
                        onCardClick = {
                            setEvent(
                                SpotifyUiEvent.HelperEvent.PlaySpotifySong(
                                    currentItem.uri
                                )
                            )
                        }
                    ) {
                        setEvent(
                            SpotifyUiEvent.HelperEvent.OnApplyClick(
                                ToneUiState(
                                    name = currentItem.name,
                                    type = 1,
                                    uri = currentItem.uri
                                )
                            )
                        )
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                item {
                    AppErrorScreen(desc = favouriteTracks.resId.toStringFromResId()) {
                        setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
                    }
                }
            }

            else -> {}
        }

        // Favourite Albums Text
        item {
            Text(
                text = stringResource(StringR.string.favourite_albums),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Showing the Favourite Album List of the user
        when (favouriteAlbums) {

            // Idle State
            is UiState.Idle -> {
//                setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
            }

            // Loading State
            is UiState.Loading -> {
                item {
                    LoadingAnimation()
                }
            }

            // Success State
            is UiState.Success -> {
                items(favouriteAlbums.data.size) {

                    // Current Item
                    val currentItem = favouriteAlbums.data[it]

                    val textToShow = currentItem.artists
                        .map { artist -> artist.name }
                        .toString()
                        .filterNot { char ->
                            char == '[' || char == ']'
                        }.trim()

                    SpotifyMusicItem(
                        imageUri = currentItem.images.firstOrNull()?.url,
                        name = currentItem.name,
                        secondaryText = textToShow,
                        onCardClick = {
                            setEvent(
                                SpotifyUiEvent.HelperEvent.PlaySpotifySong(currentItem.uri)
                            )
                        }
                    ) {

                        setEvent(
                            SpotifyUiEvent.HelperEvent.OnApplyClick(
                                ToneUiState(
                                    name = currentItem.name,
                                    type = 1,
                                    uri = currentItem.uri
                                )
                            )
                        )
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                item {
                    AppErrorScreen(desc = favouriteAlbums.resId.toStringFromResId()) {
                        setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
                    }
                }
            }

            else -> {}
        }
    }
}