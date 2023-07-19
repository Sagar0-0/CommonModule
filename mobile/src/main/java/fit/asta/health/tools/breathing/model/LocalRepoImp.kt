package fit.asta.health.tools.breathing.model

import fit.asta.health.tools.breathing.db.BreathingDao
import fit.asta.health.tools.breathing.db.BreathingData

class LocalRepoImp(private val breathingDao: BreathingDao):LocalRepo {
    override suspend fun getBreathingData(date: Int): BreathingData? {
       return breathingDao.getData(date)
    }

    override suspend fun insert(breathingData: BreathingData) {
        breathingDao.insert(breathingData)
    }

    override suspend fun updateAngle(date: Int, appliedAngleDistance: Float) {
       breathingDao.updateAngle(date, appliedAngleDistance)
    }

    override suspend fun updateState(date: Int, start: Boolean) {
       breathingDao.updateState(date, start)
    }

    override suspend fun updateTime(date: Int, time: Long) {
        breathingDao.updateTime(date, time)
    }
}