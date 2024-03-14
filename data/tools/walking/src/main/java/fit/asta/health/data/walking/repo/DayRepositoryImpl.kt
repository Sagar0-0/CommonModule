package fit.asta.health.data.walking.repo

import fit.asta.health.data.walking.local.DayDao
import fit.asta.health.data.walking.local.model.AllDay
import fit.asta.health.data.walking.local.model.Day
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class DayRepositoryImpl
@Inject constructor(
    private val dao: DayDao
) : DayRepository {


    override fun getFirstDay(): Flow<Day?> {
        return dao.getFirstDay()
    }

    override fun getDay(date: LocalDate): Flow<Day> {
        return dao.getDay(date)
    }

    override fun getDaily(date: LocalDate): Flow<AllDay?> {
        return dao.getDailyData(date)
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

    override suspend fun upsertDailyData(allDay: AllDay) {
        dao.upsertDailyData(allDay)
    }
}