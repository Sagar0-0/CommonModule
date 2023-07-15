package fit.asta.health.tools.sunlight.model

import fit.asta.health.tools.sunlight.model.network.response.ResponseData
import kotlinx.coroutines.flow.Flow


interface SunlightToolRepo {
    suspend fun getSunlightTool(
        userId: String,
        latitude: String,
        longitude: String,
        date: String,
        location: String
    ): Flow<ResponseData.SunlightToolData>
}