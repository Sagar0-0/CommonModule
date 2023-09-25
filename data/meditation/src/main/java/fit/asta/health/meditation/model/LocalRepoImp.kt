package fit.asta.health.meditation.model

import fit.asta.health.meditation.db.MeditationDao
import fit.asta.health.meditation.db.MeditationData

class LocalRepoImp(private val meditationDao: MeditationDao):LocalRepo {
    override suspend fun getWaterData(date: Int): MeditationData? {
        return meditationDao.getData(date)
    }

    override suspend fun insert(meditationData: MeditationData) {
        meditationDao.insert(meditationData)
    }

    override suspend fun updateAngle(date: Int, appliedAngleDistance: Float) {
       meditationDao.updateAngle(date, appliedAngleDistance)
    }

    override suspend fun updateState(date: Int, start: Boolean) {
     meditationDao.updateState(date, start)
    }

    override suspend fun updateTime(date: Int, time: Long) {
       meditationDao.updateTime(date, time)
    }
}