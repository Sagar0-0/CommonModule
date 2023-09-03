package fit.asta.health.data.scheduler.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
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
        remoteApi: SchedulerApiService
    ): AlarmBackendRepo {
        return AlarmBackendRepoImp(
            remoteApi = remoteApi
        )
    }


    @Singleton
    @Provides
    fun provideRepo(db: AlarmDatabase): AlarmLocalRepo {
        return AlarmLocalRepoImp(db.alarmDao())
    }


    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

//    @Singleton
//    @Provides
//    fun provideAlarmUtils(
//        @ApplicationContext context: Context,
//        alarmManager: AlarmManager
//    ): AlarmUtils {
//        return AlarmUtilsImp(
//            alarmManager = alarmManager,
//            context = context,
//            calendar = Calendar.getInstance()
//        )
//    }
}