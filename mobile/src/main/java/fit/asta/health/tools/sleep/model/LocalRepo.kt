package fit.asta.health.tools.sleep.model

import fit.asta.health.tools.sleep.model.db.SleepingLocalData

interface LocalRepo {

    suspend fun getUserId(): List<SleepingLocalData>

    suspend fun insertUserId(sleepingLocalData: SleepingLocalData)

    suspend fun deleteAllData()
}