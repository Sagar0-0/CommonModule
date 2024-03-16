package fit.asta.health.data.breathing.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.breathing.db.BreathingDatabase
import fit.asta.health.data.breathing.model.BreathingRepo
import fit.asta.health.data.breathing.model.BreathingRepoImpl
import fit.asta.health.data.breathing.model.LocalRepo
import fit.asta.health.data.breathing.model.LocalRepoImp
import fit.asta.health.data.breathing.model.api.BreathingApi
import fit.asta.health.network.utils.NetworkUtil
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

@Module
@InstallIn(SingletonComponent::class)
abstract class BreathingBindsModule {

    @Binds
    abstract fun provideBreathingRepo(breathingRepoImpl: BreathingRepoImpl): BreathingRepo

}