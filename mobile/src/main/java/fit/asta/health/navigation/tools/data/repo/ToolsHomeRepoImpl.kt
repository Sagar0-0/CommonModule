package fit.asta.health.navigation.tools.data.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.navigation.tools.data.remote.ToolsApi
import fit.asta.health.navigation.tools.data.remote.model.ToolsHome
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToolsHomeRepoImpl
@Inject constructor(
    private val toolsApi: ToolsApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ToolsHomeRepo {

    override suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): ResponseState<ToolsHome> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                toolsApi.getHomeData(
                    userId = userId,
                    latitude = latitude,
                    longitude = longitude,
                    location = location,
                    startDate = startDate
                )
            }
        }
    }
}