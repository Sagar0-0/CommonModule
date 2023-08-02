package fit.asta.health.payments.razorpay.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.payments.razorpay.api.PaymentsApi
import fit.asta.health.payments.razorpay.api.PaymentsRestApi
import fit.asta.health.payments.razorpay.repo.PaymentsRepo
import fit.asta.health.payments.razorpay.repo.PaymentsRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentsModule {

    @Singleton
    @Provides
    fun providePaymentsApi(client: OkHttpClient): PaymentsApi {
        return PaymentsRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }

    @Singleton
    @Provides
    fun providePaymentsRepo(
        remoteApi: PaymentsApi,
    ): PaymentsRepo {
        return PaymentsRepoImpl(
            remoteApi = remoteApi
        )
    }
}