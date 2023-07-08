package fit.asta.health.thirdparty.spotify.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.thirdparty.spotify.SpotifyNavRoutes
import fit.asta.health.thirdparty.spotify.view.components.MusicArtistsUI
import fit.asta.health.thirdparty.spotify.view.components.MusicSmallImageRow
import fit.asta.health.thirdparty.spotify.view.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.view.components.SearchBar
import fit.asta.health.thirdparty.spotify.view.components.MusicFilterOptions
import fit.asta.health.thirdparty.spotify.view.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

/**
 * This is the Spotify Searching Screen which searches according to the User Queries and gives the
 * user some results
 *
 * @param navController This is used to navigate to different screens
 * @param spotifyViewModelX This is the viewModel for the Spotify Integration
 */
@Composable
fun SearchScreen(
    navController: NavController,
    spotifyViewModelX: SpotifyViewModelX
) {

    // Context and Activity of the Function
    val context = LocalContext.current

    // This is the Input of the User
    val userSearchInput = remember { mutableStateOf("") }

    // It says if the sort button is active or not
    val isSortActive = remember { mutableStateOf(false) }

    // This is the filter List provided to the User to choose from
    val filterList = remember {
        mutableStateMapOf(
            "Album" to true,
            "Artist" to true,
            "Playlist" to true,
            "Track" to true
        )
    }

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {

        // This function Draws the Search Bar to the Screen
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            userInput = userSearchInput.value,
            onUserInputChange = {
                userSearchInput.value = it
            },
            onFilterButtonClick = {
                isSortActive.value = !isSortActive.value
            }
        ) {

            // Making a string with the filters chosen by the User
            val type = filterList
                .filter { it.value }
                .keys
                .toString()
                .filterNot { it == '[' || it == ']' || it == ' ' }
                .lowercase()

            // Checking if both the user Input and the filtered type is not empty
            if (userSearchInput.value.isNotEmpty() && type.isNotEmpty()) {

                // Setting the Query parameters in the ViewModel
                spotifyViewModelX.setSearchQueriesAndVariables(
                    query = userSearchInput.value,
                    type = type
                )
            } else if (userSearchInput.value.isEmpty()) {

                // No Search Query
                Toast.makeText(
                    context,
                    "Search Bar Empty!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                // No Search Filter
                Toast.makeText(
                    context,
                    "Select at least one type for searching!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // If the sort button is clicked then this UI will be rendered
        if (isSortActive.value) {
            MusicFilterOptions(
                filterList = filterList
            ) { newBoolean, key ->
                filterList[key] = newBoolean
            }
        }

        // This function controls all the UI state of this screen
        MusicStateControl(
            modifier = Modifier
                .fillMaxSize(),
            networkState = spotifyViewModelX.spotifySearch,
            onCurrentStateInitialized = {
                spotifyViewModelX.getSpotifySearchResult()
            }
        ) { networkResponse ->

            // Handling the Tracks UI here
            networkResponse.data?.tracks?.trackList.let { trackList ->

                // Tracks
                Text(
                    text = "Tracks",

                    modifier = Modifier
                        .padding(12.dp),

                    // Text and Font Properties
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W800,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

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
                                spotifyViewModelX.getTrackDetails()
                                navController.navigate(SpotifyNavRoutes.TrackDetailScreen.routes)
                            }
                        }
                    }
                }
            }

            // Handling the Artists UI here
            networkResponse.data?.artists?.artistList.let { artistsList ->

                // Artists
                Text(
                    text = "Artists",

                    modifier = Modifier
                        .padding(12.dp),

                    // Text and Font Properties
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W800,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                LazyRow(
                    modifier = Modifier
                        .height(210.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp)
                ) {

                    if (artistsList != null) {
                        items(artistsList.size) {

                            // current Item
                            val currentItem = artistsList[it]

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

            // Handling the Album UI here
            networkResponse.data?.albums?.albumItems.let { albumList ->

                // Albums Text
                Text(
                    text = "Albums",

                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),

                    // Text and Font Properties
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W800,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                LazyRow(
                    modifier = Modifier
                        .height(210.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp)
                ) {
                    if (albumList != null) {
                        items(albumList.size) {

                            // Current Item
                            val currentItem = albumList[it]
                            MusicLargeImageColumn(
                                imageUri = currentItem.images.firstOrNull()?.url,
                                headerText = currentItem.name,
                                secondaryTexts = currentItem.artists
                            ) {

                                // Navigating the Album Details Screen to get the Album Details
                                spotifyViewModelX.setAlbumId(currentItem.id)
                                spotifyViewModelX.getAlbumDetails()
                                navController.navigate(SpotifyNavRoutes.AlbumDetailScreen.routes)
                            }
                        }
                    }
                }
            }

            // Handling the Playlist UI here
            networkResponse.data?.playlists?.items.let { playlists ->

                // Playlist Text
                Text(
                    text = "Playlists",

                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),

                    // Text and Font Properties
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W800,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                LazyColumn(
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp)
                        .padding(start = 12.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    if (playlists != null) {
                        items(playlists.size) {

                            // current Item
                            val currentItem = playlists[it]
                            val textToShow =
                                "${currentItem.type} • ${currentItem.owner.displayName}"

                            MusicSmallImageRow(
                                imageUri = currentItem.images.firstOrNull()?.url,
                                name = currentItem.name,
                                secondaryText = textToShow
                            ) {
                                spotifyViewModelX.playSpotifySong(currentItem.uri)
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}