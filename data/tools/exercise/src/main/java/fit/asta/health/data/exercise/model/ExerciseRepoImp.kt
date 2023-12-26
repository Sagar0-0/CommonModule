package fit.asta.health.data.exercise.model

import fit.asta.health.data.exercise.model.api.ExerciseApi
import fit.asta.health.data.exercise.model.network.NetGetRes
import fit.asta.health.data.exercise.model.network.NetGetStart
import fit.asta.health.data.exercise.model.network.NetPost
import fit.asta.health.data.exercise.model.network.NetPutRes
import fit.asta.health.network.data.ServerRes
import fit.asta.health.network.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ExerciseRepoImp(val api:ExerciseApi):ExerciseRepo {
    override suspend fun getExerciseTool(
        uid: String,
        date: String,
        name: String
    ): Flow<NetworkResult<NetGetRes>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = api.getExerciseTool(uid, date,name)
            if (result.status.msg == "Successful") emit(NetworkResult.Success(result))
            else emit(NetworkResult.Error(message = result.status.msg))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }
    }

    override suspend fun getStart(uid: String,name: String): Flow<NetworkResult<NetGetStart>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = api.getStart(uid,name)
            if (result.status.msg == "Successful") emit(NetworkResult.Success(result))
            else emit(NetworkResult.Error(message = result.status.msg))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }
    }

    override suspend fun putExerciseData(
        netPutRes: NetPutRes,
        name: String
    ): NetworkResult<ServerRes> {
        return try {
            NetworkResult.Loading<ServerRes>()
            val result = api.putExerciseData(netPutRes, name)
            if (result.msg == "Successful") NetworkResult.Success(result)
            else NetworkResult.Error(message = result.msg)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }

    override suspend fun postExerciseData(
        netPost: NetPost,
        name: String
    ): NetworkResult<ServerRes> {
        return try {
            NetworkResult.Loading<ServerRes>()
            val result = api.postExerciseData(netPost, name)
            if (result.msg == "Successful") NetworkResult.Success(result)
            else NetworkResult.Error(message = result.msg)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }
}