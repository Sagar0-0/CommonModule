package fit.asta.health.data.address.repo

import android.annotation.SuppressLint
import android.content.IntentSender
import android.location.Address
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import fit.asta.health.common.utils.MyException
import fit.asta.health.common.utils.ResourcesProvider
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.data.address.modal.LocationResponse
import fit.asta.health.data.address.modal.MyAddress
import fit.asta.health.data.address.modal.SearchResponse
import fit.asta.health.data.address.remote.AddressApi
import fit.asta.health.data.address.remote.SearchLocationApi
import fit.asta.health.data.address.utils.LocationResourceProvider
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.UserPreferencesData
import fit.asta.health.resources.strings.R.string
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddressRepoImpl @Inject constructor(
    private val addressApi: AddressApi,
    private val searchLocationApi: SearchLocationApi,
    private val prefManager: PrefManager,
    private val resourcesProvider: ResourcesProvider,
    private val locationResourcesProvider: LocationResourceProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AddressRepo {

    override val userPreferences: Flow<UserPreferencesData> = prefManager.userData

    override fun isPermissionGranted(): Boolean {
        return locationResourcesProvider.isPermissionGranted()
    }

    override fun isLocationEnabled(): Boolean {
        return locationResourcesProvider.isLocationEnabled()
    }

    @SuppressLint("MissingPermission")//No need, as permissions are being handled using locationResourcesProvider
    override fun checkPermissionAndGetLatLng(): Flow<LocationResponse> = callbackFlow {
        val fusedLocationProviderClient = locationResourcesProvider.getFusedLocationProviderClient()
        if (!locationResourcesProvider.isPermissionGranted()) {
            trySend(LocationResponse.PermissionDenied)
        } else if (!locationResourcesProvider.isLocationEnabled()) {
            trySend(LocationResponse.ServiceDisabled)
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
                            trySend(LocationResponse.Error(string.unable_to_fetch_location))
                        } else {
                            trySend(
                                LocationResponse.Success(
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    )
                                )
                            )
                        }
                    }
                },
                Looper.getMainLooper()
            )
        }
        awaitClose { close() }
    }

    override suspend fun getAddressDetails(latLng: LatLng): Flow<ResponseState<Address>> {
        return withContext(dispatcher) {
            callbackFlow {
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
                            trySend(ResponseState.Error(MyException(string.unable_to_fetch_location)))
                        }
                    }
                } catch (e: Exception) {
                    trySend(ResponseState.Error(MyException(string.unable_to_fetch_location)))
                }

                awaitClose { close() }
            }
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
                if (latitude == 0.0 && longitude == 0.0) {
                    searchLocationApi.search(
                        text,
                        resourcesProvider.getString(string.MAPS_API_KEY)
                    )
                } else {
                    searchLocationApi.searchBiased(
                        "${latitude},${longitude}",
                        "distance",
                        text,
                        resourcesProvider.getString(string.MAPS_API_KEY)
                    )
                }
            }
        }
    }

    override suspend fun getSavedAddresses(uid: String): ResponseState<List<MyAddress>> {
        return getResponseState {
            addressApi.getAddresses(uid).data
        }
    }

    override suspend fun putAddress(myAddress: MyAddress): ResponseState<Boolean> {
        return getResponseState { addressApi.putAddress(myAddress).data.flag }
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