package fit.asta.health.data.water.model

import fit.asta.health.data.water.model.domain.WaterTool
import fit.asta.health.data.water.model.network.BeverageActivity
import fit.asta.health.data.water.model.network.NetBevQtyPut
import fit.asta.health.data.water.model.network.WaterDetailsData
import fit.asta.health.data.water.model.network.WaterToolData
import fit.asta.health.network.data.Status
import kotlinx.coroutines.flow.Flow


interface WaterToolRepo {
    suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        date: Long
    ): Flow<WaterTool>


    suspend fun updateBeverageQty(beverage: NetBevQtyPut): Flow<BeverageActivity>
    suspend fun updateWaterTool(waterToolData: WaterToolData): Flow<Status>

    suspend fun getWaterData(): Flow<List<WaterDetailsData>>
    /*
    suspend fun updateBeverage(beverage: NetBeverage): Flow<Status>
    suspend fun getBeverageList(userId: String): Flow<NetBeverageRes>*/
}