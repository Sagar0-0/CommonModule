package fit.asta.health.data.walking.data.source

import androidx.room.*
import fit.asta.health.data.walking.domain.model.AllDay
import fit.asta.health.data.walking.domain.model.Day
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DayDao {


    @Query("SELECT * FROM day ORDER BY date ASC LIMIT 1")
    fun getFirstDay(): Flow<Day?>

    @Query("SELECT * FROM day WHERE date = :date ORDER BY startupTime DESC LIMIT 1")
    fun getDay(date: LocalDate): Flow<Day>

    @Query("SELECT * FROM daily WHERE date = :date ")
    fun getDailyData(date: LocalDate): Flow<AllDay?>

    @Query("SELECT * FROM day")
    suspend fun getAllDays(): List<Day>

    @Query("SELECT * FROM day WHERE date = :date")
    fun getDays(date: LocalDate): Flow<List<Day>>

    @Upsert
    suspend fun upsertDay(day: Day)

    @Upsert
    suspend fun upsertDailyData(allDay: AllDay)

//    @Update(entity = Day::class)
//    suspend fun updateDaySettings(day: DaySettings)
}