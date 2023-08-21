package com.example.wallet.di

import com.example.wallet.api.WalletApi
import com.example.wallet.repo.WalletRepo
import com.example.wallet.repo.WalletRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalletModule {

    @Singleton
    @Provides
    fun provideWalletApi(client: OkHttpClient): WalletApi =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
            .create(WalletApi::class.java)


    @Singleton
    @Provides
    fun provideWalletRepo(
        remoteApi: WalletApi,
    ): WalletRepo {
        return WalletRepoImpl(
            remoteApi = remoteApi
        )
    }
}