package fit.asta.health.data.walking.domain.usecase

import fit.asta.health.data.walking.domain.model.Day
import fit.asta.health.data.walking.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate


class GetDay(private val dayRepository: DayRepository) {
    operator fun invoke(date: LocalDate): Flow<Day> {
        return dayRepository.getDay(date)
    }
}