package fit.asta.health.meditation.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.meditation.db.MeditationToolDatabase
import fit.asta.health.meditation.remote.MeditationApi
import fit.asta.health.meditation.repo.LocalRepo
import fit.asta.health.meditation.repo.LocalRepoImp
import fit.asta.health.meditation.repo.MeditationRepo
import fit.asta.health.meditation.repo.MeditationRepoImp
import fit.asta.health.network.utils.NetworkUtil
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MeditationModule {

    @Singleton
    @Provides
    fun provideApi(client: OkHttpClient): MeditationApi =
        NetworkUtil.getRetrofit(client).create(MeditationApi::class.java)

    @Singleton
    @Provides
    fun provideRepo(
        api: MeditationApi,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): MeditationRepo {
        return MeditationRepoImp(api, coroutineDispatcher)
    }

    @Singleton
    @Provides
    fun provideMeditationDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        MeditationToolDatabase::class.java,
        "meditation-database"
    ).build()


    @Singleton
    @Provides
    fun provideLocalRepo(db: MeditationToolDatabase): LocalRepo {
        return LocalRepoImp(db.meditationDao())
    }

}