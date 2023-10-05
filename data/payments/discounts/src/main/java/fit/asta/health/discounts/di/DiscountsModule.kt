package fit.asta.health.discounts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.discounts.remote.DiscountsApi
import fit.asta.health.discounts.repo.DiscountsRepo
import fit.asta.health.discounts.repo.DiscountsRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscountsModule {

    @Singleton
    @Provides
    fun provideDiscountsApi(client: OkHttpClient): DiscountsApi =
        NetworkUtil.getRetrofit(client).create(DiscountsApi::class.java)

    @Singleton
    @Provides
    fun provideDiscountsRepo(
        remoteApi: DiscountsApi,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): DiscountsRepo {
        return DiscountsRepoImpl(
            remoteApi = remoteApi,
            coroutineDispatcher = coroutineDispatcher
        )
    }
}