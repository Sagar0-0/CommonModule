package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.model.api.ToolsApi
import fit.asta.health.navigation.home.model.domain.ToolsHomeRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToolsHomeRepoImpl(private val toolsApi: ToolsApi) : ToolsHomeRepo {

    override suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): Flow<ToolsHomeRes.ToolsHome> {
        return flow {
            emit(
                toolsApi.getHomeData(
                    userId = userId,
                    latitude = latitude,
                    longitude = longitude,
                    location = location,
                    startDate = startDate
                ).data
            )
        }
    }
}