package fit.asta.health.scheduler.data.di

import android.app.AlarmManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.scheduler.data.api.SchedulerApiService
import fit.asta.health.scheduler.data.db.AlarmDatabase
import fit.asta.health.scheduler.data.repo.AlarmBackendRepo
import fit.asta.health.scheduler.data.repo.AlarmBackendRepoImp
import fit.asta.health.scheduler.data.repo.AlarmLocalRepo
import fit.asta.health.scheduler.data.repo.AlarmLocalRepoImp
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {


    @Singleton
    @Provides
    fun provideSchedulerApiService(client: OkHttpClient): SchedulerApiService =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
            .create(SchedulerApiService::class.java)

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