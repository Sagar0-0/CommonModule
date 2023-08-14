package fit.asta.health.common.address.data.repo

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
import fit.asta.health.common.utils.UserPreferencesData
import fit.asta.health.common.utils.getResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapsRepoImpl @Inject constructor(
    private val addressApi: AddressApi,
    private val searchLocationApi: SearchLocationApi,
    private val prefManager: PrefManager,
    private val resourcesProvider: ResourcesProvider
) : MapsRepo {

    override val userPreferences: Flow<UserPreferencesData> = prefManager.userData

    override suspend fun updateLocationPermissionRejectedCount(newValue: Int) {
        prefManager.setLocationPermissionRejectedCount(newValue)
    }

    override suspend fun setCurrentLocation(location: String) {
        prefManager.setCurrentLocation(location)
    }

    override suspend fun search(text: String, latLng: LatLng): ResponseState<SearchResponse> {
        return getResponseState {
            if (latLng.latitude == 0.00 && latLng.longitude == 0.00) {
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
            }
        }
    }

    override suspend fun getAddresses(uid: String): ResponseState<AddressesResponse> {
        return getResponseState {
            addressApi.getAddresses(uid)
        }
    }

    override suspend fun putAddress(myAddress: AddressesResponse.MyAddress): ResponseState<PutAddressResponse> {
        return getResponseState { addressApi.addNewAddress(myAddress) }
    }

    override suspend fun deleteAddress(
        uid: String,
        id: String
    ): ResponseState<DeleteAddressResponse> {
        return getResponseState {
            addressApi.deleteAddress(uid, id)
        }
    }

    override suspend fun selectCurrent(cid: String, pid: String?) {
        addressApi.selectCurrent(cid, pid)
    }
}