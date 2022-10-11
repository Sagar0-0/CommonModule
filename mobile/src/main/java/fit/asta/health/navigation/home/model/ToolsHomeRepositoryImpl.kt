package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.model.domain.ToolsHome
import fit.asta.health.network.api.RemoteApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToolsHomeRepositoryImpl(
    private val remoteApi: RemoteApis,
    private val mapper: ToolsHomeDataMapper,
) : ToolsHomeRepository {

    override suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): Flow<ToolsHome> {
        return flow {
            emit(
                mapper.mapToDomainModel(
                    remoteApi.getHomeData(
                        userId = userId,
                        latitude = latitude,
                        longitude = longitude,
                        location = location,
                        startDate = startDate,
                        endDate = endDate,
                        time = time
                    )
                )
            )
        }
    }
}