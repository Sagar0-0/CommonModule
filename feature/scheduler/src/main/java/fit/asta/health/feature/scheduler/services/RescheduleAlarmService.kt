package fit.asta.health.feature.scheduler.services

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.data.scheduler.db.AlarmInstanceDao
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.feature.scheduler.util.StateManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class RescheduleAlarmService : LifecycleService() {


    @Inject
    lateinit var alarmLocalRepo: AlarmLocalRepo

    @Inject
    lateinit var alarmInstanceDao: AlarmInstanceDao

    @Inject
    lateinit var stateManager: StateManager

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        GlobalScope.launch(Dispatchers.IO) {
            alarmLocalRepo.getAllAlarmList()?.forEach { alarm ->
                if (alarm.status) {
                    stateManager.registerAlarm(
                        this@RescheduleAlarmService,
                        alarm,
                        (alarm.skipDate == LocalDate.now().dayOfMonth)
                    )
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