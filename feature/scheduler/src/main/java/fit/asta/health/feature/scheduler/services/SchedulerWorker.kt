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
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getCurrentTime
import fit.asta.health.data.scheduler.remote.net.scheduler.Meta
import fit.asta.health.data.scheduler.remote.net.tag.Data
import fit.asta.health.data.scheduler.remote.toTagEntity
import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.feature.scheduler.util.StateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SchedulerWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val alarmLocalRepo: AlarmLocalRepo,
    private val backendRepo: AlarmBackendRepo,
    private val stateManager: StateManager,
    @UID private val uId: String
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        Log.d("TAGTAG", "doWork:start ")

        if (uId.isEmpty()) {
            Result.retry()
        } else {
            syncAlarmLocal(uId)
            syncTagsData(uId)
        }
        Result.success()
    }


    private suspend fun syncAlarmLocal(userId: String) {
        Log.d("TAGTAG", "doWork:sync alarm  ")
        val list = alarmLocalRepo.getAllAlarmList()
        if (list.isNullOrEmpty()) {
            val result = withContext(Dispatchers.Default) {
                backendRepo.getScheduleListDataFromBackend(userId)
            }
            when (result) {
                is ResponseState.Success -> {
                    result.data.list.forEach { alarm ->
//                        backendRepo.deleteScheduleDataFromBackend(alarm.idFromServer)
                        alarmLocalRepo.insertAlarm(alarm)
                        if (alarm.status) {
                            stateManager.registerAlarm(appContext, alarm)
                        }
                    }
                }

                else -> {}
            }
        } else {
            list.forEach { entity ->
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


    private suspend fun syncTagsData(userId: String) {//"6309a9379af54f142c65fbfe"
        alarmLocalRepo.getAllTags().collect {
            if (it.isEmpty()) {
                when (val result = backendRepo.getTagListFromBackend(userId)) {
                    is ResponseState.Success -> {
                        result.data.let { schedulerGetTagsList ->
                            schedulerGetTagsList.list.forEach { tag ->
                                insertTag(tag)
                            }
                            schedulerGetTagsList.customTagList?.forEach { tag ->
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
        alarmLocalRepo.insertTag(tag.toTagEntity())
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




