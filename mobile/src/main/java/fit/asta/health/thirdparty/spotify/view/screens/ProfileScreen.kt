package fit.asta.health.thirdparty.spotify.view.screens

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
import fit.asta.health.thirdparty.spotify.model.net.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.library.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.model.net.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import fit.asta.health.thirdparty.spotify.view.components.MusicProfileOptionList
import fit.asta.health.thirdparty.spotify.view.components.MusicSmallImageRow
import fit.asta.health.thirdparty.spotify.view.components.MusicStateControl
import fit.asta.health.thirdparty.spotify.view.events.SpotifyUiEvent

@Composable
fun ProfileScreen(
    currentUserTracks: SpotifyNetworkCall<SpotifyLibraryTracksModel>,
    currentUserPlaylist: SpotifyNetworkCall<SpotifyUserPlaylistsModel>,
    currentUserArtists: SpotifyNetworkCall<SpotifyUserFollowingArtist>,
    currentUserAlbums: SpotifyNetworkCall<SpotifyLibraryAlbumModel>,
    currentUserShows: SpotifyNetworkCall<SpotifyLibraryShowsModel>,
    currentUserEpisodes: SpotifyNetworkCall<SpotifyLibraryEpisodesModel>,
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
    currentUserTracks: SpotifyNetworkCall<SpotifyLibraryTracksModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserTracks)
    }

    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = currentUserTracks,
        onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserTracks) }
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
    }
}

@Composable
fun PlaylistUI(
    currentUserPlaylist: SpotifyNetworkCall<SpotifyUserPlaylistsModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserPlaylist)
    }

    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = currentUserPlaylist,
        onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserPlaylist) }
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
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.uri))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArtistsUI(
    currentUserArtists: SpotifyNetworkCall<SpotifyUserFollowingArtist>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserArtists)
    }
    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = currentUserArtists,
        onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserArtists) }
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
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.uri))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlbumsUI(
    currentUserAlbums: SpotifyNetworkCall<SpotifyLibraryAlbumModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserAlbum)
    }

    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = currentUserAlbums,
        onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserAlbum) }
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
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.album.uri))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowUI(
    currentUserShows: SpotifyNetworkCall<SpotifyLibraryShowsModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserShows)
    }

    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = currentUserShows,
        onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserShows) }
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
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.show.uri))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodeUI(
    currentUserEpisodes: SpotifyNetworkCall<SpotifyLibraryEpisodesModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserEpisode)
    }

    MusicStateControl(
        modifier = Modifier
            .fillMaxSize(),
        networkState = currentUserEpisodes,
        onCurrentStateInitialized = { setEvent(SpotifyUiEvent.NetworkIO.LoadCurrentUserEpisode) }
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
                            setEvent(SpotifyUiEvent.HelperEvent.PlaySong(currentItem.episode.uri))
                        }
                    }
                }
            }
        }
    }
}