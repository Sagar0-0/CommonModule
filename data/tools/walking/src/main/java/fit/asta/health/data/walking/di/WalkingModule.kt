package fit.asta.health.data.walking.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.health_data.HealthConnectManager
import fit.asta.health.common.sensor.MeasurableSensor
import fit.asta.health.common.sensor.StepsSensor
import fit.asta.health.data.walking.local.StepsDatabase
import fit.asta.health.data.walking.remote.WalkingApi
import fit.asta.health.data.walking.repo.DayRepository
import fit.asta.health.data.walking.repo.DayRepositoryImpl
import fit.asta.health.data.walking.repo.WalkingToolRepo
import fit.asta.health.data.walking.repo.WalkingToolRepoImpl
import fit.asta.health.data.walking.usecase.ActiveStateOfSteps
import fit.asta.health.data.walking.usecase.DayUseCases
import fit.asta.health.data.walking.usecase.GetDailyData
import fit.asta.health.data.walking.usecase.GetDay
import fit.asta.health.data.walking.usecase.GetTodayData
import fit.asta.health.data.walking.usecase.IncrementStepCount
import fit.asta.health.data.walking.usecase.IncrementStepDuration
import fit.asta.health.data.walking.usecase.IncrementStepsInDaily
import fit.asta.health.data.walking.usecase.SetDay
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalkingModule {

    @Singleton
    @Provides
    fun provideWalkingApi(client: OkHttpClient): WalkingApi =
        NetworkUtil.getRetrofit(client).create(WalkingApi::class.java)

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
    fun provideDayDao(db: StepsDatabase) = db.dayDao()

    @Provides
    @Singleton
    fun provideStepsSensor(app: Application): MeasurableSensor {
        return StepsSensor(app)
    }

    @Singleton
    @Provides
    fun provideHealth(@ApplicationContext context: Context): HealthConnectManager {
        return HealthConnectManager(context)
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

@Module
@InstallIn(SingletonComponent::class)
abstract class WalkingModuleBinds {

    @Binds
    abstract fun bindDayRepo(dayRepositoryImpl: DayRepositoryImpl): DayRepository

    @Binds
    abstract fun bindWalkingToolRepo(walkingToolRepoImpl: WalkingToolRepoImpl): WalkingToolRepo
}