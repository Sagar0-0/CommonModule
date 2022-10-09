package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.model.domain.ToolsHome
import kotlinx.coroutines.flow.Flow


interface ToolsHomeRepository {
    suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): Flow<ToolsHome>
}