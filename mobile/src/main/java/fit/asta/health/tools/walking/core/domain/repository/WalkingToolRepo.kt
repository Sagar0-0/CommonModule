package fit.asta.health.tools.walking.core.domain.repository

import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.tools.walking.core.data.source.network.request.PutData
import fit.asta.health.tools.walking.core.data.source.network.request.PutDayData
import fit.asta.health.tools.walking.core.data.source.network.response.HomeData


interface WalkingToolRepo {

    suspend fun getHomeData(userId: String): ResponseState<HomeData>
    suspend fun putData(putData: PutData): ResponseState<PutResponse>
    suspend fun putDayData(putDayData: PutDayData): ResponseState<PutResponse>
}