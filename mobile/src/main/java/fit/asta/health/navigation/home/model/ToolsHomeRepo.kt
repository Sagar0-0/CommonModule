package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.model.domain.ToolsHome
import fit.asta.health.navigation.home.model.network.NetSelectedTools
import fit.asta.health.network.data.Status
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
    ): Flow<ToolsHome>

    suspend fun updateSelectedTools(toolIds: NetSelectedTools): Flow<Status>
}