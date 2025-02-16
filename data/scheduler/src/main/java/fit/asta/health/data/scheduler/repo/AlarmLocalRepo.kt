package fit.asta.health.data.scheduler.repo

import fit.asta.health.data.scheduler.local.model.AlarmEntity
import fit.asta.health.data.scheduler.local.model.AlarmInstance
import fit.asta.health.data.scheduler.local.model.TagEntity
import kotlinx.coroutines.flow.Flow

interface AlarmLocalRepo {//TODO: Use flows for all Query requests

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

    suspend fun getAllAlarmInstanceList(): List<AlarmInstance>?
}