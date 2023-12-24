package fit.asta.health.data.walking.service

import java.time.LocalDate

data class StepCounterEvent(
    val stepCount: Int,
    val eventDate: LocalDate,
)