package fit.asta.health.tools.sunlight.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.tools.sunlight.model.SunlightToolDataMapper
import fit.asta.health.tools.sunlight.model.SunlightToolRepo
import fit.asta.health.tools.sunlight.model.SunlightToolRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SunlightModule {

    @Singleton
    @Provides
    fun provideSunlightToolDataMapper(): SunlightToolDataMapper {
        return SunlightToolDataMapper()
    }

    @Singleton
    @Provides
    fun provideSunlightToolRepo(
        remoteApi: RemoteApis,
        sunlightToolMapper: SunlightToolDataMapper,
    ): SunlightToolRepo {
        return SunlightToolRepoImpl(
            remoteApi = remoteApi,
            mapper = sunlightToolMapper
        )
    }
}