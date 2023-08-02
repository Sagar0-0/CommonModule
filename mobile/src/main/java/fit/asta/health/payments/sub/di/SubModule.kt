package fit.asta.health.payments.sub.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.payments.sub.api.SubscriptionApi
import fit.asta.health.payments.sub.api.SubscriptionRestApi
import fit.asta.health.payments.sub.repo.SubscriptionRepo
import fit.asta.health.payments.sub.repo.SubscriptionRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubModule {

    @Singleton
    @Provides
    fun provideSubApi(client: OkHttpClient): SubscriptionApi {
        return SubscriptionRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }

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