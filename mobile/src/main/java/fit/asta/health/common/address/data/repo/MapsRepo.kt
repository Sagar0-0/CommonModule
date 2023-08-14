package fit.asta.health.common.address.data.repo

import com.google.android.gms.maps.model.LatLng
import fit.asta.health.UserPreferences
import fit.asta.health.common.address.data.modal.AddressesResponse
import fit.asta.health.common.address.data.modal.AddressesResponse.MyAddress
import fit.asta.health.common.address.data.modal.DeleteAddressResponse
import fit.asta.health.common.address.data.modal.PutAddressResponse
import fit.asta.health.common.address.data.modal.SearchResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UserPreferencesData
import kotlinx.coroutines.flow.Flow

interface MapsRepo {

    val userPreferences : Flow<UserPreferencesData>
    suspend fun search(text: String, latLng: LatLng): ResponseState<SearchResponse>

    suspend fun getAddresses(uid: String): ResponseState<AddressesResponse>

    suspend fun putAddress(myAddress: MyAddress): ResponseState<PutAddressResponse>

    suspend fun deleteAddress(uid: String, id: String): ResponseState<DeleteAddressResponse>

    suspend fun selectCurrent(cid: String, pid: String?)

    suspend fun updateLocationPermissionRejectedCount(newValue: Int)

    suspend fun setCurrentLocation(location: String)
}