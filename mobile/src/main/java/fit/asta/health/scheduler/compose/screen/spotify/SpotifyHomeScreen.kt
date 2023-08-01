package fit.asta.health.scheduler.compose.screen.spotify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.scheduler.compose.components.SpotifyHomeHeader
import fit.asta.health.scheduler.compose.components.SpotifyMusicItem
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.ToneUiState
import fit.asta.health.scheduler.model.net.spotify.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.scheduler.model.net.spotify.search.TrackList
import fit.asta.health.scheduler.util.SpotifyNetworkCall

@Composable
fun SpotifyHomeScreen(
    recentlyData: SpotifyNetworkCall<SpotifyUserRecentlyPlayedModel>,
    topMixData: SpotifyNetworkCall<TrackList>,
    loadRecentlyPlayed: () -> Unit,
    loadTopMix: () -> Unit,
    navSearch: () -> Unit,
    playSong: (String) -> Unit,
    onApplyClick: (ToneUiState) -> Unit
) {

    // Fetching both the Top Mix and the Users Recently Played Songs
    LaunchedEffect(Unit) {
        loadTopMix()
        loadRecentlyPlayed()
    }

    LazyColumn(
        modifier = Modifier
            .height(LocalConfiguration.current.screenHeightDp.dp)
            .padding(16.dp)
            .width(LocalConfiguration.current.screenWidthDp.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {

        // Search Option And Spotify Title
        item {
            SpotifyHomeHeader(onSearchIconClicked = navSearch)
        }

        // Recently Played Text
        item {
            Text(
                text = "Recently Played",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Showing the user's recently played Data
        when (recentlyData) {

            // Initialized State
            is SpotifyNetworkCall.Initialized -> {}

            // Loading State
            is SpotifyNetworkCall.Loading -> {
                item {
                    LoadingAnimation()
                }
            }

            // Success State
            is SpotifyNetworkCall.Success -> {
                recentlyData.data?.trackList?.let { trackList ->
                    items(trackList.size) {
                        val currentItem = trackList[it]

                        val textToShow = currentItem.track.artists
                            .map { artist -> artist.name }
                            .toString()
                            .filterNot { char ->
                                char == '[' || char == ']'
                            }.trim()

                        SpotifyMusicItem(
                            imageUri = currentItem.track.album.images.firstOrNull()?.url,
                            name = currentItem.track.name,
                            secondaryText = textToShow,
                            onCardClick = { playSong(currentItem.track.uri) }
                        ) {
                            onApplyClick(
                                ToneUiState(
                                    name = currentItem.track.name,
                                    type = 1,
                                    uri = currentItem.track.uri
                                )
                            )
                        }
                    }
                }
            }

            // Failure State
            is SpotifyNetworkCall.Failure -> {
                item {
                    FailureScreen(
                        onClick = loadRecentlyPlayed,
                        textToShow = recentlyData.message.toString()
                    )
                }
            }
        }

        // Top Mixes Text
        item {
            Text(
                text = "Top Mixes",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Showing the user's top mix Data
        when (topMixData) {

            // Initialized State
            is SpotifyNetworkCall.Initialized -> {}

            // Loading State
            is SpotifyNetworkCall.Loading -> {
                item {
                    LoadingAnimation()
                }
            }

            // Success State
            is SpotifyNetworkCall.Success -> {
                topMixData.data?.trackList?.let { trackList ->
                    items(trackList.size) {

                        val currentItem = trackList[it]

                        val textToShow = currentItem.artists
                            .map { artist -> artist.name }
                            .toString()
                            .filterNot { char ->
                                char == '[' || char == ']'
                            }.trim()

                        SpotifyMusicItem(
                            imageUri = currentItem.album.images.firstOrNull()?.url,
                            name = currentItem.name,
                            secondaryText = textToShow,
                            onCardClick = { playSong(currentItem.uri) }
                        ) {
                            onApplyClick(
                                ToneUiState(
                                    name = currentItem.name,
                                    type = 1,
                                    uri = currentItem.uri
                                )
                            )
                        }
                    }
                }
            }

            // Failure State
            is SpotifyNetworkCall.Failure -> {
                item {
                    FailureScreen(
                        onClick = loadTopMix,
                        textToShow = topMixData.message.toString()
                    )
                }
            }
        }
    }
}

/**
 * This function shows the Failure Screen
 */
@Composable
private fun FailureScreen(
    onClick: () -> Unit,
    textToShow: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
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