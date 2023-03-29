package fit.asta.health.tools.walking.model

import fit.asta.health.tools.walking.db.StepsData

interface LocalRepo {
    suspend fun getStepsData(date: Int):StepsData?
    suspend fun insert(stepsData: StepsData)
    suspend fun updateStatus(date: Int, status: String)
    suspend fun updateStepsonRunning(date: Int,all_steps:Int)
    suspend fun updateTime(date: Int,time:Long)
    suspend fun updateSteps(date: Int,step:Int)
    suspend fun updateRealTime(date: Int,time: Int)
    suspend fun updateTargetAndRecommend(
        date: Int,
        distanceRecommend: Double,
        durationRecommend: Int,
        distanceTarget: Double,
        durationTarget: Int
    )
    suspend fun updateIdForPutData(date: Int, id:String)
    suspend fun updateCaloriesAndWeightLoosed(date: Int, calories: Double, weightLoosed: Double)
}