package fit.asta.health.navigation.track.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.navigation.track.data.remote.TrackingApiService
import fit.asta.health.navigation.track.data.repo.TrackingRepo
import fit.asta.health.navigation.track.data.repo.TrackingRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * This is the Dependency Injection Module for the Tracking Feature in the App
 */
@Module
@InstallIn(SingletonComponent::class)
object TrackingModule {

    @Singleton
    @Provides
    fun provideTrackingApi(client: OkHttpClient): TrackingApiService {
        return NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
            .create(TrackingApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideTrackingRepository(trackingApiService: TrackingApiService): TrackingRepo {
        return TrackingRepoImpl(trackingApiService = trackingApiService)
    }
}