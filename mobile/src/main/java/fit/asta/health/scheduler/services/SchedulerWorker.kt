package fit.asta.health.scheduler.services

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.scheduler.model.AlarmBackendRepo
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.db.entity.TagEntity
import fit.asta.health.scheduler.model.net.tag.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltWorker
class SchedulerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val alarmLocalRepo: AlarmLocalRepo,
    private val backendRepo: AlarmBackendRepo
) : Worker(context, params) {
    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TAGTAG", "doWork:start ")
            syncAlarmLocal()
            syncServerWithLocal()
            syncTagsData()
        }
        return Result.success()
    }

    private suspend fun syncAlarmLocal() {
        alarmLocalRepo.getAllAlarmList().let {
            Log.d("TAGTAG", "doWork:noidfromserver alarm  ")
            it.filter { alarms -> alarms.idFromServer.isEmpty() }.forEach { alarmEntity ->
                val result = withContext(Dispatchers.Default) {
                    backendRepo.updateScheduleDataOnBackend(schedule = alarmEntity)
                }
                if (result.data?.data?.flag == true) {
                    val alarm = alarmEntity.copy(idFromServer = result.data.data.id)
                    alarmLocalRepo.insertAlarm(alarm)
                    Log.d("TAGTAG", "doWork:noidfromserver alarm $alarm")
                }
            }
        }
    }

    private suspend fun syncServerWithLocal() {
        alarmLocalRepo.getAllSyncData().let {
            Log.d("TAGTAG", "doWork: alarm ")
            it.forEach { alarmSync ->
                var alarm = alarmLocalRepo.getAlarm(alarmId = alarmSync.alarmId)
                alarm?.let { alarmEntity ->
                    val result = withContext(Dispatchers.Default) {
                        backendRepo.updateScheduleDataOnBackend(schedule = alarmEntity)
                    }
                    if (result.data?.data?.flag == true) {
                        alarm = alarmEntity.copy(idFromServer = result.data.data.id)
                        alarmLocalRepo.insertAlarm(alarm!!)
                        alarmLocalRepo.deleteSyncData(alarmSync)
                        Log.d("TAGTAG", "doWork: alarm $alarm")
                    }
                }
                if (alarm == null) {
                    Log.d("TAGTAG", "doWork: alarm delete")
                    backendRepo.deleteScheduleDataFromBackend(alarmSync.scheduleId)
                    alarmLocalRepo.deleteSyncData(alarmSync)
                }
            }
        }
    }

    private suspend fun syncTagsData() {
        alarmLocalRepo.getAllTags().collect {
            if (it.isEmpty()) {
                backendRepo.getTagListFromBackend("6309a9379af54f142c65fbff").collect { tags ->
                    when (tags) {
                        is NetworkResult.Success -> {
                            tags.data.let { schedulerGetTagsList ->
                                schedulerGetTagsList?.list?.forEach { tag ->
                                    insertTag(tag)
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private suspend fun insertTag(tag: Data) {
        alarmLocalRepo.insertTag(
            TagEntity(
                meta = tag,
                selected = false
            )
        )
    }
}




