package fit.asta.health.scheduler.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.scheduler.model.AlarmBackendRepo
import fit.asta.health.scheduler.model.AlarmBackendRepoImp
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.AlarmLocalRepoImp
import fit.asta.health.scheduler.model.api.SchedulerApi
import fit.asta.health.scheduler.model.api.SchedulerRestApi
import fit.asta.health.scheduler.model.db.AlarmDatabase
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {

    @Singleton
    @Provides
    fun provideSchedulerApi(client: OkHttpClient): SchedulerApi {
        return SchedulerRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }
    @Singleton
    @Provides
    fun provideBackendRepo(
        remoteApi: SchedulerApi
    ): AlarmBackendRepo {
        return AlarmBackendRepoImp(
            remoteApi = remoteApi
        )
    }

//    @Singleton
//    @Provides
//    fun provideAlarmDatabase(
//        @ApplicationContext context: Context,
//    ) = Room.databaseBuilder(
//        context,
//        AlarmDatabase::class.java,
//        "alarm-database"
//    ).build()

    @Singleton
    @Provides
    fun provideRepo(db: AlarmDatabase): AlarmLocalRepo {
        return AlarmLocalRepoImp(db.alarmDao())
    }

}