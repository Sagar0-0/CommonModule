package fit.asta.health.scheduler.model

import fit.asta.health.scheduler.model.api.spotify.SpotifyApi
import fit.asta.health.scheduler.model.net.spotify.me.SpotifyMeModel
import fit.asta.health.scheduler.model.net.spotify.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.scheduler.model.net.spotify.search.SpotifySearchModel
import fit.asta.health.scheduler.model.net.spotify.search.TrackList
import fit.asta.health.scheduler.util.SpotifyNetworkCall
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