package fit.asta.health.tools.water.model

import fit.asta.health.network.data.Status
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.NetBevQtyPut
import fit.asta.health.tools.water.model.network.NetWaterTargetPut
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
    suspend fun updateWaterTool(netWaterTargetPut: NetWaterTargetPut): Flow<Status>
    /*
    suspend fun updateBeverage(beverage: NetBeverage): Flow<Status>
    suspend fun getBeverageList(userId: String): Flow<NetBeverageRes>*/
}