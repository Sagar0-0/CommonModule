package fit.asta.health.testimonials.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.testimonials.model.TestimonialDataMapper
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.TestimonialRepoImpl
import fit.asta.health.testimonials.model.api.TestimonialApi
import fit.asta.health.testimonials.model.api.TestimonialRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestimonialsModule {

    @Singleton
    @Provides
    fun provideTestimonialApi(client: OkHttpClient): TestimonialApi {
        return TestimonialRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }

    @Singleton
    @Provides
    fun provideTestimonialDataMapper(): TestimonialDataMapper {
        return TestimonialDataMapper()
    }

    @Singleton
    @Provides
    fun provideTestimonialRepo(
        @ApplicationContext context: Context,
        remoteApi: TestimonialApi,
        testimonialMapper: TestimonialDataMapper,
    ): TestimonialRepo {
        return TestimonialRepoImpl(
            context = context,
            remoteApi = remoteApi,
            mapper = testimonialMapper
        )
    }
}