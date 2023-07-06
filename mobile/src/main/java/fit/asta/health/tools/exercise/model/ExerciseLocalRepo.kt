package fit.asta.health.tools.exercise.model

import fit.asta.health.tools.exercise.db.ExerciseData

interface ExerciseLocalRepo {
    suspend fun getExerciseData(date: Int): ExerciseData?
    suspend fun insert(exerciseData: ExerciseData)
    suspend fun updateAngleDance(date: Int, appliedAngleDistance: Float)
    suspend fun updateAngleYoga(date: Int, appliedAngleDistance: Float)
    suspend fun updateAngleWorkout(date: Int, appliedAngleDistance: Float)
    suspend fun updateAngleHiit(date: Int, appliedAngleDistance: Float)
    suspend fun updateStateDance(date: Int, start: Boolean)
    suspend fun updateStateYoga(date: Int, start: Boolean)
    suspend fun updateStateWorkout(date: Int, start: Boolean)
    suspend fun updateStateHiit(date: Int, start: Boolean)
    suspend fun updateTimeDance(date: Int, time: Long)
    suspend fun updateTimeYoga(date: Int, time: Long)
    suspend fun updateTimeWorkout(date: Int, time: Long)
    suspend fun updateTimeHiit(date: Int, time: Long)
}