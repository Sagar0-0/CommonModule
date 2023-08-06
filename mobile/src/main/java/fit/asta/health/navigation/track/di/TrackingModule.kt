package fit.asta.health.navigation.track.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.navigation.track.model.TrackingRepo
import fit.asta.health.navigation.track.model.TrackingRepoImpl
import fit.asta.health.navigation.track.model.api.TrackingApi
import fit.asta.health.navigation.track.model.api.TrackingRestImpl
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
    fun provideTrackingApi(client: OkHttpClient): TrackingApi {
        return TrackingRestImpl(
            baseUrl = BuildConfig.BASE_URL,
            client = client
        )
    }


    @Singleton
    @Provides
    fun provideTrackingRepository(trackingApi: TrackingApi): TrackingRepo {
        return TrackingRepoImpl(trackingApi = trackingApi)
    }
}