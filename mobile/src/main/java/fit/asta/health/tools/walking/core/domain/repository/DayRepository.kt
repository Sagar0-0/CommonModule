package fit.asta.health.tools.walking.core.domain.repository

import fit.asta.health.tools.walking.core.domain.model.Day
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DayRepository {

    fun getFirstDay(): Flow<Day?>

    fun getDay(date: LocalDate): Flow<Day>

    suspend fun getAllDays(): List<Day>

    fun getDays(date: LocalDate): Flow<List<Day>>

    suspend fun upsertDay(day: Day)
}