package fit.asta.health.feedback.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.feedback.model.FeedbackDataMapper
import fit.asta.health.feedback.model.FeedbackRepo
import fit.asta.health.feedback.model.FeedbackRepoImpl
import fit.asta.health.network.api.RemoteApis
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedbackModule {

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

