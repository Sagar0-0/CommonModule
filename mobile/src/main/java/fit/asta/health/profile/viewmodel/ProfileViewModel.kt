package fit.asta.health.profile.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.network.NetworkHelper
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.domain.*
import fit.asta.health.profile.viewmodel.ProfileConstants.AGE
import fit.asta.health.profile.viewmodel.ProfileConstants.BODY_TYPE
import fit.asta.health.profile.viewmodel.ProfileConstants.DOB
import fit.asta.health.profile.viewmodel.ProfileConstants.EMAIL
import fit.asta.health.profile.viewmodel.ProfileConstants.HEIGHT
import fit.asta.health.profile.viewmodel.ProfileConstants.NAME
import fit.asta.health.profile.viewmodel.ProfileConstants.PREGNANCY_WEEK
import fit.asta.health.profile.viewmodel.ProfileConstants.PROFILE_DATA
import fit.asta.health.profile.viewmodel.ProfileConstants.USER_IMG
import fit.asta.health.profile.viewmodel.ProfileConstants.WEIGHT
import fit.asta.health.testimonials.model.domain.InputIntWrapper
import fit.asta.health.testimonials.model.domain.InputWrapper
import fit.asta.health.testimonials.model.domain.Media
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val mutableEditState = MutableStateFlow<ProfileEditState>(ProfileEditState.Loading)
    val stateEdit = mutableEditState.asStateFlow()

    private val mutableHPropState = MutableStateFlow<HPropState>(HPropState.Loading)
    val stateHp = mutableHPropState.asStateFlow()

    private val mutableState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = mutableState.asStateFlow()

    // Any Significant Health History
    private val _selectedHealthHisOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedHealthHisOption: StateFlow<TwoToggleSelections?>
        get() = _selectedHealthHisOption

    //Any Injury
    private val _selectedInjOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedInjOption: StateFlow<TwoToggleSelections?>
        get() = _selectedInjOption

    //Body Part
    private val _selectedBodyPartOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedBdyPartOption: StateFlow<TwoToggleSelections?>
        get() = _selectedBodyPartOption

    //Any Ailments
    private val _selectedAilOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedAilOption: StateFlow<TwoToggleSelections?>
        get() = _selectedAilOption

    //Any Medications
    private val _selectedMedOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedMedOption: StateFlow<TwoToggleSelections?>
        get() = _selectedMedOption

    //Any Health Target
    private val _selectedHealthTarOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedHealthTarOption: StateFlow<TwoToggleSelections?>
        get() = _selectedHealthTarOption

    //Food Res
    private val _selectedFoodResOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedFoodResOption: StateFlow<TwoToggleSelections?>
        get() = _selectedFoodResOption


    //Is Pregnant
    private val _isPregnantOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedIsPregnant: StateFlow<TwoToggleSelections?>
        get() = _isPregnantOption

    //Gender
    private val _selectedGenderOption = MutableStateFlow<ThreeToggleSelections?>(null)
    val selectedGender: StateFlow<ThreeToggleSelections?>
        get() = _selectedGenderOption

    //Physically Active
    private val _selectedPhyActOption = MutableStateFlow<ThreeToggleSelections?>(null)
    val selectedPhyAct: StateFlow<ThreeToggleSelections?>
        get() = _selectedPhyActOption

    //Working Hours
    private val _selectedWorkingHrsOption = MutableStateFlow<ThreeToggleSelections?>(null)
    val selectedWorkingHrs: StateFlow<ThreeToggleSelections?>
        get() = _selectedWorkingHrsOption

    //Working Env
    private val _selectedWorkingEnvOption = MutableStateFlow<TwoToggleSelections?>(null)
    val selectedWorkingEnv: StateFlow<TwoToggleSelections?>
        get() = _selectedWorkingEnvOption

    //Working Style
    private val _selectedWorkStyleOption = MutableStateFlow<TwoToggleSelections?>(null)
    val selectedWorkStyle: StateFlow<TwoToggleSelections?>
        get() = _selectedWorkStyleOption

    private fun setSelectedPhysicalActiveOption(option: ThreeToggleSelections) {
        _selectedPhyActOption.value = option
    }

    private fun setSelectedWorkingHrsOption(option: ThreeToggleSelections) {
        _selectedWorkingHrsOption.value = option
    }

    private fun setSelectedWorkingEnvOption(option: TwoToggleSelections) {
        _selectedWorkingEnvOption.value = option
    }

    private fun setSelectedWorkingStyleOption(option: TwoToggleSelections) {
        _selectedWorkStyleOption.value = option
    }

    private fun setSelectedHealthHisOption(option: TwoToggleSelections) {
        _selectedHealthHisOption.value = option
    }

    private fun setSelectedGenderOption(option: ThreeToggleSelections) {
        _selectedGenderOption.value = option
    }

    private fun setSelectedIsPregnantOption(option: TwoToggleSelections) {
        _isPregnantOption.value = option
    }

    private fun setSelectedInjOption(option: TwoToggleSelections) {
        _selectedInjOption.value = option
    }

    private fun setSelectedBodyPrtOption(option: TwoToggleSelections) {
        _selectedBodyPartOption.value = option
    }

    private fun setSelectedAilOption(option: TwoToggleSelections) {
        _selectedAilOption.value = option
    }

    private fun setSelectedMedOption(option: TwoToggleSelections) {
        _selectedMedOption.value = option
    }

    private fun setSelectedHealthTarOption(option: TwoToggleSelections) {
        _selectedHealthTarOption.value = option
    }

    private fun setSelectedFoodResOption(option: TwoToggleSelections) {
        _selectedFoodResOption.value = option
    }

    private var isSameItemRemovedAndAdded = false

    private val myArrayList = mutableStateListOf<HealthProperties>()
    val list = MutableStateFlow(myArrayList)

    private fun addItemFrmHp(item: HealthProperties) {

        if (!myArrayList.contains(item)) {
            if (myArrayList.contains(item) && isSameItemRemovedAndAdded) {
                isSameItemRemovedAndAdded = false
            } else {
                myArrayList.add(item)
            }
        }

    }

    private fun removeItemFrmHp(item: HealthProperties) {

        if (myArrayList.contains(item)) {
            if (myArrayList.contains(item) && !isSameItemRemovedAndAdded) {
                isSameItemRemovedAndAdded = true
            } else {
                myArrayList.remove(item)
            }
        }

    }

    val profileData = savedState.getStateFlow(PROFILE_DATA, UserProfile())

    //Details
    val name = savedState.getStateFlow(NAME, InputWrapper())
    val email = savedState.getStateFlow(EMAIL, InputWrapper())
    val userImg = savedState.getStateFlow(
        USER_IMG, Media(name = "user_img", title = "User Profile Image")
    )

    //Physique
    val dob = savedState.getStateFlow(DOB, InputIntWrapper())
    val age = savedState.getStateFlow(AGE, InputIntWrapper())
    val weight = savedState.getStateFlow(WEIGHT, InputIntWrapper())
    val height = savedState.getStateFlow(HEIGHT, InputIntWrapper())
    val pregnancyWeek = savedState.getStateFlow(PREGNANCY_WEEK, InputIntWrapper())
    val bodyType = savedState.getStateFlow(BODY_TYPE, InputIntWrapper())


    init {

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
                    ), health = Health(

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


    fun onEvent(event: ProfileEvent) {

        when (event) {
            is ProfileEvent.GetHealthProperties -> getHealthProperties(propertyType = event.propertyType)
            is ProfileEvent.SetSelectHealthHisOption -> setSelectedHealthHisOption(option = event.option)
            is ProfileEvent.SetSelectedAilOption -> setSelectedAilOption(event.option)
            is ProfileEvent.SetSelectedBodyPrtOption -> setSelectedBodyPrtOption(event.option)
            is ProfileEvent.SetSelectedFoodResOption -> setSelectedFoodResOption(event.option)
            is ProfileEvent.SetSelectedHealthTarOption -> setSelectedHealthTarOption(event.option)
            is ProfileEvent.SetSelectedInjOption -> setSelectedInjOption(event.option)
            is ProfileEvent.SetSelectedMedOption -> setSelectedMedOption(event.option)
            is ProfileEvent.SetSelectedIsPregnantOption -> setSelectedIsPregnantOption(event.option)
            is ProfileEvent.SetSelectedGenderOption -> setSelectedGenderOption(event.option)
            is ProfileEvent.SetSelectedPhyActOption -> setSelectedPhysicalActiveOption(event.option)
            is ProfileEvent.SetSelectedWorkingEnvOption -> setSelectedWorkingEnvOption(event.option)
            is ProfileEvent.SetSelectedWorkingHrsOption -> setSelectedWorkingHrsOption(event.option)
            is ProfileEvent.SetSelectedWorkingStyleOption -> setSelectedWorkingStyleOption(event.option)
            is ProfileEvent.SetSelectedAddItemOption -> addItemFrmHp(event.item)
            is ProfileEvent.SetSelectedRemoveItemOption -> removeItemFrmHp(event.item)
        }
    }


}

// viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "injury"))
