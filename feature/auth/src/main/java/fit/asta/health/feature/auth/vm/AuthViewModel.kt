package fit.asta.health.feature.auth.vm

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.fcm.remote.TokenDTO
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.profile.repo.ProfileRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

    private val isFcmTokenUploaded = authRepo.userData
        .map {
            it.isFcmTokenUploaded
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )

    private suspend fun uploadFcmToken(tokenDTO: TokenDTO) {
        viewModelScope.launch {
            authRepo.uploadFcmToken(tokenDTO)
        }.join()
    }

    fun isAuthenticated() = authRepo.isAuthenticated()

    fun isProfileAvailable(userId: String) {
        _isProfileAvailable.value = UiState.Loading
        viewModelScope.launch {
            val res = profileRepo.isProfileAvailable(userId)
            _isProfileAvailable.value = res.toUiState()
        }
    }

    fun signInWithGoogleCredentials(authCredential: AuthCredential) {
        _loginState.value = UiState.Loading
        viewModelScope.launch {
            authRepo.signInWithCredential(authCredential).collect {
                if (it is ResponseState.Success && !isFcmTokenUploaded.value) {
                    val token = Firebase.messaging.token.await()
                    uploadFcmToken(
                        TokenDTO(
                            deviceId = Build.MANUFACTURER + Build.DEVICE,
                            timeStamp = System.currentTimeMillis().toString(),
                            token = token,
                            uid = authRepo.getUserId() ?: ""
                        )
                    )
                }
                _loginState.value = it.toUiState()
            }
        }
    }

    fun logout() {
        _logoutState.value = UiState.Loading
        viewModelScope.launch {
            authRepo.signOut().collect {
                _logoutState.value = it.toUiState()
            }
        }
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

    fun navigateToBasicProfile() = viewModelScope.launch {
        authRepo.setLoginDone()
    }

    fun navigateToHome() = viewModelScope.launch {
        authRepo.setBasicProfileDone()
    }
}