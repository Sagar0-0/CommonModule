package fit.asta.health.data.water.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.water.db.WaterToolDatabase
import fit.asta.health.data.water.model.WaterLocalRepo
import fit.asta.health.data.water.model.WaterLocalRepoImpl
import fit.asta.health.data.water.model.WaterToolDataMapper
import fit.asta.health.data.water.model.WaterToolRepo
import fit.asta.health.data.water.model.WaterToolRepoImpl
import fit.asta.health.data.water.model.api.WaterApi
import fit.asta.health.data.water.model.api.WaterRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaterModule {

    @Singleton
    @Provides
    fun provideWaterApi(client: OkHttpClient): WaterApi {
        return WaterRestApi(client)
    }

    @Singleton
    @Provides
    fun provideWaterToolDataMapper(): WaterToolDataMapper {
        return WaterToolDataMapper()
    }

    @Singleton
    @Provides
    fun provideWaterToolRepo(
        remoteApi: WaterApi,
        waterToolMapper: WaterToolDataMapper,
    ): WaterToolRepo {
        return WaterToolRepoImpl(
            remoteApi = remoteApi,
            mapper = waterToolMapper
        )
    }

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
    fun provideRepo(db: WaterToolDatabase): WaterLocalRepo {
        return WaterLocalRepoImpl(db.waterDao())
    }
}