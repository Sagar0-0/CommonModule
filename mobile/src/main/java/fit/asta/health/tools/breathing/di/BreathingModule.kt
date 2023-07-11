package fit.asta.health.tools.breathing.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.tools.breathing.model.BreathingRepo
import fit.asta.health.tools.breathing.model.BreathingRepoImp
import fit.asta.health.tools.breathing.model.api.BreathingApi
import fit.asta.health.tools.breathing.model.api.BreathingRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BreathingModule {
    @Singleton
    @Provides
    fun provideApi(client: OkHttpClient): BreathingApi {
        return BreathingRestApi(baseUrl = BuildConfig.BASE_URL, client = client)
    }
    @Singleton
    @Provides
    fun provideRepo(api: BreathingApi): BreathingRepo {
        return BreathingRepoImp(api)
    }
}