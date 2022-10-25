package fit.asta.health.tools.water.model

import fit.asta.health.tools.water.model.domain.WaterTool
import kotlinx.coroutines.flow.Flow


interface WaterToolRepo {
    suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String
    ): Flow<WaterTool>
}