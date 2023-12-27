package fit.asta.health.data.sleep.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.data.sleep.model.SleepLocalRepo
import fit.asta.health.data.sleep.model.SleepLocalRepositoryImpl
import fit.asta.health.data.sleep.model.SleepRepository
import fit.asta.health.data.sleep.model.SleepRepositoryImpl
import fit.asta.health.data.sleep.model.api.SleepingApi
import fit.asta.health.data.sleep.model.db.SleepToolDatabase
import fit.asta.health.network.utils.NetworkUtil
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SleepingModule {

    @Singleton
    @Provides
    fun provideSleepApi(client: OkHttpClient): SleepingApi =
        NetworkUtil.getRetrofit(client).create(SleepingApi::class.java)

    @Singleton
    @Provides
    fun provideSleepToolRepo(
        remoteApi: SleepingApi,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): SleepRepository {
        return SleepRepositoryImpl(api = remoteApi, coroutineDispatcher = coroutineDispatcher)
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