package fit.asta.health.thirdparty.spotify.view.screens

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.thirdparty.spotify.view.components.MusicTrack
import fit.asta.health.thirdparty.spotify.view.components.StateControl
import fit.asta.health.thirdparty.spotify.viewmodel.FavouriteViewModelX

// Preview Composable Function
@Preview(name = "Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        Surface {
            FavouriteScreen(favouriteViewModelX = hiltViewModel())
        }
    }
}

/**
 * This function contains the UI of the Favourite Screen
 *
 * @param modifier THis is the modifier passed from the parent function
 * @param favouriteViewModelX This variable contains the viewModel which contains the business logic
 */
@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    favouriteViewModelX: FavouriteViewModelX
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

        Button(
            onClick = {

                Toast.makeText(context, "Not Yet Implemented", Toast.LENGTH_SHORT).show()

                // TODO :- Write Code to import Local Music and Audio
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 12.dp, start = 12.dp, end = 12.dp)
        ) {
            Text(
                text = "Import Local Music",
                modifier = Modifier
                    .padding(4.dp)
            )
        }

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
        StateControl(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth(),
            networkState = favouriteViewModelX.allTracks.collectAsState().value,
            onCurrentStateInitialized = { favouriteViewModelX.getAllTracks() },
            onCurrentStateSuccess = { networkState ->
                networkState.data?.let { trackList ->
                    LazyRow(
                        modifier = Modifier
                            .height(210.dp)
                            .width(LocalConfiguration.current.screenWidthDp.dp)
                    ) {
                        items(trackList.size) {

                            MusicTrack(
                                imageUri = trackList[it].trackAlbum!!.images[0].url,
                                trackName = trackList[it].trackName!!,
                                trackArtists = trackList[it].trackArtists!!,
                                trackUri = trackList[it].trackUri!!
                            ) { uri ->
                                val spotifyIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
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
        StateControl(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth(),
            networkState = favouriteViewModelX.allAlbums.collectAsState().value,
            onCurrentStateInitialized = { favouriteViewModelX.getAllAlbums() },
            onCurrentStateSuccess = { networkState ->
                networkState.data?.let { albumList ->
                    LazyRow(
                        modifier = Modifier
                            .height(210.dp)
                            .width(LocalConfiguration.current.screenWidthDp.dp)
                    ) {
                        items(albumList.size) {
                            MusicTrack(
                                imageUri = albumList[it].images[0].url,
                                trackName = albumList[it].name,
                                trackArtists = albumList[it].artists,
                                trackUri = albumList[it].uri
                            ) { uri ->
                                val spotifyIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                                activity.startActivity(spotifyIntent)
                            }
                        }
                    }
                }
            }
        )
    }
}