package fit.asta.health.feature.walking.view.session

import java.time.LocalDate

data class ProgressState(
    val date: LocalDate,
    val stepsTaken: Int,
    val targetDistance: Float,
    val targetDuration: Int,
    val duration: Int,
    val calorieBurned: Int,
    val distanceTravelled: Double,
    val carbonDioxideSaved: Double,
    val state: Boolean
)