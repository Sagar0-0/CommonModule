package fit.asta.health.wallet.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.wallet.remote.WalletApi
import fit.asta.health.wallet.repo.WalletRepo
import fit.asta.health.wallet.repo.WalletRepoImpl
import kotlinx.coroutines.CoroutineDispatcher
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
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): WalletRepo {
        return WalletRepoImpl(
            remoteApi = remoteApi,
            coroutineDispatcher = coroutineDispatcher
        )
    }
}