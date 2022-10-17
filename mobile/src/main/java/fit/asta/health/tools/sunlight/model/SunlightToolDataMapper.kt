package fit.asta.health.tools.sunlight.model

import fit.asta.health.tools.sunlight.model.domain.SunlightTool
import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes
import fit.asta.health.utils.DomainMapper

class SunlightToolDataMapper : DomainMapper<NetSunlightToolRes, SunlightTool> {

    override fun mapToDomainModel(networkModel: NetSunlightToolRes): SunlightTool {
        return SunlightTool(
        )
    }

    override fun mapFromDomainModel(domainModel: SunlightTool): NetSunlightToolRes {
        TODO("Not yet implemented")
    }
}