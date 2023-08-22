package com.example.referral.di

import com.example.referral.api.ReferralApi
import com.example.referral.repo.ReferralRepo
import com.example.referral.repo.ReferralRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.network.utils.NetworkUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReferralModule {

    @Singleton
    @Provides
    fun provideReferralApi(): ReferralApi =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL)
            .create(ReferralApi::class.java)

    @Singleton
    @Provides
    fun provideReferralRepo(
        remoteApi: ReferralApi,
    ): ReferralRepo {
        return ReferralRepoImpl(
            remoteApi = remoteApi
        )
    }
}