package fit.asta.health.data.water.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.water.remote.model.BevQty
import fit.asta.health.data.water.remote.model.BeverageActivity
import fit.asta.health.data.water.remote.model.Data
import fit.asta.health.data.water.remote.model.WaterDetailsData
import fit.asta.health.data.water.remote.model.WaterToolData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

//Health Tool - Water Endpoints
interface WaterApi {

    @GET("tools/water/get/?")
    suspend fun getWaterTool(
        @Query("uid") userId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("date") date: Long,
        @Query("loc") location: String
    ): Response<Data>

    @GET("tools/water/beverages/get/all")
    suspend fun getWaterData(): Response<List<WaterDetailsData>>

    @POST("tools/water/beverage/quantity/post")
    suspend fun updateBeverageQty(@Body beverage: BevQty): Response<BeverageActivity>

    @PUT("tools/water/put")
    suspend fun updateWaterTool(@Body waterToolData: WaterToolData): Response<Response.Status>
    /*
    @PUT("tools/water/beverage/add/put")
    suspend fun updateBeverage(@Body beverage: NetBeverage): Status

    @GET("tools/water/beverage/healthHisList/get/?")
    suspend fun getBeverageList(@Query("uid") userId: String): NetBeverageRes
    */
}