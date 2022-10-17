package fit.asta.health.di

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.network.AstaNetwork
import fit.asta.health.network.TokenProvider
import fit.asta.health.network.api.ApiService
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.interceptor.OnlineInterceptor
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [AppModule::class])
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://asta.fit/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)
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
    fun provideRemoteRestApi(
        @ApplicationContext app: Context,
        cache: Cache,
        token: TokenProvider
    ): RemoteApis {

        val builder = AstaNetwork.Builder()
            .setApiKey(token)
            .setCache(cache = cache)
            .addInterceptor(OnlineInterceptor(app))

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }

        return builder.build()
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