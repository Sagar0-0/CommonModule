package fit.asta.health.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.domain.UserProfile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val authRepo: AuthRepo,
    private val profileRepo: ProfileRepo,
) : ViewModel() {

    private val mutableEditState = MutableStateFlow<ProfileEditState>(ProfileEditState.Loading)
    val stateEdit = mutableEditState.asStateFlow()

    private val mutableHPropState = MutableStateFlow<HPropState>(HPropState.Loading)
    val stateHp = mutableHPropState.asStateFlow()

    private val mutableState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = mutableState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun updateProfile(userProfile: UserProfile) {
        viewModelScope.launch {
            profileRepo.updateUserProfile(userProfile)
                .catch { exception ->
                    mutableEditState.value = ProfileEditState.Error(exception)
                }.collect {
                    mutableEditState.value = ProfileEditState.Success(it)
                }
        }
    }

    private fun getHealthProperties(propertyType: String) {
        viewModelScope.launch {
            profileRepo.getHealthProperties(propertyType)
                .catch { exception ->
                    mutableHPropState.value = HPropState.Error(exception)
                }.collect {
                    mutableHPropState.value = HPropState.Success(it.healthProperties)
                }
        }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            authRepo.getUser()?.let {
                profileRepo.getUserProfile("6309a9379af54f142c65fbfe").catch { exception ->
                    mutableState.value = ProfileState.Error(exception)
                }.collect { profile ->
                    mutableState.value = ProfileState.Success(profile)
                }
            }
        }
    }
}