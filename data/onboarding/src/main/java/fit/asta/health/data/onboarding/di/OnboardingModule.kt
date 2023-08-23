package fit.asta.health.data.onboarding.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.data.onboarding.remote.OnboardingApi
import fit.asta.health.data.onboarding.repo.OnboardingRepo
import fit.asta.health.data.onboarding.repo.OnboardingRepoImpl
import fit.asta.health.datastore.IODispatcher
import fit.asta.health.datastore.PrefManager
import fit.asta.health.network.utils.NetworkUtil
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
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): OnboardingRepo {
        return OnboardingRepoImpl(
            remoteApi = remoteApi,
            prefManager = prefManager,
            coroutineDispatcher = coroutineDispatcher
        )
    }
}