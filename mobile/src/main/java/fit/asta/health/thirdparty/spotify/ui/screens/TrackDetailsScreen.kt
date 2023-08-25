package fit.asta.health.thirdparty.spotify.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.thirdparty.spotify.data.model.common.Track
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import fit.asta.health.thirdparty.spotify.ui.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.ui.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.ui.events.SpotifyUiEvent


/**
 * This screen shows the details of the Tracks choose by the user and gives the options to either
 * add this to his favourites or open the track in spotify
 *
 * @param trackNetworkState This contains the state of the Track Api call from the network
 * @param trackLocalState This contains the state of the Track fetching done from the local database
 * @param setEvent This function is used to send events from the UI to the View Model Layer
 */
@Composable
fun TrackDetailsScreen(
    trackNetworkState: SpotifyNetworkCall<Track>,
    trackLocalState: UiState<List<Track>>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    // context is stored to Show the Toast
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadTrackDetails)
    }

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // This function checks for the Network Response from the API
        MusicStateControl(
            modifier = Modifier.fillMaxSize(),
            networkState = trackNetworkState,
            onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadTrackDetails) }
        ) { networkResponse ->

            // This function checks for the Local Response from Local Database
            when (trackLocalState) {

                // Idle State
                is UiState.Idle -> {
                    setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
                }

                // Loading State
                is UiState.Loading -> {
                    LoadingAnimation(modifier = Modifier.fillMaxSize())
                }

                // Success State
                is UiState.Success -> {

                    // Parsing the Network Track Data
                    networkResponse.data.let { networkTrack ->

                        // This variable stores if the Local Database contains this Track or not
                        val isPresent = trackLocalState.data.let {
                            if (networkTrack != null)
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
                                        setEvent(SpotifyUiEvent.LocalIO.InsertTrack(networkTrack))
                                        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                                    } else {
                                        setEvent(SpotifyUiEvent.LocalIO.DeleteTrack(networkTrack))
                                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 32.dp,
                                        bottom = 12.dp,
                                        start = 12.dp,
                                        end = 12.dp
                                    )
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
                                    setEvent(SpotifyUiEvent.HelperEvent.PlaySong(networkTrack.uri))
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

                // Error State
                is UiState.Error -> {
                    AppErrorScreen(desc = trackLocalState.resId.toStringFromResId()) {
                        setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
                    }
                }
            }
        }
    }
}