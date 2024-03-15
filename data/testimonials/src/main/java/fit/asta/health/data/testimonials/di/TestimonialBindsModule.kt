package fit.asta.health.data.testimonials.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.testimonials.repo.TestimonialRepo
import fit.asta.health.data.testimonials.repo.TestimonialRepoImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class TestimonialBindsModule {
    @Binds
    abstract fun provideTestimonialRepo(testimonialRepoImpl: TestimonialRepoImpl): TestimonialRepo
}