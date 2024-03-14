package fit.asta.health.data.walking.repo

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.data.walking.remote.model.HomeData
import fit.asta.health.data.walking.remote.model.PutData
import fit.asta.health.data.walking.remote.model.PutDayData
import fit.asta.health.datastore.UserPreferencesData
import kotlinx.coroutines.flow.Flow


interface WalkingToolRepo {
    val userPreferences: Flow<UserPreferencesData>
    suspend fun updateStepsPermissionRejectedCount(newValue: Int)
    suspend fun getHomeData(userId: String): ResponseState<HomeData>
    suspend fun putData(putData: PutData): ResponseState<SubmitProfileResponse>
    suspend fun putDayData(putDayData: PutDayData): ResponseState<SubmitProfileResponse>

    suspend fun getSheetData(code: String): ResponseState<List<NetSheetData>>
    suspend fun getSheetGoalsData(tool: String): ResponseState<List<NetSheetData>>
}