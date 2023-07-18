package fit.asta.health.tools.sunlight.model.api

import fit.asta.health.tools.sunlight.model.network.response.ResponseData

//Health Tool - Sunlight Endpoints
interface SunlightApi {
    suspend fun getSunlightTool(
        userId: String,
        latitude: String,
        longitude: String,
        date: String,
        location: String
    ): ResponseData

}