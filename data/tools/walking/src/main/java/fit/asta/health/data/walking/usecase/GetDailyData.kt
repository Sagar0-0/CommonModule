package fit.asta.health.data.walking.usecase

import fit.asta.health.data.walking.local.model.AllDay
import fit.asta.health.data.walking.repo.DayRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetDailyData(private val dayRepository: DayRepository) {
    operator fun invoke(date: LocalDate): Flow<AllDay?> {
        return dayRepository.getDaily(date)
    }
}