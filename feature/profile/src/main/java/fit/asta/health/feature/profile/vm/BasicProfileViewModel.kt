package fit.asta.health.feature.profile.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.repo.ProfileRepo
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasicProfileViewModel
@Inject constructor(
    private val profileRepo: ProfileRepo,
    @UID private val uid: String
) : ViewModel() {

    private val _isProfileAvailable = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val isProfileAvailable = _isProfileAvailable.asStateFlow()

    private val _createBasicProfileState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val createBasicProfileState = _createBasicProfileState.asStateFlow()

    private val _checkReferralCodeState = MutableStateFlow<UiState<CheckReferralDTO>>(UiState.Idle)
    val checkReferralCodeState = _checkReferralCodeState.asStateFlow()

    fun isProfileAvailable() {
        _isProfileAvailable.value = UiState.Loading
        viewModelScope.launch {
            _isProfileAvailable.value = profileRepo.isProfileAvailable(uid).toUiState()
        }
    }

    fun createBasicProfile(basicProfileDTO: BasicProfileDTO) {
        _createBasicProfileState.value = UiState.Loading
        viewModelScope.launch {
            _createBasicProfileState.value =
                profileRepo.createBasicProfile(basicProfileDTO).toUiState()
        }
    }

    fun checkReferralCode(code: String) {
        _checkReferralCodeState.value = UiState.Loading
        viewModelScope.launch {
            val res = profileRepo.checkReferralCode(code)
            if ((res as? ResponseState.Success)?.data?.data == null) {
                _checkReferralCodeState.value = UiState.Error(R.string.unknown_error)
            } else {
                _checkReferralCodeState.value = res.toUiState()
            }
        }
    }

    fun resetCheckCodeState() {
        _checkReferralCodeState.value = UiState.Idle
    }

    fun resetCreateprofileState() {
        _createBasicProfileState.value = UiState.Idle
    }
}