package fit.asta.health.tools.sunlight.model

import fit.asta.health.tools.sunlight.model.domain.SunlightTool
import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes

class SunlightToolDataMapper {

    fun mapToDomainModel(networkModel: NetSunlightToolRes): SunlightTool {
        return SunlightTool(
        )
    }

    fun mapToNetworkModel(domainModel: SunlightTool): NetSunlightToolRes {
        TODO("Not yet implemented")
    }
}