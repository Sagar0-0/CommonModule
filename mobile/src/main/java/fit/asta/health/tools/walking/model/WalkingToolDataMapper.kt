package fit.asta.health.tools.walking.model

import fit.asta.health.tools.walking.model.domain.WalkingTool
import fit.asta.health.tools.walking.model.network.response.NetWalkingToolRes

class WalkingToolDataMapper {

    fun mapToDomainModel(networkModel: NetWalkingToolRes): WalkingTool {
        return WalkingTool(
        )
    }

    fun mapToNetworkModel(domainModel: WalkingTool): NetWalkingToolRes {
        TODO("Not yet implemented")
    }
}