package fit.asta.health.common.address.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.common.address.data.remote.AddressApi
import fit.asta.health.common.address.data.remote.SearchLocationApi
import fit.asta.health.common.address.data.repo.MapsRepo
import fit.asta.health.common.address.data.repo.MapsRepoImpl
import fit.asta.health.common.address.data.utils.LocationHelper
import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.ResourcesProvider
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddressModule {

    @Provides
    fun provideLocationHelper(@ApplicationContext context: Context): LocationHelper {
        return LocationHelper(context)
    }

    @Provides
    @Singleton
    fun provideSearchApi(client: OkHttpClient): SearchLocationApi =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
            .create(SearchLocationApi::class.java)

    @Provides
    @Singleton
    fun provideMapsRepo(
        addressApi: AddressApi,
        searchLocationApi: SearchLocationApi,
        resourcesProvider: ResourcesProvider,
        prefManager: PrefManager
    ): MapsRepo = MapsRepoImpl(addressApi, searchLocationApi, prefManager,resourcesProvider)

    @Provides
    @Singleton
    fun provideRemoteApi(client: OkHttpClient): AddressApi =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
            .create(AddressApi::class.java)
}