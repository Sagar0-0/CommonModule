package fit.asta.health.tools.walking.work

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.tools.walking.model.LocalRepo
import fit.asta.health.tools.walking.sensor.MeasurableSensor
import fit.asta.health.tools.walking.view.home.HomeUIState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class CountStepsService : Service() {
    @Inject
    lateinit var localRepo: LocalRepo

    @Inject
    lateinit var stepsSensor: MeasurableSensor

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    private val binder = LocalBinder()

    private val stepCountFlow = MutableStateFlow(HomeUIState())


    inner class LocalBinder : Binder() {
        fun getService(): CountStepsService = this@CountStepsService
        fun getStepCountFlow(): StateFlow<HomeUIState> = stepCountFlow
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking Steps...")
            .setContentText("Start Walking")
            .setSmallIcon(R.drawable.runing)
            .setOngoing(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        stepsSensor.startListening()
        stepsSensor.setOnSensorValuesChangedListener { step ->
            val date = LocalDate.now().dayOfMonth
            serviceScope.launch {
                val data = localRepo.getStepsData(date)
                if (data != null && data.date == date) {
                    if (data.initialSteps > 1) {
                        localRepo.updateStepsonRunning(
                            date = data.date,
                            all_steps = step[0].toInt() - data.initialSteps,
                        )
                    } else {
                        localRepo.updateSteps(date = date, step = step[0].toInt())
                    }
                    val updatedNotification = notification.setContentText(
                        "steps: ${step[0].toInt() - data.initialSteps}"
                    )
                    notificationManager.notify(1, updatedNotification.build())
                    stepCountFlow.value= stepCountFlow.value.copy(
                        distance = ((step[0].toInt() - data.initialSteps) / 1408),
                        steps = (step[0].toInt() - data.initialSteps),
                        duration = ((System.nanoTime() - data.time) / 1_000_000_000L / 60L)
                    )

                }
                else{

                }
            }
        }

        startForeground(1, notification.build())
    }



    override fun onDestroy() {
        super.onDestroy()
        stopSteps()
        stopSelf()
        serviceScope.cancel()
    }


    private fun stopSteps() {
        stepsSensor.stopListening()
    }

}