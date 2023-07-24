package fit.asta.health.tools.sleep.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SleepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: SleepData)

    @Query("SELECT * FROM sleep_data")
    suspend fun getData(): List<SleepData>

    @Query("DELETE FROM sleep_data")
    suspend fun deleteData()
}