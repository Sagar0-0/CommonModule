package fit.asta.health.thirdparty.spotify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.thirdparty.spotify.data.model.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.data.model.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.data.model.library.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.data.model.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.data.model.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.data.model.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.ui.components.MusicProfileOptionList
import fit.asta.health.thirdparty.spotify.ui.components.MusicSmallImageRow
import fit.asta.health.thirdparty.spotify.ui.events.SpotifyUiEvent

@Composable
fun ProfileScreen(
    currentUserTracks: UiState<SpotifyLibraryTracksModel>,
    currentUserPlaylist: UiState<SpotifyUserPlaylistsModel>,
    currentUserArtists: UiState<SpotifyUserFollowingArtist>,
    currentUserAlbums: UiState<SpotifyLibraryAlbumModel>,
    currentUserShows: UiState<SpotifyLibraryShowsModel>,
    currentUserEpisodes: UiState<SpotifyLibraryEpisodesModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    // This keeps the selected Item by the user
    val selectedItem = remember { mutableIntStateOf(0) }

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        MusicProfileOptionList(selectedItem = selectedItem.intValue) { selectedItem.intValue = it }

        when (selectedItem.intValue) {
            0 -> TracksUI(setEvent = setEvent, currentUserTracks = currentUserTracks)
            1 -> PlaylistUI(currentUserPlaylist = currentUserPlaylist, setEvent = setEvent)
            2 -> ArtistsUI(currentUserArtists = currentUserArtists, setEvent = setEvent)
            3 -> AlbumsUI(currentUserAlbums = currentUserAlbums, setEvent = setEvent)
            4 -> ShowUI(currentUserShows = currentUserShows, setEvent = setEvent)
            5 -> EpisodeUI(currentUserEpisodes = currentUserEpisodes, setEvent = setEvent)
        }
    }
}

@Composable
fun TracksUI(
    currentUserTracks: UiState<SpotifyLibraryTracksModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserTracks)
    }

    when (currentUserTracks) {

        is UiState.Idle -> {
//            setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserTracks)
        }

        is UiState.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        is UiState.Success -> {

            currentUserTracks.data.trackList.let { trackList ->

                LazyColumn(
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp)
                        .padding(start = 12.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(trackList.size) {

                        // current Item
                        val currentItem = trackList[it]

                        val textToShow = currentItem.track.artists
                            .map { artist -> artist.name }
                            .toString()
                            .filterNot { char ->
                                char == '[' || char == ']'
                            }.trim()

                        MusicSmallImageRow(
                            imageUri = currentItem.track.album.images.firstOrNull()?.url,
                            name = currentItem.track.name,
                            secondaryText = textToShow
                        ) {
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.track.uri))
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            AppErrorScreen(desc = currentUserTracks.resId.toStringFromResId()) {
                setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserTracks)
            }
        }
    }
}

@Composable
fun PlaylistUI(
    currentUserPlaylist: UiState<SpotifyUserPlaylistsModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserPlaylist)
    }

    when (currentUserPlaylist) {

        is UiState.Idle -> {
//            setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserPlaylist)
        }

        is UiState.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        is UiState.Success -> {

            currentUserPlaylist.data.playlistList.let { playList ->

                LazyColumn(
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp)
                        .padding(start = 12.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(playList.size) {

                        // current Item
                        val currentItem = playList[it]
                        val textToShow = "${currentItem.type} • ${currentItem.owner.displayName}"

                        MusicSmallImageRow(
                            imageUri = currentItem.images.firstOrNull()?.url,
                            name = currentItem.name,
                            secondaryText = textToShow
                        ) {
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.uri))
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            AppErrorScreen(desc = currentUserPlaylist.resId.toStringFromResId()) {
                setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserPlaylist)
            }
        }
    }
}

@Composable
fun ArtistsUI(
    currentUserArtists: UiState<SpotifyUserFollowingArtist>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserArtists)
    }

    when (currentUserArtists) {

        is UiState.Idle -> {
//            setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserArtists)
        }

        is UiState.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        is UiState.Success -> {
            currentUserArtists.data.artistList.artistList.let { artistsList ->

                LazyColumn(
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp)
                        .padding(start = 12.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(artistsList.size) {

                        // current Item
                        val currentItem = artistsList[it]

                        MusicSmallImageRow(
                            imageUri = currentItem.images.firstOrNull()?.url,
                            name = currentItem.name,
                            secondaryText = ""
                        ) {
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.uri))
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            AppErrorScreen(desc = currentUserArtists.resId.toStringFromResId()) {
                setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserArtists)
            }
        }
    }
}

@Composable
fun AlbumsUI(
    currentUserAlbums: UiState<SpotifyLibraryAlbumModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserAlbum)
    }

    when (currentUserAlbums) {

        is UiState.Idle -> {
//            setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserAlbum)
        }

        is UiState.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        is UiState.Success -> {

            currentUserAlbums.data.albumList.let { albumList ->

                LazyColumn(
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp)
                        .padding(start = 12.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(albumList.size) {

                        // current Item
                        val currentItem = albumList[it]

                        MusicSmallImageRow(
                            imageUri = currentItem.album.images.firstOrNull()?.url,
                            name = currentItem.album.name,
                            secondaryText = currentItem.album.type
                        ) {
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.album.uri))
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            AppErrorScreen(desc = currentUserAlbums.resId.toStringFromResId()) {
                setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserAlbum)
            }
        }
    }
}

@Composable
fun ShowUI(
    currentUserShows: UiState<SpotifyLibraryShowsModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserShows)
    }

    when (currentUserShows) {

        is UiState.Idle -> {
//            setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserShows)
        }

        is UiState.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        is UiState.Success -> {
            currentUserShows.data.showList.let { showList ->

                LazyColumn(
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp)
                        .padding(start = 12.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(showList.size) {

                        // current Item
                        val currentItem = showList[it]
                        val textToShow = "${currentItem.show.type} • ${currentItem.show.publisher}"

                        MusicSmallImageRow(
                            imageUri = currentItem.show.images.firstOrNull()?.url,
                            name = currentItem.show.name,
                            secondaryText = textToShow
                        ) {
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.show.uri))
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            AppErrorScreen(desc = currentUserShows.resId.toStringFromResId()) {
                setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserShows)
            }
        }
    }
}

@Composable
fun EpisodeUI(
    currentUserEpisodes: UiState<SpotifyLibraryEpisodesModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserEpisode)
    }

    when (currentUserEpisodes) {

        is UiState.Idle -> {
//            setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserEpisode)
        }

        is UiState.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        is UiState.Success -> {
            currentUserEpisodes.data.episodeList.let { episodeList ->

                LazyColumn(
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp)
                        .padding(start = 12.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(episodeList.size) {

                        // current Item
                        val currentItem = episodeList[it]

                        MusicSmallImageRow(
                            imageUri = currentItem.episode.images.firstOrNull()?.url,
                            name = currentItem.episode.name,
                            secondaryText = currentItem.episode.type
                        ) {
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.episode.uri))
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            AppErrorScreen(desc = currentUserEpisodes.resId.toStringFromResId()) {
                setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserEpisode)
            }
        }
    }
}