package fit.asta.health.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.core.network.BuildConfig
import fit.asta.health.network.AstaNetwork
import fit.asta.health.network.LanguageProvider
import fit.asta.health.network.NetworkHelper
import fit.asta.health.network.NetworkHelperImpl
import fit.asta.health.network.TokenProvider
import fit.asta.health.network.interceptor.LanguageInterceptor
import fit.asta.health.network.interceptor.OfflineInterceptor
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

    @Provides
    @Singleton
    fun provideLanguageProvider() = LanguageProvider()

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context?): ChuckerInterceptor? =
        context?.let {
            ChuckerInterceptor.Builder(it)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(true)
                .build()
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        networkHelper: NetworkHelper,
        cache: Cache,
        token: TokenProvider,
        languageProvider: LanguageProvider,
        chuckerInterceptor: ChuckerInterceptor?
    ): OkHttpClient {

        val builder = AstaNetwork.Builder()
            .setApiKey(token)
            .setCache(cache = cache)
            .setBaseUrl(BuildConfig.BASE_URL)
            .addNetworkInterceptor(LanguageInterceptor(languageProvider))
            .addInterceptor(OfflineInterceptor(networkHelper))

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            chuckerInterceptor?.let {
                builder.addInterceptor(it)
            }
        }

        return builder.build()
    }
}