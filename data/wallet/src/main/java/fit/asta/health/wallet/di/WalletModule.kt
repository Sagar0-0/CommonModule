package fit.asta.health.wallet.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.wallet.api.WalletApi
import fit.asta.health.wallet.repo.WalletRepo
import fit.asta.health.wallet.repo.WalletRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalletModule {

    @Singleton
    @Provides
    fun provideWalletApi(client: OkHttpClient): WalletApi =
        NetworkUtil.getRetrofit(client).create(WalletApi::class.java)


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