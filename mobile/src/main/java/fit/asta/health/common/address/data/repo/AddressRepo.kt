package fit.asta.health.common.address.data.repo

import android.location.Address
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.maps.model.LatLng
import fit.asta.health.common.address.data.modal.MyAddress
import fit.asta.health.common.address.data.modal.SearchResponse
import fit.asta.health.common.utils.LocationResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UserPreferencesData
import kotlinx.coroutines.flow.Flow

interface AddressRepo {

    val userPreferences: Flow<UserPreferencesData>

    fun isLocationEnabled() : Boolean
    fun isPermissionGranted() : Boolean
    fun checkPermissionAndGetLatLng() : Flow<LocationResponse>

    fun getAddressDetails(latLng: LatLng) : Flow<ResponseState<Address>>

    suspend fun search(text: String, latitude : Double, longitude: Double): ResponseState<SearchResponse>

    suspend fun getSavedAddresses(uid: String): ResponseState<List<MyAddress>>

    suspend fun putAddress(myAddress: MyAddress): ResponseState<Boolean>

    suspend fun deleteAddress(uid: String, id: String): ResponseState<Boolean>

    suspend fun selectAddress(cid: String, pid: String?) : ResponseState<Unit>

    suspend fun updateLocationPermissionRejectedCount(newValue: Int)

    suspend fun setCurrentLocation(location: String)
    fun enableLocationRequest(showPopup: (IntentSenderRequest) -> Unit)
}