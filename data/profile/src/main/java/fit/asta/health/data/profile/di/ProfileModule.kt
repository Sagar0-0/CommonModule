package fit.asta.health.data.profile.di

import android.content.ContentResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.data.profile.remote.ProfileApi
import fit.asta.health.data.profile.repo.ProfileRepo
import fit.asta.health.data.profile.repo.ProfileRepoImpl
import fit.asta.health.datastore.PrefManager
import fit.asta.health.network.utils.NetworkUtil
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileApi(client: OkHttpClient): ProfileApi =
        NetworkUtil.getRetrofit(client).create(ProfileApi::class.java)

    @Singleton
    @Provides
    fun provideProfileRepo(
        profileApi: ProfileApi,
        prefManager: PrefManager,
        contentResolver: ContentResolver,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): ProfileRepo {
        return ProfileRepoImpl(
            profileApi = profileApi,
            prefManager = prefManager,
            contentResolver = contentResolver,
            coroutineDispatcher = coroutineDispatcher
        )
    }
}