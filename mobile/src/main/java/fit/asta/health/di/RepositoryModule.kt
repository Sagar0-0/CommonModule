package fit.asta.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.navigation.home.model.ToolsHomeDataMapper
import fit.asta.health.navigation.home.model.ToolsHomeRepository
import fit.asta.health.navigation.home.model.ToolsHomeRepositoryImpl
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.profile.model.ProfileDataMapper
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.ProfileRepoImpl
import fit.asta.health.testimonials.model.TestimonialDataMapper
import fit.asta.health.testimonials.model.TestimonialRepository
import fit.asta.health.testimonials.model.TestimonialRepositoryImpl
import fit.asta.health.tools.water.model.WaterToolDataMapper
import fit.asta.health.tools.water.model.WaterToolRepository
import fit.asta.health.tools.water.model.WaterToolRepositoryImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideToolsHomeDataMapper(): ToolsHomeDataMapper {
        return ToolsHomeDataMapper()
    }

    @Singleton
    @Provides
    fun provideHomeToolsRepository(
        remoteApi: RemoteApis,
        toolsHomeMapper: ToolsHomeDataMapper,
    ): ToolsHomeRepository {
        return ToolsHomeRepositoryImpl(
            remoteApi = remoteApi,
            mapper = toolsHomeMapper
        )
    }

    @Singleton
    @Provides
    fun provideTestimonialDataMapper(): TestimonialDataMapper {
        return TestimonialDataMapper()
    }

    @Singleton
    @Provides
    fun provideTestimonialRepository(
        remoteApi: RemoteApis,
        testimonialMapper: TestimonialDataMapper,
    ): TestimonialRepository {
        return TestimonialRepositoryImpl(
            remoteApi = remoteApi,
            mapper = testimonialMapper
        )
    }

    @Singleton
    @Provides
    fun provideProfileMapper(): ProfileDataMapper {
        return ProfileDataMapper()
    }

    @Singleton
    @Provides
    @Named("profile_repository")
    fun provideProfileRepository(
        remoteApi: RemoteApis,
        recipeMapper: ProfileDataMapper,
    ): ProfileRepo {
        return ProfileRepoImpl(
            remoteApi = remoteApi,
            mapper = recipeMapper
        )
    }

    @Singleton
    @Provides
    fun provideWaterToolDataMapper(): WaterToolDataMapper {
        return WaterToolDataMapper()
    }

    @Singleton
    @Provides
    fun provideWaterToolRepository(
        remoteApi: RemoteApis,
        waterToolMapper: WaterToolDataMapper,
    ): WaterToolRepository {
        return WaterToolRepositoryImpl(
            remoteApi = remoteApi,
            mapper = waterToolMapper
        )
    }
}

