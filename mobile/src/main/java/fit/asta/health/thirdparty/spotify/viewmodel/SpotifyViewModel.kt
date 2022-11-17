package fit.asta.health.thirdparty.spotify.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.thirdparty.spotify.model.SpotifyRepository
import fit.asta.health.thirdparty.spotify.model.net.albums.SpotifyAlbumDetailsModel
import fit.asta.health.thirdparty.spotify.model.net.categories.SpotifyBrowseCategoriesModel
import fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.model.net.me.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.me.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.me.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed.SpotifyPlayerRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.model.net.me.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.me.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.model.net.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.model.net.recommendations.SpotifyRecommendationModel
import fit.asta.health.thirdparty.spotify.model.net.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.model.net.top.SpotifyTopArtistsModel
import fit.asta.health.thirdparty.spotify.model.net.top.SpotifyTopTracksModel
import fit.asta.health.thirdparty.spotify.model.net.tracks.SpotifyTrackDetailsModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.TAG
import fit.asta.health.utils.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModel @Inject constructor(
    private val repository: SpotifyRepository,
    application: Application
) : AndroidViewModel(application) {

    // Get Current User Details
    val currentUserDetailsResponse: MutableLiveData<NetworkResult<SpotifyMeModel>> =
        MutableLiveData()

    fun getCurrentUserDetails(accessToken: String) = viewModelScope.launch {
        getCurrentUserDetailsSafeCall(accessToken)
    }

    private suspend fun getCurrentUserDetailsSafeCall(accessToken: String) {
        currentUserDetailsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCurrentUserDetails(accessToken)
            currentUserDetailsResponse.value = handleResponse(response)
//            for offline catching
//            val result = currentUserDetailsResponse.value!!.data
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUserDetailsSafeCall: $e")
            currentUserDetailsResponse.value = NetworkResult.Error(message = e.message)
        }
    }

    // Get Current User Following Artists
    val currentUserFollowingArtistsResponse: MutableLiveData<NetworkResult<SpotifyUserFollowingArtist>> =
        MutableLiveData()

    fun getCurrentUserFollowingArtists(accessToken: String) = viewModelScope.launch {
        getCurrentUserFollowingArtistsSafeCall(accessToken)
    }

    private suspend fun getCurrentUserFollowingArtistsSafeCall(accessToken: String) {
        currentUserFollowingArtistsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCurrentUserFollowedArtists(accessToken)
            currentUserFollowingArtistsResponse.value = handleResponse(response)
//            for offline catching
//            val result = currentUserDetailsResponse.value!!.data
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUserDetailsSafeCall: $e")
            currentUserFollowingArtistsResponse.value = NetworkResult.Error(message = e.message)
        }
    }

    // Get Current User Top Tracks
    val currentUserTopTracksResponse: MutableLiveData<NetworkResult<SpotifyTopTracksModel>> =
        MutableLiveData()

    fun getCurrentUserTopTracks(accessToken: String) = viewModelScope.launch {
        getCurrentUserTopTracksSafeCall(accessToken)
    }

    private suspend fun getCurrentUserTopTracksSafeCall(accessToken: String) {
        currentUserTopTracksResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCurrentUserTopTracks(accessToken)
            currentUserTopTracksResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUserTopTracksSafeCall: $e")
        }
    }

    // Get Current User Top Tracks
    val currentUserTopArtistsResponse: MutableLiveData<NetworkResult<SpotifyTopArtistsModel>> =
        MutableLiveData()

    fun getCurrentUserTopArtists(accessToken: String) = viewModelScope.launch {
        getCurrentUserTopArtistsSafeCall(accessToken)
    }

    private suspend fun getCurrentUserTopArtistsSafeCall(accessToken: String) {
        currentUserTopArtistsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCurrentUserTopArtists(accessToken)
            currentUserTopArtistsResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUserTopArtistsSafeCall: $e")
        }
    }

    // Get Current User's Playlists
    val currentUserPlaylistsResponse: MutableLiveData<NetworkResult<SpotifyUserPlaylistsModel>> =
        MutableLiveData()

    fun getCurrentUserPlaylists(accessToken: String) = viewModelScope.launch {
        getCurrentUserPlaylistsSafeCall(accessToken)
    }

    private suspend fun getCurrentUserPlaylistsSafeCall(accessToken: String) {
        currentUserPlaylistsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCurrentUserPlaylists(accessToken)
            currentUserPlaylistsResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUserPlaylistsSafeCall: $e")
        }
    }

    // Get Current User's Albums
    val currentUserAlbumsResponse: MutableLiveData<NetworkResult<SpotifyLibraryAlbumModel>> =
        MutableLiveData()

    fun getCurrentUserAlbums(accessToken: String) = viewModelScope.launch {
        getCurrentUserAlbumsSafeCall(accessToken)
    }

    private suspend fun getCurrentUserAlbumsSafeCall(accessToken: String) {
        currentUserAlbumsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCurrentUserAlbums(accessToken)
            currentUserAlbumsResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUserPlaylistsSafeCall: $e")
        }
    }

    // Get Current User's Shows
    val currentUserShowsResponse: MutableLiveData<NetworkResult<SpotifyLibraryShowsModel>> =
        MutableLiveData()

    fun getCurrentUserShows(accessToken: String) = viewModelScope.launch {
        getCurrentUserShowsSafeCall(accessToken)
    }

    private suspend fun getCurrentUserShowsSafeCall(accessToken: String) {
        currentUserShowsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCurrentUserShows(accessToken)
            currentUserShowsResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUserShowsSafeCall: $e")
        }
    }

    // Get Current User's Episodes
    val currentUserEpisodesResponse: MutableLiveData<NetworkResult<SpotifyLibraryEpisodesModel>> =
        MutableLiveData()

    fun getCurrentUserEpisodes(accessToken: String) = viewModelScope.launch {
        getCurrentUserEpisodesSafeCall(accessToken)
    }

    private suspend fun getCurrentUserEpisodesSafeCall(accessToken: String) {
        currentUserEpisodesResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCurrentUserEpisodes(accessToken)
            currentUserEpisodesResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUserEpisodesSafeCall: $e")
        }
    }

    // Get Current User's Tracks
    val currentUserTracksResponse: MutableLiveData<NetworkResult<SpotifyLibraryTracksModel>> =
        MutableLiveData()

    fun getCurrentUserTracks(accessToken: String) = viewModelScope.launch {
        getCurrentUserTracksSafeCall(accessToken)
    }

    private suspend fun getCurrentUserTracksSafeCall(accessToken: String) {
        currentUserTracksResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCurrentUserTracks(accessToken)
            currentUserTracksResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUserTracksSafeCall: $e")
        }
    }

    // Get Current User's Recently Played
    val currentUserRecentlyPlayed: MutableLiveData<NetworkResult<SpotifyPlayerRecentlyPlayedModel>> =
        MutableLiveData()

    fun getCurrentUserRecentlyPlayed(accessToken: String) =
        viewModelScope.launch { getCurrentUserRecentlyPlayedSafeCall(accessToken) }

    private suspend fun getCurrentUserRecentlyPlayedSafeCall(accessToken: String) {
        currentUserRecentlyPlayed.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCurrentUserRecentlyPlayedTracks(accessToken)
            currentUserRecentlyPlayed.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUserRecentlyPlayedSafeCall: $e")
        }
    }

    // Get User's Playlists
    val userPlaylistsResponse: MutableLiveData<NetworkResult<SpotifyUserPlaylistsModel>> =
        MutableLiveData()

    fun getUserPlaylists(accessToken: String, userID: String) = viewModelScope.launch {
        getUserPlaylistsSafeCall(accessToken, userID)
    }

    private suspend fun getUserPlaylistsSafeCall(accessToken: String, userID: String) {
        userPlaylistsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getUserPlaylists(accessToken, userID)
            userPlaylistsResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getUserPlaylistsSafeCall: $e")
        }
    }

    // Get Track Details
    val trackDetailsResponse: MutableLiveData<NetworkResult<SpotifyTrackDetailsModel>> =
        MutableLiveData()

    fun getTrackDetails(accessToken: String, trackId: String) = viewModelScope.launch {
        getTrackDetailsSafeCall(accessToken, trackId)
    }

    private suspend fun getTrackDetailsSafeCall(accessToken: String, trackId: String) {
        trackDetailsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getTrackDetails(accessToken, trackId)
            trackDetailsResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getTrackDetailsSafeCall: $e")
        }
    }

    // Get Album Details
    val albumDetailsResponse: MutableLiveData<NetworkResult<SpotifyAlbumDetailsModel>> =
        MutableLiveData()

    fun getAlbumDetails(accessToken: String, albumId: String) = viewModelScope.launch {
        getAlbumDetailsSafeCall(accessToken, albumId)
    }

    private suspend fun getAlbumDetailsSafeCall(accessToken: String, trackId: String) {
        albumDetailsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getAlbumDetails(accessToken, trackId)
            albumDetailsResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getAlbumDetailsSafeCall: $e")
        }
    }

    // Get Categories
    val categoriesResponse: MutableLiveData<NetworkResult<SpotifyBrowseCategoriesModel>> =
        MutableLiveData()

    fun getCategories(accessToken: String, coutry: String) = viewModelScope.launch {
        getCategoriesSafeCall(accessToken, coutry)
    }

    private suspend fun getCategoriesSafeCall(accessToken: String, country: String) {
        categoriesResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCategories(accessToken, country)
            categoriesResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getCategoriesSafeCall: $e")
        }
    }

    // Get Search Query Result
    val searchResponse: MutableLiveData<NetworkResult<SpotifySearchModel>> = MutableLiveData()

    fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ) = viewModelScope.launch {
        searchQuerySafeCall(accessToken, query, type, includeExternal, market)
    }

    private suspend fun searchQuerySafeCall(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ) {
        searchResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.searchQuery(
                accessToken,
                query,
                type,
                includeExternal,
                market
            )
            searchResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "searchQuerySafeCall: $e")
        }
    }

    val recommendationResponse: MutableLiveData<NetworkResult<SpotifyRecommendationModel>> =
        MutableLiveData()

    fun getRecommendations(
        accessToken: String,
        seedArtists: String,
        seedGenres: String,
        seedTracks: String,
        limit: String
    ) = viewModelScope.launch {
        getRecommendationsSafeCall(accessToken, seedArtists, seedGenres, seedTracks, limit)
    }

    private suspend fun getRecommendationsSafeCall(
        accessToken: String,
        seedArtists: String,
        seedGenres: String,
        seedTracks: String,
        limit: String
    ) {
        recommendationResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getRecommendations(
                accessToken, seedArtists, seedGenres, seedTracks, limit
            )
            recommendationResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "getRecommendationsSafeCall: $e")
        }
    }

    // Handle Response Got From Spotify Web API
    private fun <T : Any> handleResponse(response: Response<T>): NetworkResult<T> {
        Log.d(TAG, "handleResponse: ${response.body()} ")
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Timeout!!\n $response"
                )
            }

            response.code() == 401 -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Bad or expired token. This can happen if the user revoked a token or the access token has expired. You should re-authenticate the user.\n $response"
                )
            }

            response.code() == 403 -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Bad OAuth request (wrong consumer key, bad nonce, expired timestamp...). Unfortunately, re-authenticating the user won't help here.\n $response"
                )
            }

            response.code() == 429 -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "The app has exceeded its rate limits. $response"
                )
            }

            response.body() == null -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Empty Body. $response"
                )
            }

            response.isSuccessful -> {
                val result = response.body()
                return NetworkResult.Success(result!!)
            }

            response.code() == 200 -> {
                val result = response.body()
                return NetworkResult.Success(result!!)
            }

            else -> return NetworkResult.Error(
                message = response.body().toString(),
                data = response.body()
            )
        }
    }
}