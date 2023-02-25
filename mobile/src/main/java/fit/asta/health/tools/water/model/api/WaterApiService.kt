package fit.asta.health.tools.water.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.tools.water.model.network.NetBevQtyPut
import fit.asta.health.tools.water.model.network.NetWaterToolRes
import retrofit2.http.*

//Health Tool - Water Endpoints
interface WaterApiService {

    @GET("tools/water/get/?")
    suspend fun getWaterTool(
        @Query("uid") userId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("loc") location: String,
        @Query("date") date: String
    ): NetWaterToolRes

    @POST("tools/water/beverage/quantity/post")
    suspend fun updateBeverageQty(@Body beverage: NetBevQtyPut): Status

    /*@PUT("tools/water/put")
    suspend fun updateWaterTool(@Body modifiedWaterTool: ModifiedWaterTool): Status

    @PUT("tools/water/beverage/add/put")
    suspend fun updateBeverage(@Body beverage: NetBeverage): Status

    @GET("tools/water/beverage/list/get/?")
    suspend fun getBeverageList(@Query("uid") userId: String): NetBeverageRes*/
}
