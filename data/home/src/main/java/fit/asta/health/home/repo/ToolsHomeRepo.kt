package fit.asta.health.home.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.home.remote.model.ToolsHome


interface ToolsHomeRepo {
    suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: Long,
        endDate: Long,
        time: String
    ): ResponseState<ToolsHome>
}