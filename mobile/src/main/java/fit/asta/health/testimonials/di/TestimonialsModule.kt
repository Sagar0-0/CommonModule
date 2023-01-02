package fit.asta.health.testimonials.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.testimonials.model.TestimonialDataMapper
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.TestimonialRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestimonialsModule {

    @Singleton
    @Provides
    fun provideTestimonialDataMapper(): TestimonialDataMapper {
        return TestimonialDataMapper()
    }

    @Singleton
    @Provides
    fun provideTestimonialRepo(
        @ApplicationContext context: Context,
        remoteApi: RemoteApis,
        testimonialMapper: TestimonialDataMapper,
    ): TestimonialRepo {
        return TestimonialRepoImpl(
            context = context,
            remoteApi = remoteApi,
            mapper = testimonialMapper
        )
    }
}

