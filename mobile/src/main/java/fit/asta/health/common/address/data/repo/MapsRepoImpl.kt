package fit.asta.health.common.address.data.repo

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import fit.asta.health.R
import fit.asta.health.UserPreferences
import fit.asta.health.common.address.data.modal.AddressesResponse
import fit.asta.health.common.address.data.modal.DeleteAddressResponse
import fit.asta.health.common.address.data.modal.PutAddressResponse
import fit.asta.health.common.address.data.modal.SearchResponse
import fit.asta.health.common.address.data.remote.AddressApi
import fit.asta.health.common.address.data.remote.SearchLocationApi
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.ResourcesProvider
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.toResponseState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MapsRepoImpl @Inject constructor(
    private val addressApi: AddressApi,
    private val searchLocationApi: SearchLocationApi,
    private val prefManager: PrefManager,
    private val resourcesProvider: ResourcesProvider
) : MapsRepo {

    override val userPreferences : Flow<UserPreferences> = prefManager.userData

    override suspend fun updateLocationPermissionRejectedCount(newValue: Int) {
        prefManager.setLocationPermissionRejectedCount(newValue)
    }

    override suspend fun setCurrentLocation(location: String) {
        prefManager.setCurrentLocation(location)
    }

    override suspend fun search(text: String, latLng: LatLng): ResponseState<SearchResponse?> {
        return if (latLng.latitude == 0.00 && latLng.longitude == 0.00) {
            searchLocationApi.search(
                text,
                resourcesProvider.getString(R.string.MAPS_API_KEY)
            )
        } else {
            searchLocationApi.searchBiased(
                "${latLng.latitude},${latLng.longitude}",
                "distance",
                text,
                resourcesProvider.getString(R.string.MAPS_API_KEY)
            )
        }.toResponseState()
    }

    override suspend fun getAddresses(uid: String): ResponseState<AddressesResponse?> {
        return addressApi.getAddresses(uid).toResponseState()
    }

    override suspend fun putAddress(myAddress: AddressesResponse.MyAddress): ResponseState<PutAddressResponse?> {
        return addressApi.addNewAddress(myAddress).toResponseState()
    }

    override suspend fun deleteAddress(
        uid: String,
        id: String
    ): ResponseState<DeleteAddressResponse?> {
        return addressApi.deleteAddress(uid, id).toResponseState()
    }

    override suspend fun selectCurrent(cid: String, pid: String?) {
        addressApi.selectCurrent(cid, pid)
    }
}