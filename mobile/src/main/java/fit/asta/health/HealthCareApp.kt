package fit.asta.health

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import fit.asta.health.common.db.AppDb
import fit.asta.health.notify.util.createNotificationChannel
import fit.asta.health.common.utils.getUriFromResourceId

@HiltAndroidApp
class HealthCareApp : MultiDexApplication() {

    companion object {
        const val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
        var mContext: Context? = null
        lateinit var appDb: AppDb
        lateinit var instance: HealthCareApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        instance = this
        setupDb()
        createNotificationChannel()

        /*this.createNotificationChannel(
            getString(R.string.breakfast_notification_channel_id),
            getString(R.string.breakfast_notification_channel_name)
        )*/
    }

    private fun setupDb() {
        appDb = AppDb.createDatabase(this.applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.createNotificationChannel(
                getString(R.string.title_reminder),
                null,
                NotificationManagerCompat.IMPORTANCE_HIGH,
                getUriFromResourceId(R.raw.alarm),
                showBadge = true,
                isVibrate = true
            )
        }
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
}