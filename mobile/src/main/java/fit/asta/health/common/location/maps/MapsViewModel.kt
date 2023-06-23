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

    private val _addressText = MutableStateFlow<String?>(null)
    val addressText = _addressText.asStateFlow()

    private var currentLatLng by mutableStateOf(LatLng(0.0, 0.0))

    private val _searchResponseState = MutableStateFlow<ResultState<SearchResponse>?>(null)
    val searchResponseState = _searchResponseState.asStateFlow()

    private var searchJob: Job? = null

    private val _addressListState = MutableStateFlow<ResultState<AddressesResponse>?>(null)
    val addressListState = _addressListState.asStateFlow()

    private var selectedAddressId by mutableStateOf<String?>(null)

    private val _addressDetail = MutableStateFlow<ResultState<android.location.Address?>?>(null)
    val addressDetail = _addressDetail.asStateFlow()

    private var noSavedAddress by mutableStateOf(true)

    fun setSelectedAdId(id: String) {
        selectedAddressId = id
    }

    fun getCurrentAddressText(context: Context) {
        getAllAddresses()
        viewModelScope.launch {
            delay(3000)
            if (noSavedAddress) {
                Log.d(TAG, "getCurrentAddressText: NO SAVED FOUND")
                saveCurrentAddressDetails(context)
            } else {
                Log.d(TAG, "getCurrentAddressText: SAVED FOUND")
                addressListState.value.also {
                    if (it is ResultState.Success) {
                        val list = it.data.data
                        for (ad in list) {
                            if (ad.selected) {
                                _addressText.value = ad.sub
                                break
                            }
                        }
                    }
                }
            }
        }

    }

    private fun saveCurrentAddressDetails(context: Context) {
        getCurrentLocation(context)
        viewModelScope.launch {
            delay(3000)
            Log.d(TAG, "saveCurrentAddressDetails: ${currentLatLng.latitude}")
            if (currentLatLng.latitude == 0.0 && currentLatLng.longitude == 0.0) return@launch
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(
                    currentLatLng.latitude,
                    currentLatLng.longitude,
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
                    _addressText.value = myAddress.sub
                    putAddress(myAddress)
                    getAllAddresses()
                    Log.d(TAG, "saveCurrentAddressDetails: Success")
                } else {
                    Log.e(TAG, "saveCurrentAddressDetails: $addresses")
                }
            } catch (e: Exception) {
                Log.e(TAG, "saveCurrentAddressDetails: ${e.message}")
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context) {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        val locationResult = fusedLocationProviderClient.lastLocation

        locationResult.addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "getCurrentLocation: Task success: ${task.result}")
                val lastKnownLocation = task.result ?: return@addOnCompleteListener
                currentLatLng = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                Log.d(TAG, "After success $currentLatLng")
            }
        }
    }

    suspend fun getAddressDetails(lat: Double, long: Double, context: Context) {
        _addressDetail.value = ResultState.Loading
        delay(1000)
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                lat,
                long,
                1
            )
            _addressDetail.value =
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
        _addressText.value = address.sub
    }

    fun getAllAddresses() = viewModelScope.launch {
        _addressListState.value = ResultState.Loading
        mapsRepo.getAddresses(uId).collect {
            _addressListState.value = it
            if (it is ResultState.Success) {
                Log.d(TAG, "getAllAddresses: Success " + it.data.status.msg)
                noSavedAddress = it.data.data.isEmpty()
            } else if (it is ResultState.Failure) {
                Log.e(TAG, "getAllAddresses: Failure " + it.msg.message)
            }
        }
    }

    fun deleteAddress(name: String, context: Context) = viewModelScope.launch {
        val response = mapsRepo.deleteAddress(uId, name)
        response.collect {
            if (it is ResultState.Success) {
                getCurrentAddressText(context)
                Log.d(TAG, "deleteAddress " + it.data.status.code.toString())
            } else if (it is ResultState.Failure) {
                Log.d(TAG, "deleteAddress " + it.msg.message)
            }
        }
    }

    fun putAddress(address: Address) = viewModelScope.launch {
        val response = mapsRepo.putAddress(address)
        response.collect {
            if (it is ResultState.Success) {
                Log.d(TAG, it.data.status.code.toString())
                if (address.selected) _addressText.value = address.sub
            }
        }
    }

}