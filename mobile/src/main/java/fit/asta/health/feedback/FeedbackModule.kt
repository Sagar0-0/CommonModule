package fit.asta.health.feedback

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.feedback.data.utils.FeedbackDataMapper
import fit.asta.health.feedback.data.repo.FeedbackRepo
import fit.asta.health.feedback.data.repo.FeedbackRepoImpl
import fit.asta.health.feedback.data.remote.FeedbackApi
import fit.asta.health.feedback.data.remote.FeedbackRestApi
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
        @ApplicationContext context: Context
    ): FeedbackRepo {
        return FeedbackRepoImpl(
            remoteApi = remoteApi,
            mapper = feedbackMapper,
            context = context
        )
    }
}

