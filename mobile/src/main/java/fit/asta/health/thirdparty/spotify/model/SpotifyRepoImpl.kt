package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.api.SpotifyApi
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class SpotifyRepoImpl @Inject constructor(private val spotifyApi: SpotifyApi) : SpotifyRepo {

    override suspend fun getCurrentUserDetails(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyMeModel>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserDetails(accessToken)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserFollowedArtists(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyUserFollowingArtist>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserFollowedArtists(accessToken)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserTopTracks(accessToken: String):
            Flow<SpotifyNetworkCall<TrackList>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserTopTracks(accessToken = accessToken)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserTopArtists(accessToken: String):
            Flow<SpotifyNetworkCall<ArtistList>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserTopArtists(accessToken = accessToken)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserAlbums(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyLibraryAlbumModel>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserAlbums(accessToken = accessToken)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserShows(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyLibraryShowsModel>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserShows(accessToken = accessToken)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserEpisodes(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyLibraryEpisodesModel>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserEpisodes(accessToken = accessToken)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserTracks(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyLibraryTracksModel>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserTracks(accessToken = accessToken)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserPlaylists(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyUserPlaylistsModel>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserPlaylists(accessToken = accessToken)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String):
            Flow<SpotifyNetworkCall<SpotifyUserRecentlyPlayedModel>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getCurrentUserRecentlyPlayedTracks(accessToken = accessToken)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserPlaylists(accessToken: String, userID: String):
            Flow<SpotifyNetworkCall<SpotifyUserPlaylistsModel>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getUserPlaylists(accessToken, userID)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTrackDetails(accessToken: String, trackID: String):
            Flow<SpotifyNetworkCall<Track>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getTrackDetails(accessToken = accessToken, trackID = trackID)
            emit(handleResponse(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAlbumDetails(accessToken: String, albumID: String):
            Flow<SpotifyNetworkCall<Album>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getAlbumDetails(accessToken = accessToken, albumID = albumID)
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

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.searchQuery(
                accessToken = accessToken,
                query = query,
                type = type,
                includeExternal = includeExternal,
                market = market
            )
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

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = spotifyApi.getRecommendations(
                accessToken = accessToken,
                seedArtists = seedArtists,
                seedGenres = seedGenres,
                seedTracks = seedTracks,
                limit = limit
            )
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