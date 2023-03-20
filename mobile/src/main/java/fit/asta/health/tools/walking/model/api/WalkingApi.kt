package fit.asta.health.tools.walking.model.api

import fit.asta.health.tools.walking.model.network.response.HomeData

//Health Tool - Walking Endpoints
interface WalkingApi {


    suspend fun getHomeData(userId: String): HomeData

}