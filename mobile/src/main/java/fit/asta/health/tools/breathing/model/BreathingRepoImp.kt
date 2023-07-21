package fit.asta.health.tools.breathing.model

import android.util.Log
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.network.data.ServerRes
import fit.asta.health.tools.breathing.model.api.BreathingApi
import fit.asta.health.tools.breathing.model.network.AllExerciseData
import fit.asta.health.tools.breathing.model.network.CustomRatioData
import fit.asta.health.tools.breathing.model.network.NetGetRes
import fit.asta.health.tools.breathing.model.network.NetGetStart
import fit.asta.health.tools.breathing.model.network.request.CustomRatioPost
import fit.asta.health.tools.breathing.model.network.request.NetPost
import fit.asta.health.tools.breathing.model.network.request.NetPut
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class BreathingRepoImp(val api: BreathingApi):BreathingRepo {
    override suspend fun getBreathingTool(
        userId: String,
        date: String
    ): Flow<NetworkResult<NetGetRes>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = api.getBreathingTool(userId, date)
            if (result.status.msg == "Successful") emit(NetworkResult.Success(result))
            else emit(NetworkResult.Error(message = result.status.msg))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }
    }

    override suspend fun getAllBreathingData(userId: String): Flow<NetworkResult<AllExerciseData>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = api.getAllBreathingData(userId)
            if (result.status.msg == "Successful") emit(NetworkResult.Success(result))
            else emit(NetworkResult.Error(message = result.status.msg))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }
    }

    override suspend fun getStart(userId: String): Flow<NetworkResult<NetGetStart>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = api.getStart(userId)
            if (result.status.msg == "Successful") emit(NetworkResult.Success(result))
            else emit(NetworkResult.Error(message = result.status.msg))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }
    }

    override suspend fun putBreathingData(netPut: NetPut): NetworkResult<ServerRes> {
        return try {
            NetworkResult.Loading<ServerRes>()
            val result = api.putBreathingData(netPut)
            Log.d("subhash", "putExeData: result${result.status}")
            if (result.status.msg == "Successful") NetworkResult.Success(result)
            else NetworkResult.Error(message = result.status.msg)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }

    override suspend fun postBreathingData(netPost: NetPost): NetworkResult<ServerRes> {
        return try {
            NetworkResult.Loading<ServerRes>()
            val result = api.postBreathingData(netPost)
            Log.d("subhash", "putExeData: result${result.status}")
            if (result.status.msg == "Successful") NetworkResult.Success(result)
            else NetworkResult.Error(message = result.status.msg)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }

    override suspend fun postRatioData(customRatioData: CustomRatioData): NetworkResult<ServerRes> {
        return try {
            NetworkResult.Loading<ServerRes>()
            val result = api.postRatioData(customRatioData)
            Log.d("subhash", "putExeData: result${result.status}")
            if (result.status.msg == "Successful") NetworkResult.Success(result)
            else NetworkResult.Error(message = result.status.msg)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }

    override suspend fun deleteRatioData(ratioId: String): NetworkResult<ServerRes> {
        return try {
            NetworkResult.Loading<ServerRes>()
            val result = api.deleteRatioData(ratioId)
            Log.d("subhash", "putExeData: result${result.status}")
            if (result.status.msg == "Successful") NetworkResult.Success(result)
            else NetworkResult.Error(message = result.status.msg)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }
}