package fit.asta.health.tools.walking.model.api

import fit.asta.health.tools.walking.model.network.request.PostReq
import fit.asta.health.tools.walking.model.network.request.PutReq
import fit.asta.health.tools.walking.model.network.response.*
import java.util.Date

//Health Tool - Walking Endpoints
interface WalkingApi {



    suspend fun getStepsDataWithUidDate(userId: String,date: Double):StepsDataWithUidDate

    suspend fun getStepsDataWithUid(userId: String):StepsDataWithUid

    suspend fun putData(putReq: PutReq):PutResponse

    suspend fun postData(postReq: PostReq):PostResponse
}