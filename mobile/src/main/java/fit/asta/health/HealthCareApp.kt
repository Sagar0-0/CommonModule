package fit.asta.health

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.HiltAndroidApp
import fit.asta.health.common.utils.Constants.CHANNEL_ID
import fit.asta.health.common.utils.Constants.CHANNEL_ID_OTHER
import fit.asta.health.feature.scheduler.services.ShutdownBroadcastReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

@HiltAndroidApp
class HealthCareApp : /*MultiDexApplication*/ Application() {
//    @Inject
//    lateinit var workerFactory: HiltWorkerFactory

    companion object {
        var mContext: Context? = null
        lateinit var instance: HealthCareApp
            private set
    }

    val currentDate = MutableStateFlow<LocalDate>(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate() {
        super.onCreate()
        mContext = this
        instance = this
        registerMidnightTimer()
        createNotificationChannel()
        createNotificationChannelForActivity()
        registerShutdownBroadcast()

//        WorkManager.initialize(
//            this, Configuration.Builder().setWorkerFactory(
//                workerFactory
//            ).build()
//        )

    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun registerShutdownBroadcast(){
        val shutdownReceiver=ShutdownBroadcastReceiver()
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_SHUTDOWN)
            addAction("android.intent.action.QUICKBOOT_POWEROFF")
            addAction(Intent.ACTION_BOOT_COMPLETED)
        }
        registerReceiver(shutdownReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
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

    private fun registerMidnightTimer() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
        }
        registerReceiver(midnightBroadcastReceiver, intentFilter)
    }

    private val midnightBroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val today = LocalDate.now()
            if (today != currentDate.value) {
                currentDate.value = today
            }
        }
    }
}
