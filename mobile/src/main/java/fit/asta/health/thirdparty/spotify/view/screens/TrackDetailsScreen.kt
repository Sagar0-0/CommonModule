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
import fit.asta.health.thirdparty.spotify.model.db.entity.TrackEntity
import fit.asta.health.thirdparty.spotify.view.components.MusicTrack
import fit.asta.health.thirdparty.spotify.view.components.StateControl
import fit.asta.health.thirdparty.spotify.viewmodel.FavouriteViewModelX
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

@Composable
fun TrackDetailsScreen(
    modifier: Modifier = Modifier,
    spotifyViewModelX: SpotifyViewModelX,
    favouriteViewModelX: FavouriteViewModelX
) {

    // context is stored to Show the Toast
    val context = LocalContext.current

    // Current Activity of the function so that we can move to the spotify app
    val activity = context as Activity

    // Root Composable function
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Checks If the Track Details are fetched or not
        StateControl(
            modifier = Modifier
                .fillMaxSize(),
            networkState = spotifyViewModelX.trackDetailsResponse,
            onCurrentStateInitialized = {
                spotifyViewModelX.getTrackDetails()
            }
        ) { networkResponse ->
            networkResponse.data.let { trackDetails ->

                // Image of The Track
                MusicTrack(
                    imageUri = trackDetails!!.album.images[0].url,
                    trackName = trackDetails.name,
                    trackArtists = trackDetails.artists,
                    trackUri = trackDetails.uri,
                ) {}

                // Add to Favourites Button
                Button(
                    onClick = {
                        favouriteViewModelX.insertTrack(
                            TrackEntity(
                                trackAlbum = trackDetails.album,
                                trackArtists = trackDetails.artists,
                                trackAvailableMarkets = trackDetails.availableMarkets,
                                trackDiscNumber = trackDetails.discNumber,
                                trackDurationMs = trackDetails.durationMs,
                                trackExplicit = trackDetails.explicit,
                                trackId = trackDetails.id,
                                trackExternalIds = trackDetails.externalIds,
                                trackExternalUrls = trackDetails.externalUrls,
                                trackHref = trackDetails.href,
                                trackIsLocal = trackDetails.isLocal,
                                trackName = trackDetails.name,
                                trackPopularity = trackDetails.popularity,
                                trackPreviewUrl = trackDetails.previewUrl,
                                trackTrackNumber = trackDetails.trackNumber,
                                trackType = trackDetails.type,
                                trackUri = trackDetails.uri
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

                // Play on Spotify Button
                Button(
                    onClick = {
                        val spotifyIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trackDetails.uri))
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