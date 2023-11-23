package fit.asta.health.tools.walking.core.domain.usecase

import fit.asta.health.tools.walking.core.domain.model.Day
import fit.asta.health.tools.walking.core.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate


class GetDay(private val dayRepository: DayRepository) {
    operator fun invoke(date: LocalDate): Flow<Day> {
        return dayRepository.getDay(date)
    }
}