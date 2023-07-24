package fit.asta.health.tools.sleep.model

import fit.asta.health.tools.sleep.model.db.SleepData

interface SleepLocalRepo {

    suspend fun insert(data: SleepData)

    suspend fun getData(): List<SleepData>

    suspend fun deleteData()
}