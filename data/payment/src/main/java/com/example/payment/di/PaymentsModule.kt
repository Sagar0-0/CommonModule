package com.example.payment.di

import com.example.payment.api.PaymentsApi
import com.example.payment.repo.PaymentsRepo
import com.example.payment.repo.PaymentsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.BuildConfig
import fit.asta.health.network.utils.NetworkUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentsModule {

    @Singleton
    @Provides
    fun providePaymentsApi() =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL)
            .create(PaymentsApi::class.java)

    @Singleton
    @Provides
    fun providePaymentsRepo(
        remoteApi: PaymentsApi,
    ): PaymentsRepo {
        return PaymentsRepoImpl(
            remoteApi = remoteApi
        )
    }
}