package fit.asta.health.thirdparty.spotify.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.thirdparty.spotify.SpotifyNavRoutes
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.view.components.MusicArtistsUI
import fit.asta.health.thirdparty.spotify.view.components.MusicPlayableSmallCards
import fit.asta.health.thirdparty.spotify.view.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.view.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX


/**
 * This function shows the spotify features and spotify integration in our app
 *
 * @param modifier THis is the modifier passed from the parent function
 * @param navController This is used to navigate from one screen to a different screen
 * @param spotifyViewModelX This variable contains the viewModel which contains the business logic
 */
@Composable
fun ThirdPartyScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    spotifyViewModelX: SpotifyViewModelX
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
            spotifyViewModelX.currentUserData.collectAsState().value.data?.displayName?.let {
                Text(
                    text = "Hey $it !!",

                    // Text and Font Properties
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W800,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Search and Profile Icon
            Row {

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
                            navController.navigate(SpotifyNavRoutes.SearchScreen.routes)
                        }
                )

                // Spacing
                Spacer(modifier = Modifier.width(8.dp))

                // Profile Icon
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface,

                    // Modifications
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {

                            // Redirecting to Spotify Profile Screen
                            navController.navigate(SpotifyNavRoutes.ProfileScreen.routes)
                        }
                )
            }
        }

        // This checks the state of the user recently Played Tracks and shows them
        MusicStateControl(
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth(),
            networkState = spotifyViewModelX.userRecentlyPlayedTracks.collectAsState().value,
            onCurrentStateInitialized = {
                spotifyViewModelX.getCurrentUserRecentlyPlayedTracks()
            }
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
                            spotifyViewModelX.playSpotifySong(currentItem.uri)
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
            networkState = spotifyViewModelX.recommendationTracks.collectAsState().value,
            onCurrentStateInitialized = {
                spotifyViewModelX.getRecommendationTracks()
            }
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
                                spotifyViewModelX.setTrackId(currentItem.id)
                                navController.navigate(SpotifyNavRoutes.TrackDetailScreen.routes)
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
            networkState = spotifyViewModelX.userTopTracks.collectAsState().value,
            onCurrentStateInitialized = {
                spotifyViewModelX.getUserTopTracks()
            }
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
                                spotifyViewModelX.setTrackId(currentItem.id)
                                navController.navigate(SpotifyNavRoutes.TrackDetailScreen.routes)
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
            networkState = spotifyViewModelX.userTopArtists.collectAsState().value,
            onCurrentStateInitialized = {
                spotifyViewModelX.getUserTopArtists()
            }
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

                            spotifyViewModelX.playSpotifySong(currentItem.uri)
                        }
                    }
                }
            }
        }
    }
}