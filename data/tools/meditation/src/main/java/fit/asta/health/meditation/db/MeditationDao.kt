package fit.asta.health.meditation.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MeditationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: MeditationData)

    @Query("SELECT * FROM meditation_data WHERE date = :date LIMIT 1")
    suspend fun getData(date: Int): MeditationData?

    @Query("UPDATE meditation_data SET appliedAngleDistance = :appliedAngleDistance WHERE date = :date")
    suspend fun updateAngle(date: Int, appliedAngleDistance: Float)

    @Query("UPDATE meditation_data SET start = :start WHERE date = :date")
    suspend fun updateState(date: Int, start: Boolean)

    @Query("UPDATE meditation_data SET time = :time WHERE date = :date")
    suspend fun updateTime(date: Int, time:Long)
}