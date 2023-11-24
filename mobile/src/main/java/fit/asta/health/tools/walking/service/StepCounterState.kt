package fit.asta.health.tools.walking.service

import java.time.LocalDate

data class StepCounterState(
    val date: LocalDate,
    val steps: Int,
    val goalDistance: Float,
    val goalDuration: Int,
    val distanceTravelled: Double,
    val calorieBurned: Int,
    val duration: Int
)