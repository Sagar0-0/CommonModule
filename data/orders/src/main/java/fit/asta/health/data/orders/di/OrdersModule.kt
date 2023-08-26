package fit.asta.health.data.orders.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.orders.remote.OrdersApi
import fit.asta.health.data.orders.repo.OrdersRepo
import fit.asta.health.data.orders.repo.OrdersRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrdersModule {

    @Singleton
    @Provides
    fun provideOrdersApi(client: OkHttpClient): OrdersApi =
        NetworkUtil.getRetrofit(client).create(OrdersApi::class.java)

    @Singleton
    @Provides
    fun provideOrdersRepo(
        ordersApi: OrdersApi,
    ): OrdersRepo {
        return OrdersRepoImpl(
            ordersApi = ordersApi
        )
    }
}