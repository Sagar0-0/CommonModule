package fit.asta.health.tools.sleep.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SleepingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: SleepingLocalData)

    @Query("SELECT * FROM sleeping_data")
    suspend fun getAllData(): List<SleepingLocalData>

    @Query("DELETE FROM sleeping_data")
    suspend fun deleteAllData()
}