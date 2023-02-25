package fit.asta.health.tools.walking.model.api

import fit.asta.health.tools.walking.model.network.response.NetWalkingToolRes
import retrofit2.http.*


//Health Tool - Walking Endpoints
interface WalkingService {

    @GET("tools/walking/get")
    suspend fun getWalkingTool(@Query("userId") userId: String): NetWalkingToolRes
}
