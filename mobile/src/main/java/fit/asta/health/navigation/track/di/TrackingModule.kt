package fit.asta.health.navigation.track.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.navigation.track.model.TrackingBreathingRepo
import fit.asta.health.navigation.track.model.TrackingBreathingRepoImpl
import fit.asta.health.navigation.track.model.TrackingWaterRepo
import fit.asta.health.navigation.track.model.TrackingWaterRepoImpl
import fit.asta.health.navigation.track.model.api.breathing.TrackingBreathingApi
import fit.asta.health.navigation.track.model.api.breathing.TrackingBreathingRestImpl
import fit.asta.health.navigation.track.model.api.water.TrackingWaterApi
import fit.asta.health.navigation.track.model.api.water.TrackingWaterRestImpl
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
    fun provideTrackingBreathingApi(client: OkHttpClient): TrackingBreathingApi {
        return TrackingBreathingRestImpl(baseUrl = BuildConfig.BASE_URL, client = client)
    }

    @Singleton
    @Provides
    fun provideTrackingBreathingRepo(
        remoteApi: TrackingBreathingApi,
    ): TrackingBreathingRepo = TrackingBreathingRepoImpl(remoteApi = remoteApi)

    @Singleton
    @Provides
    fun provideTrackingWaterApi(client: OkHttpClient): TrackingWaterApi {
        return TrackingWaterRestImpl(baseUrl = BuildConfig.BASE_URL, client = client)
    }

    @Singleton
    @Provides
    fun provideTrackingWaterRepo(
        remoteApi: TrackingWaterApi,
    ): TrackingWaterRepo = TrackingWaterRepoImpl(remoteApi = remoteApi)
}