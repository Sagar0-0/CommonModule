package fit.asta.health.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.profile.intent.ProfileState
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.domain.UserProfile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    @Named("profile_repository") val profileRepo: ProfileRepo,
) : ViewModel() {

    private val mutableState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = mutableState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            profileRepo.getUserProfile("6309a9379af54f142c65fbfe").catch { exception ->
                mutableState.value = ProfileState.Error(exception)
            }.collect {
                mutableState.value = ProfileState.Success(it)
            }
        }
    }

    private fun updateProfile(userProfile: UserProfile) {
        viewModelScope.launch {
            profileRepo.updateUserProfile(userProfile)
                .catch { exception ->
                    mutableState.value = ProfileState.Error(exception)
                }.collect {
                    //mutableState.value = ProfileState.Success(it)
                }
        }
    }

    private fun isUserProfileAvailable(userId: String) {
        viewModelScope.launch {
            profileRepo.isUserProfileAvailable(userId)
                .catch { exception ->
                    mutableState.value = ProfileState.Error(exception)
                }.collect {
                    //mutableState.value = ProfileState.Success(it)
                }
        }
    }

    private fun getHealthProperties(propertyType: String) {
        viewModelScope.launch {
            profileRepo.getHealthProperties(propertyType)
                .catch { exception ->
                    mutableState.value = ProfileState.Error(exception)
                }.collect {
                    //mutableState.value = ProfileState.Success(it)
                }
        }
    }
}