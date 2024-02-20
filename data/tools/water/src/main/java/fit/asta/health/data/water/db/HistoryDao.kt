package fit.asta.health.data.water.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import fit.asta.health.data.water.check.model.BevDataDetails
import fit.asta.health.data.water.check.model.ConsumptionHistory
import fit.asta.health.data.water.check.model.Goal
import fit.asta.health.data.water.check.model.History



@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentAdded(hist: History)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBevData(bev: BevDataDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsumedData(bevData: ConsumptionHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Query("SELECT * FROM History ORDER BY id DESC LIMIT 3")
    fun getAllHistory(): Flow<List<History>>

    @Query("SELECT * FROM BEVDETAILS ORDER BY id DESC LIMIT 1")
    fun getAllLocalBevHistory(): Flow<List<BevDataDetails>>

    @Query("SELECT * FROM ConsumptionHistory where date = :date LIMIT 1")
    fun getAllConsumptionHistory(date: String): Flow<List<ConsumptionHistory>>

    @Query("SELECT * FROM goal order by id LIMIT 1")
    fun getGoal(): Flow<List<Goal>>

//    @Query(SELECT goal FROM ConsumptionHistory )
}