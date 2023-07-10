package fit.asta.health.thirdparty.spotify.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.thirdparty.spotify.view.navigation.SpotifyNavRoutes
import fit.asta.health.thirdparty.spotify.view.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.view.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

/**
 * This function contains the UI of the Favourite Screen
 *
 * @param spotifyViewModelX This variable contains the viewModel which contains the business logic
 * @param navController This helps to navigate through Different Screens
 */
@Composable
fun FavouriteScreen(
    spotifyViewModelX: SpotifyViewModelX,
    navController: NavController
) {

    LaunchedEffect(Unit) {
        spotifyViewModelX.getAllTracks()
        spotifyViewModelX.getAllAlbums()
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
            networkState = spotifyViewModelX.allTracks.collectAsState().value,
            onCurrentStateInitialized = { spotifyViewModelX.getAllTracks() },
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
                                spotifyViewModelX.setTrackId(currentItem.id)
                                navController.navigate(SpotifyNavRoutes.TrackDetailScreen.routes)
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
            networkState = spotifyViewModelX.allAlbums.collectAsState().value,
            onCurrentStateInitialized = { spotifyViewModelX.getAllAlbums() },
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
                                spotifyViewModelX.setAlbumId(currentItem.id)
                                navController.navigate(SpotifyNavRoutes.AlbumDetailScreen.routes)
                            }
                        }
                    }
                }
            }
        )
    }
}