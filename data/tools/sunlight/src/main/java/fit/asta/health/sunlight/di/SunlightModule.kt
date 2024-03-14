package fit.asta.health.sunlight.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.sunlight.remote.SunlightApi
import fit.asta.health.sunlight.repo.SunlightRepo
import fit.asta.health.sunlight.repo.SunlightRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SunlightModule {

    @Singleton
    @Provides
    fun provideSunlightApi(client: OkHttpClient): SunlightApi =
        NetworkUtil.getRetrofit(client).create(SunlightApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SunlightBindsModule{
    @Binds
    abstract fun provideSunlightRepo(sunlightRepo: SunlightRepoImpl): SunlightRepo
}

