package fit.asta.health.thirdparty.spotify.view.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.thirdparty.spotify.view.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.view.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

/**
 * This function contains the UI of the Favourite Screen
 *
 * @param modifier THis is the modifier passed from the parent function
 * @param spotifyViewModelX This variable contains the viewModel which contains the business logic
 */
@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    spotifyViewModelX: SpotifyViewModelX
) {

    val context = LocalContext.current

    // Current Activity of the function so that we can move to the spotify app
    val activity = context as Activity

    // Root Composable function
    Column(
        modifier = modifier
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

                            MusicLargeImageColumn(
                                imageUri = trackList[it].album.images.firstOrNull()?.url,
                                headerText = trackList[it].name,
                                secondaryTexts = trackList[it].artists
                            ) {
                                val spotifyIntent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(trackList[it].uri))
                                activity.startActivity(spotifyIntent)
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
                            MusicLargeImageColumn(
                                imageUri = albumList[it].images.firstOrNull()?.url,
                                headerText = albumList[it].name,
                                secondaryTexts = albumList[it].artists
                            ) {
                                val spotifyIntent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(albumList[it].uri))
                                activity.startActivity(spotifyIntent)
                            }
                        }
                    }
                }
            }
        )
    }
}