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


interface HistoryRepo //TODO: Use the HistoryRepoImpl and implement the interface pattern here
{
    //TODO: Use SUSPEND FUNCTIONS
    suspend fun getAllHistory(): Flow<List<History>>
    suspend fun getAllLocalBevHistory(): Flow<List<BevDataDetails>>
    suspend fun getGoal(): Flow<List<Goal>>
    suspend fun getAllConsumptionHistory(date: String): Flow<List<ConsumptionHistory>>

    suspend fun getUndoConsumedQty(bevName: String): Double

    suspend fun getConsumedBevList() : Flow<List<BevQuantityConsumed>>
    suspend fun undoConsumption(bevName: String) : Int

    suspend fun insertRecentAdded(history: History)
    suspend fun insertBevData(bevDetails: BevDataDetails)

    suspend fun insertGoal(goal: Goal)

    suspend fun insertBevQtyConsumed(bevQtyPut: BevQuantityConsumed)
    suspend fun insertConsumptionData(bevData: ConsumptionHistory)
}