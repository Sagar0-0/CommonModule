package fit.asta.health.data.exercise.model

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.exercise.model.network.ExerciseData
import fit.asta.health.data.exercise.model.network.NetGetStart
import fit.asta.health.data.exercise.model.network.NetPost
import fit.asta.health.data.exercise.model.network.NetPutRes
import fit.asta.health.network.data.ServerRes
import fit.asta.health.network.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ExerciseRepo {
    suspend fun getExerciseTool(
        uid: String,
        date: Long,
        name: String
    ): ResponseState<ExerciseData>

    suspend fun getStart(uid: String, name: String): Flow<NetworkResult<NetGetStart>>

    suspend fun putExerciseData(netPutRes: NetPutRes, name: String): NetworkResult<ServerRes>

    suspend fun postExerciseData(netPost: NetPost, name: String): NetworkResult<ServerRes>
}