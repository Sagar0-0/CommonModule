package fit.asta.health.data.onboarding.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.onboarding.remote.OnboardingApi
import fit.asta.health.data.onboarding.repo.OnboardingRepo
import fit.asta.health.data.onboarding.repo.OnboardingRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {

    @Singleton
    @Provides
    fun provideOnboardingApi(client: OkHttpClient): OnboardingApi =
        NetworkUtil.getRetrofit(client).create(OnboardingApi::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class OnboardingBindsModule {

    @Binds
    abstract fun provideOnboardingRepo(onboardingRepoImpl: OnboardingRepoImpl): OnboardingRepo
}
