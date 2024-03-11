package fit.asta.health.feature.auth.vm

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.auth.fcm.remote.TokenDTO
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.map
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.data.onboarding.repo.OnboardingRepo
import fit.asta.health.data.profile.remote.model.UserProfileAvailableResponse
import fit.asta.health.data.profile.repo.ProfileRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class AuthViewModel
@Inject constructor(
    private val authRepo: AuthRepo,
    private val profileRepo: ProfileRepo,
    private val onboardingRepo: OnboardingRepo,
    @UID private val uid: String
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val loginState = _loginState.asStateFlow()

    private val _onboardingDataState = MutableStateFlow<UiState<List<OnboardingData>>>(UiState.Idle)
    val onboardingDatState = _onboardingDataState.asStateFlow()

    private val _logoutState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val logoutState = _logoutState.asStateFlow()

    private val _deleteState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val deleteState = _deleteState.asStateFlow()

    private val _isProfileAvailable =
        MutableStateFlow<UiState<UserProfileAvailableResponse>>(UiState.Idle)

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


    fun getOnboardingData() {
        _onboardingDataState.value = UiState.Loading
        viewModelScope.launch {
            _onboardingDataState.update { onboardingRepo.getData().toUiState() }
        }
    }

    private suspend fun uploadFcmToken(tokenDTO: TokenDTO) {
        viewModelScope.launch {
            authRepo.uploadFcmToken(tokenDTO)
        }.join()
    }

    private fun isProfileAvailable(userId: String = uid) {
        _isProfileAvailable.value = UiState.Loading
        viewModelScope.launch {
            _isProfileAvailable.update {
                profileRepo.isBasicProfileAvailable(userId).toUiState()
            }
        }
    }

    fun signInAndNavigate(authCredential: AuthCredential) {
        _loginState.value = UiState.Loading
        viewModelScope.launch {
            authRepo.signInWithCredential(authCredential).collect {
                if (it is ResponseState.Success) {//Login Success
                    if (!isFcmTokenUploaded.value) {//Uploading token to our server for notification
                        val token = Firebase.messaging.token.await()
                        uploadFcmToken(
                            TokenDTO(
                                deviceId = Build.MANUFACTURER + Build.DEVICE,
                                timeStamp = System.currentTimeMillis().toString(),
                                token = token,
                                uid = it.data.uid
                            )
                        )
                    }

                    val res =
                        profileRepo.isBasicProfileAvailable(it.data.uid) //Check Profile available or not
                    _isProfileAvailable.value = res.toUiState()
                    if (res is ResponseState.Success) {//If profile available request is success then navigate accordingly
                        _loginState.value = UiState.Success(Unit)
                        if (res.data.userProfilePresent) {//Profile available in server
                            navigateToHome()
                        } else {//Profile not available in server
                            navigateToBasicProfile()
                        }
                    } else {// isProfileAvailable Request failed
                        _loginState.value = res.map { Unit }.toUiState()
                    }
                } else {// Login failed
                    _loginState.value = it.map { Unit }.toUiState()
                }
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

    private fun navigateToBasicProfile() = viewModelScope.launch {
        authRepo.setLoginDone()
    }

    private fun navigateToHome() = viewModelScope.launch {
        authRepo.setBasicProfileDone()
    }

    fun onLoginFailed() {
        if (_isProfileAvailable.value !is UiState.Success) {
            isProfileAvailable()
        } else {
            _loginState.value = UiState.Idle
        }
    }
}