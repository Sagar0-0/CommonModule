package fit.asta.health.data.water.model.api

import fit.asta.health.data.water.model.network.BeverageRecentActivity
import fit.asta.health.data.water.model.network.NetBevQtyPut
import fit.asta.health.data.water.model.network.WaterToolData
import fit.asta.health.data.water.model.network.WaterToolDetailsData
import fit.asta.health.data.water.model.network.WaterToolResult
import fit.asta.health.network.data.Status
import fit.asta.health.common.utils.Response
import fit.asta.health.data.water.model.network.BeverageActivity
import fit.asta.health.data.water.model.network.Data
import fit.asta.health.data.water.model.network.WaterDetailsData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

//Health Tool - Water Endpoints
interface WaterApiService {

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
    suspend fun updateBeverageQty(@Body beverage: NetBevQtyPut): Response<BeverageActivity>

    @PUT("tools/water/put")
    suspend fun updateWaterTool(@Body waterToolData: WaterToolData): Response<Response.Status>
    /*
        @PUT("tools/water/beverage/add/put")
        suspend fun updateBeverage(@Body beverage: NetBeverage): Status

        @GET("tools/water/beverage/healthHisList/get/?")
        suspend fun getBeverageList(@Query("uid") userId: String): NetBeverageRes*/
}
