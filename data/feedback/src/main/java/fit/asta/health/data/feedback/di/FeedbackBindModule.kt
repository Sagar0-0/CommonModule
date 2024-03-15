package fit.asta.health.data.feedback.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.feedback.repo.FeedbackRepo
import fit.asta.health.data.feedback.repo.FeedbackRepoImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedbackBindModule {
    @Binds
    abstract fun provideFeedbackRepo(feedbackRepoImpl: FeedbackRepoImpl): FeedbackRepo
}