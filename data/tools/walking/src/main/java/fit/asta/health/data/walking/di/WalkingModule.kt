package fit.asta.health.data.walking.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.health_data.HealthConnectManager
import fit.asta.health.common.sensor.MeasurableSensor
import fit.asta.health.common.sensor.StepsSensor
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.data.walking.data.repository.DayRepositoryImpl
import fit.asta.health.data.walking.data.repository.WalkingToolRepoImpl
import fit.asta.health.data.walking.data.source.StepsDatabase
import fit.asta.health.data.walking.data.source.api.WalkingApi
import fit.asta.health.data.walking.domain.repository.DayRepository
import fit.asta.health.data.walking.domain.repository.WalkingToolRepo
import fit.asta.health.data.walking.domain.usecase.ActiveStateOfSteps
import fit.asta.health.data.walking.domain.usecase.DayUseCases
import fit.asta.health.data.walking.domain.usecase.GetDailyData
import fit.asta.health.data.walking.domain.usecase.GetDay
import fit.asta.health.data.walking.domain.usecase.GetTodayData
import fit.asta.health.data.walking.domain.usecase.IncrementStepCount
import fit.asta.health.data.walking.domain.usecase.IncrementStepDuration
import fit.asta.health.data.walking.domain.usecase.IncrementStepsInDaily
import fit.asta.health.data.walking.domain.usecase.SetDay
import fit.asta.health.datastore.PrefManager
import fit.asta.health.network.utils.NetworkUtil
import kotlinx.coroutines.CoroutineDispatcher
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
    fun provideWalkingApi(client: OkHttpClient): WalkingApi =
        NetworkUtil.getRetrofit(client).create(WalkingApi::class.java)

    @Singleton
    @Provides
    fun provideWalkingToolRepo(
        remoteApi: WalkingApi,
        prefManager: PrefManager,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): WalkingToolRepo {
        return WalkingToolRepoImpl(
            api = remoteApi, coroutineDispatcher = coroutineDispatcher, prefManager = prefManager
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
    fun provideHealth(@ApplicationContext context: Context): HealthConnectManager {
        return HealthConnectManager(context)
    }

    @Singleton
    @Provides
    fun provideRepoDay(db: StepsDatabase): DayRepository {
        return DayRepositoryImpl(db.dayDao())
    }

    @Singleton
    @Provides
    fun provideUseCase(dayRepository: DayRepository): DayUseCases {
        return DayUseCases(
            getDay = GetDay(dayRepository),
            getDailyData = GetDailyData(dayRepository),
            setDay = SetDay(dayRepository),
            incrementStepCount = IncrementStepCount(dayRepository),
            incrementStepDuration = IncrementStepDuration(dayRepository),
            changeStepsState = ActiveStateOfSteps(dayRepository),
            getAllDayData = GetTodayData(dayRepository),
            incrementStepsInDaily = IncrementStepsInDaily(dayRepository)
        )
    }
}