package fit.asta.health.common.location.maps

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Geocoder
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.location.LocationHelper
import fit.asta.health.common.location.maps.modal.AddressesResponse
import fit.asta.health.common.location.maps.modal.AddressesResponse.MyAddress
import fit.asta.health.common.location.maps.modal.SearchResponse
import fit.asta.health.common.location.maps.repo.MapsRepo
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.common.utils.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MapsViewModel
@Inject constructor(
    private val mapsRepo: MapsRepo,
    @Named("UId")
    val uId: String,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val TAG = "MAPS"

    val isLocationEnabled = MutableStateFlow(false)
    val isPermissionGranted = MutableStateFlow(true)

    private val _currentAddress = MutableStateFlow<android.location.Address?>(null)
    val currentAddress = _currentAddress.asStateFlow()

    private val _currentLatLng = MutableStateFlow(LatLng(0.0, 0.0))
    val currentLatLng = _currentLatLng.asStateFlow()

    private val _searchResponseState = MutableStateFlow<ResultState<SearchResponse>?>(null)
    val searchResponseState = _searchResponseState.asStateFlow()

    private var searchJob: Job? = null

    private val _addressListState = MutableStateFlow<ResultState<AddressesResponse>?>(null)
    val addressListState = _addressListState.asStateFlow()

    private var selectedAddressId by mutableStateOf<String?>(null)

    private val _markerAddressDetail =
        MutableStateFlow<ResultState<android.location.Address?>?>(null)
    val markerAddressDetail = _markerAddressDetail.asStateFlow()


    fun updateCurrentLocationData(context: Context) {
        Log.d(TAG, "updateCurrentLocationData: Called")
        updateLocationServiceStatus()
        viewModelScope.launch {
            if (isLocationEnabled.value && isPermissionGranted.value) {
                getCurrentLatLng(context)
                _currentLatLng.collect {
                    Log.d(TAG, "updateCurrentLocationData: LatLng updated")
                    getCurrentAddress(it, context)
                }
            }
        }
    }

    fun updateLocationServiceStatus() {
        isLocationEnabled.value = locationHelper.isConnected()
    }


    fun enableLocationRequest(
        context: Context,
        onFailure: (intentSenderRequest: IntentSenderRequest) -> Unit
    ) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000
        ).build()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            Log.d(
                "Location",
                "createLocationRequest: Success ${locationSettingsResponse.locationSettingsStates}"
            )
            updateCurrentLocationData(context)
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    onFailure(intentSenderRequest)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    fun setSelectedAdId(id: String) {
        selectedAddressId = id
    }

    //Call only after currentLatLng changes
    private fun getCurrentAddress(latLng: LatLng, context: Context) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                _currentLatLng.value.latitude,
                _currentLatLng.value.longitude,
                1
            )
            if (!addresses.isNullOrEmpty()) {
                _currentAddress.value = addresses[0]
                PrefUtils.setCurrentAddress(
                    "${_currentAddress.value?.subLocality}, ${_currentAddress.value?.locality}",
                    context
                )
                Log.d(TAG, "getCurrentAddress: Success: ${_currentAddress.value}")
            } else {
                Log.e(TAG, "getCurrentAddress: $addresses")
            }
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentAddress: ${e.message}")
        }
    }

    //Call this and start observing currentLatLng
    @SuppressLint("MissingPermission")
    fun getCurrentLatLng(context: Context) {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        val locationResult = fusedLocationProviderClient.lastLocation

        locationResult.addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "getCurrentLocation: Task success: ${task.result}")
                try {
                    val lastKnownLocation = task.result ?: return@addOnCompleteListener
                    _currentLatLng.value =
                        LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                    Log.d(TAG, "After success $_currentLatLng")
                } catch (e: Exception) {
                    Log.e(TAG, "getCurrentLatLng: ${e.message}")
                }
            } else {
                Log.e(TAG, "Exception: %s", task.exception)
            }
        }
    }

    suspend fun getMarkerAddressDetails(lat: Double, long: Double, context: Context) {
        _markerAddressDetail.value = ResultState.Loading
        delay(1000)
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                lat,
                long,
                1,
            )
            _markerAddressDetail.value =
                ResultState.Success(if (!addresses.isNullOrEmpty()) addresses[0] else null)
        } catch (e: Exception) {
            ResultState.Failure(e)
        }
    }

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _searchResponseState.value = ResultState.Loading
            mapsRepo.search(query).cancellable().collect {
                _searchResponseState.value = it
            }
        }
    }

    fun selectCurrentAddress(myAddress: MyAddress) = viewModelScope.launch {
        mapsRepo.selectCurrent(myAddress.id, selectedAddressId)
    }

    fun getAllAddresses() = viewModelScope.launch {
        _addressListState.value = ResultState.Loading
        mapsRepo.getAddresses(uId).collect {
            _addressListState.value = it
            if (it is ResultState.Success) {
                Log.d(TAG, "getAllAddresses: Success " + it.data.status.msg)
            } else if (it is ResultState.Failure) {
                Log.e(TAG, "getAllAddresses: Failure " + it.msg.message)
            }
        }
    }

    fun deleteAddress(name: String, onSuccess: () -> Unit) = viewModelScope.launch {
        val response = mapsRepo.deleteAddress(uId, name)
        response.collect {
            if (it is ResultState.Success) {
                onSuccess()
                getAllAddresses()
                Log.d(TAG, "deleteAddress " + it.data.status.code.toString())
            } else if (it is ResultState.Failure) {
                Log.d(TAG, "deleteAddress " + it.msg.message)
            }
        }
    }

    fun putAddress(myAddress: MyAddress, onSuccess: () -> Unit) = viewModelScope.launch {
        val response = mapsRepo.putAddress(myAddress)
        response.collect {
            if (it is ResultState.Success) {
                Log.d(TAG, "PUT: " + it.data.status.code.toString())
                onSuccess()
                getAllAddresses()
            } else {
                Log.d(TAG, "PUT: $it")
            }
        }
    }

}