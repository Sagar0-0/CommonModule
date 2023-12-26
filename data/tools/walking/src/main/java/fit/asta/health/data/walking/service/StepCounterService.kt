package fit.asta.health.data.walking.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.sensor.MeasurableSensor
import fit.asta.health.common.utils.Constants.NOTIFICATION_TAG
import fit.asta.health.data.walking.domain.usecase.DayUseCases
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import fit.asta.health.resources.drawables.R as DrwR
import fit.asta.health.resources.strings.R as StringR

@AndroidEntryPoint
class StepCounterService : LifecycleService() {

    @Inject
    lateinit var stepsSensor: MeasurableSensor


    @Inject
    lateinit var dayUseCases: DayUseCases

    private lateinit var controller: StepCounterController

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "step_counter_channel"
        private const val NOTIFICATION_ID = 0x1
        private const val PENDING_INTENT_ID = 0x1
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= VERSION_CODES.O) {
            val notificationChannel = createNotificationChannel()
            registerNotificationChannel(notificationChannel)
        }
        controller = StepCounterController(dayUseCases, lifecycleScope)

        // Create notification
        val notification = createNotification(controller.stats.value)
        startForeground(NOTIFICATION_ID, notification)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                controller.stats.collect {
                    val updatedNotification = createNotification(it)
                    notificationManager.notify(NOTIFICATION_ID, updatedNotification)
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        stepsSensor.startListening()
        stepsSensor.setOnSensorValuesChangedListener { step ->
            val eventStepCount = step.first().toInt()
            controller.onStepCountChanged(eventStepCount, LocalDate.now())
        }
    }

    private fun createNotification(state: StepCounterState): Notification = state.run {
        val title = resources.getQuantityString(StringR.plurals.step_count, steps, steps)
        val progress = if (steps == 0) 0 else (distanceTravelled * 100 / goalDistance).toInt()
        val content = getString(
            StringR.string.step_counter_stats, calorieBurned, distanceTravelled, progress
        )

        NotificationCompat.Builder(this@StepCounterService, NOTIFICATION_CHANNEL_ID)
            .setContentIntent(launchApplicationPendingIntent)
            .setSmallIcon(DrwR.drawable.runing)
            .setContentTitle(title)
            .setContentText(content)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSilent(true)
            .build()
    }

    private val launchApplicationPendingIntent
        get(): PendingIntent {
            val intent = Intent(this, Class.forName("fit.asta.health.MainActivity"))
            intent.putExtra(NOTIFICATION_TAG, "walking")
            val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            return PendingIntent.getActivity(this, PENDING_INTENT_ID, intent, flags)
        }


    @RequiresApi(VERSION_CODES.O)
    private fun createNotificationChannel(): NotificationChannel {
        val name = getString(StringR.string.steps)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        return NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
            setShowBadge(false)
        }
    }

    @RequiresApi(VERSION_CODES.O)
    private fun registerNotificationChannel(channel: NotificationChannel) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSteps()
        stopSelf()
    }


    private fun stopSteps() {
        stepsSensor.stopListening()
    }
}