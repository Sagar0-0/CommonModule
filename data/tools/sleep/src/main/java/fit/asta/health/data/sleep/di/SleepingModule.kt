package fit.asta.health.data.sleep.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.sleep.model.SleepLocalRepo
import fit.asta.health.data.sleep.model.SleepLocalRepositoryImpl
import fit.asta.health.data.sleep.model.SleepRepository
import fit.asta.health.data.sleep.model.SleepRepositoryImpl
import fit.asta.health.data.sleep.model.api.SleepingApi
import fit.asta.health.data.sleep.model.api.SleepingRestApi
import fit.asta.health.data.sleep.model.db.SleepToolDatabase
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SleepingModule {

    @Singleton
    @Provides
    fun provideSleepApi(client: OkHttpClient): SleepingApi {
        return SleepingRestApi(client)
    }

    @Singleton
    @Provides
    fun provideSleepRepository(api: SleepingApi): SleepRepository {
        return SleepRepositoryImpl(api = api)
    }


    @Singleton
    @Provides
    fun provideSleepDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        SleepToolDatabase::class.java,
        "sleep-database"
    ).build()


    @Singleton
    @Provides
    fun provideSleepLocalRepository(db: SleepToolDatabase): SleepLocalRepo {
        return SleepLocalRepositoryImpl(db.sleepDao())
    }
}