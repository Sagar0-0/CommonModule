package fit.asta.health.navigation.home.repository

import fit.asta.health.navigation.home.domain.model.ToolsHome


interface ToolsHomeRepository {

    suspend fun getHomeData(userId: String, wid: String): ToolsHome
}