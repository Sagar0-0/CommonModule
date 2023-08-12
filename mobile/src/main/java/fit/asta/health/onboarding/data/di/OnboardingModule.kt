package fit.asta.health.onboarding.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.onboarding.data.remote.api.OnboardingApi
import fit.asta.health.onboarding.data.remote.api.OnboardingRestApi
import fit.asta.health.onboarding.data.repo.OnboardingRepo
import fit.asta.health.onboarding.data.repo.OnboardingRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {

    @Singleton
    @Provides
    fun provideOnboardingApi(client: OkHttpClient): OnboardingApi =
        OnboardingRestApi(baseUrl = BuildConfig.BASE_URL, client = client)


    @Singleton
    @Provides
    fun provideOnboardingRepo(
        remoteApi: OnboardingApi,
        prefManager: PrefManager
    ): OnboardingRepo {
        return OnboardingRepoImpl(
            remoteApi = remoteApi,
            prefManager = prefManager
        )
    }
}