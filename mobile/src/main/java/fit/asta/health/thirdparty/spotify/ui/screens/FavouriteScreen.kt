package fit.asta.health.thirdparty.spotify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.thirdparty.spotify.data.model.common.Album
import fit.asta.health.thirdparty.spotify.data.model.common.Track
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import fit.asta.health.thirdparty.spotify.ui.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.ui.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.ui.events.SpotifyUiEvent
import fit.asta.health.thirdparty.spotify.ui.navigation.SpotifyNavRoutes

/**
 * This function contains the UI of the Favourite Screen
 *
 * @param tracksData This contains the state of all the TrackData in the local database
 * @param albumData This contains the state of all the albumData in the local Database
 * @param setEvent This function is used to send events from the UI to View Model layer
 * @param navigator THis function is used to handle all the navigation
 */
@Composable
fun FavouriteScreen(
    tracksData: SpotifyNetworkCall<List<Track>>,
    albumData: SpotifyNetworkCall<List<Album>>,
    setEvent: (SpotifyUiEvent) -> Unit,
    navigator: (String) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
        setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
    }

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // Track Text
        Text(
            text = "Tracks",

            modifier = Modifier
                .padding(8.dp),

            // Text and Font Properties
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W800,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        // This Draws all the Track Cards
        MusicStateControl(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth(),
            networkState = tracksData,
            onCurrentStateInitialized = { setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks) },
            onCurrentStateSuccess = { networkState ->
                networkState.data?.let { trackList ->
                    LazyRow(
                        modifier = Modifier
                            .height(210.dp)
                            .width(LocalConfiguration.current.screenWidthDp.dp)
                    ) {
                        items(trackList.size) {

                            // current Item
                            val currentItem = trackList[it]

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
        )

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

        // This Draws all the Albums Cards
        MusicStateControl(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth(),
            networkState = albumData,
            onCurrentStateInitialized = { setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums) },
            onCurrentStateSuccess = { networkState ->
                networkState.data?.let { albumList ->
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
                                setEvent(SpotifyUiEvent.HelperEvent.SetAlbumId(currentItem.id))
                                navigator(SpotifyNavRoutes.AlbumDetailScreen.routes)
                            }
                        }
                    }
                }
            }
        )
    }
}