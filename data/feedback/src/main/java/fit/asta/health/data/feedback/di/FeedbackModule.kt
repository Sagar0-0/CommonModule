package fit.asta.health.data.feedback.di

import android.content.ContentResolver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.data.feedback.remote.FeedbackApi
import fit.asta.health.data.feedback.repo.FeedbackRepo
import fit.asta.health.data.feedback.repo.FeedbackRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FeedbackModule {

    @Singleton
    @Provides
    fun provideFeedbackApi(client: OkHttpClient): FeedbackApi =
        NetworkUtil.getRetrofit(client).create(FeedbackApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedbackBindModule{
    @Binds
    abstract fun provideFeedbackRepo(feedbackRepoImpl:FeedbackRepoImpl): FeedbackRepo
}

