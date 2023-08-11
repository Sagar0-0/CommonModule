package fit.asta.health.testimonials.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.testimonials.data.repo.TestimonialRepo
import fit.asta.health.testimonials.data.repo.TestimonialRepoImpl
import fit.asta.health.testimonials.data.remote.TestimonialApiService
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestimonialsModule {

    @Singleton
    @Provides
    fun provideTestimonialRepo(
        @ApplicationContext context: Context,
        remoteApi: TestimonialApiService
    ): TestimonialRepo {
        return TestimonialRepoImpl(
            context = context,
            remoteApi = remoteApi
        )
    }

    @Singleton
    @Provides
    fun provideTestimonialService(client: OkHttpClient): TestimonialApiService =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
            .create(TestimonialApiService::class.java)
}