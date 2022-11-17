package fit.asta.health.scheduler.services

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.di.DatabaseModule

@AndroidEntryPoint
class RescheduleAlarmService : LifecycleService() {

    //    @Inject
//    lateinit var alarmRepository: AlarmRepository

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val databaseModule = DatabaseModule()
        val database = databaseModule.provideAlarmDatabase(applicationContext)
        val dao = database.alarmDao()
        dao.getAll().asLiveData().observe(
            this
        ) { alarmItems ->
            for (alarm in alarmItems) {
                if (alarm.status) {
                    alarm.schedule(applicationContext)
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