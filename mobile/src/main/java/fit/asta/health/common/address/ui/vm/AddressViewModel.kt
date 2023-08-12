package fit.asta.health.common.address.ui.vm

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Looper
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.common.address.data.modal.AddressesResponse
import fit.asta.health.common.address.data.modal.AddressesResponse.MyAddress
import fit.asta.health.common.address.data.modal.SearchResponse
import fit.asta.health.common.address.data.repo.MapsRepo
import fit.asta.health.common.address.data.utils.LocationHelper
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getLocationName
import fit.asta.health.common.utils.toUiState
import fit.asta.health.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AddressViewModel
@Inject constructor(
    private val mapsRepo: MapsRepo,
    @Named("UId")
    val uId: String,
    private val locationHelper: LocationHelper,
    @IODispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val TAG = "MAPS"

    val isLocationEnabled = MutableStateFlow(false)
    val isPermissionGranted = MutableStateFlow(true)

    private val _currentAddressState =
        MutableStateFlow<UiState<Address?>>(UiState.Loading)
    val currentAddressState = _currentAddressState.asStateFlow()

    val currentAddressStringState = mapsRepo.userPreferences
        .map {
            UiState.Success(it.currentAddress)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Idle,
        )

    val locationPermissionRejectedCount = mapsRepo.userPreferences
        .map {
            it.locationPermissionRejectedCount
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0,
        )

    private val _currentLatLng = MutableStateFlow(LatLng(0.0, 0.0))
    val currentLatLng = _currentLatLng.asStateFlow()

    private val _searchUiState =
        MutableStateFlow<UiState<SearchResponse?>>(UiState.Idle)
    val searchUiState = _searchUiState.asStateFlow()

    private var searchJob: Job? = null
    private var addressDetailJob: Job? = null

    private val _addressListState =
        MutableStateFlow<UiState<AddressesResponse?>>(UiState.Loading)
    val addressListState = _addressListState.asStateFlow()

    private var selectedAddressId by mutableStateOf<String?>(null)

    private val _markerAddressDetail =
        MutableStateFlow<UiState<Address>>(UiState.Idle)
    val markerAddressDetail = _markerAddressDetail.asStateFlow()

    init {
        updateLocationServiceStatus()
    }

    fun updateLocationPermissionRejectedCount(newValue: Int) = viewModelScope.launch(dispatcher) {
        mapsRepo.updateLocationPermissionRejectedCount(newValue)
    }

    fun clearSearchResponse() {
        _searchUiState.value = UiState.Idle
    }

    fun updateCurrentLocationData(context: Context) {
        _currentAddressState.value = UiState.Loading
        Log.d(TAG, "updateCurrentLocationData: Called")
        updateLocationServiceStatus()
        if (isLocationEnabled.value && isPermissionGranted.value) {
            getCurrentLatLng(context)
        }
    }

    private fun updateLocationServiceStatus() {
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
            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1
                ) { addresses ->
                    _currentAddressState.value = UiState.Success(addresses[0])
                    viewModelScope.launch(dispatcher) {
                        mapsRepo.setCurrentLocation(getLocationName(addresses[0]))
                    }
                    Log.d(TAG, "getCurrentAddress: Success: ${_currentAddressState.value}")
                }
            } else {
                val addresses = geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1,
                )
                if (!addresses.isNullOrEmpty()) {
                    _currentAddressState.value = UiState.Success(addresses[0])
                    viewModelScope.launch(dispatcher) {
                        viewModelScope.launch(dispatcher) {
                            mapsRepo.setCurrentLocation(getLocationName(addresses[0]))
                        }
                    }
                    Log.d(TAG, "getCurrentAddress: Success: ${_currentAddressState.value}")
                } else {
                    Log.e(TAG, "getCurrentAddress NULL: $addresses")
                    _currentAddressState.value = UiState.Error(R.string.error_fetching_location)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentAddress EXCEPTION: ${e.message}")
            _currentAddressState.value = UiState.Error(R.string.error_fetching_location)
        }
    }

    //Call this and start observing currentLatLng
    private fun getCurrentLatLng(context: Context) {
        Log.d(TAG, "getCurrentLatLng: Called")
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
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
                        Log.e(TAG, "onLocationResult: NULL LOCATION")
                        return
                    }
                    _currentLatLng.value =
                        LatLng(location.latitude, location.longitude)
                    getCurrentAddress(_currentLatLng.value, context)
                }
            },
            Looper.getMainLooper()
        )
    }

    fun getMarkerAddressDetails(lat: Double, long: Double, context: Context) {
        _markerAddressDetail.value = UiState.Loading
        addressDetailJob?.cancel()
        addressDetailJob = viewModelScope.launch(dispatcher) {
            delay(200)
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(
                        lat,
                        long,
                        1,
                    ) { p0 ->
                        _markerAddressDetail.value = UiState.Success(p0[0])
                    }
                } else {
                    val addresses = geocoder.getFromLocation(
                        lat,
                        long,
                        1,
                    )
                    _markerAddressDetail.value =
                        if (!addresses.isNullOrEmpty()) {
                            UiState.Success(addresses[0])
                        } else {
                            UiState.Error(R.string.unable_to_fetch_location)
                        }
                }
            } catch (e: Exception) {
                _markerAddressDetail.value = UiState.Error(R.string.unable_to_fetch_location)
            }

        }
    }


    fun search(query: String) {
        _searchUiState.value = UiState.Loading
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatcher) {
            _searchUiState.value = mapsRepo.search(query, _currentLatLng.value).toUiState()
        }
    }

    fun selectCurrentAddress(myAddress: MyAddress) = viewModelScope.launch(dispatcher) {
        mapsRepo.selectCurrent(myAddress.id, selectedAddressId)
    }

    fun getAllAddresses() {
        _addressListState.value = UiState.Loading
        viewModelScope.launch(dispatcher) {
            _addressListState.value = mapsRepo.getAddresses(uId).toUiState()
        }
    }

    fun deleteAddress(name: String, onSuccess: () -> Unit) = viewModelScope.launch(dispatcher) {
        val response = mapsRepo.deleteAddress(uId, name)
        if (response is ResponseState.Success) {
            Log.d(TAG, "DEL: $response")
            onSuccess()
            getAllAddresses()
        } else {
            Log.e(TAG, "DEL: $response")
        }
    }

    fun putAddress(myAddress: MyAddress, onSuccess: () -> Unit) =
        viewModelScope.launch(dispatcher) {
            val response = mapsRepo.putAddress(myAddress.copy(uid = uId))
            if (response is ResponseState.Success) {
                Log.d(TAG, "PUT: $response")
                onSuccess()
                getAllAddresses()
            } else {
                Log.e(TAG, "PUT: $response")
            }
        }

}