package fit.asta.health

import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.multidex.MultiDexApplication
import fit.asta.health.db.AppDb
import fit.asta.health.di.*
import fit.asta.health.notify.util.createNotificationChannel
import fit.asta.health.utils.getUriFromResourceId
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HealthCareApp : MultiDexApplication() {

    companion object {
        var mContext: Context? = null
        lateinit var appDb: AppDb
        lateinit var instance: HealthCareApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        instance = this
        startKoin()
        setupDb()

        /*this.createNotificationChannel(
            getString(R.string.breakfast_notification_channel_id),
            getString(R.string.breakfast_notification_channel_name)
        )*/
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@HealthCareApp)
            modules(
                appModule,
                homeModule,
                categoryModule,
                todayModule,
                courseModule,
                courseDetailsModule,
                exerciseModule,
                profileModule,
                tagsModule,
                testimonialsModule,
                subscriptionModule,
                multiSelectionModule,
                scheduleModule
            )
        }
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
}