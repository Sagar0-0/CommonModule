package fit.asta.health.tools.water.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.tools.water.model.network.NetBevQtyPut
import fit.asta.health.tools.water.model.network.NetWaterTargetPut
import fit.asta.health.tools.water.model.network.NetWaterToolRes

//Health Tool - Water Endpoints
interface WaterApi {

    suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        date: String
    ): NetWaterToolRes

    suspend fun updateBeverageQty(beverage: NetBevQtyPut): Status
    suspend fun updateWaterTool(netWaterTargetPut: NetWaterTargetPut): Status
    /*
    suspend fun updateBeverage(beverage: NetBeverage): Status
    suspend fun getBeverageList(userId: String): NetBeverageRes*/
}