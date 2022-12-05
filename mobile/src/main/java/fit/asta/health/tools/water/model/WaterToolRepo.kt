package fit.asta.health.tools.water.model

import fit.asta.health.network.data.Status
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.ModifiedWaterTool
import fit.asta.health.tools.water.model.network.NetBeverage
import fit.asta.health.tools.water.model.network.NetBeverageRes
import fit.asta.health.tools.water.model.network.NetWaterTool
import kotlinx.coroutines.flow.Flow


interface WaterToolRepo {
    suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String
    ): Flow<NetWaterTool>

    suspend fun updateWaterTool(modifiedWaterTool: ModifiedWaterTool): Flow<Status>

    suspend fun updateBeverage(beverage: NetBeverage): Flow<Status>
    suspend fun updateBeverageQty(beverage: NetBeverage): Flow<Status>
    suspend fun getBeverageList(userId: String): Flow<NetBeverageRes>
}