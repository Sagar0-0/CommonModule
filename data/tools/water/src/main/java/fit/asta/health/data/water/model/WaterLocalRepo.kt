package fit.asta.health.data.water.model

import fit.asta.health.data.water.db.WaterData

interface WaterLocalRepo {
        suspend fun getWaterData(date: Int): WaterData?
        suspend fun insert(waterData: WaterData)
        suspend fun updateAngle( date: Int, appliedAngleDistance: Float)
        suspend fun updateState(date: Int, start: Boolean)
}