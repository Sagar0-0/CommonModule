package fit.asta.health.feature.profile.basic.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.repo.ProfileRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasicProfileViewModel
@Inject constructor(
    private val profileRepo: ProfileRepo,
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _createBasicProfileState =
        MutableStateFlow<UiState<PutResponse>>(UiState.Idle)
    val createBasicProfileState = _createBasicProfileState.asStateFlow()

    private val _checkReferralCodeState = MutableStateFlow<UiState<CheckReferralDTO>>(UiState.Idle)
    val checkReferralCodeState = _checkReferralCodeState.asStateFlow()

    private val _linkAccountState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val linkAccountState = _linkAccountState.asStateFlow()

    val referralCode = authRepo.userData
        .map {
            it.referralCode
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = "",
        )

    private fun resetReferralCode() = viewModelScope.launch {
        authRepo.resetReferralCode()
    }

    fun createBasicProfile(basicProfileDTO: BasicProfileDTO) {
        _createBasicProfileState.value = UiState.Loading
        viewModelScope.launch {
            val res = profileRepo.createBasicProfile(basicProfileDTO)
            _createBasicProfileState.value = res.toUiState()
            if (res is ResponseState.Success) {
                resetReferralCode()
            }
        }
    }

    fun checkReferralCode(code: String) {
        _checkReferralCodeState.value = UiState.Loading
        viewModelScope.launch {
            val res = profileRepo.checkReferralCode(code)
            _checkReferralCodeState.value = res.toUiState()
        }
    }

    fun resetCheckCodeState() {
        _checkReferralCodeState.value = UiState.Idle
    }

    fun resetCreateProfileState() {
        _createBasicProfileState.value = UiState.Idle
    }

    fun setProfilePresent() = viewModelScope.launch {
        profileRepo.setProfilePresent()
    }

    fun getUser(): User {
        return authRepo.getUser()!!
    }

    fun navigateToHome() = viewModelScope.launch {
        authRepo.setBasicProfileDone()
    }

    fun linkWithCredentials(authCredential: AuthCredential) = viewModelScope.launch {
        authRepo.linkWithCredential(authCredential).collect {
            _linkAccountState.value = it.toUiState()
        }
    }

    fun resetLinkAccountState() {
        _linkAccountState.value = UiState.Idle
    }
}