package fit.asta.health.navigation.home.repository

import fit.asta.health.navigation.home.domain.ToolsHome


interface ToolsHomeRepository {
    suspend fun getHomeData(userId: String, wid: String): ToolsHome
}