package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.api.HealthToolsService
import fit.asta.health.navigation.home.model.domain.ToolsHome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToolsHomeRepositoryImpl(
    private val healthToolsService: HealthToolsService,
    private val mapper: ToolsHomeDataMapper,
) : ToolsHomeRepository {

    override suspend fun getHomeData(userId: String, wid: String): Flow<ToolsHome> {
        return flow {
            emit(mapper.mapToDomainModel(healthToolsService.getHomeData(userId, wid)))
        }
    }
}