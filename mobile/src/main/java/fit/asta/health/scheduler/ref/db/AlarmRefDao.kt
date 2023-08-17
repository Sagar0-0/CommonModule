package fit.asta.health.scheduler.ref.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import fit.asta.health.scheduler.ref.provider.Alarm

@Dao
interface AlarmRefDao {
    @Upsert
    suspend fun insertAndUpdate(alarm: Alarm)

    @Delete
    suspend fun delete(alarm: Alarm)

    @Query("SELECT * FROM alarms WHERE id = :alarmId")
    suspend fun getAlarmById(alarmId: Long): Alarm?
}