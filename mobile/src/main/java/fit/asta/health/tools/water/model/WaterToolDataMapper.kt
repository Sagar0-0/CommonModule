package fit.asta.health.tools.water.model

import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.response.NetWaterToolRes

class WaterToolDataMapper {

    fun mapToDomainModel(networkModel: NetWaterToolRes): WaterTool {
        return WaterTool(
        )
    }

    fun mapToNetworkModel(domainModel: WaterTool): NetWaterToolRes {
        TODO("Not yet implemented")
    }
}