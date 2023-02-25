package fit.asta.health.feedback.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.feedback.model.FeedbackDataMapper
import fit.asta.health.feedback.model.FeedbackRepo
import fit.asta.health.feedback.model.FeedbackRepoImpl
import fit.asta.health.feedback.model.api.FeedbackApi
import fit.asta.health.feedback.model.api.FeedbackRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FeedbackModule {

    @Singleton
    @Provides
    fun provideFeedbackApi(client: OkHttpClient): FeedbackApi {
        return FeedbackRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }

    @Singleton
    @Provides
    fun provideFeedbackDataMapper(): FeedbackDataMapper {
        return FeedbackDataMapper()
    }

    @Singleton
    @Provides
    fun provideFeedbackRepo(
        remoteApi: FeedbackApi,
        feedbackMapper: FeedbackDataMapper,
    ): FeedbackRepo {
        return FeedbackRepoImpl(
            remoteApi = remoteApi,
            mapper = feedbackMapper
        )
    }
}

