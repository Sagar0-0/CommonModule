package fit.asta.health.scheduler.services

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fit.asta.health.scheduler.model.AlarmBackendRepo
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.tools.walking.notification.CreateNotification
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
        var count=0
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TAGTAG", "doWork:start alarm ")
            alarmLocalRepo.getAllAlarmList().let{

                Log.d("TAGTAG", "doWork:noidfromserver alarm  ")
                it.filter { alarms -> alarms.idFromServer.isEmpty() }.forEach {alarmEntity ->
                    val result = withContext(Dispatchers.Default) {
                        backendRepo.updateScheduleDataOnBackend(schedule = alarmEntity)
                    }
                    if (result.data?.data?.flag == true) {
                       val alarm = alarmEntity.copy(idFromServer = result.data.data.id)
                        alarmLocalRepo.insertAlarm(alarm)
                        Log.d("TAGTAG", "doWork:noidfromserver alarm $alarm")
                        count++
                    }
                }
            }

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
                            count++
                        }
                    }
                    if (alarm==null){
                        count++
                        Log.d("TAGTAG", "doWork: alarm delete")
                        backendRepo.deleteScheduleDataFromBackend(alarmSync.scheduleId)
                        alarmLocalRepo.deleteSyncData(alarmSync)
                    }
                }
            }
        }
        val createNotification = CreateNotification(
            applicationContext,
            " Scheduler ",
            "alarm data is uploaded to server $count"
        )
        createNotification.showNotification()
        count=0
        return Result.success()
    }
}




