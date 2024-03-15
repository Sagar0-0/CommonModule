package fit.asta.health.data.testimonials.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.testimonials.remote.TestimonialApi
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestimonialsModule {

    @Singleton
    @Provides
    fun provideTestimonialService(client: OkHttpClient): TestimonialApi =
        NetworkUtil.getRetrofit(client).create(TestimonialApi::class.java)
}