package fit.asta.health.tools.walking.model

import fit.asta.health.tools.walking.model.domain.WalkingTool
import kotlinx.coroutines.flow.Flow


interface WalkingToolRepo {
    suspend fun getWalkingTool(userId: String): Flow<WalkingTool>
}