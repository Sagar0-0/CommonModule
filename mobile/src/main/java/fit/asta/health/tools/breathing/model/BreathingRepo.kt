package fit.asta.health.tools.breathing.model

import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.network.data.ServerRes
import fit.asta.health.tools.breathing.model.network.AllExerciseData
import fit.asta.health.tools.breathing.model.network.CustomRatioData
import fit.asta.health.tools.breathing.model.network.NetGetRes
import fit.asta.health.tools.breathing.model.network.NetGetStart
import fit.asta.health.tools.breathing.model.network.request.CustomRatioPost
import fit.asta.health.tools.breathing.model.network.request.NetPost
import fit.asta.health.tools.breathing.model.network.request.NetPut
import kotlinx.coroutines.flow.Flow

interface BreathingRepo {
    suspend fun getBreathingTool(userId: String, date: String): Flow<NetworkResult<NetGetRes>>
    suspend fun getAllBreathingData(userId: String): Flow<NetworkResult<AllExerciseData>>
    suspend fun getStart(userId: String): Flow<NetworkResult<NetGetStart>>
    suspend fun putBreathingData(netPut: NetPut): NetworkResult<ServerRes>
    suspend fun postBreathingData(netPost: NetPost): NetworkResult<ServerRes>
    suspend fun postRatioData(customRatioData: CustomRatioData): NetworkResult<ServerRes>
    suspend fun deleteRatioData(ratioId: String): NetworkResult<ServerRes>
}