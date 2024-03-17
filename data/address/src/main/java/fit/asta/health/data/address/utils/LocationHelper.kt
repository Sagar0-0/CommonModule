package fit.asta.health.data.address.utils

import android.annotation.SuppressLint
import android.content.IntentSender
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
import fit.asta.health.data.address.remote.modal.LocationResponse
import fit.asta.health.resources.strings.R.string
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationHelper
@Inject constructor(
    private val locationResourcesProvider: LocationResourceProvider
) {

    @SuppressLint("MissingPermission")//No need, as permissions are being handled using locationResourcesProvider
    suspend fun checkPermissionAndGetLatLng(): Flow<LocationResponse> = callbackFlow {

        if (!locationResourcesProvider.isPermissionGranted()) {
            trySend(LocationResponse.PermissionDenied)
        } else if (!locationResourcesProvider.isLocationEnabled()) {
            trySend(LocationResponse.ServiceDisabled)
        } else {
            val fusedLocationProviderClient =
                locationResourcesProvider.getFusedLocationProviderClient()
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    trySend(
                        LocationResponse.Success(
                            LatLng(
                                it.latitude,
                                it.longitude
                            )
                        )
                    )
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
            }
        }
        awaitClose { close() }
    }

    fun enableLocationRequest(showPopup: (IntentSenderRequest) -> Unit) {
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