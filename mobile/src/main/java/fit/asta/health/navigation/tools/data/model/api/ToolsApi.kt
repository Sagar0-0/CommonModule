package fit.asta.health.navigation.tools.data.model.api

import fit.asta.health.navigation.tools.data.model.domain.ToolsHomeRes

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