package fit.asta.health

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.BackoffPolicy
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import fit.asta.health.scheduler.services.SchedulerWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class HealthCareApp : /*MultiDexApplication*/ Application() {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    companion object {
        const val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
        var mContext: Context? = null
        lateinit var instance: HealthCareApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        instance = this
        createNotificationChannel()
        createNotificationChannelForActivity()
        WorkManager.initialize(
            this, Configuration.Builder().setWorkerFactory(
                workerFactory
            ).build()
        )
        setupWorker(this)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.app_name) + "Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotificationChannelForActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

fun setupWorker(context: Context) {
    val constraint = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    val workRequest = PeriodicWorkRequestBuilder<SchedulerWorker>(1, TimeUnit.HOURS)
        .setConstraints(constraint)
        .setBackoffCriteria(BackoffPolicy.LINEAR, 1, TimeUnit.MINUTES)
        .build()
    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "worker_for_upload_data", ExistingPeriodicWorkPolicy.UPDATE, workRequest
    )
}