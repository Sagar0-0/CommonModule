package fit.asta.health.data.walking.domain.usecase

import fit.asta.health.data.walking.domain.model.AllDay
import fit.asta.health.data.walking.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetDailyData(private val dayRepository: DayRepository) {
    operator fun invoke(date: LocalDate): Flow<AllDay?> {
        return dayRepository.getDaily(date)
    }
}