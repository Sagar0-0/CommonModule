package fit.asta.health.data.walking.usecase

import fit.asta.health.data.walking.local.model.Day
import fit.asta.health.data.walking.repo.DayRepository
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