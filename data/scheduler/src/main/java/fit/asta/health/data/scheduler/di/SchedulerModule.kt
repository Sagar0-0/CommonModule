package fit.asta.health.data.scheduler.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.scheduler.db.AlarmDatabase
import fit.asta.health.data.scheduler.remote.SchedulerApiService
import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import fit.asta.health.data.scheduler.repo.AlarmBackendRepoImp
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.data.scheduler.repo.AlarmLocalRepoImp
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {


    @Singleton
    @Provides
    fun provideSchedulerApiService(client: OkHttpClient): SchedulerApiService =
        NetworkUtil.getRetrofit(client).create(SchedulerApiService::class.java)

    @Singleton
    @Provides
    fun provideBackendRepo(
        remoteApi: SchedulerApiService,
        @ApplicationContext context: Context
    ): AlarmBackendRepo {
        return AlarmBackendRepoImp(
            context = context,
            remoteApi = remoteApi
        )
    }


    @Singleton
    @Provides
    fun provideRepo(db: AlarmDatabase): AlarmLocalRepo {
        return AlarmLocalRepoImp(db.alarmDao(), db.alarmInstanceDao())
    }


    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return ContextCompat.getSystemService(context, AlarmManager::class.java) as AlarmManager
//        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return ContextCompat.getSystemService(
            context, NotificationManager::class.java
        ) as NotificationManager
//        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}