package fit.asta.health.auth.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.data.model.domain.User
import fit.asta.health.auth.data.repo.AuthRepo
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val loginState = _loginState.asStateFlow()

    private val _logoutState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val logoutState = _logoutState.asStateFlow()

    private val _deleteState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val deleteState = _deleteState.asStateFlow()

    val currentUser: User?
        get() = authRepo.getUser()

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