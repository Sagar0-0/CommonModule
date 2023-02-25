package fit.asta.health.tools.walking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.tools.walking.model.WalkingToolDataMapper
import fit.asta.health.tools.walking.model.WalkingToolRepo
import fit.asta.health.tools.walking.model.WalkingToolRepoImpl
import fit.asta.health.tools.walking.model.api.WalkingApi
import fit.asta.health.tools.walking.model.api.WalkingRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WalkingModule {

    @Singleton
    @Provides
    fun provideWalkingApi(client: OkHttpClient): WalkingApi {
        return WalkingRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }

    @Singleton
    @Provides
    fun provideWalkingToolDataMapper(): WalkingToolDataMapper {
        return WalkingToolDataMapper()
    }

    @Singleton
    @Provides
    fun provideWalkingToolRepo(
        remoteApi: WalkingApi,
        walkingToolMapper: WalkingToolDataMapper,
    ): WalkingToolRepo {
        return WalkingToolRepoImpl(
            remoteApi = remoteApi,
            mapper = walkingToolMapper
        )
    }
}