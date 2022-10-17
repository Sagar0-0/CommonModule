package fit.asta.health.tools.water.model

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.tools.water.model.domain.WaterTool
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WaterToolRepositoryImpl(
    private val remoteApi: RemoteApis,
    private val mapper: WaterToolDataMapper,
) : WaterToolRepository {

    override suspend fun getWaterTool(userId: String): Flow<WaterTool> {
        return flow {
            emit(
                mapper.mapToDomainModel(
                    remoteApi.getWaterTool(
                        userId = userId
                    )
                )
            )
        }
    }
}