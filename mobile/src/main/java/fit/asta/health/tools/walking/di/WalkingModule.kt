package fit.asta.health.tools.walking.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.tools.walking.model.WalkingToolRepo
import fit.asta.health.tools.walking.model.WalkingToolRepoImpl
import fit.asta.health.tools.walking.model.api.WalkingApi
import fit.asta.health.tools.walking.model.api.WalkingRestApi
import fit.asta.health.tools.walking.sensor.MeasurableSensor
import fit.asta.health.tools.walking.sensor.StepsSensor
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WalkingModule {

    @Provides
    @Singleton
    fun provideStepsSensor(app: Application): MeasurableSensor {
        return StepsSensor(app)
    }

    @Singleton
    @Provides
    fun provideWalkingApi(client: OkHttpClient): WalkingApi {
        return WalkingRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }

    @Singleton
    @Provides
    fun provideWalkingToolRepo(
        remoteApi: WalkingApi
    ): WalkingToolRepo {
        return WalkingToolRepoImpl(
            remoteApi = remoteApi
        )
    }
}