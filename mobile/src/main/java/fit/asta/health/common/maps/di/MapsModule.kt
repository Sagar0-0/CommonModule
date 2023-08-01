package fit.asta.health.common.maps.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.common.maps.api.remote.MapsApi
import fit.asta.health.common.maps.api.remote.MapsRestApi
import fit.asta.health.common.maps.api.search.SearchApi
import fit.asta.health.common.maps.repo.MapsRepo
import fit.asta.health.common.maps.repo.MapsRepoImpl
import fit.asta.health.common.maps.utils.LocationHelper
import fit.asta.health.common.maps.utils.SEARCH_API_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapsModule {

    @Provides
    fun provideLocationHelper(@ApplicationContext context: Context): LocationHelper {
        return LocationHelper(context)
    }

    @Provides
    @Singleton
    fun provideSearchApi(): SearchApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SEARCH_API_BASE_URL)
        .build()
        .create(SearchApi::class.java)

    @Provides
    @Singleton
    fun provideMapsRepo(
        mapsApiService: MapsApi,
        searchApi: SearchApi,
        @ApplicationContext context: Context
    ): MapsRepo = MapsRepoImpl(mapsApiService, searchApi, context)

    @Provides
    @Singleton
    fun provideRemoteApi(client: OkHttpClient): MapsApi =
        MapsRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
}