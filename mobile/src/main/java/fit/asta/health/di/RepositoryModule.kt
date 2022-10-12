package fit.asta.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.navigation.home.model.ToolsHomeDataMapper
import fit.asta.health.navigation.home.model.ToolsHomeRepository
import fit.asta.health.navigation.home.model.ToolsHomeRepositoryImpl
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.profile.model.ProfileDataMapper
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.ProfileRepoImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHomeToolsRepository(
        remoteApi: RemoteApis,
        toolsHomeMapper: ToolsHomeDataMapper,
    ): ToolsHomeRepository {
        return ToolsHomeRepositoryImpl(
            remoteApi = remoteApi,
            mapper = toolsHomeMapper
        )
    }

    @Singleton
    @Provides
    fun provideProfileMapper(): ProfileDataMapper {
        return ProfileDataMapper()
    }

    @Singleton
    @Provides
    @Named("profile_repository")
    fun provideProfileRepository(
        remoteApi: RemoteApis,
        recipeMapper: ProfileDataMapper,
    ): ProfileRepo {
        return ProfileRepoImpl(
            remoteApi = remoteApi,
            mapper = recipeMapper
        )
    }
}

