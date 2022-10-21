package fit.asta.health.tools.walking.model

import fit.asta.health.tools.walking.model.domain.WalkingTool
import fit.asta.health.tools.walking.model.network.response.NetWalkingToolRes
import fit.asta.health.utils.DomainMapper

class WalkingToolDataMapper : DomainMapper<NetWalkingToolRes, WalkingTool> {

    override fun mapToDomainModel(networkModel: NetWalkingToolRes): WalkingTool {
        return WalkingTool(
        )
    }

    override fun mapFromDomainModel(domainModel: WalkingTool): NetWalkingToolRes {
        TODO("Not yet implemented")
    }
}