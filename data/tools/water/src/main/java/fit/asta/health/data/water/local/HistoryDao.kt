package fit.asta.health.data.water.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fit.asta.health.data.water.db.dbmodel.BevQuantityConsumed
import fit.asta.health.data.water.local.entity.BevDataDetails
import fit.asta.health.data.water.local.entity.ConsumptionHistory
import fit.asta.health.data.water.local.entity.Goal
import fit.asta.health.data.water.local.entity.History
import kotlinx.coroutines.flow.Flow

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

    @Insert
    suspend fun insertBevQtyConsumed(bevQtyPut: BevQuantityConsumed)

    @Query("SELECT * FROM History ORDER BY id DESC LIMIT 3")
    fun getAllHistory(): Flow<List<History>>

    @Query("SELECT * FROM BEVDETAILS ORDER BY id DESC LIMIT 1")
    fun getAllLocalBevHistory(): Flow<List<BevDataDetails>>

    @Query("SELECT * FROM ConsumptionHistory where date = :date LIMIT 1")
    fun getAllConsumptionHistory(date: String): Flow<List<ConsumptionHistory>>

    @Query("SELECT * FROM goal order by id LIMIT 1")
    fun getGoal(): Flow<List<Goal>>

    @Query("Select quantity from bevQuantity where name = :name AND " +
            "id = (Select MAX(id) from bevQuantity where name = :name)")
    fun getUndoQuantity(name : String) : Flow<Double>
    @Query("Delete from bevQuantity where name = :name AND " +
            "id = (Select MAX(id) from bevQuantity where name = :name)")
    suspend fun undoConsumption(name : String)
//    @Query(SELECT goal FROM ConsumptionHistory )
}