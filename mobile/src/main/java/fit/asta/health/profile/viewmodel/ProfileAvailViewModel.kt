package fit.asta.health.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.auth.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.profile.model.ProfileRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ProfileAvailViewModel
@Inject
constructor(
    private val profileRepo: ProfileRepo,
    private val authRepo: AuthRepo
) : ViewModel() {

    private val mutableState = MutableStateFlow<ProfileAvailState>(ProfileAvailState.Loading)
    val state = mutableState.asStateFlow()

    fun isUserProfileAvailable(userId: String) {
        viewModelScope.launch {
            profileRepo.isUserProfileAvailable(userId)
                .catch { exception ->
                    mutableState.value = ProfileAvailState.Error(exception)
                }.collect { userStatus ->
                    mutableState.value = ProfileAvailState.Success(userStatus)
                }
        }
    }


}