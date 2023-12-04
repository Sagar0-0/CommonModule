package fit.asta.health.data.walking.domain.usecase

import fit.asta.health.data.walking.domain.model.Day
import fit.asta.health.data.walking.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetTodayData(private val dayRepository: DayRepository) {
    operator fun invoke(date: LocalDate): Flow<List<Day>> {
        return dayRepository.getDays(date).map { list ->
            list.sortedBy { it.startupTime }
        }
    }
}