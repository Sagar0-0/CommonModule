package fit.asta.health.scheduler.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.scheduler.model.api.SchedulerApi
import fit.asta.health.scheduler.model.api.SchedulerRestApi
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
}