package fit.asta.health.scheduler.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import fit.asta.health.scheduler.model.db.AlarmDao
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.TagEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val alarmDao: AlarmDao,
) {
    fun getAllAlarm(): Flow<List<AlarmEntity>> {
        return alarmDao.getAll()
    }

    suspend fun insertAlarm(alarmEntity: AlarmEntity) {
        alarmDao.insertAlarm(alarmEntity)
    }

    suspend fun updateAlarm(alarmEntity: AlarmEntity) {
        alarmDao.updateAlarm(alarmEntity)
    }

    suspend fun deleteAlarm(alarmEntity: AlarmEntity) {
        alarmDao.deleteAlarm(alarmEntity)
    }

    suspend fun deleteAllAlarm() {
        alarmDao.deleteAllAlarms()
    }

    fun getAlarmLiveData(): LiveData<List<AlarmEntity>> {
        return alarmDao.getAll().asLiveData()
    }

    fun getAllTags(): Flow<List<TagEntity>> {
        return alarmDao.getAllTags()
    }

    suspend fun insertTag(tagEntity: TagEntity) {
        alarmDao.insertTag(tagEntity)
    }

    suspend fun updateTag(tagEntity: TagEntity) {
        alarmDao.updateTag(tagEntity)
    }

    suspend fun deleteTag(tagEntity: TagEntity) {
        alarmDao.deleteTag(tagEntity)
    }

    suspend fun deleteAllTag() {
        alarmDao.deleteAllTags()
    }
}