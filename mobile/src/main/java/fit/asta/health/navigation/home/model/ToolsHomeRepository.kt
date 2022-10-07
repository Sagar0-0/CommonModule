package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.model.domain.ToolsHome
import kotlinx.coroutines.flow.Flow


interface ToolsHomeRepository {
    suspend fun getHomeData(userId: String, wid: String): Flow<ToolsHome>
}