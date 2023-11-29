package fit.asta.health.tools.walking.core.data.repository

import fit.asta.health.tools.walking.core.data.source.DayDao
import fit.asta.health.tools.walking.core.domain.model.Day
import fit.asta.health.tools.walking.core.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DayRepositoryImpl(
    private val dao: DayDao
) : DayRepository {


    override fun getFirstDay(): Flow<Day?> {
        return dao.getFirstDay()
    }

    override fun getDay(date: LocalDate): Flow<Day> {
        return dao.getDay(date)
    }

    override suspend fun getAllDays(): List<Day> {
        return dao.getAllDays()
    }

    override fun getDays(date: LocalDate): Flow<List<Day>> {
        return dao.getDays(date)
    }

    override suspend fun upsertDay(day: Day) {
        dao.upsertDay(day)
    }
}