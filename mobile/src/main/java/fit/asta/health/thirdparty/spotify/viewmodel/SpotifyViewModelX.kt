package fit.asta.health.thirdparty.spotify.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.thirdparty.spotify.model.SpotifyRepoImpl
import fit.asta.health.thirdparty.spotify.model.net.albums.SpotifyAlbumDetailsModel
import fit.asta.health.thirdparty.spotify.model.net.me.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.me.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.me.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.me.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.me.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.model.net.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.model.netx.search.SpotifySearchModelX
import fit.asta.health.thirdparty.spotify.model.netx.top.SpotifyTopArtistsModelX
import fit.asta.health.thirdparty.spotify.model.netx.top.SpotifyTopTracksModelX
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import fit.asta.health.thirdparty.spotify.model.netx.me.SpotifyMeModelX
import fit.asta.health.thirdparty.spotify.model.netx.recently.SpotifyPlayerRecentlyPlayedModelX
import fit.asta.health.thirdparty.spotify.model.netx.recommendations.SpotifyRecommendationModelX
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModelX @Inject constructor(
    private val repository: SpotifyRepoImpl
) : ViewModel() {

    // Keeps the AccessToken of the Authorization
    private var accessToken: String = ""

    // Keeps the current User Data so that it can be used
    var currentUserData: SpotifyNetworkCall<SpotifyMeModelX> by mutableStateOf(
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
                val response = repository.getCurrentUserDetails(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    // Keeps the user Recently Played Tracks
    var userRecentlyPlayedTracks: SpotifyNetworkCall<SpotifyPlayerRecentlyPlayedModelX> by mutableStateOf(
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
                val response = repository.getCurrentUserRecentlyPlayedTracks(accessToken)
                val state = handleResponse(response)

                // Fetching the Recommendations Tracks List for the Users
                if (state.data?.items?.isNotEmpty() == true) {

                    // Setting for Future Purposes
                    seedArtists = state.data.items[0].track.artists[0].id
                    seedTracks = state.data.items[0].track.id

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
    var recommendationTracks: SpotifyNetworkCall<SpotifyRecommendationModelX> by mutableStateOf(
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
                val response = repository.getRecommendations(
                    accessToken, seedArtists, seedGenres, seedTracks, limit
                )
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    // Keeps the recommended Tracks
    var trackDetailsResponse: SpotifyNetworkCall<TrackX> by mutableStateOf(
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
                val response = repository.getTrackDetails(accessToken, trackDetailId)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    // Keeps the User Top Tracks
    var userTopTracks: SpotifyNetworkCall<SpotifyTopTracksModelX> by mutableStateOf(
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
                val response = repository.getCurrentUserTopTracks(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    // Keeps the User Top Tracks
    var userTopArtists: SpotifyNetworkCall<SpotifyTopArtistsModelX> by mutableStateOf(
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
                val response = repository.getCurrentUserTopArtists(accessToken)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     * Keeps the Spotify Search Result
     */
    var spotifySearch: SpotifyNetworkCall<SpotifySearchModelX> by mutableStateOf(
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
                    repository.searchQuery(accessToken, query, type, includeExternal, market)
                handleResponse(response)
            } catch (e: Exception) {
                SpotifyNetworkCall.Failure(message = e.message)
            }
        }
    }

    /**
     * Keeps the Spotify Album Details
     */
    var albumDetailsResponse: SpotifyNetworkCall<SpotifyAlbumDetailsModel> by mutableStateOf(
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
                val response = repository.getAlbumDetails(accessToken, albumDetailId)
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
                val response = repository.getCurrentUserTracks(accessToken)
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
                val response = repository.getCurrentUserPlaylists(accessToken)
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
                val response = repository.getCurrentUserFollowedArtists(accessToken)
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
                val response = repository.getCurrentUserAlbums(accessToken)
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
                val response = repository.getCurrentUserShows(accessToken)
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
                val response = repository.getCurrentUserEpisodes(accessToken)
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