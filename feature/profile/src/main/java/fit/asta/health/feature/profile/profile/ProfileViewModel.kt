package fit.asta.health.feature.profile.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.profile.local.entity.ProfileEntity
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.data.profile.repo.ProfileRepo
import fit.asta.health.subscription.remote.model.UserSubscribedPlan
import fit.asta.health.subscription.repo.SubscriptionRepo
import fit.asta.health.wallet.remote.model.WalletResponse
import fit.asta.health.wallet.repo.WalletRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val profileRepo: ProfileRepo,
    private val walletRepo: WalletRepo,
    private val subscriptionRepo: SubscriptionRepo,
    @UID private val uid: String,
    private val savedState: SavedStateHandle,
) : ViewModel() {

    private val _submitProfileState = MutableStateFlow<UiState<SubmitProfileResponse>>(UiState.Idle)
    val submitProfileState = _submitProfileState.asStateFlow()

    private val _walletDataState = MutableStateFlow<UiState<WalletResponse>>(UiState.Idle)
    val walletDataState = _walletDataState.asStateFlow()

    private val _subscriptionDataState = MutableStateFlow<UiState<UserSubscribedPlan>>(UiState.Idle)
    val subscriptionDataState = _subscriptionDataState.asStateFlow()

    private val _userPropertiesState =
        MutableStateFlow<UiState<List<UserProperties>>>(UiState.Idle)
    val healthPropertiesState = _userPropertiesState.asStateFlow()

    private val _userProfileState = MutableStateFlow<UiState<UserProfileResponse>>(UiState.Loading)
    val userProfileState = _userProfileState.asStateFlow()

    private val _localProfile = MutableStateFlow<ProfileEntity?>(null)
    val localProfile = _localProfile.asStateFlow()

    fun getLocalProfile() = viewModelScope.launch {
        _localProfile.value = profileRepo.getLocalProfile()
    }

    fun updateLocalProfile(profileEntity: ProfileEntity?) = viewModelScope.launch {
        if (profileEntity != null) {
            profileRepo.updateLocalProfile(profileEntity)
        }
    }

    fun getWalletData() {
        _walletDataState.value = UiState.Loading
        viewModelScope.launch {
            _walletDataState.update {
                walletRepo.getData(uid).toUiState()
            }
        }
    }

    fun getSubscriptionData() {
        _subscriptionDataState.value = UiState.Loading
        viewModelScope.launch {
            _subscriptionDataState.update {
                subscriptionRepo.getUserSubscribedPlan(uid).toUiState()
            }
        }
    }

    fun getProfileData() {
        _userProfileState.value = UiState.Loading
        viewModelScope.launch {
            val result = profileRepo.getUserProfile(uid)
            _userProfileState.update {
                result.toUiState()
            }
        }
    }

    fun saveProfileData(userProfileResponse: UserProfileResponse) {
        viewModelScope.launch {
            _submitProfileState.update {
                profileRepo.updateUserProfile(userProfileResponse).toUiState()
            }
        }
    }

    fun getHealthProperties(propertyType: String) {
        _userPropertiesState.value = UiState.Loading
        viewModelScope.launch {
            _userPropertiesState.value = profileRepo.getHealthProperties(propertyType).toUiState()
        }
    }
    fun resetHealthProperties() {
        _userPropertiesState.value = UiState.Idle
    }
}
