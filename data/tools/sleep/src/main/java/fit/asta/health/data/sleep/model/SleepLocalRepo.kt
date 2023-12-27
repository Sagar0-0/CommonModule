package fit.asta.health.data.sleep.model

import fit.asta.health.data.sleep.model.db.SleepData

interface SleepLocalRepo {

    suspend fun insert(data: SleepData)

    suspend fun getData(): List<SleepData>

    suspend fun deleteData()
}