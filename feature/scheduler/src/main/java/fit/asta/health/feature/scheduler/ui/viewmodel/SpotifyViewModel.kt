package fit.asta.health.feature.scheduler.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.data.spotify.model.common.Track
import fit.asta.health.data.spotify.model.me.SpotifyMeModel
import fit.asta.health.data.spotify.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.data.spotify.model.search.SpotifySearchModel
import fit.asta.health.data.spotify.model.search.TrackList
import fit.asta.health.data.spotify.repo.MusicRepository
import fit.asta.health.data.spotify.repo.SpotifyRepo
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ToneUiState
import fit.asta.health.feature.scheduler.ui.screen.spotify.SpotifyUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import fit.asta.health.resources.strings.R as StringR

@HiltViewModel
class SpotifyViewModel @Inject constructor(
    private val remoteRepository: SpotifyRepo,
    private val localRepository: MusicRepository,
    private val prefManager: PrefManager,
    application: Application,
) : AndroidViewModel(application) {

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
                _currentUserData.value = UiState.Error(resId = StringR.string.no_internet)
            }
        }
    }


    /**
     * This function fetches the Current User Data from the spotify api
     */
    private fun getCurrentUserDetails(accessToken: String) {

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
        _currentUserData.value = UiState.Error(StringR.string.spotify_remote_not_found)
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

    /**
     * This function sets the [ToneUiState] data for the alarm which would be stored in the
     * Database later
     */
    private fun onApplyClick(toneUiState: ToneUiState) {
        Log.d("tone", "onApplyClick: $toneUiState")
        viewModelScope.launch {
            prefManager.setSoundTone(toneUiState.uri)
            Log.d("tone", toneUiState.uri)
//            prefManager.setPreferences(Constants.SPOTIFY_SONG_KEY_TYPE, toneUiState.type)
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

    /**
     * Keeps the Spotify Search Result
     */
    private val _spotifySearch = MutableStateFlow<UiState<SpotifySearchModel>>(UiState.Idle)
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
        if (query.isEmpty() || _spotifySearch.value is UiState.Loading)
            return

        // Starting the Loading State
        _spotifySearch.value = UiState.Loading

        viewModelScope.launch {
            remoteRepository.searchQuery(
                accessToken = accessToken,
                query = query,
                type = "track",
                includeExternal = "audio",
                market = (_currentUserData.value as UiState.Success<SpotifyMeModel>).data.country
            ).toUiState().also { _spotifySearch.value = it }
        }
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