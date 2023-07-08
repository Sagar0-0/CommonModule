package fit.asta.health.thirdparty.spotify.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fit.asta.health.thirdparty.spotify.view.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.view.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

/**
 * This screen shows the details of the Albums choose by the user and gives the options to either
 * add this to his favourites or open the Album in spotify
 *
 * @param spotifyViewModelX This is the spotify ViewModel which contacts with the spotify apis
 */
@Composable
fun AlbumDetailScreen(
    spotifyViewModelX: SpotifyViewModelX
) {

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // This Function Draws the Album Details to the Screen according to the network and local responses
        AlbumDetailHelper(
            spotifyViewModelX = spotifyViewModelX
        )

    }
}

@Composable
private fun AlbumDetailHelper(
    spotifyViewModelX: SpotifyViewModelX
) {

    // context is stored to Show the Toast
    val context = LocalContext.current

    // This function checks for the Network Response from the API
    MusicStateControl(
        networkState = spotifyViewModelX.albumDetailsResponse.collectAsState().value,
        onCurrentStateInitialized = {
            spotifyViewModelX.getAlbumDetails()
        }
    ) { networkResponse ->

        // This function checks for the Local Response from Local Database
        MusicStateControl(
            networkState = spotifyViewModelX.allAlbums.collectAsState().value,
            onCurrentStateInitialized = {
                spotifyViewModelX.getAllAlbums()
            }
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
                                spotifyViewModelX.insertAlbum(networkAlbumData)
                                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                            } else {
                                spotifyViewModelX.deleteAlbum(networkAlbumData)
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
                            spotifyViewModelX.playSpotifySong(networkAlbumData.uri)
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