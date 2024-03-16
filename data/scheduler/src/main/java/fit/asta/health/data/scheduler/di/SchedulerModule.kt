package fit.asta.health.data.scheduler.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.scheduler.local.AlarmDatabase
import fit.asta.health.data.scheduler.remote.SchedulerApi
import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import fit.asta.health.data.scheduler.repo.AlarmBackendRepoImpl
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.data.scheduler.repo.AlarmLocalRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {
    @Singleton
    @Provides
    fun provideSchedulerApiService(client: OkHttpClient): SchedulerApi =
        NetworkUtil.getRetrofit(client).create(SchedulerApi::class.java)

    @Singleton
    @Provides
    fun provideRepo(db: AlarmDatabase): AlarmLocalRepo {
        return AlarmLocalRepoImpl(db.alarmDao(), db.alarmInstanceDao())
    }


    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return ContextCompat.getSystemService(context, AlarmManager::class.java) as AlarmManager
    }

    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return ContextCompat.getSystemService(
            context, NotificationManager::class.java
        ) as NotificationManager
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SchedulerBindsModule {

    @Binds
    abstract fun provideAlarmBackendRepo(alarmBackendRepoImpl: AlarmBackendRepoImpl): AlarmBackendRepo

}
