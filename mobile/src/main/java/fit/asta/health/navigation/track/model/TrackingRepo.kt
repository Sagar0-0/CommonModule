package fit.asta.health.navigation.track.model

import fit.asta.health.navigation.track.model.net.breathing.BreathingResponse
import fit.asta.health.navigation.track.model.net.meditation.MeditationResponse
import fit.asta.health.navigation.track.model.net.sleep.SleepResponse
import fit.asta.health.navigation.track.model.net.step.StepsResponse
import fit.asta.health.navigation.track.model.net.sunlight.SunlightResponse
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import fit.asta.health.navigation.track.view.util.TrackingNetworkCall
import kotlinx.coroutines.flow.Flow

interface TrackingRepo {

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
}