package fit.asta.health.tools.water.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.tools.water.model.network.NetBevQtyPut
import fit.asta.health.tools.water.model.network.WaterToolData
import fit.asta.health.tools.water.model.network.WaterToolResult
import okhttp3.OkHttpClient


//Health Tool - Water Endpoints
class WaterRestApi(baseUrl: String, client: OkHttpClient) :
    WaterApi {

    private val apiService: WaterApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(WaterApiService::class.java)

    override suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        date: String
    ): WaterToolResult {
        return apiService.getWaterTool(userId, latitude, longitude, date, location)
    }

    override suspend fun updateBeverageQty(beverage: NetBevQtyPut): Status {
        return apiService.updateBeverageQty(beverage)
    }

    override suspend fun updateWaterTool(waterToolData: WaterToolData): Status {
        return apiService.updateWaterTool(waterToolData)
    }

    /*override suspend fun updateWaterTool(modifiedWaterTool: ModifiedWaterTool): Status {
        return apiService.updateWaterTool(modifiedWaterTool)
    }

    override suspend fun updateBeverage(beverage: NetBeverage): Status {
        return apiService.updateBeverage(beverage)
    }

    override suspend fun getBeverageList(userId: String): NetBeverageRes {
        return apiService.getBeverageList(userId)
    }*/
}