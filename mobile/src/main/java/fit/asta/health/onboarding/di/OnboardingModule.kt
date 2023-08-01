package fit.asta.health.onboarding.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.onboarding.api.OnboardingApi
import fit.asta.health.onboarding.api.OnboardingRestApi
import fit.asta.health.onboarding.modal.OnboardingDataMapper
import fit.asta.health.onboarding.repo.OnboardingRepo
import fit.asta.health.onboarding.repo.OnboardingRepoImpl
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
    fun provideOnboardingDataMapper(): OnboardingDataMapper {
        return OnboardingDataMapper()
    }

    @Singleton
    @Provides
    fun provideOnboardingRepo(
        remoteApi: OnboardingApi,
        onboardingMapper: OnboardingDataMapper,
    ): OnboardingRepo {
        return OnboardingRepoImpl(
            remoteApi = remoteApi,
            mapper = onboardingMapper
        )
    }
}