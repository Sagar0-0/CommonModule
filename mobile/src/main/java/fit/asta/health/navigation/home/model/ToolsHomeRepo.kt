package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.model.domain.ToolsHomeRes
import kotlinx.coroutines.flow.Flow


interface ToolsHomeRepo {
    suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): Flow<ToolsHomeRes.ToolsHome>
}