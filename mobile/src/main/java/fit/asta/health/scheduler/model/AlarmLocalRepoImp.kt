package fit.asta.health.scheduler.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import fit.asta.health.scheduler.model.db.AlarmDao
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.TagEntity
import kotlinx.coroutines.flow.Flow

class AlarmLocalRepoImp(
    private val alarmDao: AlarmDao,
):AlarmLocalRepo {
    override fun getAllAlarm(): Flow<List<AlarmEntity>> {
        return alarmDao.getAll()
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

    override fun getAlarmLiveData(): LiveData<List<AlarmEntity>> {
        return alarmDao.getAll().asLiveData()
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
}