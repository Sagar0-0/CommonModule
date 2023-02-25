package fit.asta.health.tools.sunlight.model.api

import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes

//Health Tool - Sunlight Endpoints
interface SunlightApi {

    suspend fun getSunlightTool(userId: String): NetSunlightToolRes
}