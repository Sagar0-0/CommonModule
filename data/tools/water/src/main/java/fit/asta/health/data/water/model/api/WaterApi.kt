package fit.asta.health.data.water.model.api

import fit.asta.health.data.water.model.network.NetBevQtyPut
import fit.asta.health.data.water.model.network.WaterToolData
import fit.asta.health.data.water.model.network.WaterToolResult
import fit.asta.health.network.data.Status

//Health Tool - Water Endpoints
interface WaterApi {

    suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        date: String
    ): WaterToolResult

    suspend fun updateBeverageQty(beverage: NetBevQtyPut): Status
    suspend fun updateWaterTool(waterToolData: WaterToolData): Status
    /*
    suspend fun updateBeverage(beverage: NetBeverage): Status
    suspend fun getBeverageList(userId: String): NetBeverageRes*/
}