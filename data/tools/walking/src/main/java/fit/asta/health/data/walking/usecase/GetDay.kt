package fit.asta.health.data.walking.usecase

import fit.asta.health.data.walking.local.model.Day
import fit.asta.health.data.walking.repo.DayRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate


class GetDay(private val dayRepository: DayRepository) {
    operator fun invoke(date: LocalDate): Flow<Day> {
        return dayRepository.getDay(date)
    }
}