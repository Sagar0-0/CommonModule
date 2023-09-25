package fit.asta.health.meditation.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.meditation.db.MeditationToolDatabase
import fit.asta.health.meditation.model.LocalRepo
import fit.asta.health.meditation.model.LocalRepoImp
import fit.asta.health.meditation.model.MeditationRepo
import fit.asta.health.meditation.model.MeditationRepoImp
import fit.asta.health.meditation.model.api.MeditationApi
import fit.asta.health.meditation.model.api.MeditationRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MeditationModule {

    @Singleton
    @Provides
    fun provideApi(client: OkHttpClient): MeditationApi {
        return MeditationRestApi(client)
    }
    @Singleton
    @Provides
    fun provideRepo(api: MeditationApi):MeditationRepo{
        return MeditationRepoImp(api)
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