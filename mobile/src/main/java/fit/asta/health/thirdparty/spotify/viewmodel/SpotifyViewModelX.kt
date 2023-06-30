package fit.asta.health.thirdparty.spotify.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.thirdparty.spotify.model.SpotifyRepoImpl
import fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed.SpotifyPlayerRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.model.net.recommendations.SpotifyRecommendationModel
import fit.asta.health.thirdparty.spotify.model.net.tracks.SpotifyTrackDetailsModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModelX @Inject constructor(
    private val repository: SpotifyRepoImpl
) : ViewModel() {

    // Keeps the AccessToken of the Authorization
    var accessToken: String = ""
        private set

    // Keeps the current User Data so that it can be used
    var currentUserData: SpotifyNetworkCall<SpotifyMeModel> by mutableStateOf(
        SpotifyNetworkCall.Initialized()
    )
        private set

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
    var userRecentlyPlayedTracks: SpotifyNetworkCall<SpotifyPlayerRecentlyPlayedModel> by mutableStateOf(
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
    var trackDetailsResponse: SpotifyNetworkCall<SpotifyTrackDetailsModel> by mutableStateOf(
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
                currentUserData = SpotifyNetworkCall.Failure(message = response.toString())
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