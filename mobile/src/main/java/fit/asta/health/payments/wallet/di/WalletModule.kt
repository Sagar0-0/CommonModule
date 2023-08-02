package fit.asta.health.payments.wallet.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.payments.wallet.api.WalletApi
import fit.asta.health.payments.wallet.api.WalletRestApi
import fit.asta.health.payments.wallet.repo.WalletRepo
import fit.asta.health.payments.wallet.repo.WalletRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalletModule {

    @Singleton
    @Provides
    fun provideWalletApi(client: OkHttpClient): WalletApi {
        return WalletRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }

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