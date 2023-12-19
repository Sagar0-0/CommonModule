package fit.asta.health.discounts.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.discounts.remote.CouponsApi
import fit.asta.health.discounts.repo.CouponsRepo
import fit.asta.health.discounts.repo.CouponsRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CouponsModule {

    @Singleton
    @Provides
    fun provideCouponsApi(client: OkHttpClient): CouponsApi =
        NetworkUtil.getRetrofit(client).create(CouponsApi::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class CouponsRepoBinds {

    @Binds
    abstract fun provideCouponsRepo(couponsRepoImpl: CouponsRepoImpl): CouponsRepo

}