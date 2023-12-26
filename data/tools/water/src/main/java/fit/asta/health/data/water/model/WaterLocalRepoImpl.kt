package fit.asta.health.data.water.model

import fit.asta.health.data.water.db.WaterDao
import fit.asta.health.data.water.db.WaterData

class WaterLocalRepoImpl(private val waterDao: WaterDao) : WaterLocalRepo {
    override suspend fun getWaterData(date: Int): WaterData? {
        return waterDao.getData(date)
    }

    override suspend fun insert(waterData: WaterData) {
        waterDao.insert(waterData)
    }

    override suspend fun updateAngle(date: Int, appliedAngleDistance: Float) {
        waterDao.updateAngle(date, appliedAngleDistance)
    }

    override suspend fun updateState(date: Int, start: Boolean) {
        waterDao.updateState(date, start)
    }
}