package fit.asta.health.scheduler.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.ToneUiState
import fit.asta.health.scheduler.compose.screen.spotify.SpotifyUiEvent
import fit.asta.health.scheduler.util.Constants
import fit.asta.health.thirdparty.spotify.model.MusicRepository
import fit.asta.health.thirdparty.spotify.model.SpotifyRepo
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.model.net.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.model.net.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.model.net.search.TrackList
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModel @Inject constructor(
    private val remoteRepository: SpotifyRepo,
    private val localRepository: MusicRepository,
    private val prefUtils: PrefUtils,
    application: Application,
) : AndroidViewModel(application) {

    private var isMusicPlaying = false

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
     * This function is used to set the state as failed when the Spotify App Remote is not connected
     */
    fun unableToGetSpotifyRemote(e: Throwable) {
        _currentUserData.value = SpotifyNetworkCall.Failure(message = e.message.toString())
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


    // Keeps the user Recently Played Tracks
    private val _userRecentlyPlayedTracks =
        MutableStateFlow<SpotifyNetworkCall<SpotifyUserRecentlyPlayedModel>>(
            SpotifyNetworkCall.Initialized()
        )
    val userRecentlyPlayedTracks = _userRecentlyPlayedTracks.asStateFlow()

    /**
     * This function fetches the user Recently Played Tracks from the spotify api
     */
    private fun getCurrentUserRecentlyPlayedTracks() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserRecentlyPlayedTracks(accessToken).collectLatest {
                _userRecentlyPlayedTracks.value = it
            }
        }
    }

    /**
     * This function sets the [ToneUiState] data for the alarm which would be stored in the
     * Database later
     */
    private fun onApplyClick(toneUiState: ToneUiState) {
        Log.d("tone", "onApplyClick: $toneUiState")
        viewModelScope.launch {
            prefUtils.setPreferences(Constants.SPOTIFY_SONG_KEY_URI, toneUiState.uri)
            prefUtils.setPreferences(Constants.SPOTIFY_SONG_KEY_TYPE, toneUiState.type)
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
    private fun getUserTopTracks() {
        viewModelScope.launch {
            remoteRepository.getCurrentUserTopTracks(accessToken).collectLatest {
                _userTopTracks.value = it
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

    /**
     * This function sets the variables and the Searching Params of the search option
     */
    private fun setSearchQueriesAndVariables(query: String) {
        this.query = query
        getSpotifySearchResult()
    }

    /**
     * This function fetches the spotify search result for the User
     */
    private fun getSpotifySearchResult() {

        // Returning to prevent showing error during the first composition during Initialized State
        if (query.isEmpty() || _spotifySearch.value is SpotifyNetworkCall.Loading)
            return

        // Starting the Loading State
        _spotifySearch.value = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            remoteRepository.searchQuery(
                accessToken = accessToken,
                query = query,
                type = "track",
                includeExternal = "audio",
                market = _currentUserData.value.data!!.country
            ).collectLatest {
                _spotifySearch.value = it
            }
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
    private fun getAllTracks() {
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
    private fun getAllAlbums() {
        viewModelScope.launch {
            localRepository.getAllAlbums().collect {
                _allAlbums.value = it
            }
        }
    }

    fun eventHelper(event: SpotifyUiEvent) {
        when (event) {

            is SpotifyUiEvent.NetworkIO.LoadCurrentUserRecentlyPlayedTracks -> {
                getCurrentUserRecentlyPlayedTracks()
            }

            is SpotifyUiEvent.NetworkIO.LoadUserTopTracks -> {
                getUserTopTracks()
            }

            is SpotifyUiEvent.NetworkIO.SetSearchQueriesAndVariables -> {
                setSearchQueriesAndVariables(query = event.query)
            }

            is SpotifyUiEvent.NetworkIO.LoadSpotifySearchResult -> {
                getSpotifySearchResult()
            }

            is SpotifyUiEvent.HelperEvent.PlaySpotifySong -> {
                playSpotifySong(url = event.url)
            }

            is SpotifyUiEvent.HelperEvent.OnApplyClick -> {
                onApplyClick(toneUiState = event.toneUiState)
            }

            is SpotifyUiEvent.LocalIO.LoadAllTracks -> {
                getAllTracks()
            }

            is SpotifyUiEvent.LocalIO.LoadAllAlbums -> {
                getAllAlbums()
            }
        }
    }
}