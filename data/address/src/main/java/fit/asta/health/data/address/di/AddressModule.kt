package fit.asta.health.data.address.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.address.remote.AddressApi
import fit.asta.health.data.address.remote.SearchLocationApi
import fit.asta.health.data.address.repo.AddressRepo
import fit.asta.health.data.address.repo.AddressRepoImpl
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
    fun provideRemoteApi(client: OkHttpClient): AddressApi =
        NetworkUtil.getRetrofit(client).create(AddressApi::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AddModule {
    @Binds
    abstract fun provideAddressRepo(addressRepoImpl: AddressRepoImpl): AddressRepo
}