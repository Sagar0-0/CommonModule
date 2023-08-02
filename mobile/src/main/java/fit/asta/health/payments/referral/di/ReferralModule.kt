package fit.asta.health.payments.referral.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.payments.referral.api.ReferralApi
import fit.asta.health.payments.referral.api.ReferralRestApi
import fit.asta.health.payments.referral.repo.ReferralRepo
import fit.asta.health.payments.referral.repo.ReferralRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReferralModule {

    @Singleton
    @Provides
    fun provideReferralApi(client: OkHttpClient): ReferralApi {
        return ReferralRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }

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