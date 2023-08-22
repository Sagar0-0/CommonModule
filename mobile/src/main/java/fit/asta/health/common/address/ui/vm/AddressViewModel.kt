package fit.asta.health.common.address.ui.vm

import android.location.Address
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.auth.di.UID
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.address.data.modal.MyAddress
import fit.asta.health.common.address.data.modal.SearchResponse
import fit.asta.health.common.address.data.repo.AddressRepo
import fit.asta.health.common.utils.LocationResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel
@Inject constructor(
    private val addressRepo: AddressRepo,
    @UID private val uId: String
) : ViewModel() {

    private val addressTAG = "MAP"

    private val _isLocationEnabled = MutableStateFlow(false)
    val isLocationEnabled = _isLocationEnabled.asStateFlow()

    private val _isPermissionGranted = MutableStateFlow(false)
    val isPermissionGranted = _isPermissionGranted.asStateFlow()

    private val _savedAddressListState = MutableStateFlow<UiState<List<MyAddress>>>(UiState.Idle)
    val savedAddressListState = _savedAddressListState.asStateFlow()

    private val _putAddressState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val putAddressState = _putAddressState.asStateFlow()

    private val _deleteAddressState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val deleteAddressState = _deleteAddressState.asStateFlow()

    private val _selectAddressState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val selectAddressState = _selectAddressState.asStateFlow()

    private val _currentAddressState = MutableStateFlow<UiState<Address>>(UiState.Idle)
    val currentAddressState = _currentAddressState.asStateFlow()

    private val _searchResultState = MutableStateFlow<UiState<SearchResponse>>(UiState.Idle)
    val searchResultState = _searchResultState.asStateFlow()

    private val _markerAddressState = MutableStateFlow<UiState<Address>>(UiState.Idle)
    val markerAddressState = _markerAddressState.asStateFlow()

    val locationPermissionRejectedCount = addressRepo.userPreferences
        .map {
            it.locationPermissionRejectedCount
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0,
        )

    private var searchJob: Job? = null
    private var addressDetailJob: Job? = null

    private var selectedAddressId by mutableStateOf<String?>(null)

    init {
        setIsLocationEnabled()
        setIsPermissionGranted()
    }

    fun setIsLocationEnabled() {
        _isLocationEnabled.value = addressRepo.isLocationEnabled()
        checkPermissionAndUpdateCurrentAddress()
    }

    fun setIsPermissionGranted() {
        _isPermissionGranted.value = addressRepo.isPermissionGranted()
    }

    fun updateLocationPermissionRejectedCount(newValue: Int) = viewModelScope.launch {
        addressRepo.updateLocationPermissionRejectedCount(newValue)
    }

    fun clearSearchResponse() {
        _searchResultState.value = UiState.Idle
    }

    fun checkPermissionAndUpdateCurrentAddress() {
        Log.d(addressTAG, "updateCurrentLocationData Called")
        _currentAddressState.value = UiState.Loading
        viewModelScope.launch {
            addressRepo.checkPermissionAndGetLatLng().collect { latLng ->
                when (latLng) {
                    is LocationResponse.Success -> {
                        addressRepo.getAddressDetails(latLng.latLng).collect { addressRes ->
                            _currentAddressState.value = addressRes.toUiState()
                        }
                    }

                    is LocationResponse.Error -> {
                        _currentAddressState.value = UiState.Error(latLng.resId)
                    }

                    LocationResponse.PermissionDenied -> {
                        _isPermissionGranted.value = false
                    }

                    LocationResponse.ServiceDisabled -> {
                        _isLocationEnabled.value = false
                    }
                }
            }
        }
    }


    fun enableLocationRequest(
        showPopup: (intentSenderRequest: IntentSenderRequest) -> Unit
    ) {
        addressRepo.enableLocationRequest(showPopup)
    }

    fun setSelectedAdId(id: String) {
        selectedAddressId = id
    }

    fun getMarkerAddressDetails(latLng: LatLng) {
        _markerAddressState.value = UiState.Loading
        addressDetailJob?.cancel()
        addressDetailJob = viewModelScope.launch {
            delay(200)
            addressRepo.getAddressDetails(latLng).cancellable().collect {
                _markerAddressState.value = it.toUiState()
            }
        }
    }

    fun search(query: String) {
        _searchResultState.value = UiState.Loading
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (_currentAddressState.value is UiState.Success) {
                _searchResultState.value =
                    addressRepo.search(
                        query,
                        (_currentAddressState.value as UiState.Success).data.latitude,
                        (_currentAddressState.value as UiState.Success).data.latitude
                    ).toUiState()
            } else {
                _searchResultState.value =
                    addressRepo.search(query, 0.0, 0.0).toUiState()
            }
        }
    }

    fun selectAddress(myAddress: MyAddress) {
        _selectAddressState.value = UiState.Loading
        viewModelScope.launch {
            _selectAddressState.value =
                addressRepo.selectAddress(myAddress.id, selectedAddressId).toUiState()
        }
    }

    fun resetSelectAddressState() {
        _selectAddressState.value = UiState.Idle
    }

    fun getSavedAddresses() {
        _savedAddressListState.value = UiState.Loading
        viewModelScope.launch {
            _savedAddressListState.value = addressRepo.getSavedAddresses(uId).toUiState()
        }
    }

    fun deleteAddress(name: String) {
        _deleteAddressState.value = UiState.Loading
        viewModelScope.launch {
            _deleteAddressState.value = addressRepo.deleteAddress(uId, name).toUiState()
        }
    }

    fun resetDeleteState() {
        _deleteAddressState.value = UiState.Idle
    }

    fun putAddress(myAddress: MyAddress) {
        _putAddressState.value = UiState.Loading
        viewModelScope.launch {
            _putAddressState.value = addressRepo.putAddress(myAddress.copy(uid = uId)).toUiState()
        }
    }

    fun resetPutState() {
        _putAddressState.value = UiState.Idle
    }
}