package fit.asta.health.tools.meditation.model

import android.util.Log
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.network.data.ServerRes
import fit.asta.health.tools.meditation.model.api.MeditationApi
import fit.asta.health.tools.meditation.model.network.NetMeditationToolRes
import fit.asta.health.tools.meditation.model.network.NetMusicRes
import fit.asta.health.tools.meditation.model.network.PostRes
import fit.asta.health.tools.meditation.model.network.PutData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MeditationRepoImp(private val api: MeditationApi) : MeditationRepo {
    override fun getMeditationTool(
        uid: String,
        date: String
    ): Flow<NetworkResult<NetMeditationToolRes>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = api.getMeditationTool(uid, date)
            if (result.status.msg == "Successful") emit(NetworkResult.Success(result))
            else emit(NetworkResult.Error(message = result.status.msg))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }
    }

    override fun getMusicTool(uid: String): Flow<NetworkResult<NetMusicRes>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = api.getMusicTool(uid)
            if (result.status.msg == "Successful") emit(NetworkResult.Success(result))
            else emit(NetworkResult.Error(message = result.status.msg))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }
    }

    override suspend fun putMeditationData(putData: PutData): NetworkResult<ServerRes> {
        return try {
            NetworkResult.Loading<ServerRes>()
            val result = api.putMeditationData(putData)
            Log.d("subhash", "putMeditationData: result${result}")
            if (result.status.msg == "Successful") NetworkResult.Success(result)
            else NetworkResult.Error(message = result.status.msg)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }

    override suspend fun postMeditationData(postData: PostRes): NetworkResult<ServerRes> {
        return try {
            NetworkResult.Loading<ServerRes>()
            val result = api.postMeditationData(postData)
            if (result.status.msg  == "Successful") NetworkResult.Success(result)
            else NetworkResult.Error(message = result.status.msg)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }
}