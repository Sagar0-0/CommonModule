package fit.asta.health.tools.walking.model

import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.tools.walking.model.domain.WalkingTool
import fit.asta.health.tools.walking.model.network.request.PutData
import fit.asta.health.tools.walking.model.network.request.PutDayData
import fit.asta.health.tools.walking.model.network.response.PutResponse
import kotlinx.coroutines.flow.Flow


interface WalkingToolRepo {

    suspend fun getHomeData(userId: String):Flow<NetworkResult<WalkingTool>>

    suspend fun putData(putData: PutData):NetworkResult<PutResponse>
    suspend fun putDayData(putDayData: PutDayData):NetworkResult<PutResponse>
}