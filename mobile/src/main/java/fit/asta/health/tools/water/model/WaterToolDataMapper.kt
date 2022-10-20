package fit.asta.health.tools.water.model

import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.response.NetWaterToolRes
import fit.asta.health.utils.DomainMapper

class WaterToolDataMapper : DomainMapper<NetWaterToolRes, WaterTool> {

    override fun mapToDomainModel(networkModel: NetWaterToolRes): WaterTool {
        return WaterTool(
        )
    }

    override fun mapFromDomainModel(domainModel: WaterTool): NetWaterToolRes {
        TODO("Not yet implemented")
    }
}