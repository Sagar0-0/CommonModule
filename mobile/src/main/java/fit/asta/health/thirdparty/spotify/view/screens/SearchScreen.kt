package fit.asta.health.thirdparty.spotify.view.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import fit.asta.health.thirdparty.spotify.view.components.MusicPlaylistUI
import fit.asta.health.thirdparty.spotify.view.components.MusicTrack
import fit.asta.health.thirdparty.spotify.view.components.SearchBar
import fit.asta.health.thirdparty.spotify.view.components.SortOptionsUI
import fit.asta.health.thirdparty.spotify.view.components.StateControl
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
    val activity = context as Activity

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
            SortOptionsUI(
                filterList = filterList
            ) { newBoolean, key ->
                filterList[key] = newBoolean
            }
        }

        // This function controls all the UI state of this screen
        StateControl(
            modifier = Modifier
                .fillMaxSize(),
            networkState = spotifyViewModelX.spotifySearch,
            onCurrentStateInitialized = {
                spotifyViewModelX.getSpotifySearchResult()
            }
        ) { networkResponse ->

            // Handling the Tracks UI here
            networkResponse.data?.tracks?.items.let { trackList ->

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
                            MusicTrack(
                                imageUri = currentItem.album.images.firstOrNull()?.url,
                                headerText = currentItem.name,
                                secondaryTexts = currentItem.artists,
                                uri = "Not Using"
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
            networkResponse.data?.artists?.items.let { artistsList ->

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
                                artistName = currentItem.name,
                                artistsUri = currentItem.uri
                            ) { uri ->

                                val spotifyIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                                activity.startActivity(spotifyIntent)
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
                            MusicTrack(
                                imageUri = albumList[it].images.firstOrNull()?.url,
                                headerText = albumList[it].name,
                                secondaryTexts = albumList[it].artists,
                                uri = albumList[it].uri
                            ) { uri ->
                                val spotifyIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                                activity.startActivity(spotifyIntent)
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

                LazyRow(
                    modifier = Modifier
                        .height(64.dp)
                        .padding(start = 12.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (playlists != null) {
                        items(playlists.size) {

                            val currentItem = playlists[it]

                            MusicPlaylistUI(
                                imageUri = currentItem.images.firstOrNull()?.url,
                                playlistName = currentItem.name,
                                playlistUri = currentItem.uri,
                                playlistType = currentItem.type,
                                playlistOwner = currentItem.owner.displayName
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}