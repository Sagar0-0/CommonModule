package fit.asta.health.common.location.maps.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.auth.model.AuthRepo
import fit.asta.health.common.location.LocationHelper
import fit.asta.health.common.location.maps.api.RemoteApi
import fit.asta.health.common.location.maps.api.SearchApi
import fit.asta.health.common.location.maps.repo.MapsRepo
import fit.asta.health.common.location.maps.repo.MapsRepoImpl
import fit.asta.health.common.location.maps.utils.REMOTE_API_BASE_URL
import fit.asta.health.common.location.maps.utils.SEARCH_API_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapsModule {

    @Provides
    @Named("UId")
    fun provideUId(authRepo: AuthRepo): String = authRepo.getUserId() ?: ""

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
        remoteApi: RemoteApi,
        searchApi: SearchApi,
        @ApplicationContext context: Context
    ): MapsRepo = MapsRepoImpl(remoteApi, searchApi, context)

    @Provides
    @Singleton
    fun provideRemoteApi(): RemoteApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(REMOTE_API_BASE_URL)
        .build()
        .create(RemoteApi::class.java)
}