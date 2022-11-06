package fit.asta.health.tools.water.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.tools.water.model.WaterToolDataMapper
import fit.asta.health.tools.water.model.WaterToolRepo
import fit.asta.health.tools.water.model.WaterToolRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaterModule {

    @Singleton
    @Provides
    fun provideWaterToolDataMapper(): WaterToolDataMapper {
        return WaterToolDataMapper()
    }

    @Singleton
    @Provides
    fun provideWaterToolRepo(
        remoteApi: RemoteApis,
        waterToolMapper: WaterToolDataMapper,
    ): WaterToolRepo {
        return WaterToolRepoImpl(
            remoteApi = remoteApi,
            mapper = waterToolMapper
        )
    }
}