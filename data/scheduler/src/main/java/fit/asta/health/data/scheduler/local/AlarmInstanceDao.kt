package fit.asta.health.data.scheduler.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import fit.asta.health.data.scheduler.local.model.AlarmInstance

@Dao
interface AlarmInstanceDao {
    @Upsert
    suspend fun insertAndUpdate(alarmInstance: AlarmInstance)

    @Delete
    suspend fun delete(alarmInstance: AlarmInstance)


    @Query("SELECT * FROM alarm_instances WHERE alarm_id = :alarmId LIMIT 1")
    suspend fun getInstancesByAlarmId(alarmId: Long): AlarmInstance?


    @Query("SELECT * FROM alarm_instances WHERE mId = :instanceId")
    suspend fun getInstance(instanceId: Long): AlarmInstance?


    @Query("SELECT * FROM alarm_instances ORDER BY year DESC, month DESC, day DESC, hour DESC, minute DESC")
    suspend fun getInstancesSortedByTimeDescending(): List<AlarmInstance>?
}