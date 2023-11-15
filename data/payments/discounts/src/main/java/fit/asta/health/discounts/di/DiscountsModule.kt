package fit.asta.health.discounts.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.discounts.remote.DiscountsApi
import fit.asta.health.discounts.repo.DiscountsRepo
import fit.asta.health.discounts.repo.DiscountsRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscountsModule {

    @Singleton
    @Provides
    fun provideDiscountsApi(client: OkHttpClient): DiscountsApi =
        NetworkUtil.getRetrofit(client).create(DiscountsApi::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DiscountsRepoBinds {

    @Binds
    abstract fun provideDiscountsRepo(discountsRepoImpl: DiscountsRepoImpl): DiscountsRepo

}