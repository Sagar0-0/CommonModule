package fit.asta.health.common.location.maps

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.location.maps.modal.AddressesResponse
import fit.asta.health.common.location.maps.modal.AddressesResponse.Address
import fit.asta.health.common.location.maps.modal.SearchResponse
import fit.asta.health.common.location.maps.repo.MapsRepo
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
    val uId: String
) : ViewModel() {

    private val TAG = "MAPS"

    private val _currentAddress = MutableStateFlow<Address?>(null)
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


    fun setSelectedAdId(id: String) {
        selectedAddressId = id
    }

    //Call only after currentLatLng changes
    fun getCurrentAddress(context: Context) {
        Log.d(TAG, "getCurrentAddress: ${_currentLatLng.value.latitude}")
        if (_currentLatLng.value.latitude == 0.0 && _currentLatLng.value.longitude == 0.0) return
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                _currentLatLng.value.latitude,
                _currentLatLng.value.longitude,
                1
            )
            if (!addresses.isNullOrEmpty()) {
                val address: android.location.Address = addresses[0]
                val myAddress = Address(
                    area = address.adminArea,
                    selected = true,
                    block = address.locality ?: "",
                    hn = address.subAdminArea,
                    id = "",
                    lat = address.latitude,
                    lon = address.longitude,
                    loc = address.locality,
                    nearby = "",
                    name = "Home",
                    pin = address.postalCode,
                    ph = "Unknown",
                    sub = address.subAdminArea,
                    uid = uId
                )
                _currentAddress.value = myAddress
                Log.d(TAG, "getCurrentAddress: Success")
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
                val lastKnownLocation = task.result ?: return@addOnCompleteListener
                _currentLatLng.value =
                    LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                Log.d(TAG, "After success $_currentLatLng")
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
                1
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

    fun selectCurrentAddress(address: Address) = viewModelScope.launch {
        mapsRepo.selectCurrent(address.id, selectedAddressId)
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

    fun putAddress(address: Address, onSuccess: () -> Unit) = viewModelScope.launch {
        val response = mapsRepo.putAddress(address)
        Log.d(TAG, "PUT method called")
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