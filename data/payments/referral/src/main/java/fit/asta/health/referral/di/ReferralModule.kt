package fit.asta.health.referral.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.referral.remote.ReferralApi
import fit.asta.health.referral.repo.ReferralRepo
import fit.asta.health.referral.repo.ReferralRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReferralModule {

    @Singleton
    @Provides
    fun provideReferralApi(client: OkHttpClient): ReferralApi =
        NetworkUtil.getRetrofit(client).create(ReferralApi::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class ReferralBindModule {
    @Binds
    abstract fun providesReferralRepo(referralRepoImpl: ReferralRepoImpl): ReferralRepo
}