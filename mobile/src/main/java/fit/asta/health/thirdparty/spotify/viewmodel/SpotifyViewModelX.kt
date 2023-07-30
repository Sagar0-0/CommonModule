package fit.asta.health.thirdparty.spotify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.thirdparty.spotify.model.MusicRepository
import fit.asta.health.thirdparty.spotify.model.SpotifyRepo
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.library.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.model.net.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.model.net.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.model.net.search.ArtistList
import fit.asta.health.thirdparty.spotify.model.net.search.TrackList
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.model.net.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.model.net.recommendations.SpotifyRecommendationModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModelX @Inject constructor(
    private val remoteRepository: SpotifyRepo,
    private val localRepository: MusicRepository,
    application: Application
) : AndroidViewModel(application) {


    // Keeps the AccessToken of the Authorization
    private var accessToken: String = ""

    // Keeps the current User Data so that it can be used
    private val _currentUserData = MutableStateFlow<SpotifyNetworkCall<SpotifyMeModel>>(
        SpotifyNetworkCall.Initialized()
    )
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
                _currentUserData.value =
                    SpotifyNetworkCall.Failure(message = response.error.toString())
            }
        }
    }


    /**
     * This function fetches the Current User Data from the spotify api
     */
    private fun getCurrentUserDetails(accessToken: String) {
        viewModelScope.launch {
            remoteRepository.getCurrentUserDetails(accessToken).collectLatest {
                _currentUserData.value = it
            }
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
     * This function plays the Songs using the Spotify app Remote
     */
    fun playSpotifySong(url: String) {
        spotifyAppRemote?.playerApi?.play(url)
    }


    /**
     * This function is used to set the state as failed when the Spotify App Remote is not connected
     */
    fun unableToGetSpotifyRemote(e: Throwable) {
        _currentUserData.value = SpotifyNetworkCall.Failure(message = e.message.toString())
    }


    /**
     * This function disconnects the Spotify App Remote
     */
    fun disconnectSpotifyRemote() {
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }


    /**
     * This variable contains details of all the Tracks calls and states
     */
    private val _allTracks = MutableStateFlow<SpotifyNetworkCall<List<Track>>>(
        SpotifyNetworkCall.Initialized()
    )
    val allTracks = _allTracks.asStateFlow()

    /**
     * This function fetches all the track from the local repository
     */
    fun getAllTracks() {
        viewModelScope.launch {
            localRepository.getAllTracks().collect {
                _allTracks.value = it
            }
        }
    }


    /**
     * This variable contains details of all the albums calls and states
     */
    private val _allAlbums = MutableStateFlow<SpotifyNetworkCall<List<Album>>>(
        SpotifyNetworkCall.Initialized()
    )
    val allAlbums = _allAlbums.asStateFlow()

    /**
     * This function fetches all the albums from the local repository
     */
    fun getAllAlbums() {
        viewModelScope.launch {
            localRepository.getAllAlbums().collect {
                _allAlbums.value = it
            }
        }
    }


    /**
     * This function is used to insert a Track into the Database
     */
    fun insertTrack(track: Track) {
        viewModelScope.launch {
            localRepository.insertTrack(track)
        }
    }


    /**
     * This function deletes Tracks from the Local Database
     */
    fun deleteTrack(track: Track) {
        viewModelScope.launch {
            localRepository.deleteTrack(track)
        }
    }


    /**
     * This function is used to insert a Album into the Database
     */
    fun insertAlbum(album: Album) {
        viewModelScope.launch {
            localRepository.insertAlbum(album)
        }
    }


    /**
     * This function deletes a certain album from the Database
     */
    fun deleteAlbum(album: Album) {
        viewModelScope.launch {
            localRepository.deleteAlbum(album)
        }
    }


    // Keeps the user Recently Played Tracks
    private val _userRecentlyPlayedTracks =
        MutableStateFlow<SpotifyNetworkCall<SpotifyUserRecentlyPlayedModel>>(
            SpotifyNetworkCall.Initialized()
        )
    val userRecentlyPlayedTracks = _userRecentlyPlayedTracks.asStateFlow()

    /**
     * This function fetches the user Recently Played Tracks from the spotify api
     */
    fun getCurrentUserRecentlyPlayedTracks() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserRecentlyPlayedTracks(accessToken).collectLatest {

                // Fetching the Recommendations Tracks List for the Users
                if (it.data?.trackList?.isNotEmpty() == true) {

                    // Setting for Future Purposes
                    seedArtists = it.data.trackList[0].track.artists[0].id
                    seedTracks = it.data.trackList[0].track.id

                    // Fetching
                    getRecommendationTracks()
                }
                _userRecentlyPlayedTracks.value = it
            }
        }
    }

    // Keeps the recommended Tracks
    private val _recommendationTracks =
        MutableStateFlow<SpotifyNetworkCall<SpotifyRecommendationModel>>(
            SpotifyNetworkCall.Initialized()
        )
    val recommendationTracks = _recommendationTracks.asStateFlow()

    // These Two are kept for Calling if needed (Ex - Failed to get data in the first time)
    private var seedArtists: String = ""
    private var seedTracks: String = ""

    /**
     * This function gets the Recommendation Tracks for the users
     */
    fun getRecommendationTracks(
        seedGenres: String = "classical,country",
        limit: String = "15"
    ) {
        viewModelScope.launch {
            remoteRepository.getRecommendations(
                accessToken, seedArtists, seedGenres, seedTracks, limit
            ).collectLatest {
                _recommendationTracks.value = it
            }
        }
    }

    // Keeps the recommended Tracks
    private val _trackDetailsResponse = MutableStateFlow<SpotifyNetworkCall<Track>>(
        SpotifyNetworkCall.Initialized()
    )
    val trackDetailsResponse = _trackDetailsResponse.asStateFlow()

    // Keeps the Track Id whose details would be shown
    private var trackDetailId: String = ""

    /**
     * Setting the TrackId for the next screen
     */
    fun setTrackId(trackId: String) {
        trackDetailId = trackId
    }

    /**
     * This function fetches The Track Details
     */
    fun getTrackDetails() {
        viewModelScope.launch {
            remoteRepository.getTrackDetails(accessToken, trackDetailId).collectLatest {
                _trackDetailsResponse.value = it
            }
        }
    }

    // Keeps the User Top Tracks
    private val _userTopTracks = MutableStateFlow<SpotifyNetworkCall<TrackList>>(
        SpotifyNetworkCall.Initialized()
    )
    val userTopTracks = _userTopTracks.asStateFlow()

    /**
     * This function fetches the user top tracks from the spotify Api
     */
    fun getUserTopTracks() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserTopTracks(accessToken).collectLatest {
                _userTopTracks.value = it
            }
        }
    }

    // Keeps the User Top Tracks
    private val _userTopArtists = MutableStateFlow<SpotifyNetworkCall<ArtistList>>(
        SpotifyNetworkCall.Initialized()
    )
    val userTopArtists = _userTopArtists.asStateFlow()

    /**
     * This function fetches the top Artists from the Spotify Api
     */
    fun getUserTopArtists() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserTopArtists(accessToken).collectLatest {
                _userTopArtists.value = it
            }
        }
    }

    /**
     * Keeps the Spotify Search Result
     */
    private val _spotifySearch = MutableStateFlow<SpotifyNetworkCall<SpotifySearchModel>>(
        SpotifyNetworkCall.Initialized()
    )
    val spotifySearch = _spotifySearch.asStateFlow()

    // All Related Data to the Searching Parameters needed are stored here
    private var query = ""
    private var type = ""
    private var includeExternal = "audio"
    private var market = ""

    /**
     * This function sets the variables and the Searching Params of the search option
     */
    fun setSearchQueriesAndVariables(
        query: String,
        type: String,
        includeExternal: String = "audio",
        market: String = _currentUserData.value.data!!.country
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
    fun getSpotifySearchResult() {

        // Returning to prevent showing error during the first composition during Initialized State
        if (query.isEmpty() || type.isEmpty() || _spotifySearch.value is SpotifyNetworkCall.Loading)
            return

        // Starting the Loading State
        _spotifySearch.value = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            remoteRepository.searchQuery(accessToken, query, type, includeExternal, market)
                .collectLatest {
                    _spotifySearch.value = it
                }
        }
    }

    /**
     * Keeps the Spotify Album Details
     */
    private val _albumDetailsResponse = MutableStateFlow<SpotifyNetworkCall<Album>>(
        SpotifyNetworkCall.Initialized()
    )
    val albumDetailsResponse = _albumDetailsResponse.asStateFlow()

    // keeps the Id of the Album Whose details needs to be shown
    private var albumDetailId: String = ""

    fun setAlbumId(newAlbumId: String) {
        albumDetailId = newAlbumId
    }

    fun getAlbumDetails() {
        viewModelScope.launch {
            remoteRepository.getAlbumDetails(accessToken, albumDetailId).collectLatest {
                _albumDetailsResponse.value = it
            }
        }
    }

    /**
     * This variable keeps the current Spotify User All Tracks fetched from the spotify Api
     */
    private val _currentUserTracks =
        MutableStateFlow<SpotifyNetworkCall<SpotifyLibraryTracksModel>>(
            SpotifyNetworkCall.Initialized()
        )
    val currentUserTracks = _currentUserTracks.asStateFlow()

    /**
     * This function fetches all the current User Tracks from the spotify api
     */
    fun getCurrentUserTracks() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserTracks(accessToken).collectLatest {
                _currentUserTracks.value = it
            }
        }
    }

    /**
     * This variable keeps the current User All playlist from the spotify Api
     */
    private val _currentUserPlaylist =
        MutableStateFlow<SpotifyNetworkCall<SpotifyUserPlaylistsModel>>(
            SpotifyNetworkCall.Initialized()
        )
    val currentUserPlaylist = _currentUserPlaylist.asStateFlow()

    /**
     * This function fetches all the current User Playlist from the spotify Api
     */
    fun getCurrentUserPlaylist() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserPlaylists(accessToken).collectLatest {
                _currentUserPlaylist.value = it
            }
        }
    }

    /**
     * This variable contains the data of all the current User Artists from the spotify Api
     */
    private var _currentUserFollowingArtist =
        MutableStateFlow<SpotifyNetworkCall<SpotifyUserFollowingArtist>>(
            SpotifyNetworkCall.Initialized()
        )
    val currentUserFollowingArtist = _currentUserFollowingArtist.asStateFlow()

    /**
     * This function fetches all the current User Following Artists from the spotify Api
     */
    fun getCurrentUserFollowingArtists() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserFollowedArtists(accessToken).collectLatest {
                _currentUserFollowingArtist.value = it
            }
        }
    }

    /**
     * This variable contains the current User all albums from the spotify Api
     */
    private val _currentUserAlbum = MutableStateFlow<SpotifyNetworkCall<SpotifyLibraryAlbumModel>>(
        SpotifyNetworkCall.Initialized()
    )
    val currentUserAlbum = _currentUserAlbum.asStateFlow()

    /**
     * This function fetches all the current User Albums from the spotify Api
     */
    fun getCurrentUserAlbum() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserAlbums(accessToken).collectLatest {
                _currentUserAlbum.value = it
            }
        }
    }

    /**
     * This variable contains the currentUserShows from the spotify Api
     */
    private val _currentUserShow = MutableStateFlow<SpotifyNetworkCall<SpotifyLibraryShowsModel>>(
        SpotifyNetworkCall.Initialized()
    )
    val currentUserShow = _currentUserShow.asStateFlow()

    /**
     * This function fetches all the shows data from the spotify Api
     */
    fun getCurrentUserShows() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserShows(accessToken).collectLatest {
                _currentUserShow.value = it
            }
        }
    }

    /**
     * This variable contains the current User All the Episodes from the spotify APi
     */
    private val _currentUserEpisode =
        MutableStateFlow<SpotifyNetworkCall<SpotifyLibraryEpisodesModel>>(
            SpotifyNetworkCall.Initialized()
        )
    val currentUserEpisode = _currentUserEpisode.asStateFlow()

    /**
     *  This function fetches the current User all the Episodes from the spotify APi
     */
    fun getCurrentUserEpisode() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserEpisodes(accessToken).collectLatest {
                _currentUserEpisode.value = it
            }
        }
    }
}