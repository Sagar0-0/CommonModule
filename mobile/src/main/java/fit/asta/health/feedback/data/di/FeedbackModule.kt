package fit.asta.health.feedback.data.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.di.IODispatcher
import fit.asta.health.feedback.data.remote.FeedbackApi
import fit.asta.health.feedback.data.repo.FeedbackRepo
import fit.asta.health.feedback.data.repo.FeedbackRepoImpl
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FeedbackModule {

    @Singleton
    @Provides
    fun provideFeedbackApi(client: OkHttpClient): FeedbackApi =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
            .create(FeedbackApi::class.java)


    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver =
        context.contentResolver

    @Singleton
    @Provides
    fun provideFeedbackRepo(
        remoteApi: FeedbackApi,
        contentResolver: ContentResolver,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): FeedbackRepo {
        return FeedbackRepoImpl(
            remoteApi = remoteApi,
            contentResolver = contentResolver,
            coroutineDispatcher = coroutineDispatcher
        )
    }
}

