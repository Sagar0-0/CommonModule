package fit.asta.health.main

import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getShortAddressName
import fit.asta.health.data.address.remote.modal.LocationResponse
import fit.asta.health.data.address.repo.AddressRepo
import fit.asta.health.datastore.PrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val prefManager: PrefManager,
    private val authRepo: AuthRepo,
    private val addressRepo: AddressRepo
) : ViewModel() {

    private val _isLocationEnabled = MutableStateFlow(false)
    val isLocationEnabled = _isLocationEnabled.asStateFlow()

    fun setReferralChecked() = viewModelScope.launch {
        prefManager.setReferralChecked()
    }

    fun setIsLocationEnabled() {
        _isLocationEnabled.value = addressRepo.isLocationEnabled()
    }

    fun isPermissionGranted() = addressRepo.isPermissionGranted()

    val notificationsEnabled = prefManager.userData
        .map {
            it.notificationStatus
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )

    val isReferralChecked = prefManager.userData
        .map {
            UiState.Success(it.isReferralChecked)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UiState.Loading,
        )

    val theme = prefManager.userData
        .map {
            UiState.Success(it.theme)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UiState.Loading,
        )

    private val _currentAddressName = MutableStateFlow<UiState<String>>(UiState.Idle)
    val currentAddressName = _currentAddressName.asStateFlow()

    val screenCode = prefManager.userData
        .map {
            UiState.Success(it.screenCode)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UiState.Loading,
        )

    val locationPermissionRejectedCount = prefManager.userData
        .map {
            it.locationPermissionRejectedCount
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0,
        )

    init {
        setCurrentLocation()
    }

    private fun setCurrentLocation() {
        viewModelScope.launch {
            prefManager.userData.map { it.currentAddress }.collectLatest {
                if (it.isNotEmpty()) {
                    _currentAddressName.value = UiState.Success(it)
                }
            }
        }
    }

    fun checkPermissionAndUpdateCurrentAddress() {
        _currentAddressName.value = UiState.Loading
        viewModelScope.launch {
            addressRepo.checkPermissionAndGetLatLng().collect { latLng ->
                when (latLng) {
                    is LocationResponse.Success -> {
                        addressRepo.getAddressDetails(latLng.latLng).collectLatest { addressRes ->
                            if (addressRes is ResponseState.Success) {
                                prefManager.setAddressValue(
                                    address = addressRes.data.getShortAddressName(),
                                    lat = latLng.latLng.latitude,
                                    long = latLng.latLng.longitude
                                )
                                _currentAddressName.value =
                                    UiState.Success(addressRes.data.getShortAddressName())
                            }
                        }
                    }

                    is LocationResponse.Error -> {
                        _currentAddressName.value =
                            UiState.ErrorMessage(R.string.error_fetching_location)
                    }

                    LocationResponse.ServiceDisabled -> {
                        _isLocationEnabled.value = false
                    }

                    else -> {}
                }
            }
        }
    }

    fun updateLocationPermissionRejectedCount(value: Int) = viewModelScope.launch {
        prefManager.setLocationPermissionRejectedCount(value)
    }

    fun enableLocationRequest(showPopup: (IntentSenderRequest) -> Unit) {
        addressRepo.enableLocationRequest(showPopup)
    }

    fun setNotificationStatus(newValue: Boolean) = viewModelScope.launch {
        prefManager.setNotificationStatus(newValue)
    }

    fun getUser() = authRepo.getUser()
    fun setReferCode(code: String) = viewModelScope.launch {
        prefManager.setReferralCode(code)
    }
}