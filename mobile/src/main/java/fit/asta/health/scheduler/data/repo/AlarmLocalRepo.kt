package fit.asta.health.scheduler.data.repo

import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import fit.asta.health.scheduler.data.db.entity.TagEntity
import kotlinx.coroutines.flow.Flow

interface AlarmLocalRepo {

    fun getAllAlarm(): Flow<List<AlarmEntity>>
    suspend fun getAlarm(alarmId: Long): AlarmEntity?

    suspend fun insertAlarm(alarmEntity: AlarmEntity)

    suspend fun updateAlarm(alarmEntity: AlarmEntity)

    suspend fun deleteAlarm(alarmEntity: AlarmEntity)

    suspend fun deleteAllAlarm()


    fun getAllTags(): Flow<List<TagEntity>>

    suspend fun insertTag(tagEntity: TagEntity)

    suspend fun updateTag(tagEntity: TagEntity)

    suspend fun deleteTag(tagEntity: TagEntity)

    suspend fun deleteAllTag()

    suspend fun getAllAlarmList(): List<AlarmEntity>?
}