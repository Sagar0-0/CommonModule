package fit.asta.health.thirdparty.spotify.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fit.asta.health.thirdparty.spotify.view.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.view.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX


/**
 * This screen shows the details of the Tracks choose by the user and gives the options to either
 * add this to his favourites or open the track in spotify
 *
 * @param spotifyViewModelX This is the spotify ViewModel which contacts with the spotify apis
 */
@Composable
fun TrackDetailsScreen(
    spotifyViewModelX: SpotifyViewModelX
) {

    LaunchedEffect(Unit) {
        spotifyViewModelX.getTrackDetails()
    }

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TrackDetailHelper(
            spotifyViewModelX = spotifyViewModelX
        )

    }
}

@Composable
private fun TrackDetailHelper(
    spotifyViewModelX: SpotifyViewModelX
) {

    // context is stored to Show the Toast
    val context = LocalContext.current

    // This function checks for the Network Response from the API
    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = spotifyViewModelX.trackDetailsResponse.collectAsState().value,
        onCurrentStateInitialized = {
            spotifyViewModelX.getTrackDetails()
        }
    ) { networkResponse ->

        // This function checks for the Local Response from Local Database
        MusicStateControl(
            networkState = spotifyViewModelX.allTracks.collectAsState().value,
            onCurrentStateInitialized = {
                spotifyViewModelX.getAllTracks()
            }
        ) { localResponse ->

            // Parsing the Network Track Data
            networkResponse.data.let { networkTrack ->

                // This variable stores if the Local Database contains this Track or not
                val isPresent = localResponse.data.let {
                    if (it != null && networkTrack != null)
                        it.contains(networkTrack)
                    else
                        false
                }

                if (networkTrack != null) {
                    MusicLargeImageColumn(
                        imageUri = networkTrack.album.images.firstOrNull()?.url,
                        headerText = networkTrack.name,
                        secondaryTexts = networkTrack.artists
                    ) {}

                    // Add and Remove Favourites Button
                    Button(
                        onClick = {

                            if (!isPresent) {
                                spotifyViewModelX.insertTrack(networkTrack)
                                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                            } else {
                                spotifyViewModelX.deleteTrack(networkTrack)
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp, bottom = 12.dp, start = 12.dp, end = 12.dp)
                    ) {
                        Text(
                            text = if (!isPresent)
                                "Add To Favourites" else "Remove From Favourites",
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }

                    // Play on Spotify Button
                    Button(
                        onClick = {
                            spotifyViewModelX.playSpotifySong(url = networkTrack.uri)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
                    ) {
                        Text(
                            text = "Play using Spotify",
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}