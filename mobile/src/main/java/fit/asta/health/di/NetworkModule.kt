package fit.asta.health.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.network.AstaNetwork
import fit.asta.health.network.NetworkHelper
import fit.asta.health.network.NetworkHelperImpl
import fit.asta.health.network.TokenProvider
import fit.asta.health.network.api.Api
import fit.asta.health.network.api.RestApi
import fit.asta.health.network.interceptor.OnlineInterceptor
import fit.asta.health.network.repo.FileUploadRepo
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module(includes = [AppModule::class])
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpCache(@ApplicationContext app: Context) =
        Cache(app.cacheDir, AstaNetwork.CACHE_SIZE)

    @Provides
    @Singleton
    fun provideTokenProvider() = TokenProvider()

    @Singleton
    @Provides
    fun provideRemoteRestApi(client: OkHttpClient): Api {
        return RestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }

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

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }

        return builder.build()
    }

    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelperImpl(context)
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