package fit.asta.health.data.exercise.model

import fit.asta.health.data.exercise.db.ExerciseDao
import fit.asta.health.data.exercise.db.ExerciseData

class ExerciseLocalRepoImp(private val dao: ExerciseDao):ExerciseLocalRepo {
    override suspend fun getExerciseData(date: Int): ExerciseData? {
       return dao.getData(date)
    }

    override suspend fun insert(exerciseData: ExerciseData) {
        dao.insert(exerciseData)
    }

    override suspend fun updateAngleDance(date: Int, appliedAngleDistance: Float) {
        dao.updateAngleDance(date, appliedAngleDistance)
    }

    override suspend fun updateAngleYoga(date: Int, appliedAngleDistance: Float) {
        dao.updateAngleYoga(date, appliedAngleDistance)
    }

    override suspend fun updateAngleWorkout(date: Int, appliedAngleDistance: Float) {
       dao.updateAngleWorkout(date, appliedAngleDistance)
    }

    override suspend fun updateAngleHiit(date: Int, appliedAngleDistance: Float) {
        dao.updateAngleHiit(date, appliedAngleDistance)
    }

    override suspend fun updateStateDance(date: Int, start: Boolean) {
        dao.updateStateDance(date, start)
    }

    override suspend fun updateStateYoga(date: Int, start: Boolean) {
        dao.updateStateYoga(date, start)
    }

    override suspend fun updateStateWorkout(date: Int, start: Boolean) {
       dao.updateStateWorkout(date, start)
    }

    override suspend fun updateStateHiit(date: Int, start: Boolean) {
       dao.updateStateHiit(date, start)
    }

    override suspend fun updateTimeDance(date: Int, time: Long) {
       dao.updateTimeDance(date, time)
    }

    override suspend fun updateTimeYoga(date: Int, time: Long) {
        dao.updateTimeYoga(date, time)
    }

    override suspend fun updateTimeWorkout(date: Int, time: Long) {
        dao.updateTimeWorkout(date, time)
    }

    override suspend fun updateTimeHiit(date: Int, time: Long) {
        dao.updateTimeHiit(date,time)
    }
}