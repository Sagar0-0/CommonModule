package fit.asta.health.tools.walking.work

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import fit.asta.health.R
import fit.asta.health.tools.walking.model.LocalRepo
import fit.asta.health.tools.walking.sensor.MeasurableSensor
import kotlinx.coroutines.*
import java.time.LocalDate
import javax.inject.Inject

class CountStepsService @Inject()constructor(
    private val stepsSensor: MeasurableSensor,
    private val localRepo: LocalRepo
): Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking Steps...")
            .setContentText("Steps: ")
            .setSmallIcon(R.drawable.runing)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
                        "steps: (${step[0].toInt() - data.initialSteps})"
                    )
                    notificationManager.notify(1, updatedNotification.build())
                }
            }
        }

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopSteps()
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }



    private fun stopSteps() {
        stepsSensor.stopListening()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}