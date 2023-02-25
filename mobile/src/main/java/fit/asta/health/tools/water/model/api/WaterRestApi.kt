package fit.asta.health.tools.water.model.api

import android.util.Log
import fit.asta.health.network.data.Status
import fit.asta.health.tools.water.model.network.NetBevQtyPut
import fit.asta.health.tools.water.model.network.NetWaterToolRes
import fit.asta.health.utils.NetworkUtil
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
        startDate: String,
        endDate: String
    ): NetWaterToolRes {

        val l = apiService.getWaterTool(userId, latitude, longitude, location, startDate, endDate)
        Log.i("RestApiline 140", l.waterTool.toString())
        return l
    }

    override suspend fun updateBeverageQty(beverage: NetBevQtyPut): Status {
        return apiService.updateBeverageQty(beverage)
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