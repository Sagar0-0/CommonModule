package fit.asta.health.onboarding.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.onboarding.data.remote.OnboardingApi
import fit.asta.health.onboarding.data.repo.OnboardingRepo
import fit.asta.health.onboarding.data.repo.OnboardingRepoImpl
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {

    @Singleton
    @Provides
    fun provideOnboardingApi(client: OkHttpClient): OnboardingApi =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
            .create(OnboardingApi::class.java)

    @Singleton
    @Provides
    fun provideOnboardingRepo(
        remoteApi: OnboardingApi,
        prefManager: PrefManager,
        coroutineDispatcher: CoroutineDispatcher
    ): OnboardingRepo {
        return OnboardingRepoImpl(
            remoteApi = remoteApi,
            prefManager = prefManager,
            coroutineDispatcher = coroutineDispatcher
        )
    }
}