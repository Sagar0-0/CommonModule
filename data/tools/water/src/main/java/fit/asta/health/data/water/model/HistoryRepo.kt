package fit.asta.health.data.water.model

import dagger.hilt.android.scopes.ActivityScoped
import fit.asta.health.data.water.check.model.BevDataDetails
import fit.asta.health.data.water.check.model.ConsumptionHistory
import fit.asta.health.data.water.check.model.Goal
import fit.asta.health.data.water.check.model.History
import fit.asta.health.data.water.db.HistoryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScoped
class HistoryRepo @Inject constructor(val historyDao: HistoryDao) {
    fun getAllHistory(): Flow<List<History>> = historyDao.getAllHistory()
    fun getAllLocalBevHistory(): Flow<List<BevDataDetails>> = historyDao.getAllLocalBevHistory()
    fun getGoal(): Flow<List<Goal>> = historyDao.getGoal()
    fun getAllConsumptionHistory(date: String): Flow<List<ConsumptionHistory>> =
        historyDao.getAllConsumptionHistory(date)

    suspend fun insertRecentAdded(history: History) = historyDao.insertRecentAdded(history)
    suspend fun insertBevData(bevDetails: BevDataDetails) = historyDao.insertBevData(bevDetails)

    suspend fun insertGoal(goal: Goal) = historyDao.insertGoal(goal)
    suspend fun insertConsumptionData(bevData: ConsumptionHistory) =
        historyDao.insertConsumedData(bevData)
}