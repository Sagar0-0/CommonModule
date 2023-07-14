package fit.asta.health.tools.sleep.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.tools.sleep.model.LocalRepo
import fit.asta.health.tools.sleep.model.LocalRepoImpl
import fit.asta.health.tools.sleep.model.SleepRepository
import fit.asta.health.tools.sleep.model.SleepRepositoryImpl
import fit.asta.health.tools.sleep.model.api.SleepingApi
import fit.asta.health.tools.sleep.model.api.SleepingRestApi
import fit.asta.health.tools.sleep.model.db.SleepingDatabase
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SleepingModule {

    @Singleton
    @Provides
    fun provideSleepApi(client: OkHttpClient): SleepingApi {
        return SleepingRestApi(
            baseUrl = BuildConfig.BASE_URL,
            client = client
        )
    }

    @Singleton
    @Provides
    fun provideSleepRepository(api: SleepingApi): SleepRepository {
        return SleepRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideSleepingDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        SleepingDatabase::class.java,
        "sleeping-database"
    ).build()

    @Singleton
    @Provides
    fun provideLocalRepo(db: SleepingDatabase): LocalRepo {
        return LocalRepoImpl(db.sleepingDao())
    }

}