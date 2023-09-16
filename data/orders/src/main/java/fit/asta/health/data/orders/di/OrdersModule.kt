package fit.asta.health.data.orders.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.data.orders.remote.OrdersApi
import fit.asta.health.data.orders.repo.OrdersRepo
import fit.asta.health.data.orders.repo.OrdersRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrdersModule {

    @Singleton
    @Provides
    fun provideOrdersApi(client: OkHttpClient): OrdersApi =
        NetworkUtil.getRetrofit(client).create(OrdersApi::class.java)
}
@Module
@InstallIn(SingletonComponent::class)
abstract class OrdersBindRepo{
    @Binds
    abstract fun provideOrdersRepo(ordersRepoImpl: OrdersRepoImpl): OrdersRepo
}
