package fit.asta.health.tools.water.model

import fit.asta.health.tools.water.model.domain.WaterTool
import kotlinx.coroutines.flow.Flow


interface WaterToolRepository {
    suspend fun getWaterTool(userId: String): Flow<WaterTool>
}