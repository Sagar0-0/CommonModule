package fit.asta.health.data.walking.usecase

import fit.asta.health.data.walking.repo.DayRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class IncrementStepDuration(
    private val repository: DayRepository
) {
    suspend operator fun invoke(date: LocalDate, by: Int) {
        val day = repository.getDay(date).first()
        if (day.state) {
            val updatedDay = day.copy(duration = day.duration + by)
            repository.upsertDay(updatedDay)
        }
    }
}