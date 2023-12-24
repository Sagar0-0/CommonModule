package fit.asta.health.data.breathing.model

import fit.asta.health.data.breathing.db.BreathingData

interface LocalRepo {
    suspend fun getBreathingData(date: Int): BreathingData?
    suspend fun insert(breathingData: BreathingData)
    suspend fun updateAngle( date: Int, appliedAngleDistance: Float)
    suspend fun updateState(date: Int, start: Boolean)
    suspend fun updateTime(date: Int, time:Long)
}