package fit.asta.health.tools.meditation.model

import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.network.data.Status
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
            emit(NetworkResult.Success(result))
        }.catch {
          emit(NetworkResult.Error(message = it.message))
        }
    }

    override fun getMusicTool(uid: String): Flow<NetworkResult<NetMusicRes>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = api.getMusicTool(uid)
            emit(NetworkResult.Success(result))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }
    }

    override suspend fun putMeditationData(putData: PutData): Status {
        TODO("Not yet implemented")
    }

    override suspend fun postMeditationData(postData: PostRes): Status {
        TODO("Not yet implemented")
    }
}