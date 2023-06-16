package fit.asta.health.tools.meditation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.tools.meditation.model.MeditationRepo
import fit.asta.health.tools.meditation.model.MeditationRepoImp
import fit.asta.health.tools.meditation.model.api.MeditationApi
import fit.asta.health.tools.meditation.model.api.MeditationRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MeditationModule {

    @Singleton
    @Provides
    fun provideApi(client: OkHttpClient): MeditationApi {
        return MeditationRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }
    @Singleton
    @Provides
    fun provideRepo(api: MeditationApi):MeditationRepo{
        return MeditationRepoImp(api)
    }
}