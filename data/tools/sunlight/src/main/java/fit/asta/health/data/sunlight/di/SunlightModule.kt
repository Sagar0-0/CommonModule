package fit.asta.health.data.sunlight.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.sunlight.model.SunlightToolDataMapper
import fit.asta.health.data.sunlight.model.SunlightToolRepo
import fit.asta.health.data.sunlight.model.SunlightToolRepoImpl
import fit.asta.health.data.sunlight.model.api.SunlightApi
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SunlightModule {

    @Singleton
    @Provides
    fun provideSunlightApi(client: OkHttpClient) =
        NetworkUtil.getRetrofit(client).create(SunlightApi::class.java)

    @Singleton
    @Provides
    fun provideSunlightToolDataMapper(): SunlightToolDataMapper {
        return SunlightToolDataMapper()
    }

    @Singleton
    @Provides
    fun provideSunlightToolRepo(
        remoteApi: SunlightApi,
    ): SunlightToolRepo {
        return SunlightToolRepoImpl(
            remoteApi = remoteApi,
        )
    }
}