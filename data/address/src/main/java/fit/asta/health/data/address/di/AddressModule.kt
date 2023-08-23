package fit.asta.health.data.address.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.BuildConfig
import fit.asta.health.common.utils.ResourcesProvider
import fit.asta.health.data.address.remote.AddressApi
import fit.asta.health.data.address.remote.SearchLocationApi
import fit.asta.health.data.address.repo.AddressRepo
import fit.asta.health.data.address.repo.AddressRepoImpl
import fit.asta.health.data.address.utils.LocationResourceProvider
import fit.asta.health.datastore.PrefManager
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddressModule {

    private const val SEARCH_API_BASE_URL = "https://maps.googleapis.com/maps/api/"

    @Provides
    @Singleton
    fun provideSearchApi(client: OkHttpClient): SearchLocationApi =
        NetworkUtil.getRetrofit(baseUrl = SEARCH_API_BASE_URL, client = client)
            .create(SearchLocationApi::class.java)

    @Provides
    @Singleton
    fun provideMapsRepo(
        addressApi: AddressApi,
        searchLocationApi: SearchLocationApi,
        resourcesProvider: ResourcesProvider,
        prefManager: PrefManager,
        locationResourceProvider: LocationResourceProvider
    ): AddressRepo = AddressRepoImpl(
        addressApi,
        searchLocationApi,
        prefManager,
        resourcesProvider,
        locationResourceProvider
    )

    @Provides
    @Singleton
    fun provideRemoteApi(client: OkHttpClient): AddressApi =
        NetworkUtil.getRetrofit(baseUrl = BuildConfig.BASE_URL, client = client)
            .create(AddressApi::class.java)

}