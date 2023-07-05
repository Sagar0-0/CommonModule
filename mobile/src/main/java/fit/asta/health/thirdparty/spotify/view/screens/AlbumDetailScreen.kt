package fit.asta.health.thirdparty.spotify.view.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX
import fit.asta.health.thirdparty.spotify.view.components.MusicLargeImageColumn
import fit.asta.health.thirdparty.spotify.view.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.viewmodel.FavouriteViewModelX
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

/**
 * This screen shows the details of the Albums choose by the user and gives the options to either
 * add this to his favourites or open the Album in spotify
 *
 * @param spotifyViewModelX This is the spotify ViewModel which contacts with the spotify apis
 * @param favouriteViewModelX This is the favourites ViewModels which contacts with the local database
 */
@Composable
fun AlbumDetailScreen(
    spotifyViewModelX: SpotifyViewModelX,
    favouriteViewModelX: FavouriteViewModelX
) {
    // context is stored to Show the Toast
    val context = LocalContext.current

    // Current Activity of the function so that we can move to the spotify app
    val activity = context as Activity

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Checks If the Album Details is fetched or not
        MusicStateControl(
            modifier = Modifier
                .fillMaxSize(),
            networkState = spotifyViewModelX.albumDetailsResponse,
            onCurrentStateInitialized = {
                spotifyViewModelX.getTrackDetails()
            }
        ) { networkResponse ->

            networkResponse.data.let { albumData ->

                if (albumData != null) {
                    MusicLargeImageColumn(
                        imageUri = albumData.images.firstOrNull()?.url,
                        headerText = albumData.name,
                        secondaryTexts = albumData.artists
                    ) {}

                    // Add to Favourites Button
                    Button(
                        onClick = {

                            favouriteViewModelX.insertAlbum(
                                AlbumX(
                                    albumType = albumData.albumType,
                                    artists = albumData.artists,
                                    availableMarkets = albumData.availableMarkets,
                                    externalUrls = albumData.externalUrls,
                                    href = albumData.href,
                                    id = albumData.id,
                                    images = albumData.images,
                                    name = albumData.name,
                                    releaseDate = albumData.releaseDate,
                                    releaseDatePrecision = albumData.releaseDatePrecision,
                                    totalTracks = albumData.totalTracks,
                                    type = albumData.type,
                                    uri = albumData.uri
                                )
                            )

                            Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp, bottom = 12.dp, start = 12.dp, end = 12.dp)
                    ) {
                        Text(
                            text = "Add To Favourites",
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }
                }

                // Play on Spotify Button
                Button(
                    onClick = {
                        val spotifyIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(albumData!!.uri))
                        activity.startActivity(spotifyIntent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
                ) {
                    Text(
                        text = "Open at Spotify",
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}