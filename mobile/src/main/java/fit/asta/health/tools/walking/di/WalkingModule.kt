package fit.asta.health.tools.walking.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.tools.walking.db.StepsDatabase
import fit.asta.health.tools.walking.model.LocalRepo
import fit.asta.health.tools.walking.model.LocalRepoImpl
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
        return WalkingRestApi(client)
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

    @Singleton
    @Provides
    fun provideStepsDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        StepsDatabase::class.java,
        "steps-database"
    ).build()

    @Singleton
    @Provides
    fun provideRepo(db:StepsDatabase):LocalRepo{
        return LocalRepoImpl(db.stepsDataDAO())
    }


}