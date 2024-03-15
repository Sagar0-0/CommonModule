package fit.asta.health.home.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.home.remote.ToolsHomeApi
import fit.asta.health.home.remote.model.ToolsHome
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToolsHomeRepoImpl
@Inject constructor(
    private val toolsHomeApi: ToolsHomeApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ToolsHomeRepo {

    override suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: Long,
        endDate: Long,
        time: String
    ): ResponseState<ToolsHome> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                toolsHomeApi.getHomeData(
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