package fit.asta.health.di

import fit.asta.health.navigation.home.network.HealthToolsService
import fit.asta.health.navigation.home.repository.ToolsHomeDtoMapper
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
        toolsHomeMapper: ToolsHomeDtoMapper,
    ): ToolsHomeRepository{
        return ToolsHomeRepositoryImpl(
            healthToolsService = healthToolsService,
            mapper = toolsHomeMapper
        )
    }
}

