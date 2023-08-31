package fit.asta.health.feature.scheduler.services

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getCurrentTime
import fit.asta.health.data.scheduler.db.entity.TagEntity
import fit.asta.health.data.scheduler.remote.net.scheduler.Meta
import fit.asta.health.data.scheduler.remote.net.tag.Data
import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SchedulerWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val alarmLocalRepo: AlarmLocalRepo,
    private val backendRepo: AlarmBackendRepo
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        Log.d("TAGTAG", "doWork:start ")
        syncAlarmLocal()
        syncTagsData()
        Result.success()
    }

    private suspend fun syncAlarmLocal() {
        alarmLocalRepo.getAllAlarmList()?.let {
            Log.d("TAGTAG", "doWork:sync alarm  ")
            it.forEach { entity ->
                when (entity.meta.sync) {
                    1 -> {//update
                        val result = withContext(Dispatchers.Default) {
                            backendRepo.updateScheduleDataOnBackend(schedule = entity)
                        }
                        when (result) {
                            is ResponseState.Success -> {
                                if (result.data.data.flag) {
                                    val alarm = entity.copy(
                                        idFromServer = result.data.data.id,
                                        meta = Meta(
                                            cBy = entity.meta.cBy,
                                            cDate = entity.meta.cDate,
                                            sync = 0,
                                            uDate = getCurrentTime()
                                        )
                                    )
                                    alarmLocalRepo.insertAlarm(alarm)
                                    Log.d("TAGTAG", "doWork:noidfromserver alarm $alarm")
                                }
                            }

                            else -> {}
                        }
                    }

                    2 -> { // delete
                        val result = withContext(Dispatchers.Default) {
                            backendRepo.deleteScheduleDataFromBackend(entity.idFromServer)
                        }
                        when (result) {
                            is ResponseState.Success -> {
                                alarmLocalRepo.deleteAlarm(entity)
                            }

                            else -> {}
                        }
                    }

                    else -> {}
                }
            }
        }
    }


    private suspend fun syncTagsData() {
        alarmLocalRepo.getAllTags().collect {
            if (it.isEmpty()) {
                when (val result = backendRepo.getTagListFromBackend("6309a9379af54f142c65fbfe")) {
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

    companion object {
        private val SyncConstraints
            get() = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        /**
         * Expedited one time work to sync data on app startup
         */
//        fun startUpSyncWork() = PeriodicWorkRequestBuilder<DelegatingWorker>(1, TimeUnit.HOURS)
//            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
//            .setConstraints(SyncConstraints)
//            .setInputData(SchedulerWorker::class.delegatedData())
//            .build()

        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SchedulerWorker::class.delegatedData())
            .build()
    }
}




