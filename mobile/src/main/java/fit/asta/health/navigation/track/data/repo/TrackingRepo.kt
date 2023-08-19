package fit.asta.health.navigation.track.data.repo

import fit.asta.health.navigation.track.data.remote.model.breathing.BreathingResponse
import fit.asta.health.navigation.track.data.remote.model.exercise.ExerciseResponse
import fit.asta.health.navigation.track.data.remote.model.meditation.MeditationResponse
import fit.asta.health.navigation.track.data.remote.model.menu.HomeMenuResponse
import fit.asta.health.navigation.track.data.remote.model.sleep.SleepResponse
import fit.asta.health.navigation.track.data.remote.model.step.StepsResponse
import fit.asta.health.navigation.track.data.remote.model.sunlight.SunlightResponse
import fit.asta.health.navigation.track.data.remote.model.water.WaterResponse
import fit.asta.health.navigation.track.ui.util.TrackingNetworkCall
import kotlinx.coroutines.flow.Flow

interface TrackingRepo {

    suspend fun getHomeDetails(
        uid: String,
        date: String,
        location: String
    ): Flow<TrackingNetworkCall<HomeMenuResponse>>

    suspend fun getWaterDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<WaterResponse>>

    suspend fun getStepsDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<StepsResponse>>

    suspend fun getMeditationDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<MeditationResponse>>

    suspend fun getBreathingDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<BreathingResponse>>

    suspend fun getSleepDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<SleepResponse>>

    suspend fun getSunlightDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Flow<TrackingNetworkCall<SunlightResponse>>

    suspend fun getExerciseDetails(
        uid: String,
        date: String,
        location: String,
        exercise: String,
        status: String
    ): Flow<TrackingNetworkCall<ExerciseResponse>>
}