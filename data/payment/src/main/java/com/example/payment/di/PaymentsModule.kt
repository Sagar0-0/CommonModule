package com.example.payment.di

import com.example.network.BuildConfig
import com.example.network.NetworkUtil
import com.example.payment.api.PaymentsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.payments.pay.repo.PaymentsRepo
import fit.asta.health.payments.pay.repo.PaymentsRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentsModule {

    @Singleton
    @Provides
    fun providePaymentsApi(client: OkHttpClient) =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
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