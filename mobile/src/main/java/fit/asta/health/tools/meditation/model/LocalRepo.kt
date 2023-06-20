package fit.asta.health.tools.meditation.model

import fit.asta.health.tools.meditation.db.MeditationData



interface LocalRepo {
    suspend fun getWaterData(date: Int): MeditationData?
    suspend fun insert(meditationData: MeditationData)
    suspend fun updateAngle( date: Int, appliedAngleDistance: Float)
    suspend fun updateState(date: Int, start: Boolean)
    suspend fun updateTime(date: Int, time:Long)
}