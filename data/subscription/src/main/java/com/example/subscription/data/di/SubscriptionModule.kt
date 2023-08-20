package com.example.subscription.data.di

import com.example.network.BuildConfig
import com.example.network.NetworkUtil
import com.example.subscription.data.remote.SubscriptionApi
import com.example.subscription.data.repo.SubscriptionRepo
import com.example.subscription.data.repo.SubscriptionRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubscriptionModule {

    @Singleton
    @Provides
    fun provideSubscriptionApi(client: OkHttpClient): SubscriptionApi =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
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