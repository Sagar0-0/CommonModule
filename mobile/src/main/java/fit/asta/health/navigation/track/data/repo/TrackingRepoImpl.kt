package fit.asta.health.navigation.track.data.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.navigation.track.data.remote.TrackingApiService
import fit.asta.health.navigation.track.data.remote.model.breathing.BreathingResponse
import fit.asta.health.navigation.track.data.remote.model.exercise.ExerciseResponse
import fit.asta.health.navigation.track.data.remote.model.meditation.MeditationResponse
import fit.asta.health.navigation.track.data.remote.model.menu.HomeMenuResponse
import fit.asta.health.navigation.track.data.remote.model.sleep.SleepResponse
import fit.asta.health.navigation.track.data.remote.model.step.StepsResponse
import fit.asta.health.navigation.track.data.remote.model.sunlight.SunlightResponse
import fit.asta.health.navigation.track.data.remote.model.water.WaterResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrackingRepoImpl(
    private val trackingApiService: TrackingApiService,
    @IODispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TrackingRepo {

    override suspend fun getHomeDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): ResponseState<HomeMenuResponse> {

        return withContext(dispatcher) {
            getResponseState {
                trackingApiService.getHomeDetails(
                    uid = uid,
                    date = date,
                    location = location,
                    status = status
                )
            }
        }
    }

    override suspend fun getWaterDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): ResponseState<WaterResponse> {

        return withContext(dispatcher) {
            getResponseState {
                trackingApiService.getWaterDetails(
                    uid = uid,
                    date = date,
                    location = location,
                    status = status
                )
            }
        }
    }

    override suspend fun getStepsDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): ResponseState<StepsResponse> {

        return withContext(dispatcher) {
            getResponseState {
                trackingApiService.getStepsDetails(
                    uid = uid,
                    date = date,
                    location = location,
                    status = status
                )
            }
        }
    }

    override suspend fun getMeditationDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): ResponseState<MeditationResponse> {

        return withContext(dispatcher) {
            getResponseState {
                trackingApiService.getMeditationDetails(
                    uid = uid,
                    date = date,
                    location = location,
                    status = status
                )
            }
        }
    }

    override suspend fun getBreathingDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): ResponseState<BreathingResponse> {

        return withContext(dispatcher) {
            getResponseState {
                trackingApiService.getBreathingDetails(
                    uid = uid,
                    date = date,
                    location = location,
                    status = status
                )
            }
        }
    }

    override suspend fun getSleepDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): ResponseState<SleepResponse> {

        return withContext(dispatcher) {
            getResponseState {
                trackingApiService.getSleepDetails(
                    uid = uid,
                    date = date,
                    location = location,
                    status = status
                )
            }
        }
    }

    override suspend fun getSunlightDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): ResponseState<SunlightResponse> {

        return withContext(dispatcher) {
            getResponseState {
                trackingApiService.getSunlightDetails(
                    uid = uid,
                    date = date,
                    location = location,
                    status = status
                )
            }
        }
    }

    override suspend fun getExerciseDetails(
        uid: String,
        date: String,
        location: String,
        exercise: String,
        status: String
    ): ResponseState<ExerciseResponse> {

        return withContext(dispatcher) {
            getResponseState {
                trackingApiService.getExerciseDetails(
                    uid = uid,
                    date = date,
                    location = location,
                    exercise = exercise,
                    status = status
                )
            }
        }
    }
}