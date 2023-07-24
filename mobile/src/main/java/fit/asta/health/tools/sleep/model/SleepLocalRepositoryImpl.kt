package fit.asta.health.tools.sleep.model

import fit.asta.health.tools.sleep.model.db.SleepDao
import fit.asta.health.tools.sleep.model.db.SleepData

class SleepLocalRepositoryImpl(
    private val sleepDao: SleepDao
) : SleepLocalRepo {

    override suspend fun insert(data: SleepData) {
        sleepDao.insert(data)
    }

    override suspend fun getData(): List<SleepData> {
        return sleepDao.getData()
    }

    override suspend fun deleteData() {
        sleepDao.deleteData()
    }
}