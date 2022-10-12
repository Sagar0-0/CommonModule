package fit.asta.health.profile.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.domain.UserProfile
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(@Named("profile_repository")val repo: ProfileRepo): ViewModel() {

    val profile: MutableState<UserProfile?> = mutableStateOf(null)

    //val mainProfile: MutableState<MainProfile?> = mutableStateOf(null)
    val mainProfile: MutableState<UserProfile?> = mutableStateOf(null)
    val physique: MutableState<Map<String, Any?>?> = mutableStateOf(null)
    val diet: MutableState<Map<String, Any?>?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
            profile.value = repo.getProfileData("6309a9379af54f142c65fbfe")

            /*var add=""
            for (i in profile.value!!.profileData["addr"] as Map<String, String>){
                add=add+" "+i.value
            }
            mainProfile.value=MainProfile(
                profileUrl = profile.value?.profileData?.get("url") as String,
                name = profile.value!!.profileData["name"] as String,
                email = profile.value!!.profileData["email"] as String,
                phoneNumber = "91+ "+ profile.value!!.profileData["ph"] as String,
                dateOfBirth = profile.value!!.profileData["dob"] as String,
                address = add
            )
            physique.value=profile.value!!.physic
            diet.value=profile.value!!.diet*/
            }
        }

    }