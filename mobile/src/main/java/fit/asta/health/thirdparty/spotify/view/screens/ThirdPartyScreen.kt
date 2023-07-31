package fit.asta.health.thirdparty.spotify.view.screens

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
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.thirdparty.spotify.view.navigation.SpotifyNavRoutes
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.model.net.recommendations.SpotifyRecommendationModel
import fit.asta.health.thirdparty.spotify.model.net.search.ArtistList
import fit.asta.health.thirdparty.spotify.model.net.search.TrackList
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import fit.asta.health.thirdparty.spotify.view.components.MusicArtistsUI
import fit.asta.health.thirdparty.spotify.view.components.MusicPlayableSmallCards
import fit.asta.health.thirdparty.spotify.view.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.view.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.view.events.SpotifyUiEvent


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
    recentlyPlayed: SpotifyNetworkCall<SpotifyUserRecentlyPlayedModel>,
    recommendedData: SpotifyNetworkCall<SpotifyRecommendationModel>,
    topTracksData: SpotifyNetworkCall<TrackList>,
    topArtistsData: SpotifyNetworkCall<ArtistList>,
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
            Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {

                // Search Icon
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
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
                    contentDescription = "Library",
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
        MusicStateControl(
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth(),
            networkState = recentlyPlayed,
            onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserRecentlyPlayedTracks) }
        ) { networkState ->
            networkState.data?.trackList.let { networkTrackList ->

                // making a list of tracks to be displayed into the screen
                val tracksList = ArrayList<Track>()
                networkTrackList?.forEach { item ->
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

        // Recommended Text
        Text(
            text = "Recommended",

            modifier = Modifier
                .padding(12.dp),

            // Text and Font Properties
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W800,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        // This function draws the recommendation Tracks for the User
        MusicStateControl(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            networkState = recommendedData,
            onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadRecommendationTracks) }
        ) { networkResponse ->
            networkResponse.data?.trackList.let { trackList ->

                // Showing the Tracks List UI inside a Lazy Row
                LazyRow(
                    modifier = Modifier
                        .height(210.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp)
                ) {
                    if (trackList != null) {
                        items(trackList.size) {
                            val currentItem = trackList[it]

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
            }
        }

        // Top Tracks
        Text(
            text = "Top Tracks",

            modifier = Modifier
                .padding(12.dp),

            // Text and Font Properties
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W800,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        // This function draws the top tracks for the User
        MusicStateControl(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            networkState = topTracksData,
            onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopTracks) }
        ) { networkResponse ->
            networkResponse.data?.trackList.let { itemTopTrack ->

                // Showing the Tracks List UI inside a Lazy Row
                LazyRow(
                    modifier = Modifier
                        .height(210.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp)
                ) {
                    if (itemTopTrack != null) {
                        items(itemTopTrack.size) {
                            val currentItem = itemTopTrack[it]

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
            }
        }

        // Top Artists
        Text(
            text = "Top Artists",

            modifier = Modifier
                .padding(12.dp),

            // Text and Font Properties
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W800,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        // This function draws the top Artists for the User
        MusicStateControl(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            networkState = topArtistsData,
            onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopArtists) }
        ) { networkResponse ->

            LazyRow(
                modifier = Modifier
                    .height(210.dp)
                    .width(LocalConfiguration.current.screenWidthDp.dp)
            ) {

                networkResponse.data?.artistList?.let { topArtistsList ->
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
    }
}