package fit.asta.health.thirdparty.spotify.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed.Track
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import fit.asta.health.thirdparty.spotify.view.components.FailureScreen
import fit.asta.health.thirdparty.spotify.view.components.LoadingScreen
import fit.asta.health.thirdparty.spotify.view.components.MusicSmallTrack
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

@Composable
fun ThirdPartyScreen(
    modifier: Modifier = Modifier,
    spotifyViewModelX: SpotifyViewModelX
) {

    val context = LocalContext.current

    // Root Composable function
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // Welcoming Text , Search Bar , Profile Icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Welcoming Text with User Name
            spotifyViewModelX.currentUserData.data?.displayName?.let {
                Text(
                    text = "Hey $it !!",

                    // Text and Font Properties
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W800,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Search and Profile Icon
            Row {

                // Search Icon
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface,

                    // Modifications
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            Toast
                                .makeText(context, "Not Yet Implemented", Toast.LENGTH_SHORT)
                                .show()
                            // TODO :- To be implemented
                        }
                )

                // Spacing
                Spacer(modifier = Modifier.width(8.dp))

                // Profile Icon
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface,

                    // Modifications
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            Toast
                                .makeText(context, "Not Yet Implemented", Toast.LENGTH_SHORT)
                                .show()
                            // TODO :- To be implemented
                        }
                )
            }
        }

        // This checks the state of the user recently Played Tracks and shows them
        RecommendedStateControl(
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth(),
            networkState = spotifyViewModelX.userRecentlyPlayedTracks,
            onCurrentStateInitialized = {
                spotifyViewModelX.getCurrentUserRecentlyPlayedTracks()
            }
        ) { networkState ->
            networkState.data?.items.let { albumList ->

                // making a list of tracks to be displayed into the screen
                val tracksList = ArrayList<Track>()
                albumList?.forEach { item ->
                    if (!tracksList.contains(item.track)) {
                        tracksList.add(item.track)
                    }
                }

                // This Draws the Lazy Horizontal Grid for the recently played tracks
                LazyHorizontalGrid(
                    rows = GridCells.Adaptive(58.dp),
                    modifier = Modifier
                        .height(190.dp)
                        .padding(start = 12.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    items(tracksList.size) {

                        // This draws the UI for the tracks
                        MusicSmallTrack(
                            imageUri = tracksList[it].album.images[0].url,
                            trackName = tracksList[it].name,
                            trackUri = tracksList[it].uri
                        )
                    }
                }
            }
        }


        // Recommended Text
        Text(
            text = "Recommended",

            modifier = Modifier
                .padding(12.dp),

            // Text and Font Properties
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W800,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * This checks the state of the call and shows the UI Accordingly
 */
@Composable
fun <T : SpotifyNetworkCall<*>> RecommendedStateControl(
    modifier: Modifier = Modifier,
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
            modifier = modifier
        )

        // Data fetched successfully
        is SpotifyNetworkCall.Success<*> -> onCurrentStateSuccess(networkState)

        // Data Fetched UnSuccessfully
        is SpotifyNetworkCall.Failure<*> -> {
            FailureScreen(
                modifier = modifier,
                textToShow = networkState.message.toString()
            ) {
                onCurrentStateInitialized()
            }
        }
    }
}