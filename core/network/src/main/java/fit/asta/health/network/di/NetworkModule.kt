package fit.asta.health.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.core.network.BuildConfig
import fit.asta.health.network.AstaNetwork
import fit.asta.health.network.NetworkHelper
import fit.asta.health.network.NetworkHelperImpl
import fit.asta.health.network.TokenProvider
import fit.asta.health.network.api.Api
import fit.asta.health.network.api.ApiService
import fit.asta.health.network.interceptor.OfflineInterceptor
import fit.asta.health.network.interceptor.OnlineInterceptor
import fit.asta.health.network.repo.FileUploadRepo
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelperImpl(context)
    }

    @Provides
    @Singleton
    fun provideHttpCache(@ApplicationContext app: Context) =
        Cache(app.cacheDir, AstaNetwork.CACHE_SIZE)

    @Provides
    @Singleton
    fun provideTokenProvider() = TokenProvider()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        networkHelper: NetworkHelper,
        cache: Cache,
        token: TokenProvider
    ): OkHttpClient {

        val builder = AstaNetwork.Builder()
            .setApiKey(token)
            .setCache(cache = cache)
            .setBaseUrl(BuildConfig.BASE_URL)
            .addInterceptor(OnlineInterceptor(networkHelper))
            .addInterceptor(OfflineInterceptor(networkHelper))

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        return NetworkUtil.getRetrofit(client).create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideFileRepo(
        @ApplicationContext context: Context,
        remoteApi: Api
    ): FileUploadRepo {
        return FileUploadRepo(context, remoteApi)
    }

    /*
    @Singleton
    @Provides
    fun provideLocalRestApi(@ApplicationContext app: Context, cache: Cache): RemoteApis {

        val builder = AstaNetwork.Builder()
            .setCache(cache = cache)
            .addInterceptor(OfflineInterceptor(app))
            .addCertificatePinner(Certificate())

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.HEADERS)
            )
        }

        return builder.build()
    }
    */
}