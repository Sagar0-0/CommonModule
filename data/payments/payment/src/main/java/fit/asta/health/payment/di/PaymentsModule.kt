package fit.asta.health.payment.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.payment.remote.PaymentsApi
import fit.asta.health.payment.repo.PaymentsRepo
import fit.asta.health.payment.repo.PaymentsRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentsModule {

    @Singleton
    @Provides
    fun providePaymentsApi(client: OkHttpClient): PaymentsApi =
        NetworkUtil.getRetrofit(client).create(PaymentsApi::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class PaymentsBindingModule {
    @Binds
    abstract fun providePaymentsRepo(paymentsRepoImpl: PaymentsRepoImpl): PaymentsRepo
}