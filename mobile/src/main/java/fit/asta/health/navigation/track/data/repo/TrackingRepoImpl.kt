package fit.asta.health.navigation.track.data.repo

import fit.asta.health.navigation.track.data.remote.TrackingApiService
import fit.asta.health.navigation.track.data.remote.model.breathing.BreathingResponse
import fit.asta.health.navigation.track.data.remote.model.exercise.ExerciseResponse
import fit.asta.health.navigation.track.data.remote.model.meditation.MeditationResponse
import fit.asta.health.navigation.track.data.remote.model.menu.HomeMenuResponse
import fit.asta.health.navigation.track.data.remote.model.sleep.SleepResponse
import fit.asta.health.navigation.track.data.remote.model.step.StepsResponse
import fit.asta.health.navigation.track.data.remote.model.sunlight.SunlightResponse
import fit.asta.health.navigation.track.data.remote.model.water.WaterResponse
import fit.asta.health.navigation.track.ui.util.TrackingNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class TrackingRepoImpl(
    private val trackingApiService: TrackingApiService
) : TrackingRepo {

    override suspend fun getHomeDetails(
        uid: String,
        date: String,
        location: String
    ): Flow<TrackingNetworkCall<HomeMenuResponse>> {

        return flow {
            emit(TrackingNetworkCall.Loading())

            // Fetching Data
            val response = trackingApiService.getHomeDetails(
                uid = uid,
                date = date,
                location = location
            )
            emit(handleResponse(response))
        }.catch {
            emit(TrackingNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getWaterDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<WaterResponse>> {

        return flow {
            emit(TrackingNetworkCall.Loading())

            // Fetching Data
            val response = trackingApiService.getWaterDetails(
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
            val response = trackingApiService.getStepsDetails(
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
            val response = trackingApiService.getMeditationDetails(
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
            val response = trackingApiService.getBreathingDetails(
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
            val response = trackingApiService.getSleepDetails(
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
            val response = trackingApiService.getSunlightDetails(
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

    override suspend fun getExerciseDetails(
        uid: String,
        date: String,
        location: String,
        exercise: String,
        status: String
    ): Flow<TrackingNetworkCall<ExerciseResponse>> {

        return flow {
            emit(TrackingNetworkCall.Loading())

            // Fetching Data
            val response = trackingApiService.getExerciseDetails(
                uid = uid,
                date = date,
                location = location,
                exercise = exercise,
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