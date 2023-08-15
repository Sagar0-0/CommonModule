package fit.asta.health.scheduler.services

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.scheduler.data.api.net.tag.Data
import fit.asta.health.scheduler.data.db.entity.TagEntity
import fit.asta.health.scheduler.data.repo.AlarmBackendRepo
import fit.asta.health.scheduler.data.repo.AlarmLocalRepo
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
                when (result) {
                    is ResponseState.Success -> {
                        if (result.data.data.flag) {
                            val alarm = alarmEntity.copy(idFromServer = result.data.data.id)
                            alarmLocalRepo.insertAlarm(alarm)
                            Log.d("TAGTAG", "doWork:noidfromserver alarm $alarm")
                        }
                    }

                    else -> {}
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
                    when (result) {
                        is ResponseState.Success -> {
                            if (result.data.data.flag) {
                                alarm = alarmEntity.copy(idFromServer = result.data.data.id)
                                alarmLocalRepo.insertAlarm(alarm!!)
                                alarmLocalRepo.deleteSyncData(alarmSync)
                                Log.d("TAGTAG", "doWork: alarm $alarm")
                            }
                        }

                        else -> {}
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
                when (val result = backendRepo.getTagListFromBackend("6309a9379af54f142c65fbff")) {
                    is ResponseState.Success -> {
                        result.data.let { schedulerGetTagsList ->
                            schedulerGetTagsList.list.forEach { tag ->
                                insertTag(tag)
                            }
                        }
                    }

                    else -> {}
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




