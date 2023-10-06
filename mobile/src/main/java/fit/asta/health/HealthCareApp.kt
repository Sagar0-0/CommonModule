package fit.asta.health

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import fit.asta.health.common.utils.Constants.CHANNEL_ID
import fit.asta.health.common.utils.Constants.CHANNEL_ID_OTHER
import fit.asta.health.feature.scheduler.services.SchedulerWorker

@HiltAndroidApp
class HealthCareApp : /*MultiDexApplication*/ Application() {
//    @Inject
//    lateinit var workerFactory: HiltWorkerFactory

    companion object {
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
//        WorkManager.initialize(
//            this, Configuration.Builder().setWorkerFactory(
//                workerFactory
//            ).build()
//        )
        setupWorker(this)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.app_name) + "Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val missedAlarmChannel = NotificationChannel(
                CHANNEL_ID_OTHER,
                getString(R.string.app_name) + " Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
            manager.createNotificationChannel(missedAlarmChannel)
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
    WorkManager.getInstance(context).apply {
        // Run sync on app startup and ensure only one sync worker runs at any time
        enqueueUniqueWork(
            "SyncWorkName",
            ExistingWorkPolicy.KEEP,
            SchedulerWorker.startUpSyncWork(),
        )
    }
//   WorkManager.getInstance(context).apply {
//       enqueueUniquePeriodicWork(
//           "worker_for_upload_data", ExistingPeriodicWorkPolicy.UPDATE,
//           SchedulerWorker.startUpSyncWork()
//       )
//   }
}