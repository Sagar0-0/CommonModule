package fit.asta.health.common.health_data

import androidx.health.connect.client.units.Energy
import androidx.health.connect.client.units.Length

/**
 * Represents data, both aggregated and raw, associated with a single exercise session. Used to
 * collate results from aggregate and raw reads from Health Connect in one object.
 */
data class ExerciseSessionData(
    val totalSteps: Long? = null,
    val totalDistance: Length? = null,
    val totalEnergyBurned: Energy? = null,
)
