package fit.asta.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.feedback.model.FeedbackDataMapper
import fit.asta.health.feedback.model.FeedbackRepo
import fit.asta.health.feedback.model.FeedbackRepoImpl
import fit.asta.health.navigation.home.model.ToolsHomeDataMapper
import fit.asta.health.navigation.home.model.ToolsHomeRepo
import fit.asta.health.navigation.home.model.ToolsHomeRepoImpl
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.profile.model.ProfileDataMapper
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.ProfileRepoImpl
import fit.asta.health.testimonials.model.TestimonialDataMapper
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.TestimonialRepoImpl
import fit.asta.health.tools.sunlight.model.SunlightToolDataMapper
import fit.asta.health.tools.sunlight.model.SunlightToolRepo
import fit.asta.health.tools.sunlight.model.SunlightToolRepoImpl
import fit.asta.health.tools.water.model.WaterToolDataMapper
import fit.asta.health.tools.water.model.WaterToolRepo
import fit.asta.health.tools.water.model.WaterToolRepoImpl
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
    fun provideHomeToolsRep(
        remoteApi: RemoteApis,
        toolsHomeMapper: ToolsHomeDataMapper,
    ): ToolsHomeRepo {
        return ToolsHomeRepoImpl(
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
    fun provideTestimonialRepo(
        remoteApi: RemoteApis,
        testimonialMapper: TestimonialDataMapper,
    ): TestimonialRepo {
        return TestimonialRepoImpl(
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
    fun provideProfileRepo(
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

    @Singleton
    @Provides
    fun provideFeedbackDataMapper(): FeedbackDataMapper {
        return FeedbackDataMapper()
    }

    @Singleton
    @Provides
    fun provideFeedbackRepo(
        remoteApi: RemoteApis,
        feedbackMapper: FeedbackDataMapper,
    ): FeedbackRepo {
        return FeedbackRepoImpl(
            remoteApi = remoteApi,
            mapper = feedbackMapper
        )
    }
}

