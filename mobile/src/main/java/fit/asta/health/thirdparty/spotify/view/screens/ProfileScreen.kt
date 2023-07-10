package fit.asta.health.thirdparty.spotify.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.thirdparty.spotify.view.components.MusicSmallImageRow
import fit.asta.health.thirdparty.spotify.view.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    spotifyViewModelX: SpotifyViewModelX
) {

    // This keeps the selected Item by the user
    val selectedItem = remember { mutableIntStateOf(0) }

    // Option List for the screen
    val categoryList = listOf(
        "Track",
        "Playlist",
        "Artist",
        "Album",
        "Show",
        "Episode"
    )

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        FlowRow(
            modifier = Modifier
                .padding(12.dp)
                .wrapContentWidth()
        ) {


            categoryList.forEachIndexed { index, option ->

                OutlinedButton(
                    onClick = {
                        selectedItem.intValue = index
                    },
                    modifier = Modifier
                        .padding(4.dp),

                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (index == selectedItem.intValue)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                    )
                ) {

                    // Category Name
                    Text(
                        text = option,
                        fontSize = 12.sp
                    )
                }
            }
        }

        when (selectedItem.intValue) {
            0 -> TracksUI(spotifyViewModelX)
            1 -> PlaylistUI(spotifyViewModelX)
            2 -> ArtistsUI(spotifyViewModelX)
            3 -> AlbumsUI(spotifyViewModelX)
            4 -> ShowUI(spotifyViewModelX)
            5 -> EpisodeUI(spotifyViewModelX)
        }
    }
}

@Composable
fun TracksUI(spotifyViewModelX: SpotifyViewModelX) {

    LaunchedEffect(Unit) {
        spotifyViewModelX.getCurrentUserTracks()
    }

    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = spotifyViewModelX.currentUserTracks.collectAsState().value,
        onCurrentStateInitialized = {
            spotifyViewModelX.getCurrentUserTracks()
        }
    ) { networkResponse ->
        networkResponse.data?.trackList.let { trackList ->

            LazyColumn(
                modifier = Modifier
                    .height(LocalConfiguration.current.screenHeightDp.dp)
                    .padding(start = 12.dp)
                    .width(LocalConfiguration.current.screenWidthDp.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (trackList != null) {
                    items(trackList.size) {

                        // current Item
                        val currentItem = trackList[it]

                        val textToShow = currentItem.track.artists
                            .map { artist -> artist.name }
                            .toString()
                            .filterNot { char ->
                                char == '[' || char == ']'
                            }
                            .trim()
                        MusicSmallImageRow(
                            imageUri = currentItem.track.album.images.firstOrNull()?.url,
                            name = currentItem.track.name,
                            secondaryText = textToShow
                        ) {
                            spotifyViewModelX.playSpotifySong(currentItem.track.uri)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlaylistUI(spotifyViewModelX: SpotifyViewModelX) {

    LaunchedEffect(Unit) {
        spotifyViewModelX.getCurrentUserPlaylist()
    }

    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = spotifyViewModelX.currentUserPlaylist.collectAsState().value,
        onCurrentStateInitialized = {
            spotifyViewModelX.getCurrentUserPlaylist()
        }
    ) { networkResponse ->
        networkResponse.data?.playlistList.let { playList ->

            LazyColumn(
                modifier = Modifier
                    .height(LocalConfiguration.current.screenHeightDp.dp)
                    .padding(start = 12.dp)
                    .width(LocalConfiguration.current.screenWidthDp.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (playList != null) {
                    items(playList.size) {

                        // current Item
                        val currentItem = playList[it]
                        val textToShow = "${currentItem.type} • ${currentItem.owner.displayName}"

                        MusicSmallImageRow(
                            imageUri = currentItem.images.firstOrNull()?.url,
                            name = currentItem.name,
                            secondaryText = textToShow
                        ) {
                            spotifyViewModelX.playSpotifySong(currentItem.uri)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArtistsUI(spotifyViewModelX: SpotifyViewModelX) {

    LaunchedEffect(Unit) {
        spotifyViewModelX.getCurrentUserFollowingArtists()
    }
    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = spotifyViewModelX.currentUserFollowingArtist.collectAsState().value,
        onCurrentStateInitialized = {
            spotifyViewModelX.getCurrentUserFollowingArtists()
        }
    ) { networkResponse ->
        networkResponse.data?.artistList?.artistList.let { artistsList ->

            LazyColumn(
                modifier = Modifier
                    .height(LocalConfiguration.current.screenHeightDp.dp)
                    .padding(start = 12.dp)
                    .width(LocalConfiguration.current.screenWidthDp.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (artistsList != null) {
                    items(artistsList.size) {

                        // current Item
                        val currentItem = artistsList[it]

                        MusicSmallImageRow(
                            imageUri = currentItem.images.firstOrNull()?.url,
                            name = currentItem.name,
                            secondaryText = ""
                        ) {
                            spotifyViewModelX.playSpotifySong(currentItem.uri)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlbumsUI(spotifyViewModelX: SpotifyViewModelX) {

    LaunchedEffect(Unit) {
        spotifyViewModelX.getCurrentUserAlbum()
    }

    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = spotifyViewModelX.currentUserAlbum.collectAsState().value,
        onCurrentStateInitialized = {
            spotifyViewModelX.getCurrentUserAlbum()
        }
    ) { networkResponse ->
        networkResponse.data?.albumList.let { albumList ->

            LazyColumn(
                modifier = Modifier
                    .height(LocalConfiguration.current.screenHeightDp.dp)
                    .padding(start = 12.dp)
                    .width(LocalConfiguration.current.screenWidthDp.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (albumList != null) {
                    items(albumList.size) {

                        // current Item
                        val currentItem = albumList[it]

                        MusicSmallImageRow(
                            imageUri = currentItem.album.images.firstOrNull()?.url,
                            name = currentItem.album.name,
                            secondaryText = currentItem.album.type
                        ) {
                            spotifyViewModelX.playSpotifySong(currentItem.album.uri)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowUI(spotifyViewModelX: SpotifyViewModelX) {

    LaunchedEffect(Unit) {
        spotifyViewModelX.getCurrentUserShows()
    }

    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = spotifyViewModelX.currentUserShow.collectAsState().value,
        onCurrentStateInitialized = {
            spotifyViewModelX.getCurrentUserShows()
        }
    ) { networkResponse ->
        networkResponse.data?.showList.let { showList ->

            LazyColumn(
                modifier = Modifier
                    .height(LocalConfiguration.current.screenHeightDp.dp)
                    .padding(start = 12.dp)
                    .width(LocalConfiguration.current.screenWidthDp.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (showList != null) {
                    items(showList.size) {

                        // current Item
                        val currentItem = showList[it]
                        val textToShow = "${currentItem.show.type} • ${currentItem.show.publisher}"

                        MusicSmallImageRow(
                            imageUri = currentItem.show.images.firstOrNull()?.url,
                            name = currentItem.show.name,
                            secondaryText = textToShow
                        ) {
                            spotifyViewModelX.playSpotifySong(currentItem.show.uri)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodeUI(spotifyViewModelX: SpotifyViewModelX) {

    LaunchedEffect(Unit) {
        spotifyViewModelX.getCurrentUserEpisode()
    }

    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = spotifyViewModelX.currentUserEpisode.collectAsState().value,
        onCurrentStateInitialized = {
            spotifyViewModelX.getCurrentUserEpisode()
        }
    ) { networkResponse ->
        networkResponse.data?.episodeList.let { episodeList ->

            LazyColumn(
                modifier = Modifier
                    .height(LocalConfiguration.current.screenHeightDp.dp)
                    .padding(start = 12.dp)
                    .width(LocalConfiguration.current.screenWidthDp.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (episodeList != null) {
                    items(episodeList.size) {

                        // current Item
                        val currentItem = episodeList[it]

                        MusicSmallImageRow(
                            imageUri = currentItem.episode.images.firstOrNull()?.url,
                            name = currentItem.episode.name,
                            secondaryText = currentItem.episode.type
                        ) {
                            spotifyViewModelX.playSpotifySong(currentItem.episode.uri)
                        }
                    }
                }
            }
        }
    }
}