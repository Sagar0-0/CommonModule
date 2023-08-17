package fit.asta.health.scheduler.services

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.scheduler.data.repo.AlarmLocalRepo
import fit.asta.health.scheduler.data.repo.AlarmUtils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class RescheduleAlarmService : LifecycleService() {

    @Inject
    lateinit var alarmUtils: AlarmUtils

    @Inject
    lateinit var alarmLocalRepo: AlarmLocalRepo

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        GlobalScope.launch(Dispatchers.IO) {
            alarmLocalRepo.getAllAlarmList().forEach { alarm ->
                if (alarm.skipDate != LocalDate.now().dayOfMonth) {
                    alarmUtils.scheduleAlarm(alarm)
                }
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }
}