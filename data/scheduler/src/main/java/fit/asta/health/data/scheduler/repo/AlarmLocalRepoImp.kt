package fit.asta.health.data.scheduler.repo

import fit.asta.health.data.scheduler.db.AlarmDao
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.db.entity.TagEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlarmLocalRepoImp(
    private val alarmDao: AlarmDao,
) : AlarmLocalRepo {
    override fun getAllAlarm(): Flow<List<AlarmEntity>> {
        return alarmDao.getAll().map { alarms ->
            alarms.sortedBy { alarmEntity ->
                alarmEntity.time.hours * 60 + alarmEntity.time.minutes
            }
        }
    }

    override suspend fun getAlarm(alarmId: Long): AlarmEntity? {
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

    override suspend fun getAllAlarmList(): List<AlarmEntity>? {
        return alarmDao.getAllAlarm()
    }
}