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

    override suspend fun search(text: String, latLng: LatLng): Flow<ResponseState<SearchResponse>> =
        callbackFlow {
            trySend(
                try {
                    ResponseState.Success(
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
                    )
                } catch (e: Exception) {
                    ResponseState.Error(
                        e
                    )
                }

            )
            awaitClose {
                close()
            }
        }

    override suspend fun getAddresses(uid: String): ResponseState<AddressesResponse> {
        return try {
            ResponseState.Success(
                addressApi.getAddresses(uid)
            )
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }

    override suspend fun putAddress(myAddress: AddressesResponse.MyAddress): ResponseState<PutAddressResponse> {
        return try {
            ResponseState.Success(addressApi.addNewAddress(myAddress))
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }

    override suspend fun deleteAddress(
        uid: String,
        id: String
    ): ResponseState<DeleteAddressResponse> {
        return try {
            ResponseState.Success(addressApi.deleteAddress(uid, id))
        } catch (e: Exception) {
            Log.e("MAPS", "REPO deleteAddress: ${e.message}")
            ResponseState.Error(e)
        }
    }

    override suspend fun selectCurrent(cid: String, pid: String?) {
        addressApi.selectCurrent(cid, pid)
    }
}