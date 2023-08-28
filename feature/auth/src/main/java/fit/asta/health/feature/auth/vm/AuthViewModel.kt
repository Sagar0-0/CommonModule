package fit.asta.health.feature.auth.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.profile.repo.ProfileRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class AuthViewModel
@Inject constructor(
    private val authRepo: AuthRepo,
    private val profileRepo: ProfileRepo
) : ViewModel() {


    private val _isProfileAvailable = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val isProfileAvailable = _isProfileAvailable.asStateFlow()

    private val _loginState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val loginState = _loginState.asStateFlow()

    private val _logoutState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val logoutState = _logoutState.asStateFlow()

    private val _deleteState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val deleteState = _deleteState.asStateFlow()

    val currentUser: User?
        get() = authRepo.getUser()

    fun isAuthenticated() = authRepo.isAuthenticated()

    fun isProfileAvailable(userId: String) {
        _isProfileAvailable.value = UiState.Loading
        viewModelScope.launch {
            val res = profileRepo.isProfileAvailable(userId)
            if (res is ResponseState.Success && res.data) {
                authRepo.setBasicProfileDone()
            }
            _isProfileAvailable.value = res.toUiState()
        }
    }

    fun signInWithGoogleCredentials(authCredential: AuthCredential) {
        _loginState.value = UiState.Loading
        viewModelScope.launch {
            authRepo.signInWithCredential(authCredential).collect {
                _loginState.value = it.toUiState()
            }
        }
    }

    fun logout() {
        _logoutState.value = UiState.Loading
        _logoutState.value = authRepo.signOut().toUiState()
    }

    fun resetLogoutState() {
        _logoutState.value = UiState.Idle
    }

    fun deleteAccount() {
        _deleteState.value = UiState.Loading
        viewModelScope.launch {
            authRepo.deleteAccount().collect {
                _deleteState.value = it.toUiState()
            }
        }
    }

    fun resetDeleteState() {
        _deleteState.value = UiState.Idle
    }
}