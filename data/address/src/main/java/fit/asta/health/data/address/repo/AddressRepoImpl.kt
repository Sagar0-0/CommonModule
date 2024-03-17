package fit.asta.health.data.address.repo

import android.location.Address
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.maps.model.LatLng
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.core.network.BuildConfig
import fit.asta.health.data.address.remote.AddressApi
import fit.asta.health.data.address.remote.LocationApi
import fit.asta.health.data.address.remote.modal.DeleteAddressResponse
import fit.asta.health.data.address.remote.modal.LocationResponse
import fit.asta.health.data.address.remote.modal.MyAddress
import fit.asta.health.data.address.remote.modal.PutAddressResponse
import fit.asta.health.data.address.remote.modal.SearchResponse
import fit.asta.health.data.address.utils.AddressManager
import fit.asta.health.data.address.utils.LocationHelper
import fit.asta.health.data.address.utils.LocationResourceProvider
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.UserPreferencesData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddressRepoImpl @Inject constructor(
    private val addressApi: AddressApi,
    private val locationApi: LocationApi,
    private val prefManager: PrefManager,
    private val addressManager: AddressManager,
    private val locationResourcesProvider: LocationResourceProvider,
    private val locationHelper: LocationHelper,
    @IODispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AddressRepo {

    override val userPreferences: Flow<UserPreferencesData> = prefManager.userData

    override fun isPermissionGranted(): Boolean {
        return locationResourcesProvider.isPermissionGranted()
    }

    override fun isLocationEnabled(): Boolean {
        return locationResourcesProvider.isLocationEnabled()
    }

    override suspend fun checkPermissionAndGetLatLng(): Flow<LocationResponse> {
        return locationHelper.checkPermissionAndGetLatLng()
    }

    override suspend fun getAddressDetails(latLng: LatLng): Flow<ResponseState<Address>> {
        return withContext(dispatcher) {
            addressManager.getAddressDetails(latLng)
        }
    }

    override suspend fun updateLocationPermissionRejectedCount(newValue: Int) {
        prefManager.setLocationPermissionRejectedCount(newValue)
    }

    override suspend fun setCurrentLocation(location: String) {
        prefManager.setCurrentLocation(location)
    }

    override suspend fun search(
        text: String,
        latitude: Double,
        longitude: Double
    ): ResponseState<SearchResponse> {
        return withContext(dispatcher) {
            getResponseState {
                if (latitude == 0.00 && longitude == 0.00) {
                    locationApi.search(
                        query = text,
                        key = BuildConfig.MAPS_API_KEY
                    )
                } else {
                    locationApi.searchBiased(
                        latLng = "${latitude},${longitude}",
                        rankBy = "distance",
                        query = text,
                        key = BuildConfig.MAPS_API_KEY
                    )
                }
            }
        }
    }

    override suspend fun getSavedAddresses(uid: String): ResponseState<List<MyAddress>> {
        return getApiResponseState {
            addressApi.getAddresses(uid)
        }
    }

    override suspend fun putAddress(myAddress: MyAddress): ResponseState<PutAddressResponse> {
        return getApiResponseState {
            addressApi.putAddress(myAddress)
        }
    }

    override suspend fun deleteAddress(
        uid: String,
        id: String
    ): ResponseState<DeleteAddressResponse> {
        return getApiResponseState {
            addressApi.deleteAddress(uid, id)
        }
    }

    override suspend fun selectAddress(cid: String, pid: String?): ResponseState<Unit> {
        return getResponseState {
            addressApi.selectCurrent(cid, pid)
        }
    }

    override suspend fun enableLocationRequest(showPopup: (IntentSenderRequest) -> Unit) {
        locationHelper.enableLocationRequest(showPopup)
    }
}