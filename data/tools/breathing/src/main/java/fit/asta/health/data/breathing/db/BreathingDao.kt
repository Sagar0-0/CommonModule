package fit.asta.health.data.breathing.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BreathingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: BreathingData)

    @Query("SELECT * FROM breathing_data WHERE date = :date LIMIT 1")
    suspend fun getData(date: Int): BreathingData?

    @Query("UPDATE breathing_data SET appliedAngleDistance = :appliedAngleDistance WHERE date = :date")
    suspend fun updateAngle(date: Int, appliedAngleDistance: Float)

    @Query("UPDATE breathing_data SET start = :start WHERE date = :date")
    suspend fun updateState(date: Int, start: Boolean)

    @Query("UPDATE breathing_data SET time = :time WHERE date = :date")
    suspend fun updateTime(date: Int, time:Long)
}