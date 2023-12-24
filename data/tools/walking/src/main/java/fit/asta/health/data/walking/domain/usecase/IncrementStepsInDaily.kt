package fit.asta.health.data.walking.domain.usecase

import fit.asta.health.data.walking.domain.model.AllDay
import fit.asta.health.data.walking.domain.repository.DayRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class IncrementStepsInDaily(
    private val repository: DayRepository
) {

    suspend operator fun invoke(date: LocalDate, by: Int) {
        val day = repository.getDaily(date).first()
        if (day == null) {
            repository.upsertDailyData(AllDay(date, 0))
        } else {
            repository.upsertDailyData(day.copy(steps = day.steps + by))
        }
    }
}