package fit.asta.health.offers.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.offers.remote.OffersApi
import fit.asta.health.offers.repo.OffersRepo
import fit.asta.health.offers.repo.OffersRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OffersModule {

    @Singleton
    @Provides
    fun provideOffersApi(client: OkHttpClient): OffersApi =
        NetworkUtil.getRetrofit(client).create(OffersApi::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class OffersModuleBinds {
    @Binds
    abstract fun provideOffersRepo(offersRepoImpl: OffersRepoImpl): OffersRepo
}