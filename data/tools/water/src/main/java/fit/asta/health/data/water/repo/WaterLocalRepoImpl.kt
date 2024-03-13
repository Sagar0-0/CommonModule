package fit.asta.health.data.water.repo

import fit.asta.health.data.water.local.WaterDao
import fit.asta.health.data.water.local.WaterData

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