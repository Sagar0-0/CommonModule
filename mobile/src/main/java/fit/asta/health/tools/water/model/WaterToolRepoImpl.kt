package fit.asta.health.tools.water.model

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.tools.water.model.domain.WaterTool
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WaterToolRepoImpl(
    private val remoteApi: RemoteApis,
    private val mapper: WaterToolDataMapper,
) : WaterToolRepo {

    override suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String
    ): Flow<WaterTool> {
        return flow {
            emit(
                mapper.mapToDomainModel(
                    remoteApi.getWaterTool(
                        userId = userId,
                        latitude = latitude,
                        longitude = longitude,
                        location = location,
                        startDate = startDate,
                        endDate = endDate
                    )
                )
            )
        }
    }
}