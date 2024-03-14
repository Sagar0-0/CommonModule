package fit.asta.health.data.water.repo

import dagger.hilt.android.scopes.ActivityScoped
import fit.asta.health.data.water.local.HistoryDao
import fit.asta.health.data.water.local.entity.BevDataDetails
import fit.asta.health.data.water.local.entity.ConsumptionHistory
import fit.asta.health.data.water.local.entity.Goal
import fit.asta.health.data.water.local.entity.History
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScoped
class HistoryRepo @Inject constructor(private val historyDao: HistoryDao) {
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