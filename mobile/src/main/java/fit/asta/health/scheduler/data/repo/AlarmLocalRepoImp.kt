package fit.asta.health.scheduler.data.repo

import fit.asta.health.scheduler.data.db.AlarmDao
import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import fit.asta.health.scheduler.data.db.entity.AlarmSync
import fit.asta.health.scheduler.data.db.entity.TagEntity
import kotlinx.coroutines.flow.Flow

class AlarmLocalRepoImp(
    private val alarmDao: AlarmDao,
) : AlarmLocalRepo {
    override fun getAllAlarm(): Flow<List<AlarmEntity>> {
        return alarmDao.getAll()
    }

    override suspend fun getAlarm(alarmId: Int): AlarmEntity? {
        return alarmDao.getAlarm(alarmId)
    }

    override suspend fun insertAlarm(alarmEntity: AlarmEntity) {
        alarmDao.insertAlarm(alarmEntity)
    }

    override suspend fun updateAlarm(alarmEntity: AlarmEntity) {
        alarmDao.updateAlarm(alarmEntity)
    }

    override suspend fun deleteAlarm(alarmEntity: AlarmEntity) {
        alarmDao.deleteAlarm(alarmEntity)
    }

    override suspend fun deleteAllAlarm() {
        alarmDao.deleteAllAlarms()
    }


    override fun getAllTags(): Flow<List<TagEntity>> {
        return alarmDao.getAllTags()
    }

    override suspend fun insertTag(tagEntity: TagEntity) {
        alarmDao.insertTag(tagEntity)
    }

    override suspend fun updateTag(tagEntity: TagEntity) {
        alarmDao.updateTag(tagEntity)
    }

    override suspend fun deleteTag(tagEntity: TagEntity) {
        alarmDao.deleteTag(tagEntity)
    }

    override suspend fun deleteAllTag() {
        alarmDao.deleteAllTags()
    }

    override suspend fun getAllSyncData(): List<AlarmSync> {
        return alarmDao.getAllSyncData()
    }

    override suspend fun getAllAlarmList(): List<AlarmEntity> {
        return alarmDao.getAllAlarm()
    }

    override suspend fun getSyncData(id: Int): AlarmSync? {
       return alarmDao.getSyncData(id)
    }

    override suspend fun insertSyncData(alarmSync: AlarmSync) {
       alarmDao.insertSyncData(alarmSync)
    }

    override suspend fun deleteAllSyncData() {
        alarmDao.deleteAllSyncData()
    }

    override suspend fun deleteSyncData(alarmSync: AlarmSync) {
        alarmDao.deleteSyncData(alarmSync)
    }
}