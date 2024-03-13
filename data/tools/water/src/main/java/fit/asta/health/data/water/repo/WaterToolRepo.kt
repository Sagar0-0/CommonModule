package fit.asta.health.data.water.repo

import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.water.remote.model.BevQty
import fit.asta.health.data.water.remote.model.BeverageActivity
import fit.asta.health.data.water.remote.model.Data
import fit.asta.health.data.water.remote.model.WaterDetailsData
import fit.asta.health.data.water.remote.model.WaterToolData

interface WaterToolRepo {
    suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        date: Long
    ): ResponseState<Data>

    suspend fun updateBeverageQty(beverage: BevQty): ResponseState<BeverageActivity>
    suspend fun updateWaterTool(waterToolData: WaterToolData): ResponseState<Response.Status>
    suspend fun getWaterData(): ResponseState<List<WaterDetailsData>>
    /*
    suspend fun updateBeverage(beverage: NetBeverage): Flow<Status>
    suspend fun getBeverageList(userId: String): Flow<NetBeverageRes>
    */
}