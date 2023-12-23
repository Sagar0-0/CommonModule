package fit.asta.health.data.walking.domain.repository

import fit.asta.health.data.walking.domain.model.AllDay
import fit.asta.health.data.walking.domain.model.Day
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DayRepository {

    fun getFirstDay(): Flow<Day?>

    fun getDay(date: LocalDate): Flow<Day>
    fun getDaily(date: LocalDate): Flow<AllDay?>

    suspend fun getAllDays(): List<Day>

    fun getDays(date: LocalDate): Flow<List<Day>>

    suspend fun upsertDay(day: Day)
    suspend fun upsertDailyData(allDay: AllDay)
}