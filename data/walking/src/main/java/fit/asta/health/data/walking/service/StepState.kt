package fit.asta.health.data.walking.service

import java.time.LocalDate

data class StepState(
    val date: LocalDate,
    val steps: Int,
    val distanceTravelled: Double,
    val calorieBurned: Int,
    val duration: Int
)