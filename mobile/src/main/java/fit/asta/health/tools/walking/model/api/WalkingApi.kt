package fit.asta.health.tools.walking.model.api

import fit.asta.health.tools.walking.model.network.request.PutData
import fit.asta.health.tools.walking.model.network.response.HomeData
import fit.asta.health.tools.walking.model.network.response.PutResponse

//Health Tool - Walking Endpoints
interface WalkingApi {


    suspend fun getHomeData(userId: String): HomeData

    suspend fun putData(putData: PutData):PutResponse
}