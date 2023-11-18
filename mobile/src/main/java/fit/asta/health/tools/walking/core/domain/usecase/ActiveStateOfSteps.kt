package fit.asta.health.tools.walking.core.domain.usecase

import fit.asta.health.tools.walking.core.domain.repository.DayRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class ActiveStateOfSteps(
    private val repository: DayRepository
) {
    suspend operator fun invoke(date: LocalDate, state: Boolean) {
        val day = repository.getDay(date).first()
        val updatedDay = day.copy(state = state)
        repository.upsertDay(updatedDay)
    }
}