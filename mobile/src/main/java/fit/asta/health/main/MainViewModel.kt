package fit.asta.health.main

import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.auth.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.common.address.data.repo.AddressRepo
import fit.asta.health.common.utils.LocationResponse
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getLocationName
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

    private val _isPermissionGranted = MutableStateFlow(false)
    val isPermissionGranted = _isPermissionGranted.asStateFlow()

    fun setIsLocationEnabled() {
        _isLocationEnabled.value = addressRepo.isLocationEnabled()
    }

    fun setIsPermissionGranted() {
        _isPermissionGranted.value = addressRepo.isPermissionGranted()
    }

    val notificationsEnabled = prefManager.userData
        .map {
            it.notificationStatus
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    private val _currentAddressName = MutableStateFlow<UiState<String>>(UiState.Idle)
    val currentAddressName = _currentAddressName.asStateFlow()

    val onboardingStatus = prefManager.userData
        .map {
            it.onboardingShown
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = true,
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

    fun setCurrentLocation() {
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
                                prefManager.setCurrentLocation(addressRes.data.getLocationName())
                                _currentAddressName.value =
                                    UiState.Success(addressRes.data.getLocationName())
                            }
                        }
                    }

                    is LocationResponse.Error -> {
                        _currentAddressName.value = UiState.Error(R.string.error_fetching_location)
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
    fun isAuth() = authRepo.isAuthenticated()
}