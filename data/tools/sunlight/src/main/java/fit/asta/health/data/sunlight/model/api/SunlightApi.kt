package fit.asta.health.data.sunlight.model.api

import fit.asta.health.common.utils.Response
import fit.asta.health.data.sunlight.model.network.response.SunlightToolData
import retrofit2.http.*


//Health Tool - Sunlight Endpoints
interface SunlightApi {

    @GET("tools/sunlight/get?")
    suspend fun getSunlightTool(
        @Query("userId") userId: String = "6309a9379af54f142c65fbfe",
        @Query("lat") latitude: String = "",
        @Query("lon") longitude: String = "",
        @Query("date") date: String = "",
        @Query("loc") location: String = ""
    ): Response<SunlightToolData>
}
