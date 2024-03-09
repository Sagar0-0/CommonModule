package fit.asta.health.data.water.model.api

import fit.asta.health.data.water.model.network.NetBevQtyPut
import fit.asta.health.data.water.model.network.WaterToolData
import fit.asta.health.data.water.model.network.WaterToolDetailsData
import fit.asta.health.data.water.model.network.WaterToolResult
import fit.asta.health.network.data.Status
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import retrofit2.Response


//Health Tool - Water Endpoints
class WaterRestApi(client: OkHttpClient) :
    WaterApi {

    private val apiService: WaterApiService = NetworkUtil
        .getRetrofit(client)
        .create(WaterApiService::class.java)

    override suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        date: Long
    ): WaterToolResult {
        return apiService.getWaterTool(userId, latitude, longitude, date, location)
    }

    override suspend fun updateBeverageQty(beverage: NetBevQtyPut): Status {
        return apiService.updateBeverageQty(beverage)
    }

    override suspend fun updateWaterTool(waterToolData: WaterToolData): Status {
        return apiService.updateWaterTool(waterToolData)
    }

    override suspend fun getWaterData(): Response<WaterToolDetailsData> {
        return apiService.getWaterData()
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