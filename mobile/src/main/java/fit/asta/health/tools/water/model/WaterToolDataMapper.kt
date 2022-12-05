package fit.asta.health.tools.water.model

import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.NetWaterTool
import fit.asta.health.tools.water.model.network.NetWaterToolRes

class WaterToolDataMapper {

    fun mapToDomainModel(networkModel: NetWaterToolRes): NetWaterTool {
        return networkModel.waterTool
    }

    fun mapToNetworkModel(domainModel: WaterTool): NetWaterToolRes {
        TODO("Not yet implemented")
    }
}