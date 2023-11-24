package fit.asta.health.tools.walking.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.tools.walking.core.data.repository.DayRepositoryImpl
import fit.asta.health.tools.walking.core.data.repository.WalkingToolRepoImpl
import fit.asta.health.tools.walking.core.data.source.StepsDatabase
import fit.asta.health.tools.walking.core.data.source.api.WalkingApi
import fit.asta.health.tools.walking.core.domain.repository.DayRepository
import fit.asta.health.tools.walking.core.domain.repository.WalkingToolRepo
import fit.asta.health.tools.walking.core.domain.usecase.ActiveStateOfSteps
import fit.asta.health.tools.walking.core.domain.usecase.DayUseCases
import fit.asta.health.tools.walking.core.domain.usecase.GetDay
import fit.asta.health.tools.walking.core.domain.usecase.GetTodayData
import fit.asta.health.tools.walking.core.domain.usecase.IncrementStepCount
import fit.asta.health.tools.walking.core.domain.usecase.IncrementStepDuration
import fit.asta.health.tools.walking.core.domain.usecase.SetDay
import fit.asta.health.tools.walking.sensor.MeasurableSensor
import fit.asta.health.tools.walking.sensor.StepsSensor
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
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): WalkingToolRepo {
        return WalkingToolRepoImpl(
            api = remoteApi, coroutineDispatcher = coroutineDispatcher
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
    fun provideRepoDay(db: StepsDatabase): DayRepository {
        return DayRepositoryImpl(db.dayDao())
    }

    @Singleton
    @Provides
    fun provideUseCase(dayRepository: DayRepository): DayUseCases {
        return DayUseCases(
            getDay = GetDay(dayRepository),
            setDay = SetDay(dayRepository),
            incrementStepCount = IncrementStepCount(dayRepository),
            incrementStepDuration = IncrementStepDuration(dayRepository),
            changeStepsState = ActiveStateOfSteps(dayRepository),
            getAllDayData = GetTodayData(dayRepository)
        )
    }
}