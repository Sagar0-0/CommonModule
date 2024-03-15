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
import fit.asta.health.data.scheduler.remote.net.tag.TagData
import fit.asta.health.data.scheduler.util.toTagEntity
import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.feature.scheduler.util.StateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

@HiltWorker
class SchedulerWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val alarmLocalRepo: AlarmLocalRepo,
    private val backendRepo: AlarmBackendRepo,
    private val stateManager: StateManager,
    @UID private val uId: String
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun getForegroundInfo(): ForegroundInfo = appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        Log.v("TAGTAG", "doWork: uid $uId")

        if (uId.isNotEmpty()) {
            syncAlarmLocal(uId)
            syncTagsData(uId)
        }
        Result.success()
    }


    private suspend fun syncAlarmLocal(userId: String) {
        Log.v("TAGTAG", "doWork:syncAlarmLocal  ")
        val list = alarmLocalRepo.getAllAlarmList()
        if (list.isNullOrEmpty()) {
            when (val result = backendRepo.getScheduleListDataFromBackend(userId)) {
                is ResponseState.Success -> {
                    result.data.forEach { alarm ->
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
                if (entity.currentAlarmDate != LocalDate.now().dayOfMonth) {
                    val updateData =
                        entity.copy(isMissed = null, currentAlarmDate = LocalDate.now().dayOfMonth)
                    alarmLocalRepo.updateAlarm(updateData)
                }
                when (entity.meta.sync) {
                    1 -> {//update
                        val alarm = entity.copy(
                            meta = Meta(
                                cBy = entity.meta.cBy,
                                cDate = entity.meta.cDate,
                                sync = 0,
                                uDate = getCurrentTime()
                            )
                        )
                        when (val result =
                            backendRepo.updateScheduleDataOnBackend(schedule = alarm)) {
                            is ResponseState.Success -> {
                                val newAlarm = alarm.copy(idFromServer = result.data.id)
                                alarmLocalRepo.insertAlarm(newAlarm)
                                Log.v("TAGTAG", "doWork:noidfromserver alarm $alarm")
                            }

                            else -> {}
                        }
                    }

                    2 -> { // delete
                        when (backendRepo.deleteScheduleDataFromBackend(entity.idFromServer)) {
                            is ResponseState.Success -> {
                                alarmLocalRepo.deleteAlarm(entity)
                                Log.v("TAGTAG", "doWork:delete alarm ")
                            }

                            else -> {
                                Log.v("TAGTAG", "doWork:delete alarm error ")
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }


    private suspend fun syncTagsData(userId: String) {
        Log.v("TAGTAG", "doWork: syncTagsData")
        alarmLocalRepo.getAllTags().collect {
            if (it.isEmpty()) {
                when (val result = backendRepo.getTagListFromBackend(userId)) {
                    is ResponseState.Success -> {
                        result.data.let { schedulerGetTagsList ->
                            schedulerGetTagsList.tagData.forEach { tag ->
                                insertTag(tag)
                            }
                            schedulerGetTagsList.customTagData?.forEach { tag ->
                                insertTag(tag)
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private suspend fun insertTag(tag: TagData) {
        alarmLocalRepo.insertTag(tag.toTagEntity())
    }

    companion object {
        private val SyncConstraints
            get() = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        /**
         * Expedited one time work to sync data on app startup
         */
//        fun startUpSyncWork() = PeriodicWorkRequestBuilder<DelegatingWorker>(1, TimeUnit.HOURS)
//            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
//            .setConstraints(SyncConstraints)
//            .setInputData(SchedulerWorker::class.delegatedData())
//            .build()

        fun startUpSyncWork() =
            OneTimeWorkRequestBuilder<DelegatingWorker>().setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(SyncConstraints)
                .setInputData(SchedulerWorker::class.delegatedData()).build()
    }
}




