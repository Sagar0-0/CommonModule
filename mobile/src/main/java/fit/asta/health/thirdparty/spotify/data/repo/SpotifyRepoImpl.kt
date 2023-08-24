package fit.asta.health.thirdparty.spotify.data.repo

import fit.asta.health.thirdparty.spotify.data.remote.SpotifyApiService
import fit.asta.health.thirdparty.spotify.data.model.common.Album
import fit.asta.health.thirdparty.spotify.data.model.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.data.model.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.data.model.library.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.data.model.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.data.model.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.data.model.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.data.model.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.data.model.search.ArtistList
import fit.asta.health.thirdparty.spotify.data.model.search.TrackList
import fit.asta.health.thirdparty.spotify.data.model.common.Track
import fit.asta.health.thirdparty.spotify.data.model.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.data.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.data.model.recommendations.SpotifyRecommendationModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class SpotifyRepoImpl(private val spotifyApi: SpotifyApiService) : SpotifyRepo {

    override suspend fun getCurrentUserDetails(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyMeModel>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserDetails(headerMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserFollowedArtists(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyUserFollowingArtist>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserFollowedArtists(headerMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserTopTracks(accessToken: String):
            Flow<SpotifyNetworkCall<TrackList>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserTopTracks(headerMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserTopArtists(accessToken: String):
            Flow<SpotifyNetworkCall<ArtistList>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserTopArtists(headerMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserAlbums(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyLibraryAlbumModel>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserAlbums(headerMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserShows(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyLibraryShowsModel>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserShows(headerMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserEpisodes(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyLibraryEpisodesModel>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserEpisodes(headerMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserTracks(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyLibraryTracksModel>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserTracks(headerMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserPlaylists(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyUserPlaylistsModel>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserPlaylists(headerMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyUserRecentlyPlayedModel>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserRecentlyPlayedTracks(headerMap, queryMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserPlaylists(accessToken: String, userID: String):
            Flow<SpotifyNetworkCall<SpotifyUserPlaylistsModel>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getUserPlaylists(headerMap, userID)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTrackDetails(accessToken: String, trackID: String):
            Flow<SpotifyNetworkCall<Track>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getTrackDetails(headerMap, trackID)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAlbumDetails(accessToken: String, albumID: String):
            Flow<SpotifyNetworkCall<Album>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getAlbumDetails(headerMap, albumID)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): Flow<SpotifyNetworkCall<SpotifySearchModel>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()
        queryMap["q"] = query
        queryMap["type"] = type
        queryMap["include_external"] = includeExternal
        queryMap["market"] = market

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.searchQuery(headerMap, queryMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getRecommendations(
        accessToken: String,
        seedArtists: String,
        seedGenres: String,
        seedTracks: String,
        limit: String
    ): Flow<SpotifyNetworkCall<SpotifyRecommendationModel>> {

        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()
        queryMap["seed_artists"] = seedArtists
        queryMap["seed_genres"] = seedGenres
        queryMap["seed_tracks"] = seedTracks
        queryMap["limit"] = limit

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getRecommendations(headerMap, queryMap)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
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