package fit.asta.health.common.address.data.repo

import android.annotation.SuppressLint
import android.content.IntentSender
import android.location.Address
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import fit.asta.health.R
import fit.asta.health.common.address.data.modal.MyAddress
import fit.asta.health.common.address.data.modal.SearchResponse
import fit.asta.health.common.address.data.remote.AddressApi
import fit.asta.health.common.address.data.remote.SearchLocationApi
import fit.asta.health.common.address.data.utils.LocationResourceProvider
import fit.asta.health.common.utils.MyException
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.ResourcesProvider
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UserPreferencesData
import fit.asta.health.common.utils.getResponseState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AddressRepoImpl @Inject constructor(
    private val addressApi: AddressApi,
    private val searchLocationApi: SearchLocationApi,
    private val prefManager: PrefManager,
    private val resourcesProvider: ResourcesProvider,
    private val locationResourcesProvider: LocationResourceProvider
) : AddressRepo {


    override val userPreferences: Flow<UserPreferencesData> = prefManager.userData

    override fun isPermissionGranted(): Boolean {
        return locationResourcesProvider.isPermissionGranted()
    }
    override fun isLocationEnabled(): Boolean {
        return locationResourcesProvider.isLocationEnabled()
    }

    @SuppressLint("MissingPermission")//No need, as permissions are being handled using locationResourcesProvider
    override fun checkPermissionAndGetLatLng(): Flow<ResponseState<LatLng>> = callbackFlow {
        val fusedLocationProviderClient = locationResourcesProvider.getFusedLocationProviderClient()
        if (!locationResourcesProvider.isPermissionGranted() || !locationResourcesProvider.isLocationEnabled()) {
            trySend(ResponseState.Error(MyException(R.string.location_access_required)))
        } else {
            fusedLocationProviderClient.requestLocationUpdates(
                LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    10000
                ).build(),
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        // Get the current location
                        val location = locationResult.locations.firstOrNull()
                        if (location == null) {
                            trySend(ResponseState.Error(MyException(R.string.unable_to_fetch_location)))
                        } else {
                            trySend(ResponseState.Success(LatLng(location.latitude,location.longitude)))
                        }
                    }
                },
                Looper.getMainLooper()
            )
        }
        awaitClose { close() }
    }

    override fun getAddressDetails(latLng: LatLng): Flow<ResponseState<Address>> = callbackFlow {
        try {
            val geocoder = locationResourcesProvider.getGeocoder()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1
                ) { addresses ->
                    trySend(ResponseState.Success(addresses[0]))
                }
            } else {
                val addresses = geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1,
                )
                if (!addresses.isNullOrEmpty()) {
                    trySend(ResponseState.Success(addresses[0]))
                } else {
                    trySend(ResponseState.Error(MyException(R.string.unable_to_fetch_location)))
                }
            }
        } catch (e: Exception) {
            trySend(ResponseState.Error(MyException(R.string.unable_to_fetch_location)))
        }

        awaitClose { close() }
    }


    override suspend fun updateLocationPermissionRejectedCount(newValue: Int) {
        prefManager.setLocationPermissionRejectedCount(newValue)
    }

    override suspend fun setCurrentLocation(location: String) {
        prefManager.setCurrentLocation(location)
    }

    override suspend fun search(text: String, latLng: LatLng): ResponseState<SearchResponse> {
        return getResponseState {
            if (latLng.latitude == 0.0 && latLng.longitude == 0.0) {
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

    override suspend fun getAddresses(uid: String): ResponseState<List<MyAddress>> {
        return getResponseState {
            addressApi.getAddresses(uid).data
        }
    }

    override suspend fun putAddress(myAddress: MyAddress): ResponseState<Boolean> {
        return getResponseState { addressApi.addNewAddress(myAddress).data.flag }
    }

    override suspend fun deleteAddress(
        uid: String,
        id: String
    ): ResponseState<Boolean> {
        return getResponseState {
            addressApi.deleteAddress(uid, id).flag
        }
    }

    override suspend fun selectAddress(cid: String, pid: String?): ResponseState<Unit> {
        return getResponseState {
            addressApi.selectCurrent(cid, pid)
        }
    }

    override fun enableLocationRequest(showPopup: (IntentSenderRequest) -> Unit) {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                10000
            ).build()

            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

            val client: SettingsClient = locationResourcesProvider.getSettingsClient()
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
            task.addOnSuccessListener { locationSettingsResponse ->
                Log.d(
                    "Location",
                    "createLocationRequest: Success ${locationSettingsResponse.locationSettingsStates}"
                )
            }
            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        val intentSenderRequest =
                            IntentSenderRequest.Builder(exception.resolution).build()
                        showPopup(intentSenderRequest)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                }
            }
        }
}