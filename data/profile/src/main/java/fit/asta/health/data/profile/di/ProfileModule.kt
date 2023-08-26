package fit.asta.health.data.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.profile.remote.BasicProfileApi
import fit.asta.health.data.profile.repo.ProfileRepo
import fit.asta.health.data.profile.repo.ProfileRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileApi(client: OkHttpClient) =
        NetworkUtil.getRetrofit(client).create(BasicProfileApi::class.java)


    @Singleton
    @Provides
    fun provideProfileRepo(
        profileApi: BasicProfileApi,
    ): ProfileRepo {
        return ProfileRepoImpl(profileApi = profileApi)
    }
}