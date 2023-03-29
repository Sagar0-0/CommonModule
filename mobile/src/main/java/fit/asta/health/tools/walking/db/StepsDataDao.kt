package fit.asta.health.tools.walking.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface StepsDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stepsData: StepsData)

    @Query("SELECT * FROM steps_data WHERE date = :date LIMIT 1")
    suspend fun getData(date: Int): StepsData?

    @Query("UPDATE steps_data SET status = :status WHERE date = :date")
    suspend fun updateStatus(date: Int, status: String)

    @Query("UPDATE steps_data SET all_steps = :all_steps WHERE date = :date")
    suspend fun updateStepsonRunning(date: Int, all_steps: Int)

    @Query("UPDATE steps_data SET initial_steps = :initial_steps WHERE date = :date")
    suspend fun updateSteps(date: Int, initial_steps: Int)

    @Query("UPDATE steps_data SET time = :time WHERE date = :date")
    suspend fun updateTime(date: Int, time: Long)

    @Query("UPDATE steps_data SET realtime = :realtime WHERE date = :date")
    suspend fun updateRealTime(date: Int, realtime: Int)

    @Query("UPDATE steps_data SET distanceRecommend = :distanceRecommend,durationRecommend = :durationRecommend,distanceTarget = :distanceTarget,durationTarget = :durationTarget WHERE date = :date")
    suspend fun updateTargetAndRecommend(
        date: Int,
        distanceRecommend: Double,
        durationRecommend: Int,
        distanceTarget: Double,
        durationTarget: Int
    )

    @Query("UPDATE steps_data SET id = :id WHERE date = :date")
    suspend fun updateIdForPutData(date: Int, id: String)

    @Query("UPDATE steps_data SET calories = :calories,weightLoosed = :weightLoosed WHERE date = :date")
    suspend fun updateCaloriesAndWeightLoosed(date: Int, calories: Double, weightLoosed: Double)


}