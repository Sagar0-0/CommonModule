package fit.asta.health.data.sunlight.model

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.sunlight.model.network.response.SunlightToolData


interface SunlightToolRepo {
    suspend fun getSunlightTool(
        userId: String,
        latitude: String,
        longitude: String,
        date: String,
        location: String
    ): ResponseState<SunlightToolData>

}