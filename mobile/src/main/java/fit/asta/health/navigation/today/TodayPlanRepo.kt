package fit.asta.health.navigation.today

import fit.asta.health.navigation.today.data.TodayPlanItemData
import kotlinx.coroutines.flow.Flow

interface TodayPlanRepo {
    suspend fun fetchTodayPlan(userId: String): Flow<List<TodayPlanItemData>>
}