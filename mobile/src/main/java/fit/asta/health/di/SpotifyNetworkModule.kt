package fit.asta.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.api.SpotifyApiService
import fit.asta.health.network.api.SpotifyRemoteApis
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Named("SPOTIFY")
object SpotifyNetworkModule {

    @Singleton
    @Provides
    @Named("SPOTIFY")
    fun provideRetrofitInstance(
//        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(SPOTIFY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("SPOTIFY")
    fun provideApiService(@Named("SPOTIFY") retrofit: Retrofit): SpotifyApiService {
        return retrofit.create(SpotifyApiService::class.java)
    }

    @Singleton
    @Provides
    @Named("SPOTIFY")
    fun provideSpotifyRemoteApis(@Named("SPOTIFY") retrofit: Retrofit): SpotifyRemoteApis {
        return retrofit.create(SpotifyRemoteApis::class.java)
    }
}