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
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.Contact
import fit.asta.health.profile.model.domain.Diet
import fit.asta.health.profile.model.domain.Health
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.LifeStyle
import fit.asta.health.profile.model.domain.Physique
import fit.asta.health.profile.model.domain.ProfileMedia
import fit.asta.health.profile.model.domain.Session
import fit.asta.health.profile.model.domain.ThreeToggleSelections
import fit.asta.health.profile.model.domain.TwoToggleSelections
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.viewmodel.ProfileConstants.ADDRESS
import fit.asta.health.profile.viewmodel.ProfileConstants.AGE
import fit.asta.health.profile.viewmodel.ProfileConstants.BEDTIME
import fit.asta.health.profile.viewmodel.ProfileConstants.BMI
import fit.asta.health.profile.viewmodel.ProfileConstants.BODY_TYPE
import fit.asta.health.profile.viewmodel.ProfileConstants.DOB
import fit.asta.health.profile.viewmodel.ProfileConstants.EMAIL
import fit.asta.health.profile.viewmodel.ProfileConstants.HEIGHT
import fit.asta.health.profile.viewmodel.ProfileConstants.ID
import fit.asta.health.profile.viewmodel.ProfileConstants.INJURIES_SINCE
import fit.asta.health.profile.viewmodel.ProfileConstants.JENDTIME
import fit.asta.health.profile.viewmodel.ProfileConstants.JSTARTTIME
import fit.asta.health.profile.viewmodel.ProfileConstants.NAME
import fit.asta.health.profile.viewmodel.ProfileConstants.PHONE
import fit.asta.health.profile.viewmodel.ProfileConstants.PREGNANCY_WEEK
import fit.asta.health.profile.viewmodel.ProfileConstants.PROFILE_DATA
import fit.asta.health.profile.viewmodel.ProfileConstants.USER_IMG
import fit.asta.health.profile.viewmodel.ProfileConstants.WAKEUPTIME
import fit.asta.health.profile.viewmodel.ProfileConstants.WEIGHT
import fit.asta.health.testimonials.model.domain.InputIntWrapper
import fit.asta.health.testimonials.model.domain.InputWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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


    private val _stateSubmit = MutableStateFlow<ProfileSubmitState>(ProfileSubmitState.Loading)
    val stateSubmit = _stateSubmit.asStateFlow()


    private val mutableHPropState = MutableStateFlow<HPropState>(HPropState.Loading)
    val stateHp = mutableHPropState.asStateFlow()


    private val _mutableState = MutableStateFlow<ProfileGetState>(ProfileGetState.Loading)
    val state = _mutableState.asStateFlow()


    private val _mutableEditState = MutableStateFlow<ProfileGetState>(ProfileGetState.Loading)
    val stateEdit = _mutableEditState.asStateFlow()


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


    private val areDietInputsValid = MutableStateFlow(false)
    val dietInputsValid: StateFlow<Boolean>
        get() = areDietInputsValid


    private fun isDietValid(valid: Boolean) {
        areDietInputsValid.value = valid
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


    //Addiction Option
    private val _selectedAddictionOption =
        MutableStateFlow<TwoToggleSelections?>(null) // event raising -> lifecycle
    val selectedAddictionOption: StateFlow<TwoToggleSelections?>
        get() = _selectedAddictionOption


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


    private fun setSelectedAddictionOption(option: TwoToggleSelections) {
        _selectedAddictionOption.value = option
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
            6 to mutableStateListOf<HealthProperties>()
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
        USER_IMG, ProfileMedia(name = "user_img", title = "User Profile Image")
    )

    private val userID = savedState.getStateFlow(ID, "")


    //Physique
    val dob = savedState.getStateFlow(DOB, InputWrapper())
    val age = savedState.getStateFlow(AGE, InputWrapper())
    val weight = savedState.getStateFlow(WEIGHT, InputWrapper())
    val height = savedState.getStateFlow(HEIGHT, InputWrapper())
    val pregnancyWeek = savedState.getStateFlow(PREGNANCY_WEEK, InputWrapper())
    private val bodyType = savedState.getStateFlow(BODY_TYPE, InputIntWrapper())
    val bmi = savedState.getStateFlow(BMI, InputWrapper())

    //Health
    val injuriesSince = savedState.getStateFlow(INJURIES_SINCE, InputWrapper())

    //LifeStyle
    val wakeUpTime = savedState.getStateFlow(WAKEUPTIME, InputWrapper())
    val bedTime = savedState.getStateFlow(BEDTIME, InputWrapper())
    val jStartTime = savedState.getStateFlow(JSTARTTIME, InputWrapper())
    val jEndTime = savedState.getStateFlow(JENDTIME, InputWrapper())

    init {
        loadUserProfile()
    }


    fun loadUserProfile() {

        if (networkHelper.isConnected()) {
            authRepo.getUser()?.let {
                loadUserProfileResponse(userId = it.uid)
            }
        } else {
            _mutableState.value = ProfileGetState.NoInternet
        }

    }

    private fun loadEditProfile(userID: String) {
        viewModelScope.launch {
            when (val result = profileRepo.editUserProfile(userID)) {
                is ApiResponse.Error -> {}
                is ApiResponse.HttpError -> {
                    _mutableEditState.value = ProfileGetState.Empty
                }

                is ApiResponse.Success -> {

                    savedState[PROFILE_DATA] = result.data


                    savedState[NAME] = result.data.contact.name

                    _mutableEditState.value = ProfileGetState.Success(result.data)
                }
            }
        }
    }

    //view only
    private fun loadUserProfileResponse(userId: String) {

        viewModelScope.launch {
//            profileRepo.getUserProfile(userId).catch { exception ->
//                _mutableState.value = ProfileGetState.Error(exception)
//            }.collect { profile ->
//                //User Profile Data
//                _mutableState.value = ProfileGetState.Success(profile)
//            }

            when (val result = profileRepo.getUserProfile(userId)) {
                is ApiResponse.Error -> {}
                is ApiResponse.HttpError -> {
                    _mutableState.value = ProfileGetState.Empty
                }

                is ApiResponse.Success -> {

                    //Profile Data Replica
                    savedState[PROFILE_DATA] = result.data

                    //Access Data
                    //Contact
                    savedState[NAME] = InputWrapper(value = result.data.contact.name)
                    savedState[EMAIL] = InputWrapper(value = result.data.contact.email)
                    savedState[PHONE] = InputWrapper(value = result.data.contact.phone)
                    savedState[USER_IMG] = result.data.contact.url
                    savedState[ADDRESS] = result.data.contact.address
                    savedState[ID] = result.data.id

                    //Physique
                    savedState[AGE] = InputWrapper(value = result.data.physique.age.toString())
                    savedState[DOB] = InputWrapper(value = result.data.contact.dob)
                    savedState[WEIGHT] =
                        InputWrapper(value = result.data.physique.weight.toString())
                    savedState[HEIGHT] =
                        InputWrapper(value = result.data.physique.height.toString())
                    _selectedGenderOption.value = when (result.data.physique.gender) {
                        1 -> ThreeToggleSelections.First
                        2 -> ThreeToggleSelections.Second
                        3 -> ThreeToggleSelections.Third
                        else -> {
                            null
                        }
                    }
                    _isPregnantOption.value = when (result.data.physique.isPregnant) {
                        1 -> {
                            TwoToggleSelections.First
                        }

                        2 -> TwoToggleSelections.Second
                        else -> {
                            null
                        }
                    }
                    _isOnPeriodOption.value = when (result.data.physique.onPeriod) {
                        1 -> {
                            TwoToggleSelections.First
                        }

                        2 -> TwoToggleSelections.Second
                        else -> {
                            null
                        }
                    }
                    savedState[PREGNANCY_WEEK] =
                        InputWrapper(value = result.data.physique.pregnancyWeek.toString())

                    //Health
                    _selectedHealthHisOption.value =
                        if (result.data.health.healthHistory.isNullOrEmpty()) {
                            TwoToggleSelections.Second
                        } else {
                            TwoToggleSelections.First
                        }
                    _selectedInjOption.value = if (result.data.health.injuries.isNullOrEmpty()) {
                        TwoToggleSelections.Second
                    } else {
                        TwoToggleSelections.First
                    }
                    savedState[INJURIES_SINCE] =
                        InputWrapper(value = result.data.health.injurySince.toString())
                    _selectedBodyPartOption.value =
                        if (result.data.health.bodyPart.isNullOrEmpty()) {
                            TwoToggleSelections.Second
                        } else {
                            TwoToggleSelections.First
                        }
                    _selectedAilOption.value = if (result.data.health.ailments.isNullOrEmpty()) {
                        TwoToggleSelections.Second
                    } else {
                        TwoToggleSelections.First
                    }
                    _selectedMedOption.value = if (result.data.health.medications.isNullOrEmpty()) {
                        TwoToggleSelections.Second
                    } else {
                        TwoToggleSelections.First
                    }
                    _selectedHealthTarOption.value =
                        if (result.data.health.healthTargets.isNullOrEmpty()) {
                            TwoToggleSelections.Second
                        } else {
                            TwoToggleSelections.First
                        }
                    _selectedAddictionOption.value =
                        if (result.data.health.addiction.isNullOrEmpty()) {
                            TwoToggleSelections.Second
                        } else {
                            TwoToggleSelections.First
                        }
                    _healthPropertiesData.value = _healthPropertiesData.value.toMutableMap().apply {
                        put(
                            key = 0,
                            value = mutableStateListOf(*result.data.health.healthHistory!!.toTypedArray())
                        )
                        put(
                            key = 1,
                            value = mutableStateListOf(*result.data.health.injuries!!.toTypedArray())
                        )
                        put(
                            key = 2,
                            value = mutableStateListOf(*result.data.health.bodyPart!!.toTypedArray())
                        )
                        put(
                            key = 3,
                            value = mutableStateListOf(*result.data.health.ailments!!.toTypedArray())
                        )
                        put(
                            key = 4,
                            value = mutableStateListOf(*result.data.health.medications!!.toTypedArray())
                        )
                        put(
                            key = 5,
                            value = mutableStateListOf(*result.data.health.healthTargets!!.toTypedArray())
                        )
                        put(
                            key = 6,
                            value = mutableStateListOf(*result.data.health.addiction!!.toTypedArray())
                        )
                    }

                    //LifeStyle
                    _selectedPhyActOption.value = when (result.data.lifeStyle.physicalActivity) {
                        1 -> ThreeToggleSelections.First
                        2 -> ThreeToggleSelections.Second
                        3 -> ThreeToggleSelections.Third
                        else -> {
                            null
                        }
                    }
                    _selectedWorkingEnvOption.value = when (result.data.lifeStyle.workingEnv) {
                        1 -> TwoToggleSelections.First
                        2 -> TwoToggleSelections.Second
                        else -> {
                            null
                        }
                    }
                    _selectedWorkStyleOption.value = when (result.data.lifeStyle.workStyle) {
                        1 -> TwoToggleSelections.First
                        2 -> TwoToggleSelections.Second
                        else -> {
                            null
                        }
                    }
                    _selectedWorkingHrsOption.value = when (result.data.lifeStyle.workingHours) {
                        1 -> ThreeToggleSelections.First
                        2 -> ThreeToggleSelections.Second
                        3 -> ThreeToggleSelections.Third
                        else -> {
                            null
                        }
                    }
                    _lfPropertiesData.value = _lfPropertiesData.value.toMutableMap().apply {
                        put(
                            key = 0,
                            value = mutableStateListOf(*result.data.lifeStyle.curActivities!!.toTypedArray())
                        )
                        put(
                            key = 1,
                            value = mutableStateListOf(*result.data.lifeStyle.prefActivities!!.toTypedArray())
                        )
                        put(
                            key = 2,
                            value = mutableStateListOf(*result.data.lifeStyle.lifeStyleTargets!!.toTypedArray())
                        )
                    }

                    //Diet
                    _dietPropertiesData.value = _dietPropertiesData.value.toMutableMap().apply {
                        put(
                            key = 0,
                            value = mutableStateListOf(*result.data.diet.preference!!.toTypedArray())
                        )
                        put(
                            key = 1,
                            value = mutableStateListOf(*result.data.diet.nonVegDays!!.toTypedArray())
                        )
                        put(
                            key = 2,
                            value = mutableStateListOf(*result.data.diet.allergies!!.toTypedArray())
                        )
                        put(
                            key = 3,
                            value = mutableStateListOf(*result.data.diet.cuisines!!.toTypedArray())
                        )
                        put(
                            key = 4,
                            value = mutableStateListOf(*result.data.diet.foodRestrictions!!.toTypedArray())
                        )
                    }
                    _selectedFoodResOption.value =
                        if (result.data.diet.foodRestrictions.isNullOrEmpty()) {
                            TwoToggleSelections.Second
                        } else {
                            TwoToggleSelections.First
                        }

                    //Profile Data
                    _mutableState.value = ProfileGetState.Success(result.data)
                }
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
                    uid = it.uid, id = userID.value, contact = Contact(
                        dob = dob.value.value,
                        email = email.value.value.trim(),
                        name = name.value.value.trim(),
                        url = userImg.value,
                        localUrl = userImg.value.localUrl
                    ), physique = Physique(
                        weight = weight.value.value.toFloat(),
                        age = age.value.value.toInt(),
                        height = height.value.value.toFloat(),
                        pregnancyWeek = if (pregnancyWeek.value.value.isEmpty()) {
                            0
                        } else {
                            pregnancyWeek.value.value.toInt()
                        },
                        bodyType = bodyType.value.value,
                        gender = when (_selectedGenderOption.value) {
                            ThreeToggleSelections.First -> 1
                            ThreeToggleSelections.Second -> 2
                            ThreeToggleSelections.Third -> 3
                            null -> 0
                        },
                        isPregnant = when (_isPregnantOption.value) {
                            TwoToggleSelections.First -> 1
                            else -> {
                                2
                            }
                        },
                        bmi = userBMI(
                            userWeight = weight.value.value.toFloat(),
                            userHeight = height.value.value.toFloat()
                        ),
                        onPeriod = when (_isOnPeriodOption.value) {
                            TwoToggleSelections.First -> 1
                            else -> {
                                2
                            }
                        }
                    ), health = Health(
                        healthHistory = convertHealthArrayList(0),
                        injuries = convertHealthArrayList(1),
                        bodyPart = convertHealthArrayList(2),
                        ailments = convertHealthArrayList(3),
                        medications = convertHealthArrayList(4),
                        healthTargets = convertHealthArrayList(5),
                        addiction = convertHealthArrayList(6),
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
                            ThreeToggleSelections.First -> 1
                            ThreeToggleSelections.Second -> 2
                            ThreeToggleSelections.Third -> 3
                            null -> 0
                        },
                        workingEnv = when (_selectedWorkingEnvOption.value) {
                            TwoToggleSelections.First -> 1
                            TwoToggleSelections.Second -> 2
                            null -> 0
                        },
                        workStyle = when (_selectedWorkStyleOption.value) {
                            TwoToggleSelections.First -> 1
                            TwoToggleSelections.Second -> 2
                            null -> 0
                        },
                        workingHours = when (_selectedWorkingHrsOption.value) {
                            ThreeToggleSelections.First -> 1
                            ThreeToggleSelections.Second -> 2
                            ThreeToggleSelections.Third -> 3
                            null -> 0
                        },
                        workingTime = Session(
                            from = bedTime.value.value.toDouble(),
                            to = wakeUpTime.value.value.toDouble()
                        ),
                        sleep = Session(
                            from = jStartTime.value.value.toDouble(),
                            to = jEndTime.value.value.toDouble()
                        ),
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
                    _stateSubmit.value = ProfileSubmitState.Error(exception)
                }.collect { profile ->
                    _stateSubmit.value = ProfileSubmitState.Success(profile)
                }
            }
        } else {
            _stateSubmit.value = ProfileSubmitState.NoInternet
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


    private fun userBMI(userWeight: Float, userHeight: Float): Float {
        return ((userWeight / (userHeight * userHeight)) * 10000)
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
            is ProfileEvent.SetSelectedAddictionOption -> setSelectedAddictionOption(event.option)
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
            is ProfileEvent.IsDietValid -> isDietValid(event.valid)
            is ProfileEvent.OnProfilePicClear -> {
                savedState[USER_IMG] = userImg.value.copy(
                    localUrl = null, url = ""
                )
            }

            is ProfileEvent.OnUserJEndTimeChange -> {
                savedState[JENDTIME] = jEndTime.value.copy(
                    value = event.jEndTime
                )
            }

            is ProfileEvent.OnUserJStartTimeChange -> {
                savedState[JSTARTTIME] = jStartTime.value.copy(
                    value = event.jStartTime
                )
            }

            is ProfileEvent.OnUserBedTimeChange -> {
                savedState[BEDTIME] = bedTime.value.copy(
                    value = event.bedTime
                )
            }

            is ProfileEvent.OnUserWakeUpTimeChange -> {
                savedState[WAKEUPTIME] = wakeUpTime.value.copy(
                    value = event.wakeUpTime
                )
            }
        }

    }


    private fun isCreateUserProfileDirty(): Boolean {
        return profileData.value.contact.name != name.value.value || profileData.value.contact.email != email.value.value || profileData.value.contact.dob != dob.value.value || profileData.value.physique.age != age.value.value.toInt() || profileData.value.physique.weight != weight.value.value.toFloat() || profileData.value.physique.height != height.value.value.toFloat() || profileData.value.physique.pregnancyWeek != pregnancyWeek.value.value.toInt() || profileData.value.health.injurySince != injuriesSince.value.value.toInt() // || profileData.value.contact.url != userImg.value.url  || profileData.value.contact.url != userImg.value.localUrl?.path
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


    val doAllDataInputsValid = combine(
        areDetailsInputsValid,
        arePhyInputsValid,
        areHealthInputsValid,
        areLSValid,
        areDietInputsValid
    ) { areDetailsInputsValid, arePhyInputsValid, areHealthInputsValid, areLSValid, areDietInputsValid ->
        areDetailsInputsValid && arePhyInputsValid && areHealthInputsValid && areLSValid && areDietInputsValid
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)


}
