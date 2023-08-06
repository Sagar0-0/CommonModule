package fit.asta.health.navigation.track.model

import fit.asta.health.navigation.track.model.api.TrackingApi
import fit.asta.health.navigation.track.model.net.breathing.BreathingResponse
import fit.asta.health.navigation.track.model.net.meditation.MeditationResponse
import fit.asta.health.navigation.track.model.net.sleep.SleepResponse
import fit.asta.health.navigation.track.model.net.step.StepsResponse
import fit.asta.health.navigation.track.model.net.sunlight.SunlightResponse
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import fit.asta.health.navigation.track.view.util.TrackingNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class TrackingRepoImpl @Inject constructor(
    private val trackingApi: TrackingApi
) : TrackingRepo {

    override suspend fun getWaterDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<WaterResponse>> {

        return flow {
            emit(TrackingNetworkCall.Loading())

            // Fetching Data
            val response = trackingApi.getWaterDetails(
                uid = uid,
                date = date,
                location = location,
                status = status
            )
            emit(handleResponse(response))
        }.catch {
            emit(TrackingNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getStepsDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<StepsResponse>> {

        return flow {
            emit(TrackingNetworkCall.Loading())

            // Fetching Data
            val response = trackingApi.getStepsDetails(
                uid = uid,
                date = date,
                location = location,
                status = status
            )
            emit(handleResponse(response))
        }.catch {
            emit(TrackingNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMeditationDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<MeditationResponse>> {

        return flow {
            emit(TrackingNetworkCall.Loading())

            // Fetching Data
            val response = trackingApi.getMeditationDetails(
                uid = uid,
                date = date,
                location = location,
                status = status
            )
            emit(handleResponse(response))
        }.catch {
            emit(TrackingNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getBreathingDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<BreathingResponse>> {
        return flow {
            emit(TrackingNetworkCall.Loading())

            // Fetching Data
            val response = trackingApi.getBreathingDetails(
                uid = uid,
                date = date,
                location = location,
                status = status
            )
            emit(handleResponse(response))
        }.catch {
            emit(TrackingNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSleepDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<SleepResponse>> {

        return flow {
            emit(TrackingNetworkCall.Loading())

            // Fetching Data
            val response = trackingApi.getSleepDetails(
                uid = uid,
                date = date,
                location = location,
                status = status
            )
            emit(handleResponse(response))
        }.catch {
            emit(TrackingNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSunlightDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<SunlightResponse>> {

        return flow {
            emit(TrackingNetworkCall.Loading())

            // Fetching Data
            val response = trackingApi.getSunlightDetails(
                uid = uid,
                date = date,
                location = location,
                status = status
            )
            emit(handleResponse(response))
        }.catch {
            emit(TrackingNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    /**
     *  Handle Response Got From Spotify Web API
     */
    private fun <T : Any> handleResponse(response: Response<T>): TrackingNetworkCall<T> {
        when {
            response.message().toString().contains("timeout") -> {
                return TrackingNetworkCall.Failure(
                    data = response.body(),
                    message = "Timeout!!\n $response"
                )
            }

            response.code() == 401 -> {
                return TrackingNetworkCall.Failure(
                    data = response.body(),
                    message = "Bad or expired token. This can happen if the user revoked a token or the access token has expired. You should re-authenticate the user.\n $response"
                )
            }

            response.code() == 403 -> {
                return TrackingNetworkCall.Failure(
                    data = response.body(),
                    message = "Bad OAuth request (wrong consumer key, bad nonce, expired timestamp...). Unfortunately, re-authenticating the user won't help here.\n $response"
                )
            }

            response.code() == 429 -> {
                return TrackingNetworkCall.Failure(
                    data = response.body(),
                    message = "The app has exceeded its rate limits. $response"
                )
            }

            response.body() == null -> {
                return TrackingNetworkCall.Failure(
                    data = response.body(),
                    message = "Empty Body. $response"
                )
            }

            response.isSuccessful -> {
                val result = response.body()!!
                return TrackingNetworkCall.Success(result)
            }

            response.code() == 200 -> {
                val result = response.body()!!
                return TrackingNetworkCall.Success(result)
            }

            else -> return TrackingNetworkCall.Failure(
                message = response.body().toString(),
                data = response.body()
            )
        }
    }

}