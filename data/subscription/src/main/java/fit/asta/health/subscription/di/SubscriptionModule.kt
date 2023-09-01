package fit.asta.health.subscription.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.subscription.remote.SubscriptionApi
import fit.asta.health.subscription.repo.SubscriptionRepo
import fit.asta.health.subscription.repo.SubscriptionRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubscriptionModule {

    @Singleton
    @Provides
    fun provideSubscriptionApi(client: OkHttpClient): SubscriptionApi =
        NetworkUtil.getRetrofit(client).create(SubscriptionApi::class.java)


}

@Module
@InstallIn(SingletonComponent::class)
abstract class SubModule {
    @Binds
    abstract fun provideSubRepo(
        subscriptionRepoImpl: SubscriptionRepoImpl
    ): SubscriptionRepo
}