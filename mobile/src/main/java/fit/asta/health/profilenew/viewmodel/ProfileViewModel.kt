package fit.asta.health.profilenew.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.profilenew.ProfileRepo.ProfileRepo
import fit.asta.health.profilenew.data.ProileData
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(@Named("profile_repository")val repo: ProfileRepo): ViewModel() {

        val profile: MutableState<ProileData?> = mutableStateOf(null)

        init {
            viewModelScope.launch {
                profile.value=repo.getProfileData("6309a9379af54f142c65fbfe")
            }
        }


    }