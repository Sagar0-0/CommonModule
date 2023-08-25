package fit.asta.health.tools.sunlight.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.tools.sunlight.model.SunlightToolDataMapper
import fit.asta.health.tools.sunlight.model.SunlightToolRepo
import fit.asta.health.tools.sunlight.model.SunlightToolRepoImpl
import fit.asta.health.tools.sunlight.model.api.SunlightApi
import fit.asta.health.tools.sunlight.model.api.SunlightRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SunlightModule {

    @Singleton
    @Provides
    fun provideSunlightApi(client: OkHttpClient): SunlightApi {
        return SunlightRestApi(client)
    }

    @Singleton
    @Provides
    fun provideSunlightToolDataMapper(): SunlightToolDataMapper {
        return SunlightToolDataMapper()
    }

    @Singleton
    @Provides
    fun provideSunlightToolRepo(
        remoteApi: SunlightApi,
    ): SunlightToolRepo {
        return SunlightToolRepoImpl(
            remoteApi = remoteApi,
        )
    }
}