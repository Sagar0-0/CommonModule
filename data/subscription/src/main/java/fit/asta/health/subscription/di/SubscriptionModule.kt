package fit.asta.health.subscription.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.subscription.remote.SubscriptionApi
import fit.asta.health.subscription.repo.SubscriptionRepo
import fit.asta.health.subscription.repo.SubscriptionRepoImpl
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubscriptionModule {

    @Singleton
    @Provides
    fun provideSubscriptionApi(client: OkHttpClient): SubscriptionApi =
        NetworkUtil.getRetrofit(client).create(SubscriptionApi::class.java)

    @Singleton
    @Provides
    fun provideSubRepo(
        remoteApi: SubscriptionApi,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): SubscriptionRepo {
        return SubscriptionRepoImpl(
            remoteApi = remoteApi,
            coroutineDispatcher = coroutineDispatcher
        )
    }
}