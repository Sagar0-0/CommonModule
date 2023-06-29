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
import androidx.compose.foundation.lazy.LazyRow
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
import fit.asta.health.thirdparty.spotify.view.components.MusicSmallTrack
import fit.asta.health.thirdparty.spotify.view.components.MusicTrack
import fit.asta.health.thirdparty.spotify.view.components.StateControl
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX


/**
 * This function shows the spotify features and spotify integration in our app
 *
 * @param modifier THis is the modifier passed from the parent function
 * @param spotifyViewModelX This variable contains the viewModel which contains the business logic
 */
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
        StateControl(
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

        // This function draws the recommendation Tracks for the User
        StateControl(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            networkState = spotifyViewModelX.recommendationTracks,
            onCurrentStateInitialized = {
                spotifyViewModelX.getRecommendationTracks()
            }
        ) { networkResponse ->
            networkResponse.data?.let { recommendations ->

                // Showing the Tracks List UI inside a Lazy Row
                LazyRow(
                    modifier = Modifier
                        .height(210.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp)
                ) {
                    items(recommendations.tracks.size) {
                        val currentItem = recommendations.tracks[it]

                        // This function draws the Track UI
                        MusicTrack(
                            imageUri = currentItem.album.images[0].url,
                            trackName = currentItem.name,
                            trackArtists = currentItem.artists,
                            trackUri = currentItem.id
                        )
                    }
                }
            }
        }
    }
}