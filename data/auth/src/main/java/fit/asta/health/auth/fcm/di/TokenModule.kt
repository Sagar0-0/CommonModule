package fit.asta.health.auth.fcm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.auth.fcm.remote.TokenApi
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.datastore.PrefManager
import fit.asta.health.auth.fcm.repo.TokenRepo
import fit.asta.health.auth.fcm.repo.TokenRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    @Provides
    @Singleton
    fun provideTokenApi(
        okHttpClient: OkHttpClient
    ): TokenApi = NetworkUtil.getRetrofit(okHttpClient).create(TokenApi::class.java)
    @Provides
    @Singleton
    fun provideTokenRepo(
        prefManager: PrefManager,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): TokenRepo = TokenRepoImpl(prefManager, coroutineDispatcher)
}