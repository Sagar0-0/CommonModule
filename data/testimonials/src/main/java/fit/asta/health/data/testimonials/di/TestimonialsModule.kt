package fit.asta.health.data.testimonials.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.testimonials.remote.TestimonialApiService
import fit.asta.health.data.testimonials.repo.TestimonialRepo
import fit.asta.health.data.testimonials.repo.TestimonialRepoImpl
import fit.asta.health.network.utils.NetworkUtil
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
        NetworkUtil.getRetrofit(client).create(TestimonialApiService::class.java)
}