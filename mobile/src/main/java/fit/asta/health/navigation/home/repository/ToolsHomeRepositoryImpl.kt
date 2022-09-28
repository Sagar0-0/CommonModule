package fit.asta.health.navigation.home.repository

import fit.asta.health.navigation.home.domain.model.ToolsHome
import fit.asta.health.navigation.home.network.HealthToolsService

class ToolsHomeRepositoryImpl(
    private val healthToolsService: HealthToolsService,
    private val mapper: ToolsHomeDtoMapper,
) : ToolsHomeRepository {

    override suspend fun getHomeData(userId: String, wid: String): ToolsHome {
        return mapper.mapToDomainModel(healthToolsService.getHomeData(userId, wid))
    }
}