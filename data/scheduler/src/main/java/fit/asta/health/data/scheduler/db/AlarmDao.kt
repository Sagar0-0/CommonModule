package fit.asta.health.data.scheduler.db

import androidx.room.*
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.db.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

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