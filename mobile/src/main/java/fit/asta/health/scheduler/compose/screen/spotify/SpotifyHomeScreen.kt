package fit.asta.health.scheduler.compose.screen.spotify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.scheduler.compose.components.SpotifyHomeHeader
import fit.asta.health.scheduler.compose.components.SpotifyMusicItem
import fit.asta.health.thirdparty.spotify.model.net.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.model.net.search.TrackList
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall

@Composable
fun SpotifyHomeScreen(
    recentlyData: SpotifyNetworkCall<SpotifyUserRecentlyPlayedModel>,
    topMixData: SpotifyNetworkCall<TrackList>,
    loadRecentlyPlayed: () -> Unit,
    loadTopMix: () -> Unit,
    navSearch: () -> Unit,
    playSong: (String) -> Unit
) {

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

        item {
            SpotifyHomeHeader(onSearchIconClicked = navSearch)
        }

        item {
            Text(
                text = "Recently Played",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        when (recentlyData) {
            is SpotifyNetworkCall.Initialized -> {}
            is SpotifyNetworkCall.Loading -> {}
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

                        }
                    }
                }
            }

            is SpotifyNetworkCall.Failure -> {}
        }

        item {
            Text(
                text = "Top Mixes",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        when (topMixData) {
            is SpotifyNetworkCall.Initialized -> {}
            is SpotifyNetworkCall.Loading -> {}
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

                        }
                    }
                }
            }

            is SpotifyNetworkCall.Failure -> {}
        }
    }
}