package fit.asta.health.thirdparty.spotify.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.thirdparty.spotify.model.db.entity.TrackEntity
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import fit.asta.health.thirdparty.spotify.view.components.MusicTrack
import fit.asta.health.thirdparty.spotify.viewmodel.FavoriteViewModelX

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
 */
@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    favoriteViewModelX: FavoriteViewModelX = hiltViewModel()
) {

    // Root Composable function
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        Button(
            onClick = {
                // TODO :-
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

        // This Draws all the Track Screens
        TracksControl(
            networkState = favoriteViewModelX.allTracks.collectAsState().value,
            onCurrentStateInitialized = { favoriteViewModelX.getAllTracks() },
            onCurrentStateFailure = {
                FailureScreen(textToShow = "Can't Fetch the Tracks !! Retry Again ??") {
                    favoriteViewModelX.getAllTracks()
                }
            },
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
                                trackArtists = trackList[it].trackArtists!!
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
fun TracksControl(
    networkState: SpotifyNetworkCall<List<TrackEntity>>,
    onCurrentStateInitialized: () -> Unit,
    onCurrentStateSuccess: @Composable (SpotifyNetworkCall<List<TrackEntity>>) -> Unit,
    onCurrentStateFailure: @Composable () -> Unit
) {

    // Checking which state is there
    when (networkState) {

        // Nothing is done yet and the fetching will be initiated here
        is SpotifyNetworkCall.Initialized -> onCurrentStateInitialized()

        // The data is being fetched
        is SpotifyNetworkCall.Loading -> LoadingScreen()

        // Data fetched successfully
        is SpotifyNetworkCall.Success -> onCurrentStateSuccess(networkState)

        // Data Fetched UnSuccessfully
        is SpotifyNetworkCall.Failure -> onCurrentStateFailure()
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .height(210.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun FailureScreen(
    textToShow: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 32.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = textToShow,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),

                textAlign = TextAlign.Center
            )
        }
    }
}