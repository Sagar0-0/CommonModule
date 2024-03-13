package fit.asta.health.data.water.model.api

import fit.asta.health.data.water.model.network.BeverageRecentActivity
import fit.asta.health.data.water.model.network.NetBevQtyPut
import fit.asta.health.data.water.model.network.WaterToolData
import fit.asta.health.data.water.model.network.WaterToolDetailsData
import fit.asta.health.data.water.model.network.WaterToolResult
import fit.asta.health.common.utils.Response
import fit.asta.health.data.water.model.network.BeverageActivity
import fit.asta.health.data.water.model.network.Data
import fit.asta.health.data.water.model.network.WaterDetailsData
import fit.asta.health.network.data.Status


//Health Tool - Water Endpoints
interface WaterApi {

    suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        date: Long
    ): Response<Data>

    suspend fun updateBeverageQty(beverage: NetBevQtyPut): Response<BeverageActivity>
    suspend fun updateWaterTool(waterToolData: WaterToolData): Response<Response.Status>

    suspend fun getWaterData(): Response<List<WaterDetailsData>>
    /*
    suspend fun updateBeverage(beverage: NetBeverage): Status
    suspend fun getBeverageList(userId: String): NetBeverageRes*/
}