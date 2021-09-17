package fit.asta.health.navigation.today

import fit.asta.health.navigation.today.data.TodayDataMapper
import fit.asta.health.navigation.today.data.TodayPlanItemData
import fit.asta.health.network.api.RemoteApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TodayPlanRepoImpl(
    private val remoteApi: RemoteApis,
    private val dataMapper: TodayDataMapper
) : TodayPlanRepo {

    override suspend fun fetchTodayPlan(userId: String): Flow<List<TodayPlanItemData>> {
        return flow {
            emit(dataMapper.toMap(remoteApi.getTodayPlan(userId)))
        }
    }
}