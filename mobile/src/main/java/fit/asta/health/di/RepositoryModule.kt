package fit.asta.health.di

import com.google.gson.GsonBuilder
import fit.asta.health.navigation.home.api.HealthToolsService
import fit.asta.health.navigation.home.model.ToolsHomeDataMapper
import fit.asta.health.navigation.home.model.ToolsHomeRepository
import fit.asta.health.navigation.home.model.ToolsHomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.api.ApiService
import fit.asta.health.profilenew.ProfileDaoMapper
import fit.asta.health.profilenew.ProfileRepo.ProfileRepo
import fit.asta.health.profilenew.ProfileRepo.ProfileRepo_Impl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
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
        recipeService: ApiService,
        recipeMapper: ProfileDaoMapper,
    ): ProfileRepo {
        return ProfileRepo_Impl(
            apiService = recipeService,
            mapper = recipeMapper
        )
    }
}

