package fit.asta.health.offers.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.offers.remote.OffersApi
import fit.asta.health.offers.repo.OffersRepo
import fit.asta.health.offers.repo.OffersRepoImpl
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OffersModule {

    @Singleton
    @Provides
    fun provideOffersApi(client: OkHttpClient): OffersApi =
        NetworkUtil.getRetrofit(client).create(OffersApi::class.java)

    @Singleton
    @Provides
    fun provideOffersRepo(
        remoteApi: OffersApi,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): OffersRepo {
        return OffersRepoImpl(
            remoteApi = remoteApi,
            coroutineDispatcher = coroutineDispatcher
        )
    }
}