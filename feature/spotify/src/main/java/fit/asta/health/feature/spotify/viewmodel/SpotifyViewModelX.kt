package fit.asta.health.feature.spotify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.spotify.repo.MusicRepository
import fit.asta.health.data.spotify.repo.SpotifyRepo
import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.data.spotify.model.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.data.spotify.model.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.data.spotify.model.library.following.SpotifyUserFollowingArtist
import fit.asta.health.data.spotify.model.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.data.spotify.model.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.data.spotify.model.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.data.spotify.model.search.SpotifySearchModel
import fit.asta.health.data.spotify.model.search.ArtistList
import fit.asta.health.data.spotify.model.search.TrackList
import fit.asta.health.data.spotify.model.common.Track
import fit.asta.health.data.spotify.model.me.SpotifyMeModel
import fit.asta.health.data.spotify.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.data.spotify.model.recommendations.SpotifyRecommendationModel
import fit.asta.health.data.spotify.model.saved.SpotifyLikedSongsResponse
import fit.asta.health.feature.spotify.events.SpotifyUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import fit.asta.health.resources.strings.R

@HiltViewModel
class SpotifyViewModelX @Inject constructor(
    private val remoteRepository: SpotifyRepo,
    private val localRepository: MusicRepository
) : ViewModel() {


    private var isMusicPlaying = false

    // Keeps the AccessToken of the Authorization
    private var accessToken: String = ""

    // Keeps the current User Data so that it can be used
    private val _currentUserData = MutableStateFlow<UiState<SpotifyMeModel>>(UiState.Idle)
    val currentUserData = _currentUserData.asStateFlow()


    /**
     * This function handles what we need to do after we get the Authorization request response
     */
    fun handleSpotifyAuthResponse(response: AuthorizationResponse) {
        when (response.type) {

            // If the Response is a token that means its a successful response
            AuthorizationResponse.Type.TOKEN -> {
                accessToken = response.accessToken

                // Fetching the User Data from the api
                getCurrentUserDetails(accessToken = accessToken)
            }

            // If the Response is an Error or anything else
            else -> {
                _currentUserData.value = UiState.Error(resId = R.string.no_internet)
            }
        }
    }


    /**
     * This function fetches the Current User Data from the spotify api
     */
    fun getCurrentUserDetails(accessToken: String) {


        if (_currentUserData.value is UiState.Loading)
            return

        _currentUserData.value = UiState.Loading

        viewModelScope.launch {
            _currentUserData.value = remoteRepository.getCurrentUserDetails(accessToken).toUiState()
        }
    }


    /**
     * This is the Spotify Remote which helps to play songs using spotify directly
     */
    private var spotifyAppRemote: SpotifyAppRemote? = null


    /**
     * This function sets the Spotify App remote
     */
    fun setSpotifyAppRemote(appRemote: SpotifyAppRemote) {
        this.spotifyAppRemote = appRemote
    }


    /**
     * This function is used to set the state as failed when the Spotify App Remote is not connected
     */
    fun unableToGetSpotifyRemote() {
        _currentUserData.value = UiState.Error(R.string.spotify_remote_not_found)
    }


    /**
     * This function plays the Songs using the Spotify app Remote
     */
    private fun playSpotifySong(url: String) {
        spotifyAppRemote?.playerApi?.play(url)
        spotifyAppRemote?.playerApi?.resume()
        isMusicPlaying = true
    }


    /**
     * This function disconnects the Spotify App Remote
     */
    fun disconnectSpotifyRemote() {
        spotifyAppRemote?.let {
            if (isMusicPlaying) {
                onSpotifyRemoteStop()
                isMusicPlaying = false
            }
            SpotifyAppRemote.disconnect(it)
        }
    }


    /**
     * This function is used to pause the spotify remote player
     */
    fun onSpotifyRemoteStop() {
        spotifyAppRemote?.playerApi?.pause()
    }

    /**
     * This variable contains details of all the Tracks calls and states
     */
    private val _allTracks = MutableStateFlow<UiState<List<Track>>>(UiState.Idle)
    val allTracks = _allTracks.asStateFlow()

    /**
     * This function fetches all the track from the local repository
     */
    private fun getAllTracks() {

        if (_allTracks.value is UiState.Loading)
            return

        _allTracks.value = UiState.Loading

        viewModelScope.launch {
            _allTracks.value = localRepository.getAllTracks().toUiState()
        }
    }


    /**
     * This variable contains details of all the albums calls and states
     */
    private val _allAlbums = MutableStateFlow<UiState<List<Album>>>(UiState.Idle)
    val allAlbums = _allAlbums.asStateFlow()

    /**
     * This function fetches all the albums from the local repository
     */
    private fun getAllAlbums() {

        if (_allAlbums.value is UiState.Loading)
            return

        _allAlbums.value = UiState.Loading

        viewModelScope.launch {
            _allAlbums.value = localRepository.getAllAlbums().toUiState()
        }
    }


    /**
     * This function is used to insert a Track into the Database
     */
    private fun insertTrack(track: Track) {
        viewModelScope.launch {
            localRepository.insertTrack(track)
        }
    }


    /**
     * This function deletes Tracks from the Local Database
     */
    private fun deleteTrack(track: Track) {
        viewModelScope.launch {
            localRepository.deleteTrack(track)
        }
    }


    /**
     * This function is used to insert a Album into the Database
     */
    private fun insertAlbum(album: Album) {
        viewModelScope.launch {
            localRepository.insertAlbum(album)
        }
    }


    /**
     * This function deletes a certain album from the Database
     */
    private fun deleteAlbum(album: Album) {
        viewModelScope.launch {
            localRepository.deleteAlbum(album)
        }
    }


    // Keeps the user Recently Played Tracks
    private val _userRecentlyPlayedTracks =
        MutableStateFlow<UiState<SpotifyUserRecentlyPlayedModel>>(UiState.Idle)
    val userRecentlyPlayedTracks = _userRecentlyPlayedTracks.asStateFlow()

    /**
     * This function fetches the user Recently Played Tracks from the spotify api
     */
    private fun getCurrentUserRecentlyPlayedTracks() {

        if (_userRecentlyPlayedTracks.value is UiState.Loading)
            return

        _userRecentlyPlayedTracks.value = UiState.Loading

        viewModelScope.launch {
            _userRecentlyPlayedTracks.value = remoteRepository
                .getCurrentUserRecentlyPlayedTracks(accessToken)
                .toUiState()
        }
    }

    // Keeps the recommended Tracks
    private val _recommendationTracks =
        MutableStateFlow<UiState<SpotifyRecommendationModel>>(UiState.Idle)
    val recommendationTracks = _recommendationTracks.asStateFlow()

    // These Two are kept for Calling if needed (Ex - Failed to get data in the first time)
    private var seedArtists: String = ""
    private var seedTracks: String = ""

    /**
     * This function gets the Recommendation Tracks for the users
     */
    private fun getRecommendationTracks(
        seedGenres: String = "classical,country",
        limit: String = "15"
    ) {

        if (_userRecentlyPlayedTracks.value !is UiState.Success)
            return

        if (_recommendationTracks.value is UiState.Loading)
            return

        _recommendationTracks.value = UiState.Loading

        viewModelScope.launch {
            val recentTracks = (_userRecentlyPlayedTracks.value as UiState.Success).data.trackList

            // Fetching the Recommendations Tracks List for the Users
            if (recentTracks.isNotEmpty()) {

                // Setting for Future Purposes
                seedArtists = recentTracks[0].track.artists[0].id
                seedTracks = recentTracks[0].track.id

                // Fetching
                _recommendationTracks.value = remoteRepository.getRecommendations(
                    accessToken, seedArtists, seedGenres, seedTracks, limit
                ).toUiState()
            }
        }
    }

    // Keeps the recommended Tracks
    private val _trackDetailsResponse = MutableStateFlow<UiState<Track>>(UiState.Idle)
    val trackDetailsResponse = _trackDetailsResponse.asStateFlow()

    // Keeps the Track Id whose details would be shown
    private var trackDetailId: String = ""

    /**
     * Setting the TrackId for the next screen
     */
    private fun setTrackId(trackId: String) {
        trackDetailId = trackId
    }

    /**
     * This function fetches The Track Details
     */
    private fun getTrackDetails() {

        if (_trackDetailsResponse.value is UiState.Loading)
            return

        _trackDetailsResponse.value = UiState.Loading

        viewModelScope.launch {
            _trackDetailsResponse.value = remoteRepository
                .getTrackDetails(accessToken, trackDetailId)
                .toUiState()
        }
    }

    // Keeps the User Top Tracks
    private val _userTopTracks = MutableStateFlow<UiState<TrackList>>(UiState.Idle)
    val userTopTracks = _userTopTracks.asStateFlow()

    /**
     * This function fetches the user top tracks from the spotify Api
     */
    private fun getUserTopTracks() {

        if (_userTopTracks.value is UiState.Loading)
            return

        _userTopTracks.value = UiState.Loading

        viewModelScope.launch {
            _userTopTracks.value = remoteRepository.getCurrentUserTopTracks(accessToken).toUiState()
        }
    }

    // Keeps the User Top Tracks
    private val _userTopArtists = MutableStateFlow<UiState<ArtistList>>(UiState.Idle)
    val userTopArtists = _userTopArtists.asStateFlow()

    /**
     * This function fetches the top Artists from the Spotify Api
     */
    private fun getUserTopArtists() {

        if (_userTopArtists.value is UiState.Loading)
            return

        _userTopArtists.value = UiState.Loading

        viewModelScope.launch {
            _userTopArtists.value = remoteRepository
                .getCurrentUserTopArtists(accessToken)
                .toUiState()
        }
    }

    /**
     * Keeps the Spotify Search Result
     */
    private val _spotifySearch = MutableStateFlow<UiState<SpotifySearchModel>>(UiState.Idle)
    val spotifySearch = _spotifySearch.asStateFlow()

    // All Related Data to the Searching Parameters needed are stored here
    private var query = ""
    private var type = ""
    private var includeExternal = "audio"
    private var market = ""

    /**
     * This function sets the variables and the Searching Params of the search option
     */
    private fun setSearchQueriesAndVariables(
        query: String,
        type: String,
        includeExternal: String = "audio",
        market: String = (_currentUserData.value as UiState.Success<SpotifyMeModel>).data.country
    ) {
        this.query = query
        this.type = type
        this.includeExternal = includeExternal
        this.market = market
        getSpotifySearchResult()
    }

    /**
     * This function fetches the spotify search result for the User
     */
    private fun getSpotifySearchResult() {

        // Returning to prevent showing error during the first composition during Initialized State
        if (query.isEmpty() || type.isEmpty() || _spotifySearch.value is UiState.Loading)
            return

        // Starting the Loading State
        _spotifySearch.value = UiState.Loading

        viewModelScope.launch {
            _spotifySearch.value = remoteRepository
                .searchQuery(accessToken, query, type, includeExternal, market)
                .toUiState()
        }
    }

    /**
     * Keeps the Spotify Album Details
     */
    private val _albumDetailsResponse = MutableStateFlow<UiState<Album>>(UiState.Idle)
    val albumDetailsResponse = _albumDetailsResponse.asStateFlow()

    // keeps the Id of the Album Whose details needs to be shown
    private var albumDetailId: String = ""

    private fun setAlbumId(newAlbumId: String) {
        albumDetailId = newAlbumId
    }

    private fun getAlbumDetails() {

        if (_albumDetailsResponse.value is UiState.Loading)
            return

        _albumDetailsResponse.value = UiState.Loading

        viewModelScope.launch {
            _albumDetailsResponse.value = remoteRepository
                .getAlbumDetails(accessToken, albumDetailId)
                .toUiState()
        }
    }

    /**
     * This variable keeps the current Spotify User All Tracks fetched from the spotify Api
     */
    private val _currentUserTracks =
        MutableStateFlow<UiState<SpotifyLibraryTracksModel>>(UiState.Idle)
    val currentUserTracks = _currentUserTracks.asStateFlow()

    /**
     * This function fetches all the current User Tracks from the spotify api
     */
    private fun getCurrentUserTracks() {

        if (_currentUserTracks.value is UiState.Loading)
            return

        _currentUserTracks.value = UiState.Loading

        viewModelScope.launch {
            _currentUserTracks.value = remoteRepository
                .getCurrentUserTracks(accessToken)
                .toUiState()
        }
    }

    /**
     * This variable keeps the current User All playlist from the spotify Api
     */
    private val _currentUserPlaylist =
        MutableStateFlow<UiState<SpotifyUserPlaylistsModel>>(UiState.Idle)
    val currentUserPlaylist = _currentUserPlaylist.asStateFlow()

    /**
     * This function fetches all the current User Playlist from the spotify Api
     */
    private fun getCurrentUserPlaylist() {

        if (_currentUserPlaylist.value is UiState.Loading)
            return

        _currentUserPlaylist.value = UiState.Loading

        viewModelScope.launch {
            _currentUserPlaylist.value = remoteRepository
                .getCurrentUserPlaylists(accessToken)
                .toUiState()
        }
    }

    /**
     * This variable contains the data of all the current User Artists from the spotify Api
     */
    private var _currentUserFollowingArtist =
        MutableStateFlow<UiState<SpotifyUserFollowingArtist>>(UiState.Idle)
    val currentUserFollowingArtist = _currentUserFollowingArtist.asStateFlow()

    /**
     * This function fetches all the current User Following Artists from the spotify Api
     */
    private fun getCurrentUserArtists() {

        if (_currentUserFollowingArtist.value is UiState.Loading)
            return

        _currentUserFollowingArtist.value = UiState.Loading

        viewModelScope.launch {
            _currentUserFollowingArtist.value = remoteRepository
                .getCurrentUserFollowedArtists(accessToken)
                .toUiState()
        }
    }

    /**
     * This variable contains the current User all albums from the spotify Api
     */
    private val _currentUserAlbum =
        MutableStateFlow<UiState<SpotifyLibraryAlbumModel>>(UiState.Idle)
    val currentUserAlbum = _currentUserAlbum.asStateFlow()

    /**
     * This function fetches all the current User Albums from the spotify Api
     */
    private fun getCurrentUserAlbum() {

        if (_currentUserAlbum.value is UiState.Loading)
            return

        _currentUserAlbum.value = UiState.Loading

        viewModelScope.launch {
            _currentUserAlbum.value = remoteRepository.getCurrentUserAlbums(accessToken).toUiState()
        }
    }

    /**
     * This variable contains the currentUserShows from the spotify Api
     */
    private val _currentUserShow = MutableStateFlow<UiState<SpotifyLibraryShowsModel>>(UiState.Idle)
    val currentUserShow = _currentUserShow.asStateFlow()

    /**
     * This function fetches all the shows data from the spotify Api
     */
    private fun getCurrentUserShows() {

        if (_currentUserShow.value is UiState.Loading)
            return

        _currentUserShow.value = UiState.Loading

        viewModelScope.launch {
            _currentUserShow.value = remoteRepository.getCurrentUserShows(accessToken).toUiState()
        }
    }

    /**
     * This variable contains the current User All the Episodes from the spotify APi
     */
    private val _currentUserEpisode =
        MutableStateFlow<UiState<SpotifyLibraryEpisodesModel>>(UiState.Idle)
    val currentUserEpisode = _currentUserEpisode.asStateFlow()

    /**
     *  This function fetches the current User all the Episodes from the spotify APi
     */
    private fun getCurrentUserEpisode() {

        if (_currentUserEpisode.value is UiState.Loading)
            return

        _currentUserEpisode.value = UiState.Loading

        viewModelScope.launch {
            _currentUserEpisode.value = remoteRepository
                .getCurrentUserEpisodes(accessToken)
                .toUiState()
        }
    }

    /**
     * This variable contains the current User All the saved songs from the spotify APi
     */
    private val _currentUserSavedSongs =
        MutableStateFlow<UiState<SpotifyLikedSongsResponse>>(UiState.Idle)
    val currentUserSavedSongs = _currentUserSavedSongs.asStateFlow()

    /**
     *  This function fetches the current User all the Episodes from the spotify APi
     */
    private fun getCurrentUserLikedSongs() {

        if (_currentUserSavedSongs.value is UiState.Loading)
            return

        _currentUserSavedSongs.value = UiState.Loading

        viewModelScope.launch {
            _currentUserSavedSongs.value = remoteRepository
                .getCurrentUserSavedSongs(
                    accessToken = accessToken,
                    market = "ES",
                    limit = "10",
                    offset = "5"
                ).toUiState()
        }
    }


    fun uiEventListener(event: SpotifyUiEvent) {
        when (event) {

            is SpotifyUiEvent.HelperEvent.PlaySong -> {
                playSpotifySong(event.songUri)
            }

            is SpotifyUiEvent.HelperEvent.SetTrackId -> {
                setTrackId(event.trackId)
            }

            is SpotifyUiEvent.HelperEvent.SetAlbumId -> {
                setAlbumId(event.albumId)
            }

            is SpotifyUiEvent.HelperEvent.SetSearchQueriesAndVariables -> {
                setSearchQueriesAndVariables(query = event.query, type = event.type)
            }

            is SpotifyUiEvent.NetworkIO.LoadCurrentUserRecentlyPlayedTracks -> {
                getCurrentUserRecentlyPlayedTracks()
            }

            is SpotifyUiEvent.NetworkIO.LoadRecommendationTracks -> {
                getRecommendationTracks()
            }

            is SpotifyUiEvent.NetworkIO.LoadUserTopTracks -> {
                getUserTopTracks()
            }

            is SpotifyUiEvent.NetworkIO.LoadUserTopArtists -> {
                getUserTopArtists()
            }

            is SpotifyUiEvent.NetworkIO.LoadAlbumDetails -> {
                getAlbumDetails()
            }

            is SpotifyUiEvent.NetworkIO.LoadCurrentUserTracks -> {
                getCurrentUserTracks()
            }

            is SpotifyUiEvent.NetworkIO.LoadCurrentUserPlaylist -> {
                getCurrentUserPlaylist()
            }

            is SpotifyUiEvent.NetworkIO.LoadCurrentUserArtists -> {
                getCurrentUserArtists()
            }

            is SpotifyUiEvent.NetworkIO.LoadCurrentUserAlbum -> {
                getCurrentUserAlbum()
            }

            is SpotifyUiEvent.NetworkIO.LoadCurrentUserShows -> {
                getCurrentUserShows()
            }

            is SpotifyUiEvent.NetworkIO.LoadCurrentUserEpisode -> {
                getCurrentUserEpisode()
            }

            is SpotifyUiEvent.NetworkIO.LoadSpotifySearchResult -> {
                getSpotifySearchResult()
            }

            is SpotifyUiEvent.NetworkIO.LoadTrackDetails -> {
                getTrackDetails()
            }

            is SpotifyUiEvent.LocalIO.LoadAllTracks -> {
                getAllTracks()
            }

            is SpotifyUiEvent.LocalIO.LoadAllAlbums -> {
                getAllAlbums()
            }

            is SpotifyUiEvent.LocalIO.InsertTrack -> {
                insertTrack(event.track)
            }

            is SpotifyUiEvent.LocalIO.DeleteTrack -> {
                deleteTrack(event.track)
            }

            is SpotifyUiEvent.LocalIO.InsertAlbum -> {
                insertAlbum(event.album)
            }

            is SpotifyUiEvent.LocalIO.DeleteAlbum -> {
                deleteAlbum(event.album)
            }
        }
    }
}