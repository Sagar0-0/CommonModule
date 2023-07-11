package fit.asta.health.tools.breathing.model.api

import fit.asta.health.network.data.ServerRes
import fit.asta.health.tools.breathing.model.network.AllExerciseData
import fit.asta.health.tools.breathing.model.network.NetGetRes
import fit.asta.health.tools.breathing.model.network.NetGetStart
import fit.asta.health.tools.breathing.model.network.request.CustomRatioPost
import fit.asta.health.tools.breathing.model.network.request.NetPost
import fit.asta.health.tools.breathing.model.network.request.NetPut

interface BreathingApi {
    suspend fun getBreathingTool(userId: String, date: String): NetGetRes
    suspend fun getAllBreathingData(userId: String): AllExerciseData
    suspend fun getStart(userId: String): NetGetStart
    suspend fun putBreathingData(netPut: NetPut): ServerRes
    suspend fun postBreathingData(netPost: NetPost): ServerRes
    suspend fun postRatioData(customRatioPost: CustomRatioPost): ServerRes
    suspend fun deleteRatioData(ratioId: String): ServerRes
    suspend fun postAdminData(customRatioPost: CustomRatioPost): ServerRes
    suspend fun deleteAdminData(exerciseId: String): ServerRes
}