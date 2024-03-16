package fit.asta.health.data.water.repo

import fit.asta.health.data.water.local.WaterData

interface WaterLocalRepo {
    suspend fun getWaterData(date: Int): WaterData?
    suspend fun insert(waterData: WaterData)
    suspend fun updateAngle(date: Int, appliedAngleDistance: Float)
    suspend fun updateState(date: Int, start: Boolean)
}