package fit.asta.health.navigation.home.model.api

import fit.asta.health.navigation.home.model.domain.ToolsHomeRes

//Tools Endpoints
interface ToolsApi {
    suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String
    ): ToolsHomeRes
}