package fit.asta.health.tools.sunlight.model

import fit.asta.health.tools.sunlight.model.api.SunlightApi
import fit.asta.health.tools.sunlight.model.domain.SunlightTool
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SunlightToolRepoImpl(
    private val remoteApi: SunlightApi,
    private val mapper: SunlightToolDataMapper,
) : SunlightToolRepo {

    override suspend fun getSunlightTool(userId: String): Flow<SunlightTool> {
        return flow {
            emit(
                mapper.mapToDomainModel(
                    remoteApi.getSunlightTool(
                        userId = userId
                    )
                )
            )
        }
    }
}