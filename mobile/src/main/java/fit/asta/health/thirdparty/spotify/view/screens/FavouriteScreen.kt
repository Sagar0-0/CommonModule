package fit.asta.health.thirdparty.spotify.view.screens

import android.content.res.Configuration
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
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import fit.asta.health.thirdparty.spotify.view.components.FailureScreen
import fit.asta.health.thirdparty.spotify.view.components.LoadingScreen
import fit.asta.health.thirdparty.spotify.view.components.MusicTrack
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
            FavouriteScreen()
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
    favouriteViewModelX: FavouriteViewModelX = hiltViewModel()
) {

    // Root Composable function
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        val context = LocalContext.current

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
                            )
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
                            )
                        }
                    }
                }
            }
        )
    }
}

/**
 * This checks the state of the call and shows the UI Accordingly
 */
@Composable
fun <T : SpotifyNetworkCall<*>> StateControl(
    networkState: T,
    onCurrentStateInitialized: () -> Unit,
    onCurrentStateSuccess: @Composable (T) -> Unit
) {

    // Checking which state is there
    when (networkState) {

        // Nothing is done yet and the fetching will be initiated here
        is SpotifyNetworkCall.Initialized<*> -> onCurrentStateInitialized()

        // The data is being fetched
        is SpotifyNetworkCall.Loading<*> -> LoadingScreen(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth()
        )

        // Data fetched successfully
        is SpotifyNetworkCall.Success<*> -> onCurrentStateSuccess(networkState)

        // Data Fetched UnSuccessfully
        is SpotifyNetworkCall.Failure<*> -> {
            FailureScreen(
                modifier = Modifier
                    .height(210.dp)
                    .fillMaxWidth(),
                textToShow = networkState.message.toString()
            ) {
                onCurrentStateInitialized()
            }
        }
    }
}