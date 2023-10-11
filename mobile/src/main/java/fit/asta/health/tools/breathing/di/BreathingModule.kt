package fit.asta.health.tools.breathing.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.tools.breathing.db.BreathingDatabase
import fit.asta.health.tools.breathing.model.BreathingRepo
import fit.asta.health.tools.breathing.model.BreathingRepoImp
import fit.asta.health.tools.breathing.model.LocalRepo
import fit.asta.health.tools.breathing.model.LocalRepoImp
import fit.asta.health.tools.breathing.model.api.BreathingApi
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BreathingModule {
    @Singleton
    @Provides
    fun provideApi(client: OkHttpClient): BreathingApi =
        NetworkUtil.getRetrofit(client).create(BreathingApi::class.java)

    @Singleton
    @Provides
    fun provideRepo(
        api: BreathingApi,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): BreathingRepo {
        return BreathingRepoImp(api, coroutineDispatcher)
    }

    @Singleton
    @Provides
    fun provideBreathingDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        BreathingDatabase::class.java,
        "breathing-database"
    ).build()


    @Singleton
    @Provides
    fun provideLocalRepo(db: BreathingDatabase): LocalRepo {
        return LocalRepoImp(db.breathingDao())
    }
}