package fit.asta.health.data.water.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.water.local.HistoryDatabase
import fit.asta.health.data.water.local.WaterToolDatabase
import fit.asta.health.data.water.remote.WaterApi
import fit.asta.health.data.water.repo.HistoryRepo
import fit.asta.health.data.water.repo.WaterLocalRepo
import fit.asta.health.data.water.repo.WaterLocalRepoImpl
import fit.asta.health.data.water.repo.WaterToolRepo
import fit.asta.health.data.water.repo.WaterToolRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaterModule {

    @Singleton
    @Provides
    fun provideWaterApi(client: OkHttpClient): WaterApi =
        NetworkUtil.getRetrofit(client).create(WaterApi::class.java)

    @Singleton
    @Provides
    fun provideWaterDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        WaterToolDatabase::class.java,
        "water-database"
    ).build()

    @Singleton
    @Provides
    fun provideWaterDao(waterToolDatabase: WaterToolDatabase) = waterToolDatabase.waterDao()

    @Singleton
    @Provides
    fun provideHistoryDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        HistoryDatabase::class.java,
        "HistoryDatabase"
    ).build()


    @Singleton
    @Provides
    fun provideHistoryRepo(db: HistoryDatabase): HistoryRepo {
        return HistoryRepo(db.historyDao())
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class WaterBindsModule {

    @Binds
    abstract fun provideWaterToolRepo(waterToolRepoImpl: WaterToolRepoImpl): WaterToolRepo

    @Binds
    abstract fun provideWaterLocalRepo(waterLocalRepoImpl: WaterLocalRepoImpl): WaterLocalRepo

    @Binds
    fun provideHistoryRepo(db: HistoryDatabase): HistoryRepo {
        return HistoryRepo(db.historyDao())
    }

}