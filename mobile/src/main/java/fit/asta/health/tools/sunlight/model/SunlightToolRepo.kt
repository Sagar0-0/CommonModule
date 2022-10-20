package fit.asta.health.tools.sunlight.model

import fit.asta.health.tools.sunlight.model.domain.SunlightTool
import kotlinx.coroutines.flow.Flow


interface SunlightToolRepo {
    suspend fun getSunlightTool(userId: String): Flow<SunlightTool>
}