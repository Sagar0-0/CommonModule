package fit.asta.health.profile.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.profile.data.api.ProfileApiService
import fit.asta.health.profile.data.repo.ProfileRepo
import fit.asta.health.profile.data.repo.ProfileRepoImpl
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileApi(client: OkHttpClient): ProfileApiService {
        return NetworkUtil.getRetrofit(client).create(ProfileApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileRepo(
        @ApplicationContext context: Context,
        remoteApi: ProfileApiService
    ): ProfileRepo {
        return ProfileRepoImpl(remoteApi = remoteApi, context = context)
    }
}