package fit.asta.health.thirdparty.spotify.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.thirdparty.spotify.model.SpotifyRepoImpl
import fit.asta.health.thirdparty.spotify.model.db.MusicRepository
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModelX @Inject constructor(
    private val remoteRepository: SpotifyRepoImpl,
    private val localRepository: MusicRepository,
    application: Application
) : AndroidViewModel(application) {


    // Keeps the AccessToken of the Authorization
    private var accessToken: String = ""

    // Keeps the current User Data so that it can be used
    var currentUserData: SpotifyNetworkCall<SpotifyMeModel> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set


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
                currentUserData = SpotifyNetworkCall.Failure(message = response.error.toString())
            }
        }
    }


    /**
     * This function fetches the Current User Data from the spotify api
     */
    private fun getCurrentUserDetails(accessToken: String) {

        // Starting the Loading State
        currentUserData = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            currentUserData = try {

                // Fetching the data from the Api
                val response = remoteRepository.getCurrentUserDetails(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
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
        currentUserData = SpotifyNetworkCall.Failure(message = e.message.toString())
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

        _allTracks.value = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            localRepository.local.getAllTracks().catch { exception ->
                _allTracks.value = SpotifyNetworkCall.Failure(
                    message = exception.message
                )
            }.collect {
                _allTracks.value = SpotifyNetworkCall.Success(
                    data = it
                )
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

        _allAlbums.value = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            localRepository.local.getAllAlbums().catch { exception ->
                _allAlbums.value = SpotifyNetworkCall.Failure(
                    message = exception.message
                )
            }.collect {
                _allAlbums.value = SpotifyNetworkCall.Success(
                    data = it
                )
            }
        }
    }


    /**
     * This function is used to insert a Track into the Database
     */
    fun insertTrack(track: Track) {
        viewModelScope.launch {
            localRepository.local.insertTrack(track)
            getAllTracks()
        }
    }


    /**
     * This function deletes Tracks from the Local Database
     */
    fun deleteTrack(track: Track) {
        viewModelScope.launch {
            localRepository.local.deleteTrack(track)
            getAllTracks()
        }
    }


    /**
     * This function is used to insert a Album into the Database
     */
    fun insertAlbum(album: Album) {
        viewModelScope.launch {
            localRepository.local.insertAlbum(album)
            getAllAlbums()
        }
    }


    /**
     * This function deletes a certain album from the Database
     */
    fun deleteAlbum(album: Album) {
        viewModelScope.launch {
            localRepository.local.deleteAlbum(album)
            getAllAlbums()
        }
    }


    // Keeps the user Recently Played Tracks
    var userRecentlyPlayedTracks: SpotifyNetworkCall<SpotifyUserRecentlyPlayedModel> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

    /**
     * This function fetches the user Recently Played Tracks from the spotify api
     */
    fun getCurrentUserRecentlyPlayedTracks() {

        // Starting the Loading State
        userRecentlyPlayedTracks = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            userRecentlyPlayedTracks = try {

                // Fetching the data from the Api
                val response = remoteRepository.getCurrentUserRecentlyPlayedTracks(accessToken)
                val state = handleResponse(response)

                // Fetching the Recommendations Tracks List for the Users
                if (state.data?.trackList?.isNotEmpty() == true) {

                    // Setting for Future Purposes
                    seedArtists = state.data.trackList[0].track.artists[0].id
                    seedTracks = state.data.trackList[0].track.id

                    // Fetching
                    getRecommendationTracks()
                }

                state   // Assigning
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    // Keeps the recommended Tracks
    var recommendationTracks: SpotifyNetworkCall<SpotifyRecommendationModel> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

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

        // Starting the Loading State
        recommendationTracks = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            recommendationTracks = try {

                // Fetching the data from the Api
                val response = remoteRepository.getRecommendations(
                    accessToken, seedArtists, seedGenres, seedTracks, limit
                )
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    // Keeps the recommended Tracks
    var trackDetailsResponse: SpotifyNetworkCall<Track> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

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

        // Starting the Loading State
        trackDetailsResponse = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            trackDetailsResponse = try {

                // Fetching the data from the Api
                val response = remoteRepository.getTrackDetails(accessToken, trackDetailId)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    // Keeps the User Top Tracks
    var userTopTracks: SpotifyNetworkCall<TrackList> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

    /**
     * This function fetches the user top tracks from the spotify Api
     */
    fun getUserTopTracks() {

        // Starting the Loading State
        userTopTracks = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            userTopTracks = try {

                // Fetching the data from the Api
                val response = remoteRepository.getCurrentUserTopTracks(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    // Keeps the User Top Tracks
    var userTopArtists: SpotifyNetworkCall<ArtistList> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

    /**
     * This function fetches the top Artists from the Spotify Api
     */
    fun getUserTopArtists() {

        // Starting the Loading State
        userTopArtists = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            userTopArtists = try {

                // Fetching the data from the Api
                val response = remoteRepository.getCurrentUserTopArtists(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     * Keeps the Spotify Search Result
     */
    var spotifySearch: SpotifyNetworkCall<SpotifySearchModel> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

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
        market: String = currentUserData.data!!.country
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
        if (query.isEmpty() || type.isEmpty() || spotifySearch is SpotifyNetworkCall.Loading)
            return

        // Starting the Loading State
        spotifySearch = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            spotifySearch = try {

                // Fetching the data from the Api
                val response =
                    remoteRepository.searchQuery(accessToken, query, type, includeExternal, market)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     * Keeps the Spotify Album Details
     */
    var albumDetailsResponse: SpotifyNetworkCall<Album> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

    // keeps the Id of the Album Whose details needs to be shown
    private var albumDetailId: String = ""

    fun setAlbumId(newAlbumId: String) {
        albumDetailId = newAlbumId
    }

    fun getAlbumDetails() {

        // Starting the Loading State
        albumDetailsResponse = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            albumDetailsResponse = try {

                // Fetching the data from the Api
                val response = remoteRepository.getAlbumDetails(accessToken, albumDetailId)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     * This variable keeps the current Spotify User All Tracks fetched from the spotify Api
     */
    var currentUserTracks: SpotifyNetworkCall<SpotifyLibraryTracksModel> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

    /**
     * This function fetches all the current User Tracks from the spotify api
     */
    fun getCurrentUserTracks() {

        // Starting the Loading State
        currentUserTracks = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            currentUserTracks = try {

                // Fetching the data from the Api
                val response = remoteRepository.getCurrentUserTracks(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     * This variable keeps the current User All playlist from the spotify Api
     */
    var currentUserPlaylist: SpotifyNetworkCall<SpotifyUserPlaylistsModel> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

    /**
     * This function fetches all the current User Playlist from the spotify Api
     */
    fun getCurrentUserPlaylist() {

        // Starting the Loading State
        currentUserPlaylist = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            currentUserPlaylist = try {

                // Fetching the data from the Api
                val response = remoteRepository.getCurrentUserPlaylists(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     * This variable contains the data of all the current User Artists from the spotify Api
     */
    var currentUserFollowingArtist: SpotifyNetworkCall<SpotifyUserFollowingArtist> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

    /**
     * This function fetches all the current User Following Artists from the spotify Api
     */
    fun getCurrentUserFollowingArtists() {

        // Starting the Loading State
        currentUserFollowingArtist = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            currentUserFollowingArtist = try {

                // Fetching the data from the Api
                val response = remoteRepository.getCurrentUserFollowedArtists(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     * This variable contains the current User all albums from the spotify Api
     */
    var currentUserAlbum: SpotifyNetworkCall<SpotifyLibraryAlbumModel> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

    /**
     * This function fetches all the current User Albums from the spotify Api
     */
    fun getCurrentUserAlbum() {

        // Starting the Loading State
        currentUserAlbum = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            currentUserAlbum = try {

                // Fetching the data from the Api
                val response = remoteRepository.getCurrentUserAlbums(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     * This variable contains the currentUserShows from the spotify Api
     */
    var currentUserShow: SpotifyNetworkCall<SpotifyLibraryShowsModel> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

    /**
     * This function fetches all the shows data from the spotify Api
     */
    fun getCurrentUserShows() {

        // Starting the Loading State
        currentUserShow = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            currentUserShow = try {

                // Fetching the data from the Api
                val response = remoteRepository.getCurrentUserShows(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     * This variable contains the current User All the Episodes from the spotify APi
     */
    var currentUserEpisode: SpotifyNetworkCall<SpotifyLibraryEpisodesModel> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

    /**
     *  This function fetches the current User all the Episodes from the spotify APi
     */
    fun getCurrentUserEpisode() {

        // Starting the Loading State
        currentUserEpisode = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            currentUserEpisode = try {

                // Fetching the data from the Api
                val response = remoteRepository.getCurrentUserEpisodes(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     *  Handle Response Got From Spotify Web API
     */
    private fun <T : Any> handleResponse(response: Response<T>): SpotifyNetworkCall<T> {
        when {
            response.message().toString().contains("timeout") -> {
                return SpotifyNetworkCall.Failure(
                    data = response.body(),
                    message = "Timeout!!\n $response"
                )
            }

            response.code() == 401 -> {
                return SpotifyNetworkCall.Failure(
                    data = response.body(),
                    message = "Bad or expired token. This can happen if the user revoked a token or the access token has expired. You should re-authenticate the user.\n $response"
                )
            }

            response.code() == 403 -> {
                return SpotifyNetworkCall.Failure(
                    data = response.body(),
                    message = "Bad OAuth request (wrong consumer key, bad nonce, expired timestamp...). Unfortunately, re-authenticating the user won't help here.\n $response"
                )
            }

            response.code() == 429 -> {
                return SpotifyNetworkCall.Failure(
                    data = response.body(),
                    message = "The app has exceeded its rate limits. $response"
                )
            }

            response.body() == null -> {
                return SpotifyNetworkCall.Failure(
                    data = response.body(),
                    message = "Empty Body. $response"
                )
            }

            response.isSuccessful -> {
                val result = response.body()!!
                return SpotifyNetworkCall.Success(result)
            }

            response.code() == 200 -> {
                val result = response.body()!!
                return SpotifyNetworkCall.Success(result)
            }

            else -> return SpotifyNetworkCall.Failure(
                message = response.body().toString(),
                data = response.body()
            )
        }
    }
}