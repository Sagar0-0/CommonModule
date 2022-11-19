package fit.asta.health.navigation.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.navigation.home.model.ToolsHomeDataMapper
import fit.asta.health.navigation.home.model.ToolsHomeRepo
import fit.asta.health.navigation.home.model.ToolsHomeRepoImpl
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.testimonials.model.TestimonialDataMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Singleton
    @Provides
    fun provideToolsHomeDataMapper(testimonialDataMapper: TestimonialDataMapper): ToolsHomeDataMapper {
        return ToolsHomeDataMapper(testimonialDataMapper)
    }

    @Singleton
    @Provides
    fun provideHomeToolsRep(
        remoteApi: RemoteApis,
        toolsHomeMapper: ToolsHomeDataMapper,
    ): ToolsHomeRepo {
        return ToolsHomeRepoImpl(
            remoteApi = remoteApi,
            mapper = toolsHomeMapper
        )
    }
}

