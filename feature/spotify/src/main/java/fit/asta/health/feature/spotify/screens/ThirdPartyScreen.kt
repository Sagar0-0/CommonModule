package fit.asta.health.feature.spotify.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.spotify.model.common.Track
import fit.asta.health.data.spotify.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.data.spotify.model.recommendations.SpotifyRecommendationModel
import fit.asta.health.data.spotify.model.search.ArtistList
import fit.asta.health.data.spotify.model.search.TrackList
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.spotify.components.MusicArtistsUI
import fit.asta.health.feature.spotify.components.MusicLargeImageColumn
import fit.asta.health.feature.spotify.components.MusicPlayableSmallCards
import fit.asta.health.feature.spotify.events.SpotifyUiEvent
import fit.asta.health.feature.spotify.navigation.SpotifyNavRoutes
import fit.asta.health.resources.strings.R

/**
 * This function shows the spotify features and spotify integration in our app
 *
 * @param modifier This is the modifier passed from the parent function
 * @param displayName This contains the name of the account Holder
 * @param recentlyPlayed This contains the list of Recently Played Tracks
 * @param recommendedData This contains the list of all the recommended Tracks
 * @param topTracksData This contains the top Tracks of the User
 * @param topArtistsData This contains the top Artists of the user
 * @param setEvent This function handles all the events from the UI to the View Model
 * @param navigator This function navigates from one Screen to the other one
 */
@Composable
fun ThirdPartyScreen(
    modifier: Modifier = Modifier,
    displayName: String?,
    recentlyPlayed: UiState<SpotifyUserRecentlyPlayedModel>,
    recommendedData: UiState<SpotifyRecommendationModel>,
    topTracksData: UiState<TrackList>,
    topArtistsData: UiState<ArtistList>,
    setEvent: (SpotifyUiEvent) -> Unit,
    navigator: (String) -> Unit
) {

    // Root Composable function
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {

        // Welcoming Text , Search Bar , Profile Icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Welcoming Text with User Name
            Text(
                text = "Hey ${displayName ?: "User"} !!",

                // Text and Font Properties
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W800,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Search and Profile Icon
            Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {

                // Search Icon
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,

                    // Modifications
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {

                            // Redirecting to Spotify Search Screen
                            navigator(SpotifyNavRoutes.SearchScreen.routes)
                        }
                )

                // Profile Icon
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,

                    // Modifications
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {

                            // Redirecting to Spotify Profile Screen
                            navigator(SpotifyNavRoutes.ProfileScreen.routes)
                        }
                )
            }
        }

        // This checks the state of the user recently Played Tracks and shows them
        when (recentlyPlayed) {

            is UiState.Idle -> {
                setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserRecentlyPlayedTracks)
            }

            is UiState.Loading -> {
                LoadingAnimation(
                    modifier = Modifier
                        .height(190.dp)
                        .fillMaxWidth()
                )
            }

            is UiState.Success -> {

//                setEvent(SpotifyUiEvent.NetworkIO.LoadRecommendationTracks)
                recentlyPlayed.data.trackList.let { networkTrackList ->

                    // making a list of tracks to be displayed into the screen
                    val tracksList = ArrayList<Track>()
                    networkTrackList.forEach { item ->
                        if (!tracksList.contains(item.track)) {
                            tracksList.add(item.track)
                        }
                    }

                    // This Draws the Lazy Horizontal Grid for the recently played tracks
                    LazyHorizontalGrid(
                        rows = GridCells.Adaptive(58.dp),
                        modifier = Modifier
                            .height(190.dp)
                            .padding(start = 12.dp)
                            .width(LocalConfiguration.current.screenWidthDp.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        items(tracksList.size) {

                            // Current Item
                            val currentItem = tracksList[it]

                            // This draws the UI for the tracks
                            MusicPlayableSmallCards(
                                imageUri = currentItem.album.images.firstOrNull()?.url,
                                name = currentItem.name
                            ) {
                                setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.uri))
                            }
                        }
                    }
                }
            }

            is UiState.ErrorMessage -> {
                AppErrorScreen(
                    modifier = Modifier
                        .height(190.dp)
                        .fillMaxWidth(),
                    desc = recentlyPlayed.resId.toStringFromResId()
                ) {
                    setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserRecentlyPlayedTracks)
                }
            }

            else -> {}
        }

        // Recommended Text
        Text(
            text = stringResource(id = R.string.recommended),

            modifier = Modifier
                .padding(12.dp),

            // Text and Font Properties
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W800,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        // This function draws the recommendation Tracks for the User
        when (recommendedData) {

            // Idle state
            is UiState.Idle -> {
                setEvent(SpotifyUiEvent.NetworkIO.LoadRecommendationTracks)
            }

            // Loading State
            is UiState.Loading -> {
                LoadingAnimation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                )
            }

            // Success State
            is UiState.Success -> {

                // Showing the Tracks List UI inside a Lazy Row
                LazyRow(
                    modifier = Modifier
                        .height(210.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp)
                ) {
                    items(recommendedData.data.trackList.size) {
                        val currentItem = recommendedData.data.trackList[it]

                        // This function draws the Track UI
                        MusicLargeImageColumn(
                            imageUri = currentItem.album.images.firstOrNull()?.url,
                            headerText = currentItem.name,
                            secondaryTexts = currentItem.artists
                        ) {

                            // Navigating to the Track Details Screen
                            setEvent(SpotifyUiEvent.HelperEvent.SetTrackId(currentItem.id))
                            navigator(SpotifyNavRoutes.TrackDetailScreen.routes)
                        }
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                AppErrorScreen(desc = recommendedData.resId.toStringFromResId()) {
                    setEvent(SpotifyUiEvent.NetworkIO.LoadRecommendationTracks)
                }
            }

            else -> {}
        }

        // Top Tracks
        Text(
            text = stringResource(id = R.string.top_tracks),

            modifier = Modifier
                .padding(12.dp),

            // Text and Font Properties
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W800,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        // This function draws the top tracks for the User
        when (topTracksData) {

            // Idle state
            is UiState.Idle -> {
                setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopTracks)
            }

            // Loading State
            is UiState.Loading -> {
                LoadingAnimation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                )
            }

            // Success State
            is UiState.Success -> {

                // Showing the Tracks List UI inside a Lazy Row
                LazyRow(
                    modifier = Modifier
                        .height(210.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp)
                ) {
                    items(topTracksData.data.trackList.size) {
                        val currentItem = topTracksData.data.trackList[it]

                        // This function draws the Track UI
                        MusicLargeImageColumn(
                            imageUri = currentItem.album.images.firstOrNull()?.url,
                            headerText = currentItem.name,
                            secondaryTexts = currentItem.artists
                        ) {

                            // Navigating to the Track Details Screen
                            setEvent(SpotifyUiEvent.HelperEvent.SetTrackId(currentItem.id))
                            navigator(SpotifyNavRoutes.TrackDetailScreen.routes)
                        }
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                AppErrorScreen(desc = topTracksData.resId.toStringFromResId()) {
                    setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopTracks)
                }
            }

            else -> {}
        }

        // Top Artists
        Text(
            text = stringResource(id = R.string.top_artists),

            modifier = Modifier
                .padding(12.dp),

            // Text and Font Properties
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W800,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        // This function draws the top Artists for the User
        when (topArtistsData) {

            // Idle state
            is UiState.Idle -> {
                setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopArtists)
            }

            // Loading State
            is UiState.Loading -> {
                LoadingAnimation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                )
            }

            // Success State
            is UiState.Success -> {

                LazyRow(
                    modifier = Modifier
                        .height(210.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp)
                ) {

                    topArtistsData.data.artistList.let { topArtistsList ->
                        items(topArtistsList.size) {

                            // current Item
                            val currentItem = topArtistsList[it]

                            // Shows the Artists UI
                            MusicArtistsUI(
                                imageUri = currentItem.images.firstOrNull()?.url,
                                artistName = currentItem.name
                            ) {
                                setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.uri))
                            }
                        }
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                AppErrorScreen(desc = topArtistsData.resId.toStringFromResId()) {
                    setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopArtists)
                }
            }

            else -> {}
        }
    }
}