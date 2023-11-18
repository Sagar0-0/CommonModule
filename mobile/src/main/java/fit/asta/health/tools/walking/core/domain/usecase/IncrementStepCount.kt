package fit.asta.health.tools.walking.core.domain.usecase

import fit.asta.health.tools.walking.core.domain.repository.DayRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class IncrementStepCount(
    private val repository: DayRepository
) {

    suspend operator fun invoke(date: LocalDate, by: Int) {
        val day = repository.getDay(date).first()
        val updatedDay = day.copy(steps = day.steps + by)
        repository.upsertDay(updatedDay)
    }
}