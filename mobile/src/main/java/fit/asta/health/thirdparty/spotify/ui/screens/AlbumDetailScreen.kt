package fit.asta.health.thirdparty.spotify.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fit.asta.health.thirdparty.spotify.data.model.common.Album
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import fit.asta.health.thirdparty.spotify.ui.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.ui.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.ui.events.SpotifyUiEvent

/**
 * This screen shows the details of the Albums choose by the user and gives the options to either
 * add this to his favourites or open the Album in spotify
 *
 * @param albumNetworkResponse This contains the state of the Network Data of the Album
 * @param albumLocalResponse This contains the state of the Local Data of the Album
 * @param setEvent This function is used to send the ui events from the UI to the View Model layer
 */
@Composable
fun AlbumDetailScreen(
    albumNetworkResponse: SpotifyNetworkCall<Album>,
    albumLocalResponse: SpotifyNetworkCall<List<Album>>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    // context is stored to Show the Toast
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadAlbumDetails)
        setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
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
            networkState = albumNetworkResponse,
            onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadAlbumDetails) }
        ) { networkResponse ->

            // This function checks for the Local Response from Local Database
            MusicStateControl(
                networkState = albumLocalResponse,
                onCurrentStateInitialized = { setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums) }
            ) { localResponse ->

                // Parsing the Network Album Data
                networkResponse.data.let { networkAlbumData ->

                    // This variable stores if the Local Database contains this Album or not
                    val isPresent = localResponse.data.let { albumList ->
                        if (networkAlbumData != null && albumList != null)
                            albumList.contains(networkAlbumData)
                        else
                            false
                    }

                    // Showing the Album Data at the Screen
                    if (networkAlbumData != null) {
                        MusicLargeImageColumn(
                            imageUri = networkAlbumData.images.firstOrNull()?.url,
                            headerText = networkAlbumData.name,
                            secondaryTexts = networkAlbumData.artists
                        ) {}

                        // Add to Favourites Button
                        Button(
                            onClick = {

                                // Checking if the Album is already present or not
                                if (!isPresent) {
                                    setEvent(SpotifyUiEvent.LocalIO.InsertAlbum(networkAlbumData))
                                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                                } else {
                                    setEvent(SpotifyUiEvent.LocalIO.DeleteAlbum(networkAlbumData))
                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 32.dp, bottom = 12.dp, start = 12.dp, end = 12.dp)
                        ) {
                            Text(
                                text = if (!isPresent)
                                    "Add To Favourites" else "Delete From Favourites",
                                modifier = Modifier
                                    .padding(4.dp)
                            )
                        }

                        // Play on Spotify Button
                        Button(
                            onClick = {
                                setEvent(SpotifyUiEvent.HelperEvent.PlaySong(networkAlbumData.uri))
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
}