package fit.asta.health.navigation.tools.data.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.navigation.tools.data.remote.model.ToolsHome


interface ToolsHomeRepo {
    suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): ResponseState<ToolsHome>
}