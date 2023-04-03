package fit.asta.health.profile.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.common.utils.UiString
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.network.NetworkHelper
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.domain.*
import fit.asta.health.profile.viewmodel.ProfileConstants.AGE
import fit.asta.health.profile.viewmodel.ProfileConstants.BODY_TYPE
import fit.asta.health.profile.viewmodel.ProfileConstants.DOB
import fit.asta.health.profile.viewmodel.ProfileConstants.EMAIL
import fit.asta.health.profile.viewmodel.ProfileConstants.HEIGHT
import fit.asta.health.profile.viewmodel.ProfileConstants.INJURIES_SINCE
import fit.asta.health.profile.viewmodel.ProfileConstants.NAME
import fit.asta.health.profile.viewmodel.ProfileConstants.PREGNANCY_WEEK
import fit.asta.health.profile.viewmodel.ProfileConstants.PROFILE_DATA
import fit.asta.health.profile.viewmodel.ProfileConstants.USER_IMG
import fit.asta.health.profile.viewmodel.ProfileConstants.WEIGHT
import fit.asta.health.testimonials.model.domain.InputIntWrapper
import fit.asta.health.testimonials.model.domain.InputWrapper
import fit.asta.health.testimonials.model.domain.Media
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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

    private val mutableCreateState =
        MutableStateFlow<ProfileCreateState>(ProfileCreateState.Loading)
    val createState = mutableCreateState.asStateFlow()


    // Any Significant Health History
    private val _selectedHealthHisOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedHealthHisOption: StateFlow<TwoToggleSelections?>
        get() = _selectedHealthHisOption


    private val areHealthInputsValid = MutableStateFlow(false)
    val healthInputsValid: StateFlow<Boolean>
        get() = areHealthInputsValid

    private fun isHealthValid(valid: Boolean) {
        areHealthInputsValid.value = valid
    }


    private val areAllInputsValid = MutableStateFlow(false)
    val allInputsValid: StateFlow<Boolean>
        get() = areAllInputsValid

    private fun doAllInputsValid(valid: Boolean) {
        areAllInputsValid.value = valid
    }


    private val arePhyInputsValid = MutableStateFlow(false)
    val phyInputsValid: StateFlow<Boolean>
        get() = arePhyInputsValid

    private fun isPhyValid(valid: Boolean) {
        arePhyInputsValid.value = valid
    }

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

    //Is onPeriod
    private val _isOnPeriodOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedOnPeriod: StateFlow<TwoToggleSelections?>
        get() = _isOnPeriodOption

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

    private fun setSelectedOnPeriodOption(option: TwoToggleSelections) {
        _isOnPeriodOption.value = option
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


    // health section
    private val _healthPropertiesData = MutableStateFlow(
        mapOf(
            0 to mutableStateListOf<HealthProperties>(),
            1 to mutableStateListOf<HealthProperties>(),
            2 to mutableStateListOf<HealthProperties>(),
            3 to mutableStateListOf<HealthProperties>(),
            4 to mutableStateListOf<HealthProperties>(),
            5 to mutableStateListOf<HealthProperties>(),
        )
    )

    val healthPropertiesData: StateFlow<Map<Int, SnapshotStateList<HealthProperties>>> =
        _healthPropertiesData

    private fun healthAdd(cardViewIndex: Int, item: HealthProperties, composeIndex: ComposeIndex) {
        val propertyData = when (composeIndex) {
            ComposeIndex.First -> _healthPropertiesData
            ComposeIndex.Second -> _lfPropertiesData
            ComposeIndex.Third -> _dietPropertiesData
        }
        val currentList = propertyData.value[cardViewIndex] ?: mutableStateListOf()
        if (!currentList.contains(item)) {
            if (currentList.contains(item) && isSameItemRemovedAndAdded) {
                isSameItemRemovedAndAdded = false
            } else {
                currentList.add(item)
            }
        }
        val updatedData = propertyData.value.toMutableMap()
        updatedData[cardViewIndex] = currentList
        propertyData.value = updatedData.toMap()
    }

    private fun healthRemove(
        cardViewIndex: Int,
        item: HealthProperties,
        composeIndex: ComposeIndex,
    ) {

        val propertyData = when (composeIndex) {
            ComposeIndex.First -> _healthPropertiesData
            ComposeIndex.Second -> _lfPropertiesData
            ComposeIndex.Third -> _dietPropertiesData
        }

        val currentList = propertyData.value[cardViewIndex] ?: mutableStateListOf()
        if (currentList.contains(item)) {
            if (currentList.contains(item) && !isSameItemRemovedAndAdded) {
                isSameItemRemovedAndAdded = true
            } else {
                currentList.remove(item)
            }
        }
        val updatedData = propertyData.value.toMutableMap()
        updatedData[cardViewIndex] = currentList
        propertyData.value = updatedData.toMap()
    }

    //LifeStyleSection
    private val _lfPropertiesData = MutableStateFlow(
        mapOf(
            0 to mutableStateListOf<HealthProperties>(),
            1 to mutableStateListOf<HealthProperties>(),
            2 to mutableStateListOf<HealthProperties>(),
        )
    )

    val lfPropertiesData: StateFlow<Map<Int, SnapshotStateList<HealthProperties>>> =
        _lfPropertiesData

    //Diet
    private val _dietPropertiesData = MutableStateFlow(
        mapOf(
            0 to mutableStateListOf<HealthProperties>(),
            1 to mutableStateListOf<HealthProperties>(),
            2 to mutableStateListOf<HealthProperties>(),
            3 to mutableStateListOf<HealthProperties>(),
            4 to mutableStateListOf<HealthProperties>(),
        )
    )

    val dpData: StateFlow<Map<Int, SnapshotStateList<HealthProperties>>> = _dietPropertiesData

    private val profileData = savedState.getStateFlow(PROFILE_DATA, UserProfile())

    //Details
    val name = savedState.getStateFlow(NAME, InputWrapper())
    val email = savedState.getStateFlow(EMAIL, InputWrapper())


    val userImg = savedState.getStateFlow(
        USER_IMG, Media(name = "user_img", title = "User Profile Image")
    )


    //Physique
    val dob = savedState.getStateFlow(DOB, InputWrapper())
    val age = savedState.getStateFlow(AGE, InputWrapper())
    val weight = savedState.getStateFlow(WEIGHT, InputWrapper())
    val height = savedState.getStateFlow(HEIGHT, InputWrapper())
    val pregnancyWeek = savedState.getStateFlow(PREGNANCY_WEEK, InputWrapper())
    private val bodyType = savedState.getStateFlow(BODY_TYPE, InputIntWrapper())

    //Health
    val injuriesSince = savedState.getStateFlow(INJURIES_SINCE, InputWrapper())

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

    private fun convertHealthArrayList(cardViewIndex: Int): ArrayList<HealthProperties> {
        return when (cardViewIndex) {
            0 -> _healthPropertiesData.value[0]?.let { ArrayList(it) }!!
            1 -> _healthPropertiesData.value[1]?.let { ArrayList(it) }!!
            2 -> _healthPropertiesData.value[2]?.let { ArrayList(it) }!!
            3 -> _healthPropertiesData.value[3]?.let { ArrayList(it) }!!
            4 -> _healthPropertiesData.value[4]?.let { ArrayList(it) }!!
            5 -> _healthPropertiesData.value[5]?.let { ArrayList(it) }!!
            else -> arrayListOf()
        }
    }

    private fun convertLSArrayList(cardViewIndex: Int): ArrayList<HealthProperties> {
        return when (cardViewIndex) {
            0 -> _lfPropertiesData.value[0]?.let { ArrayList(it) }!!
            1 -> _lfPropertiesData.value[1]?.let { ArrayList(it) }!!
            2 -> _lfPropertiesData.value[2]?.let { ArrayList(it) }!!
            else -> arrayListOf()
        }
    }

    private fun convertDietArrayList(cardViewIndex: Int): ArrayList<HealthProperties> {
        return when (cardViewIndex) {
            0 -> _dietPropertiesData.value[0]?.let { ArrayList(it) }!!
            1 -> _dietPropertiesData.value[1]?.let { ArrayList(it) }!!
            2 -> _dietPropertiesData.value[2]?.let { ArrayList(it) }!!
            3 -> _dietPropertiesData.value[3]?.let { ArrayList(it) }!!
            4 -> _dietPropertiesData.value[4]?.let { ArrayList(it) }!!
            else -> arrayListOf()
        }
    }

    private fun submit() {

        authRepo.getUser()?.let {
            updateProfile(
                UserProfile(
                    uid = it.uid, contact = Contact(
                        dob = dob.value.value,
                        email = email.value.value.trim(),
                        name = name.value.value.trim(),
                        url = userImg.value.url
                    ), physique = Physique(
                        weight = weight.value.value.toFloat(),
                        age = age.value.value.toInt(),
                        height = height.value.value.toFloat(),
                        pregnancyWeek = if (pregnancyWeek.value.value == "") {
                            0
                        } else {
                            pregnancyWeek.value.value.toInt()
                        },
                        bodyType = bodyType.value.value,
                        gender = when (_selectedGenderOption.value) {
                            ThreeToggleSelections.First -> "male"
                            ThreeToggleSelections.Second -> "female"
                            ThreeToggleSelections.Third -> "others"
                            null -> ""
                        },
                        isPregnant = when (_isPregnantOption.value) {
                            TwoToggleSelections.First -> true
                            else -> {
                                false
                            }
                        }
                    ), health = Health(
                        healthHistory = convertHealthArrayList(0),
                        injuries = convertHealthArrayList(1),
                        bodyPart = convertHealthArrayList(2),
                        ailments = convertHealthArrayList(3),
                        medications = convertHealthArrayList(4),
                        targets = convertHealthArrayList(5),
                        injurySince = if (injuriesSince.value.value == "") {
                            0
                        } else {
                            injuriesSince.value.value.toInt()
                        }
                    ), lifeStyle = LifeStyle(
                        curActivities = convertLSArrayList(0),
                        prefActivities = convertLSArrayList(1),
                        lifeStyleTargets = convertLSArrayList(2),
                        physicalActivity = when (_selectedPhyActOption.value) {
                            ThreeToggleSelections.First -> HealthProperties(name = "Less")
                            ThreeToggleSelections.Second -> HealthProperties(name = "Moderate")
                            ThreeToggleSelections.Third -> HealthProperties(name = "Very")
                            null -> HealthProperties()
                        },
                        workingEnv = when (_selectedWorkingEnvOption.value) {
                            TwoToggleSelections.First -> HealthProperties(name = "Standing")
                            TwoToggleSelections.Second -> HealthProperties(name = "Sitting")
                            null -> HealthProperties()
                        },
                        workStyle = when (_selectedWorkStyleOption.value) {
                            TwoToggleSelections.First -> HealthProperties(name = "Indoor")
                            TwoToggleSelections.Second -> HealthProperties(name = "Outdoor")
                            null -> HealthProperties()
                        },
                        workingHours = when (_selectedWorkingHrsOption.value) {
                            ThreeToggleSelections.First -> HealthProperties(name = "Morning")
                            ThreeToggleSelections.Second -> HealthProperties(name = "Afternoon")
                            ThreeToggleSelections.Third -> HealthProperties(name = "Night")
                            null -> HealthProperties()
                        },
                    ), Diet(
                        preference = convertDietArrayList(0),
                        nonVegDays = convertDietArrayList(1),
                        allergies = convertDietArrayList(2),
                        cuisines = convertDietArrayList(3),
                        foodRestrictions = convertDietArrayList(4)
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

    private fun onValidateDetailsText(value: String, min: Int, max: Int): UiString {
        return when {
            value.isBlank() -> UiString.Resource(R.string.the_field_can_not_be_blank)
            value.length > max -> UiString.Resource(R.string.data_length_more, max.toString())
            value.length in 1 until min -> UiString.Resource(
                R.string.data_length_less, min.toString()
            )
            else -> UiString.Empty
        }
    }

    private fun onValidateDetailsEmail(value: String): UiString {
        return when {
            value.isBlank() -> UiString.Resource(R.string.the_field_can_not_be_blank)

            !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches() -> UiString.Resource(
                R.string.that_is_not_a_valid_email
            )
            else -> UiString.Empty
        }
    }

    private fun onValidateProfileMedia(localUrl: Uri?, url: String): UiString {
        return if (localUrl != null || url.isNotBlank()) UiString.Empty
        else UiString.Resource(R.string.the_media_can_not_be_blank)
    }

    private fun onValidateAge(value: String, min: Int): UiString {
        return when {
            value.isBlank() -> UiString.Resource(R.string.the_field_can_not_be_blank)
            value.toInt() < min -> UiString.Resource(R.string.validate_age, min.toString())
            else -> UiString.Empty
        }
    }

    private fun onValidatePhy(type: String, value: String, min: Double, max: Double): UiString {
        return when {
            value.isBlank() -> UiString.Resource(R.string.the_field_can_not_be_blank)
            value.toDouble() < min -> UiString.Resource(
                R.string.validate_min_phy, type, min.toString()
            )
            value.toDouble() > max -> UiString.Resource(
                R.string.validate_max_phy, type, max.toString()
            )
            else -> UiString.Empty
        }
    }

    fun validateDataList(
        list: SnapshotStateList<HealthProperties>,
        listName: String,
    ): UiString {
        return when {
            list.isEmpty() -> UiString.Resource(R.string.validate_emptyList, listName)
            else -> UiString.Empty
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
            is ProfileEvent.SetSelectedIsOnPeriodOption -> setSelectedOnPeriodOption(event.option)
            is ProfileEvent.SetSelectedGenderOption -> setSelectedGenderOption(event.option)
            is ProfileEvent.SetSelectedPhyActOption -> setSelectedPhysicalActiveOption(event.option)
            is ProfileEvent.SetSelectedWorkingEnvOption -> setSelectedWorkingEnvOption(event.option)
            is ProfileEvent.SetSelectedWorkingHrsOption -> setSelectedWorkingHrsOption(event.option)
            is ProfileEvent.SetSelectedWorkingStyleOption -> setSelectedWorkingStyleOption(event.option)
            is ProfileEvent.SetSelectedAddItemOption -> healthAdd(
                cardViewIndex = event.index, item = event.item, composeIndex = event.composeIndex
            )

            is ProfileEvent.SetSelectedRemoveItemOption -> healthRemove(
                cardViewIndex = event.index, item = event.item, composeIndex = event.composeIndex
            )

            is ProfileEvent.OnEmailChange -> {
                savedState[EMAIL] = email.value.copy(
                    value = event.email, error = onValidateDetailsEmail(event.email)
                )
            }

            is ProfileEvent.OnNameChange -> {
                savedState[NAME] = name.value.copy(
                    value = event.name, error = onValidateDetailsText(event.name, 1, 20)
                )
            }

            is ProfileEvent.OnUserImgChange -> {
                savedState[USER_IMG] = userImg.value.copy(
                    localUrl = event.url
                )
            }

            is ProfileEvent.OnUserHeightChange -> {
                savedState[HEIGHT] = height.value.copy(
                    value = event.height, error = onValidatePhy(
                        type = "Height", value = event.height, min = 54.64, max = 251.0
                    )
                )
            }

            is ProfileEvent.OnUserWeightChange -> {
                savedState[WEIGHT] = weight.value.copy(
                    value = event.weight, error = onValidatePhy(
                        type = "Weight", value = event.weight, min = 2.13, max = 635.0
                    )
                )
            }

            is ProfileEvent.OnUserAGEChange -> {
                savedState[AGE] = age.value.copy(
                    value = event.age, error = onValidateAge(event.age, min = 10)
                )
            }

            is ProfileEvent.OnUserDOBChange -> {
                savedState[DOB] = dob.value.copy(
                    value = event.dob
                )
            }

            is ProfileEvent.OnUserPregWeekChange -> {
                savedState[PREGNANCY_WEEK] = pregnancyWeek.value.copy(
                    value = event.week, error = onValidatePhy(
                        type = "Pregnancy Week", value = event.week, min = 19.0, max = 55.0
                    )
                )
            }

            is ProfileEvent.OnUserInjuryTimeChange -> {
                savedState[INJURIES_SINCE] = injuriesSince.value.copy(
                    value = event.time
                )
            }

            ProfileEvent.OnSubmit -> {
                submit()
            }
            is ProfileEvent.IsHealthValid -> {
                isHealthValid(valid = event.valid)
            }
            is ProfileEvent.IsPhyValid -> {
                isPhyValid(event.valid)
            }
            is ProfileEvent.DoAllInputsValid -> doAllInputsValid(valid = event.valid)
        }

    }

    private fun isCreateUserProfileDirty(): Boolean {
        return profileData.value.contact.name != name.value.value || profileData.value.contact.email != email.value.value || profileData.value.contact.url != userImg.value.url || profileData.value.contact.dob != dob.value.value || profileData.value.physique.age != age.value.value.toInt() || profileData.value.physique.weight != weight.value.value.toFloat() || profileData.value.physique.height != height.value.value.toFloat() || profileData.value.physique.pregnancyWeek != pregnancyWeek.value.value.toInt() || profileData.value.health.injurySince != injuriesSince.value.value.toInt()
    }


    // Details Input Validity
    val areDetailsInputsValid = combine(name, email) { name, email ->
        name.value.isNotEmpty() && name.error is UiString.Empty && email.value.isNotEmpty() && email.error is UiString.Empty
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)


    // Physique Input Validity
    //Basic Details
    val areBasicPhysiqueInputsValid = combine(
        age, weight, height, _selectedGenderOption
    ) { age, weight, height, _selectedGenderOption ->
        age.error is UiString.Empty && weight.value.isNotEmpty() && weight.error is UiString.Empty && height.value.isNotEmpty() && height.error is UiString.Empty && _selectedGenderOption != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)


    //Gender Details Verification
    val areFemaleInputNull = combine(
        _isOnPeriodOption, _isPregnantOption, areBasicPhysiqueInputsValid
    ) { _isOnPeriodOption, _isPregnantOption, areBasicPhysiqueInputsValid ->
        _isOnPeriodOption != null && _isPregnantOption != null && areBasicPhysiqueInputsValid
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)

    val arePregnancyInputValid = combine(
        areFemaleInputNull, _isPregnantOption, pregnancyWeek
    ) { areFemaleInputNull, _isPregnantOption, pregnancyWeek ->
        areFemaleInputNull && _isPregnantOption == TwoToggleSelections.First && pregnancyWeek.value.isNotEmpty() && pregnancyWeek.error is UiString.Empty
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)

    //Health Inputs Valid
    val areSelectedHealthOptionsNull = combine(
        _selectedHealthHisOption,
        _selectedInjOption,
        _selectedAilOption,
        _selectedMedOption,
        _selectedHealthTarOption
    ) { selectedHealthHis, selectedInjury, selectedAil, selectedMed, selectedHealthTar ->
        selectedHealthHis != null && selectedInjury != null && selectedAil != null && selectedMed != null && selectedHealthTar != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)

    //LifeStyle Inputs Valid
    val areLSValid = combine(
        _selectedPhyActOption,
        _selectedWorkingEnvOption,
        _selectedWorkStyleOption,
        _selectedWorkingHrsOption
    ) { _selectedPhyActOption, _selectedWorkingEnvOption, _selectedWorkStyleOption, _selectedWorkingHrsOption ->
        _selectedPhyActOption != null && _selectedWorkingEnvOption != null && _selectedWorkStyleOption != null && _selectedWorkingHrsOption != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)

    val doAllDataInputsClear = combine(
        areDetailsInputsValid, arePhyInputsValid, areHealthInputsValid, areLSValid
    ) { areDetailsInputsValid, arePhyInputsValid, areHealthInputsValid, areLSValid ->
        areDetailsInputsValid && arePhyInputsValid && areHealthInputsValid && areLSValid
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)

}
