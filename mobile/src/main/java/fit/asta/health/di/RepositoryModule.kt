package fit.asta.health.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.navigation.home.api.HealthToolsService
import fit.asta.health.navigation.home.model.ToolsHomeDataMapper
import fit.asta.health.navigation.home.model.ToolsHomeRepository
import fit.asta.health.navigation.home.model.ToolsHomeRepositoryImpl
import fit.asta.health.network.api.ApiService
import fit.asta.health.profilenew.ProfileDaoMapper
import fit.asta.health.profilenew.ProfileRepo.ProfileRepo
import fit.asta.health.profilenew.ProfileRepo.ProfileRepo_Impl
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHomeToolsRepository(
        healthToolsService: HealthToolsService,
        toolsHomeMapper: ToolsHomeDataMapper,
    ): ToolsHomeRepository {
        return ToolsHomeRepositoryImpl(
            healthToolsService = healthToolsService,
            mapper = toolsHomeMapper
        )
    }

    @Singleton
    @Provides
    fun provideProfileMapper(): ProfileDaoMapper {
        return ProfileDaoMapper()
    }

    @Singleton
    @Provides
    @Named("profile_repository")
    fun provideProfileRepository(
        apiService: ApiService,
        recipeMapper: ProfileDaoMapper,
    ): ProfileRepo {
        return ProfileRepo_Impl(
            apiService = apiService,
            mapper = recipeMapper
        )
    }
}

