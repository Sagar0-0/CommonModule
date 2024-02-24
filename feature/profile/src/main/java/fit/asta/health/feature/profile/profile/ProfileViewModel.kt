package fit.asta.health.feature.profile.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.profile.remote.model.BooleanInt
import fit.asta.health.data.profile.remote.model.Gender
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.data.profile.repo.ProfileRepo
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
    @UID private val uid: String,
) : ViewModel() {

    private val _submitProfileState = MutableStateFlow<UiState<SubmitProfileResponse>>(UiState.Idle)
    val submitProfileState = _submitProfileState.asStateFlow()

    private val _userPropertiesState =
        MutableStateFlow<UiState<List<UserProperties>>>(UiState.Idle)
    val healthPropertiesState = _userPropertiesState.asStateFlow()

    private val _userProfileState = MutableStateFlow<UiState<UserProfileResponse>>(UiState.Loading)
    val userProfileState = _userProfileState.asStateFlow()


    fun getProfileData() {
        _userProfileState.value = UiState.Loading
        viewModelScope.launch {
            val result = profileRepo.getUserProfile(uid)
            _userProfileState.update {
                result.toUiState()
            }
        }
    }

    fun setName(name: String) {
        viewModelScope.launch {
            profileRepo.setName(uid, name)
        }
    }

    fun setGender(
        gender: Gender?,
        isPregnant: BooleanInt?,
        onPeriod: BooleanInt?,
        pregnancyWeek: Int?
    ) {
        viewModelScope.launch {
            profileRepo.setGenderDetails(
                uid = uid,
                gender = gender,
                isPregnant = isPregnant,
                onPeriod = onPeriod,
                pregnancyWeek = pregnancyWeek
            )
        }
    }

    fun setDob(dob: String, age: Int) {
        viewModelScope.launch {
            profileRepo.setDob(uid = uid, dob = dob, age = age)
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
