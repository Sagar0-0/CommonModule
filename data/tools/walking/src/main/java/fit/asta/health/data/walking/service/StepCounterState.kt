package fit.asta.health.data.walking.service

import java.time.LocalDate

data class StepCounterState(
    val date: LocalDate,
    val steps: Int,
    val goalDistance: Float,
    val goalDuration: Float,
    val distanceTravelled: Double,
    val calorieBurned: Int,
    val duration: Int
)
