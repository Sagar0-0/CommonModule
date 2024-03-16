package fit.asta.health.data.scheduler.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import fit.asta.health.data.scheduler.local.model.AlarmEntity
import fit.asta.health.data.scheduler.local.model.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {//TODO: Use flows for all Query requests

    @Query("SELECT * FROM alarm_table ORDER BY alarmId ASC")
    fun getAll(): Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarm_table ORDER BY alarmId ASC")
    suspend fun getAllAlarm(): List<AlarmEntity>?

    @Query("SELECT * FROM alarm_table WHERE alarmId = :alarmId LIMIT 1")
    suspend fun getAlarm(alarmId: Long): AlarmEntity?

    @Upsert
    suspend fun insertAlarm(alarmEntity: AlarmEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAlarm(alarmEntity: AlarmEntity)

    @Delete
    suspend fun deleteAlarm(alarmEntity: AlarmEntity)

    @Query("DELETE FROM alarm_table")
    suspend fun deleteAllAlarms()

    @Query("SELECT * FROM tag_table ORDER BY id ASC")
    fun getAllTags(): Flow<List<TagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tagEntity: TagEntity)

    @Update
    suspend fun updateTag(tagEntity: TagEntity)

    @Delete
    suspend fun deleteTag(tagEntity: TagEntity)

    @Query("DELETE FROM tag_table")
    suspend fun deleteAllTags()

}