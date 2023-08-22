package fit.asta.health.subscription.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.subscription.remote.SubscriptionApi
import fit.asta.health.subscription.repo.SubscriptionRepo
import fit.asta.health.subscription.repo.SubscriptionRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubscriptionModule {

    @Singleton
    @Provides
    fun provideSubscriptionApi(): SubscriptionApi =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL)
            .create(SubscriptionApi::class.java)

    @Singleton
    @Provides
    fun provideSubRepo(
        remoteApi: SubscriptionApi,
    ): SubscriptionRepo {
        return SubscriptionRepoImpl(
            remoteApi = remoteApi
        )
    }
}