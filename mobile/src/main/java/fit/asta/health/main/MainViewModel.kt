package fit.asta.health.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.data.repo.AuthRepo
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val prefManager: PrefManager,
    private val authRepo: AuthRepo
) : ViewModel() {

    val notificationsEnabled = prefManager.userData
        .map {
            it.notificationStatus
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val currentAddressName = prefManager.userData
        .map {
            UiState.Success(it.currentAddress)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading,
        )

    val onboardingStatus = prefManager.userData
        .map {
            it.onboardingShown
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = true,
        )

    fun setNotificationStatus(newValue: Boolean) = viewModelScope.launch {
        prefManager.setNotificationStatus(newValue)
    }

    fun isAuth() = authRepo.isAuthenticated()
}