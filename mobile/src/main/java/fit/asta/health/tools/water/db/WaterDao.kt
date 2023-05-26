package fit.asta.health.tools.water.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(waterData: WaterData)

    @Query("SELECT * FROM water_data WHERE date = :date LIMIT 1")
    suspend fun getData(date: Int): WaterData?

    @Query("UPDATE water_data SET appliedAngleDistance = :appliedAngleDistance WHERE date = :date")
    suspend fun updateAngle(date: Int, appliedAngleDistance: Float)

    @Query("UPDATE water_data SET start = :start WHERE date = :date")
    suspend fun updateState(date: Int, start: Boolean)
}