package fit.asta.health.profile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.network.NetworkHelper
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.domain.Contact
import fit.asta.health.profile.model.domain.Physique
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.viewmodel.ProfileConstants.AGE
import fit.asta.health.profile.viewmodel.ProfileConstants.BODY_TYPE
import fit.asta.health.profile.viewmodel.ProfileConstants.DOB
import fit.asta.health.profile.viewmodel.ProfileConstants.EMAIL
import fit.asta.health.profile.viewmodel.ProfileConstants.HEIGHT
import fit.asta.health.profile.viewmodel.ProfileConstants.IS_PREGNANT
import fit.asta.health.profile.viewmodel.ProfileConstants.NAME
import fit.asta.health.profile.viewmodel.ProfileConstants.PREGNANCY_WEEK
import fit.asta.health.profile.viewmodel.ProfileConstants.PROFILE_DATA
import fit.asta.health.profile.viewmodel.ProfileConstants.USER_IMG
import fit.asta.health.profile.viewmodel.ProfileConstants.USER_SELECTION
import fit.asta.health.profile.viewmodel.ProfileConstants.WEIGHT
import fit.asta.health.testimonials.model.domain.InputIntWrapper
import fit.asta.health.testimonials.model.domain.InputWrapper
import fit.asta.health.testimonials.model.domain.Media
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val profileRepo: ProfileRepo,
    private val authRepo: AuthRepo,
    private val networkHelper: NetworkHelper,
    private val savedState: SavedStateHandle,
) : ViewModel() {

    private val isProfileAvailState =
        MutableStateFlow<ProfileAvailState>(ProfileAvailState.Loading)
    val statePropAvail = isProfileAvailState.asStateFlow()

    private val mutableEditState = MutableStateFlow<ProfileEditState>(ProfileEditState.Loading)
    val stateEdit = mutableEditState.asStateFlow()

    private val mutableHPropState = MutableStateFlow<HPropState>(HPropState.Loading)
    val stateHp = mutableHPropState.asStateFlow()

    private val mutableState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = mutableState.asStateFlow()

    private val profileData = savedState.getStateFlow(PROFILE_DATA, UserProfile())

    //Details
    val name = savedState.getStateFlow(NAME, InputWrapper())
    val email = savedState.getStateFlow(EMAIL, InputWrapper())
    private val userImg = savedState.getStateFlow(
        USER_IMG, Media(name = "user_img", title = "User Profile Image")
    )

    //Physique
    private val dob = savedState.getStateFlow(DOB, InputIntWrapper())
    val age = savedState.getStateFlow(AGE, InputIntWrapper())
    val weight = savedState.getStateFlow(WEIGHT, InputIntWrapper())
    val height = savedState.getStateFlow(HEIGHT, InputIntWrapper())
    val pregnancyWeek = savedState.getStateFlow(PREGNANCY_WEEK, InputIntWrapper())
    val bodyType = savedState.getStateFlow(BODY_TYPE, InputIntWrapper())

    //Health/LifeStyle/Diet Selection
    val gender = savedState.getStateFlow(ProfileConstants.GENDER, initialValue = null)
    val userSelection = savedState.getStateFlow(USER_SELECTION, initialValue = null)
    val isPregnant = savedState.getStateFlow(IS_PREGNANT, initialValue = null)

    init {
        loadUserProfile()
    }

    fun onUserProfileAvail() {
        if (networkHelper.isConnected()) {
            authRepo.getUser()?.let {
                isUserProfileAvailable(userId = it.uid)
            }
        } else {
            isProfileAvailState.value = ProfileAvailState.NoInternet
        }
    }

    private fun loadUserProfile() {

        if (networkHelper.isConnected()) {
            authRepo.getUser()?.let {
                loadUserProfileResponse(userId = it.uid)
            }
        } else {
            mutableState.value = ProfileState.NoInternet
        }

    }

    private fun isUserProfileAvailable(userId: String) {
        viewModelScope.launch {
            profileRepo.isUserProfileAvailable(userId).catch { exception ->
                isProfileAvailState.value = ProfileAvailState.Error(exception)
            }.collect { userStatus ->
                isProfileAvailState.value = ProfileAvailState.Success(userStatus)
            }
        }
    }

    //view only
    private fun loadUserProfileResponse(userId: String) {

        viewModelScope.launch {
            profileRepo.getUserProfile(userId).catch { exception ->
                mutableState.value = ProfileState.Error(exception)
            }.collect { profile ->
                //User Profile Data
                mutableState.value = ProfileState.Success(profile)
            }
        }

    }

    private fun submit() {

        authRepo.getUser()?.let {
            updateProfile(
                UserProfile(
                    uid = it.uid, contact = Contact(
                        dob = dob.value.value.toString(),
                        email = email.value.value.trim(),
                        name = name.value.value,
                        url = userImg.value.url
                    ), physique = Physique(
                        bodyType = bodyType.value.value,
                        height = height.value.value,
                        pregnancyWeek = pregnancyWeek.value.value,
                        weight = weight.value.value
                    )
                )
            )
        }

    }


    //create+edit+update after edit
    private fun updateProfile(userProfile: UserProfile) {

        if (networkHelper.isConnected()) {
            viewModelScope.launch {
                profileRepo.updateUserProfile(userProfile).catch { exception ->
                    mutableEditState.value = ProfileEditState.Error(exception)
                }.collect { profile ->
                    mutableEditState.value = ProfileEditState.Success(profile)
                }
            }
        } else {
            mutableEditState.value = ProfileEditState.NoInternet
        }

    }

    private fun getHealthProperties(propertyType: String) {

        if (networkHelper.isConnected()) {
            viewModelScope.launch {
                profileRepo.getHealthProperties(propertyType).catch { exception ->
                    mutableHPropState.value = HPropState.Error(exception)
                }.collect {
                    mutableHPropState.value = HPropState.Success(it.healthProperties)
                }
            }
        } else {
            mutableHPropState.value = HPropState.NoInternet
        }

    }

}

