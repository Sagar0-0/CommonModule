package fit.asta.health.feature.spotify.screens

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.spotify.model.search.SpotifySearchModel
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.feature.spotify.components.MusicArtistsUI
import fit.asta.health.feature.spotify.components.MusicFilterOptions
import fit.asta.health.feature.spotify.components.MusicLargeImageColumn
import fit.asta.health.feature.spotify.components.MusicSmallImageRow
import fit.asta.health.feature.spotify.components.SearchBar
import fit.asta.health.feature.spotify.events.SpotifyUiEvent
import fit.asta.health.feature.spotify.navigation.SpotifyNavRoutes
import fit.asta.health.resources.strings.R

/**
 * This is the Spotify Searching Screen which searches according to the User Queries and gives the
 * user some results
 *
 * @param spotifySearchState This variable contains the spotify search state
 * @param setEvent This variable is used to send events from the UI to the View Model layer
 * @param navigator This variable helps in navigation to different screens
 */
@Composable
fun SearchScreen(
    spotifySearchState: UiState<SpotifySearchModel>,
    setEvent: (SpotifyUiEvent) -> Unit,
    navigator: (String) -> Unit
) {

    // Context and Activity of the Function
    val context = LocalContext.current

    // This is the Input of the User
    val userSearchInput = remember { mutableStateOf("") }

    // It says if the sort button is active or not
    val isSortActive = remember { mutableStateOf(false) }

    // This is the filter List provided to the User to choose from
    val filterList = remember {
        mutableStateMapOf("Album" to true, "Artist" to true, "Playlist" to true, "Track" to true)
    }

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        val searchBarEmpty = stringResource(id = R.string.search_bar_empty)
        val selectAtLeastOneOption = stringResource(id = R.string.select_at_least_one_type)

        // This function Draws the Search Bar to the Screen
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            userInput = userSearchInput.value,
            onUserInputChange = { userSearchInput.value = it },
            onFilterButtonClick = { isSortActive.value = !isSortActive.value }
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
                setEvent(
                    SpotifyUiEvent.HelperEvent.SetSearchQueriesAndVariables(
                        query = userSearchInput.value,
                        type = type
                    )
                )

            } else if (userSearchInput.value.isEmpty()) {

                // No Search Query
                Toast.makeText(context, searchBarEmpty, Toast.LENGTH_SHORT).show()
            } else {

                // No Search Filter
                Toast.makeText(context, selectAtLeastOneOption, Toast.LENGTH_SHORT).show()
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
        when (spotifySearchState) {

            is UiState.Idle -> {}

            is UiState.Loading -> {
                LoadingAnimation(modifier = Modifier.fillMaxSize())
            }

            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {

                    // Handling the Tracks UI here
                    spotifySearchState.data.tracks.trackList.let { trackList ->

                        // Tracks
                        item {
                            Text(
                                text = stringResource(id = R.string.tracks),

                                modifier = Modifier
                                    .padding(12.dp),

                                // Text and Font Properties
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.W800,
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Showing the Tracks List UI inside a Lazy Row
                        item {
                            LazyRow(
                                modifier = Modifier
                                    .height(210.dp)
                                    .width(LocalConfiguration.current.screenWidthDp.dp)
                            ) {
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

                    // Handling the Artists UI here
                    spotifySearchState.data.artists.artistList.let { artistsList ->

                        // Artists
                        item {
                            Text(
                                text = stringResource(id = R.string.artists),

                                modifier = Modifier
                                    .padding(12.dp),

                                // Text and Font Properties
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.W800,
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        item {
                            LazyRow(
                                modifier = Modifier
                                    .height(210.dp)
                                    .width(LocalConfiguration.current.screenWidthDp.dp)
                            ) {

                                items(artistsList.size) {

                                    // current Item
                                    val currentItem = artistsList[it]

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

                    // Handling the Album UI here
                    spotifySearchState.data.albums.albumItems.let { albumList ->

                        // Albums Text
                        item {
                            Text(
                                text = stringResource(id = R.string.albums),

                                modifier = Modifier
                                    .padding(top = 24.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),

                                // Text and Font Properties
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.W800,
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        item {
                            LazyRow(
                                modifier = Modifier
                                    .height(210.dp)
                                    .width(LocalConfiguration.current.screenWidthDp.dp)
                            ) {
                                items(albumList.size) {

                                    // Current Item
                                    val currentItem = albumList[it]
                                    MusicLargeImageColumn(
                                        imageUri = currentItem.images.firstOrNull()?.url,
                                        headerText = currentItem.name,
                                        secondaryTexts = currentItem.artists
                                    ) {

                                        // Navigating the Album Details Screen to get the Album Details
                                        setEvent(
                                            SpotifyUiEvent.HelperEvent.SetAlbumId(
                                                currentItem.id
                                            )
                                        )
                                        navigator(SpotifyNavRoutes.AlbumDetailScreen.routes)
                                    }
                                }
                            }
                        }
                    }

                    // Handling the Playlist UI here
                    spotifySearchState.data.playlists.items.let { playlists ->

                        // Playlist Text
                        item {
                            Text(
                                text = stringResource(id = R.string.playlists),

                                modifier = Modifier
                                    .padding(top = 24.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),

                                // Text and Font Properties
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.W800,
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

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
                                setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.uri))
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            is UiState.ErrorMessage -> {
                AppErrorScreen(desc = spotifySearchState.resId.toStringFromResId()) {
                    setEvent(SpotifyUiEvent.NetworkIO.LoadSpotifySearchResult)
                }
            }

            else -> {}
        }
    }
}