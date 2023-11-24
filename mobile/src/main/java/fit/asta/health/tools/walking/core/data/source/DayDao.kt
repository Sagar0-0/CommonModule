package fit.asta.health.tools.walking.core.data.source

import androidx.room.*
import fit.asta.health.tools.walking.core.domain.model.Day
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DayDao {


    @Query("SELECT * FROM day ORDER BY date ASC LIMIT 1")
    fun getFirstDay(): Flow<Day?>

    @Query("SELECT * FROM day WHERE date = :date ORDER BY startupTime DESC LIMIT 1")
    fun getDay(date: LocalDate): Flow<Day>

    @Query("SELECT * FROM day")
    suspend fun getAllDays(): List<Day>

    @Query("SELECT * FROM day")
    fun getDays(): Flow<List<Day>>

    @Upsert
    suspend fun upsertDay(day: Day)

//    @Update(entity = Day::class)
//    suspend fun updateDaySettings(day: DaySettings)
}