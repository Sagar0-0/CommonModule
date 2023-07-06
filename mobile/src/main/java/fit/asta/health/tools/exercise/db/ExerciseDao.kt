package fit.asta.health.tools.exercise.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: ExerciseData)

    @Query("SELECT * FROM exercise_data WHERE date = :date LIMIT 1")
    suspend fun getData(date: Int): ExerciseData?

    @Query("UPDATE exercise_data SET appliedAngleDistanceDance = :appliedAngleDistance WHERE date = :date")
    suspend fun updateAngleDance(date: Int, appliedAngleDistance: Float)

    @Query("UPDATE exercise_data SET appliedAngleDistanceYoga = :appliedAngleDistance WHERE date = :date")
    suspend fun updateAngleYoga(date: Int, appliedAngleDistance: Float)

    @Query("UPDATE exercise_data SET appliedAngleDistanceWorkout = :appliedAngleDistance WHERE date = :date")
    suspend fun updateAngleWorkout(date: Int, appliedAngleDistance: Float)

    @Query("UPDATE exercise_data SET appliedAngleDistanceHiit = :appliedAngleDistance WHERE date = :date")
    suspend fun updateAngleHiit(date: Int, appliedAngleDistance: Float)

    @Query("UPDATE exercise_data SET startDance = :start WHERE date = :date")
    suspend fun updateStateDance(date: Int, start: Boolean)

    @Query("UPDATE exercise_data SET startYoga = :start WHERE date = :date")
    suspend fun updateStateYoga(date: Int, start: Boolean)

    @Query("UPDATE exercise_data SET startWorkout = :start WHERE date = :date")
    suspend fun updateStateWorkout(date: Int, start: Boolean)

    @Query("UPDATE exercise_data SET startHiit = :start WHERE date = :date")
    suspend fun updateStateHiit(date: Int, start: Boolean)

    @Query("UPDATE exercise_data SET timeDance = :time WHERE date = :date")
    suspend fun updateTimeDance(date: Int, time: Long)

    @Query("UPDATE exercise_data SET timeYoga = :time WHERE date = :date")
    suspend fun updateTimeYoga(date: Int, time: Long)

    @Query("UPDATE exercise_data SET timeWorkout = :time WHERE date = :date")
    suspend fun updateTimeWorkout(date: Int, time: Long)

    @Query("UPDATE exercise_data SET timeHiit = :time WHERE date = :date")
    suspend fun updateTimeHiit(date: Int, time: Long)
}