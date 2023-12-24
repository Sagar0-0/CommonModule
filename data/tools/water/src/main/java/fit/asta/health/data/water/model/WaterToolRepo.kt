package fit.asta.health.data.water.model

import fit.asta.health.data.water.model.domain.WaterTool
import fit.asta.health.data.water.model.network.NetBevQtyPut
import fit.asta.health.data.water.model.network.WaterToolData
import fit.asta.health.network.data.Status
import kotlinx.coroutines.flow.Flow


interface WaterToolRepo {
    suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        date: String
    ): Flow<WaterTool>


    suspend fun updateBeverageQty(beverage: NetBevQtyPut): Flow<Status>
    suspend fun updateWaterTool(waterToolData: WaterToolData): Flow<Status>
    /*
    suspend fun updateBeverage(beverage: NetBeverage): Flow<Status>
    suspend fun getBeverageList(userId: String): Flow<NetBeverageRes>*/
}