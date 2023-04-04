package fit.asta.health.tools.walking.model.api

import fit.asta.health.tools.walking.model.network.request.*
import fit.asta.health.tools.walking.model.network.response.*
import retrofit2.http.*


//Health Tool - Walking Endpoints
interface WalkingService {


    @GET("tools/walking/get/")
    suspend fun getHomeData(
        @Query("uid") userId: String
    ): HomeData


    @PUT("tools/walking/put/")
    suspend fun putData( @Body putData: PutData):PutResponse

    @PUT("tools/walking/day/post/")
    suspend fun putDayData( @Body putDayData: PutDayData):PutResponse



}
