package fit.asta.health.payment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.payment.remote.PaymentsApi
import fit.asta.health.payment.repo.PaymentsRepo
import fit.asta.health.payment.repo.PaymentsRepoImpl
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentsModule {

    @Singleton
    @Provides
    fun providePaymentsApi(client: OkHttpClient): PaymentsApi =
        NetworkUtil.getRetrofit(client).create(PaymentsApi::class.java)

    @Singleton
    @Provides
    fun providePaymentsRepo(
        remoteApi: PaymentsApi,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): PaymentsRepo {
        return PaymentsRepoImpl(
            remoteApi = remoteApi,
            coroutineDispatcher = coroutineDispatcher
        )
    }
}