package fit.asta.health.tools.water.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.tools.water.model.network.NetBevQtyPut
import fit.asta.health.tools.water.model.network.WaterToolData
import fit.asta.health.tools.water.model.network.WaterToolResult

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