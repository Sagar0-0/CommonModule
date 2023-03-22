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

    override suspend fun updateTime(date: Int, time: Int) {
       stepsDataDao.updateTime(date, time)
    }

    override suspend fun updateSteps(date: Int, step: Int) {
       stepsDataDao.updateSteps(date,step)
    }


}