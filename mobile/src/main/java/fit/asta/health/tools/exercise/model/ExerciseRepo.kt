package fit.asta.health.tools.exercise.model

import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.network.data.ServerRes
import fit.asta.health.tools.exercise.model.network.NetGetRes
import fit.asta.health.tools.exercise.model.network.NetGetStart
import fit.asta.health.tools.exercise.model.network.NetPost
import fit.asta.health.tools.exercise.model.network.NetPutRes
import kotlinx.coroutines.flow.Flow

interface ExerciseRepo {
    suspend fun getExerciseTool(uid: String, date: String,name:String): Flow<NetworkResult<NetGetRes>>

    suspend fun getStart(uid: String):Flow<NetworkResult<NetGetStart>>

    suspend fun putExerciseData(netPutRes: NetPutRes, name:String): NetworkResult<ServerRes>

    suspend fun postExerciseData(netPost: NetPost, name:String): NetworkResult<ServerRes>
}