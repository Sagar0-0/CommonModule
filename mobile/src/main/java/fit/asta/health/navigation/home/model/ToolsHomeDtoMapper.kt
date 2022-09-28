package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.model.domain.ToolsHome
import fit.asta.health.utils.DomainMapper
import fit.asta.health.navigation.home.model.network.response.HealthToolsResponse

class ToolsHomeDtoMapper : DomainMapper<HealthToolsResponse, ToolsHome> {

    override fun mapToDomainModel(model: HealthToolsResponse): ToolsHome {
        TODO()
        //return ToolsHome()
    }

    override fun mapFromDomainModel(domainModel: ToolsHome): HealthToolsResponse {
        TODO()
        //return HealthToolsResponse()
    }

    fun toDomainList(initial: HealthToolsResponse): List<ToolsHome> {
        TODO()
        //return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<ToolsHome>): List<ToolsHome> {
        TODO()
        //return initial.map { mapFromDomainModel(it) }
    }
}
