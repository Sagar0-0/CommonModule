package com.example.wallet.di

import com.example.wallet.api.WalletApi
import com.example.wallet.repo.WalletRepo
import com.example.wallet.repo.WalletRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.network.utils.NetworkUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalletModule {

    @Singleton
    @Provides
    fun provideWalletApi(): WalletApi =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL)
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