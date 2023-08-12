package fit.asta.health.feedback.data.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.feedback.data.remote.api.FeedbackApi
import fit.asta.health.feedback.data.remote.api.FeedbackRestApi
import fit.asta.health.feedback.data.repo.FeedbackRepo
import fit.asta.health.feedback.data.repo.FeedbackRepoImpl
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


    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver =
        context.contentResolver

    @Singleton
    @Provides
    fun provideFeedbackRepo(
        remoteApi: FeedbackApi,
        contentResolver: ContentResolver
    ): FeedbackRepo {
        return FeedbackRepoImpl(
            remoteApi = remoteApi,
            contentResolver = contentResolver
        )
    }
}

