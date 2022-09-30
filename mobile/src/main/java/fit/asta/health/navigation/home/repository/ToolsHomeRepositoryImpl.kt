package fit.asta.health.navigation.home.repository

import fit.asta.health.navigation.home.model.ToolsHomeDataMapper
import fit.asta.health.navigation.home.model.domain.ToolsHome
import fit.asta.health.navigation.home.api.HealthToolsService

class ToolsHomeRepositoryImpl(
    private val healthToolsService: HealthToolsService,
    private val mapper: ToolsHomeDataMapper,
) : ToolsHomeRepository {

    override suspend fun getHomeData(userId: String, wid: String): ToolsHome {
        return mapper.mapToDomainModel(healthToolsService.getHomeData(userId, wid))
    }
}