package fit.asta.health.tools.walking.model

import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.tools.walking.model.api.WalkingApi
import fit.asta.health.tools.walking.model.domain.WalkingTool
import fit.asta.health.tools.walking.model.network.request.PutData
import fit.asta.health.tools.walking.model.network.response.PutResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class WalkingToolRepoImpl(
    private val remoteApi: WalkingApi
) : WalkingToolRepo {


    override suspend fun getHomeData(userId: String): Flow<NetworkResult<WalkingTool>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(NetworkResult.Success(remoteApi.getHomeData(userId).mapToDomain()))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun putData(putData: PutData): PutResponse {
        return remoteApi.putData(putData)
    }
}