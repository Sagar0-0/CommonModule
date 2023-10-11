package fit.asta.health.tools.breathing.model

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.network.data.ServerRes
import fit.asta.health.tools.breathing.model.network.AllExerciseData
import fit.asta.health.tools.breathing.model.network.CustomRatioData
import fit.asta.health.tools.breathing.model.network.NetGetRes
import fit.asta.health.tools.breathing.model.network.NetGetStart
import fit.asta.health.tools.breathing.model.network.request.NetPost
import fit.asta.health.tools.breathing.model.network.request.NetPut

interface BreathingRepo {
    suspend fun getBreathingTool(userId: String, date: String): ResponseState<NetGetRes>
    suspend fun getAllBreathingData(userId: String): ResponseState<AllExerciseData>
    suspend fun getStart(userId: String): ResponseState<NetGetStart>
    suspend fun putBreathingData(netPut: NetPut): ResponseState<ServerRes>
    suspend fun postBreathingData(netPost: NetPost): ResponseState<ServerRes>
    suspend fun postRatioData(customRatioData: CustomRatioData): ResponseState<ServerRes>
    suspend fun deleteRatioData(ratioId: String): ResponseState<ServerRes>
    suspend fun getSheetData(code: String): ResponseState<List<NetSheetData>>
}