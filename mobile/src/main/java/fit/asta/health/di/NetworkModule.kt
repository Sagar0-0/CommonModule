package fit.asta.health.di

import fit.asta.health.navigation.home.api.HealthToolsService
import fit.asta.health.navigation.home.model.ToolsHomeDataMapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRecipeMapper(): ToolsHomeDataMapper {
        return ToolsHomeDataMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeService(): HealthToolsService {
        return Retrofit.Builder()
            .baseUrl("https://asta.fit/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(HealthToolsService::class.java)
    }

    /**
     * I might include proper authentication later on food2fork.ca
     * For now just hard code a token.
     */
    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken(): String{
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }

}