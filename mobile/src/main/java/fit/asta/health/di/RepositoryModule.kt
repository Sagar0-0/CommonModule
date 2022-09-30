package fit.asta.health.di

import fit.asta.health.navigation.home.api.HealthToolsService
import fit.asta.health.navigation.home.model.ToolsHomeDataMapper
import fit.asta.health.navigation.home.repository.ToolsHomeRepository
import fit.asta.health.navigation.home.repository.ToolsHomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        healthToolsService: HealthToolsService,
        toolsHomeMapper: ToolsHomeDataMapper,
    ): ToolsHomeRepository{
        return ToolsHomeRepositoryImpl(
            healthToolsService = healthToolsService,
            mapper = toolsHomeMapper
        )
    }
}

