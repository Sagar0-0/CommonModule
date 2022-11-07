package fit.asta.health.tools.walking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.tools.walking.model.WalkingToolDataMapper
import fit.asta.health.tools.walking.model.WalkingToolRepo
import fit.asta.health.tools.walking.model.WalkingToolRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalkingModule {

    @Singleton
    @Provides
    fun provideWalkingToolDataMapper(): WalkingToolDataMapper {
        return WalkingToolDataMapper()
    }

    @Singleton
    @Provides
    fun provideWalkingToolRepo(
        remoteApi: RemoteApis,
        walkingToolMapper: WalkingToolDataMapper,
    ): WalkingToolRepo {
        return WalkingToolRepoImpl(
            remoteApi = remoteApi,
            mapper = walkingToolMapper
        )
    }
}