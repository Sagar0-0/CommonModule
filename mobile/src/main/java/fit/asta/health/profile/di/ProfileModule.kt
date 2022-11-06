package fit.asta.health.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.profile.model.ProfileDataMapper
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.ProfileRepoImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileMapper(): ProfileDataMapper {
        return ProfileDataMapper()
    }

    @Singleton
    @Provides
    @Named("profile_repository")
    fun provideProfileRepo(
        remoteApi: RemoteApis,
        recipeMapper: ProfileDataMapper,
    ): ProfileRepo {
        return ProfileRepoImpl(
            remoteApi = remoteApi,
            mapper = recipeMapper
        )
    }
}

