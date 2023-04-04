package fit.asta.health.tools.walking.model

import fit.asta.health.tools.walking.db.StepsData
import fit.asta.health.tools.walking.db.StepsDataDao

class LocalRepoImpl(private val stepsDataDao: StepsDataDao):LocalRepo {
    override suspend fun getStepsData(date: Int): StepsData? {
        return stepsDataDao.getData(date)
    }

    override suspend fun insert(stepsData: StepsData) {
        stepsDataDao.insert(stepsData)
    }

    override suspend fun updateStatus(date: Int, status: String) {
        stepsDataDao.updateStatus(date, status)
    }

    override suspend fun updateStepsonRunning(date: Int, all_steps: Int) {
        stepsDataDao.updateStepsonRunning(date,all_steps)
    }

    override suspend fun updateTime(date: Int, time: Long) {
       stepsDataDao.updateTime(date, time)
    }

    override suspend fun updateSteps(date: Int, step: Int) {
       stepsDataDao.updateSteps(date,step)
    }

    override suspend fun updateRealTime(date: Int, time: Int) {
      stepsDataDao.updateRealTime(date, time)
    }

    override suspend fun updateTargetAndRecommend(
        date: Int,
        distanceRecommend: Double,
        durationRecommend: Int,
        distanceTarget: Double,
        durationTarget: Int
    ) {
       stepsDataDao.updateTargetAndRecommend(date, distanceRecommend, durationRecommend, distanceTarget, durationTarget)
    }

    override suspend fun updateIdForPutData(date: Int, id: String) {
        stepsDataDao.updateIdForPutData(date, id)
    }

    override suspend fun updateCaloriesAndWeightLoosed(
        date: Int,
        calories: Double,
        weightLoosed: Double
    ) {
        stepsDataDao.updateCaloriesAndWeightLoosed(date, calories, weightLoosed)
    }

    override suspend fun updateAppliedAngleDistance(date: Int, appliedAngleDistance: Float) {
       stepsDataDao.updateAppliedAngleDistance(date, appliedAngleDistance)
    }

    override suspend fun updateAppliedAngleDuration(date: Int, appliedAngleDuration: Float) {
        stepsDataDao.updateAppliedAngleDuration(date, appliedAngleDuration)
    }

    override suspend fun getAllStepsData(): List<StepsData> {
       return stepsDataDao.getAllStepsData()
    }

    override suspend fun deleteOldData(today: Int, yesterday: Int) {
       stepsDataDao.deleteOldData(today, yesterday)
    }


}