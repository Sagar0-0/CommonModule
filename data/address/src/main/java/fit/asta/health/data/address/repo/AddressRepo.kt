package fit.asta.health.data.address.repo

import android.location.Address
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.maps.model.LatLng
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.address.remote.modal.DeleteAddressResponse
import fit.asta.health.data.address.remote.modal.LocationResponse
import fit.asta.health.data.address.remote.modal.MyAddress
import fit.asta.health.data.address.remote.modal.PutAddressResponse
import fit.asta.health.data.address.remote.modal.SearchResponse
import fit.asta.health.datastore.UserPreferencesData
import kotlinx.coroutines.flow.Flow

interface AddressRepo {

    val userPreferences: Flow<UserPreferencesData>

    fun isLocationEnabled(): Boolean
    fun isPermissionGranted(): Boolean
    fun checkPermissionAndGetLatLng(): Flow<LocationResponse>

    suspend fun getAddressDetails(latLng: LatLng): Flow<ResponseState<Address>>

    suspend fun search(
        text: String,
        latitude: Double,
        longitude: Double
    ): ResponseState<SearchResponse>

    suspend fun getSavedAddresses(uid: String): ResponseState<List<MyAddress>>

    suspend fun putAddress(myAddress: MyAddress): ResponseState<PutAddressResponse>

    suspend fun deleteAddress(uid: String, id: String): ResponseState<DeleteAddressResponse>

    suspend fun selectAddress(cid: String, pid: String?): ResponseState<Unit>

    suspend fun updateLocationPermissionRejectedCount(newValue: Int)

    suspend fun setCurrentLocation(location: String)
    fun enableLocationRequest(showPopup: (IntentSenderRequest) -> Unit)
}