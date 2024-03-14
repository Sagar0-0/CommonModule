package fit.asta.health.data.walking.usecase

import fit.asta.health.data.walking.local.model.AllDay
import fit.asta.health.data.walking.repo.DayRepository
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