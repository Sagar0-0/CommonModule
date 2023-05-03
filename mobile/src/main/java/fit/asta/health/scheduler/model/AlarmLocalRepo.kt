package fit.asta.health.scheduler.model

import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.AlarmSync
import fit.asta.health.scheduler.model.db.entity.TagEntity
import kotlinx.coroutines.flow.Flow

interface AlarmLocalRepo {

    fun getAllAlarm(): Flow<List<AlarmEntity>>
    suspend fun getAlarm(alarmId: Int): AlarmEntity?

    suspend fun insertAlarm(alarmEntity: AlarmEntity)

    suspend fun updateAlarm(alarmEntity: AlarmEntity)

    suspend fun deleteAlarm(alarmEntity: AlarmEntity)

    suspend fun deleteAllAlarm()


    fun getAllTags(): Flow<List<TagEntity>>

    suspend fun insertTag(tagEntity: TagEntity)

    suspend fun updateTag(tagEntity: TagEntity)

    suspend fun deleteTag(tagEntity: TagEntity)

    suspend fun deleteAllTag()

    suspend fun getAllSyncData(): List<AlarmSync>
    suspend fun getAllAlarmList(): List<AlarmEntity>
    suspend fun getSyncData(id: Int): AlarmSync?
    suspend fun insertSyncData(alarmSync: AlarmSync)
    suspend fun deleteAllSyncData()
    suspend fun deleteSyncData(alarmSync: AlarmSync)
}