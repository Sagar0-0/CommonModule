package fit.asta.health.tools.sleep.model

import fit.asta.health.tools.sleep.model.db.SleepingDao
import fit.asta.health.tools.sleep.model.db.SleepingLocalData

class LocalRepoImpl(private val sleepingDao: SleepingDao) : LocalRepo {

    override suspend fun getUserId(): List<SleepingLocalData> {
        return sleepingDao.getAllData()
    }

    override suspend fun insertUserId(sleepingLocalData: SleepingLocalData) {
        sleepingDao.insert(sleepingLocalData)
    }

    override suspend fun deleteAllData() {
        sleepingDao.deleteAllData()
    }
}