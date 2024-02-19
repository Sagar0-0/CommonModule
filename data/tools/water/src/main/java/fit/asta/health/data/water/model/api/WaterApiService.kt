package fit.asta.health.data.water.model.api

import fit.asta.health.data.water.model.network.WaterToolDetailsData
import fit.asta.health.data.water.model.network.NetBevQtyPut
import fit.asta.health.data.water.model.network.WaterToolData
import fit.asta.health.data.water.model.network.WaterToolResult
import fit.asta.health.network.data.Status
import retrofit2.Response
import retrofit2.http.*

//Health Tool - Water Endpoints
interface WaterApiService {

    @GET("tools/water/get/?")
    suspend fun getWaterTool(
        @Query("uid") userId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("date") date: String,
        @Query("loc") location: String
    ): WaterToolResult

    @GET("tools/water/beverages/get/all")
    suspend fun getWaterData() : Response<WaterToolDetailsData>
    @POST("tools/water/beverage/quantity/post")
    suspend fun updateBeverageQty(@Body beverage: NetBevQtyPut): Status

    @PUT("tools/water/put")
    suspend fun updateWaterTool(@Body waterToolData: WaterToolData): Status
    /*
        @PUT("tools/water/beverage/add/put")
        suspend fun updateBeverage(@Body beverage: NetBeverage): Status

        @GET("tools/water/beverage/healthHisList/get/?")
        suspend fun getBeverageList(@Query("uid") userId: String): NetBeverageRes*/
}
