package fit.asta.health.data.water.repo

import dagger.hilt.android.scopes.ActivityScoped
import fit.asta.health.data.water.local.HistoryDao
import fit.asta.health.data.water.local.entity.BevDataDetails
import fit.asta.health.data.water.local.entity.BevQuantityConsumed
import fit.asta.health.data.water.local.entity.ConsumptionHistory
import fit.asta.health.data.water.local.entity.Goal
import fit.asta.health.data.water.local.entity.History
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class HistoryRepoImpl @Inject constructor(
    private val historyDao: HistoryDao
) : HistoryRepo  {
    override suspend fun getAllHistory(): Flow<List<History>> = historyDao.getAllHistory()
    override suspend fun getAllLocalBevHistory(): Flow<List<BevDataDetails>> = historyDao.getAllLocalBevHistory()
    override suspend fun getGoal(): Flow<List<Goal>> = historyDao.getGoal()
    override suspend fun getAllConsumptionHistory(date: String): Flow<List<ConsumptionHistory>> =
        historyDao.getAllConsumptionHistory(date)

    override suspend fun getUndoConsumedQty(bevName: String): Double = historyDao.getUndoQuantity(bevName)

    override suspend fun getConsumedBevList() = historyDao.getBevConsumptionList()
    override suspend fun undoConsumption(bevName: String) = historyDao.undoConsumption(bevName)

    override suspend fun insertRecentAdded(history: History) = historyDao.insertRecentAdded(history)
    override suspend fun insertBevData(bevDetails: BevDataDetails) = historyDao.insertBevData(bevDetails)

    override suspend fun insertGoal(goal: Goal) = historyDao.insertGoal(goal)

    override suspend fun insertBevQtyConsumed(bevQtyPut: BevQuantityConsumed) =
        historyDao.insertBevQtyConsumed(bevQtyPut)

    override suspend fun insertConsumptionData(bevData: ConsumptionHistory) =
        historyDao.insertConsumedData(bevData)
}