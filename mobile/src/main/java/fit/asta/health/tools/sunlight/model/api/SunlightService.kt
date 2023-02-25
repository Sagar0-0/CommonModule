package fit.asta.health.tools.sunlight.model.api

import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes
import retrofit2.http.*


//Health Tool - Sunlight Endpoints
interface SunlightService {

    @GET("tools/sunlight/get")
    suspend fun getSunlightTool(@Query("userId") userId: String): NetSunlightToolRes
}
