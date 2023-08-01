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
import fit.asta.health.scheduler.model.SpotifyRepo
import fit.asta.health.scheduler.model.SpotifyRepoImpl
import fit.asta.health.scheduler.model.api.SchedulerApi
import fit.asta.health.scheduler.model.api.SchedulerRestApi
import fit.asta.health.scheduler.model.api.spotify.SpotifyApi
import fit.asta.health.scheduler.model.api.spotify.SpotifyRestImpl
import fit.asta.health.scheduler.model.db.AlarmDatabase
import fit.asta.health.scheduler.util.Constants
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

    @Singleton
    @Provides
    fun provideSpotifyApi(client: OkHttpClient): SpotifyApi {
        return SpotifyRestImpl(baseUrl = Constants.SPOTIFY_BASE_URL, client = client)
    }

    @Singleton
    @Provides
    fun provideSpotifyRepo(api: SpotifyApi): SpotifyRepo {
        return SpotifyRepoImpl(spotifyApi = api)
    }
}