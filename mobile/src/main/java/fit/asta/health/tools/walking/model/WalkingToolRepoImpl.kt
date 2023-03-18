package fit.asta.health.tools.walking.model

import fit.asta.health.tools.walking.model.api.WalkingApi
import fit.asta.health.tools.walking.model.domain.WalkingTool
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class WalkingToolRepoImpl(
    private val remoteApi: WalkingApi,
    private val mapper: WalkingToolDataMapper,
) : WalkingToolRepo {

    override suspend fun getWalkingTool(userId: String): Flow<WalkingTool> {
        return flow {
        }
    }
}