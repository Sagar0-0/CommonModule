package fit.asta.health.data.walking.domain.repository

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.walking.data.source.network.request.PutData
import fit.asta.health.data.walking.data.source.network.request.PutDayData
import fit.asta.health.data.walking.data.source.network.response.HomeData
import fit.asta.health.datastore.UserPreferencesData
import kotlinx.coroutines.flow.Flow


interface WalkingToolRepo {
    val userPreferences: Flow<UserPreferencesData>
    suspend fun updateStepsPermissionRejectedCount(newValue: Int)
    suspend fun getHomeData(userId: String): ResponseState<HomeData>
    suspend fun putData(putData: PutData): ResponseState<PutResponse>
    suspend fun putDayData(putDayData: PutDayData): ResponseState<PutResponse>

    suspend fun getSheetData(code: String): ResponseState<List<NetSheetData>>
    suspend fun getSheetGoalsData(tool: String): ResponseState<List<NetSheetData>>
}