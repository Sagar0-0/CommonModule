package fit.asta.health.tools.walking.model.api

import fit.asta.health.tools.walking.model.network.response.NetWalkingToolRes

//Health Tool - Walking Endpoints
interface WalkingApi {

    suspend fun getWalkingTool(userId: String): NetWalkingToolRes
}