package fit.asta.health.feature.profile.show.vm

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.InputWrapper
import fit.asta.health.common.utils.UiString
import fit.asta.health.data.profile.remote.model.Contact
import fit.asta.health.data.profile.remote.model.Diet
import fit.asta.health.data.profile.remote.model.Health
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.data.profile.remote.model.LifeStyle
import fit.asta.health.data.profile.remote.model.Physique
import fit.asta.health.data.profile.remote.model.ProfileMedia
import fit.asta.health.data.profile.remote.model.UserProfile
import fit.asta.health.data.profile.repo.ProfileRepo
import fit.asta.health.feature.profile.ProfileConstants.ADDRESS
import fit.asta.health.feature.profile.ProfileConstants.AGE
import fit.asta.health.feature.profile.ProfileConstants.BEDTIME
import fit.asta.health.feature.profile.ProfileConstants.BODY_TYPE
import fit.asta.health.feature.profile.ProfileConstants.DOB
import fit.asta.health.feature.profile.ProfileConstants.EMAIL
import fit.asta.health.feature.profile.ProfileConstants.HEIGHT
import fit.asta.health.feature.profile.ProfileConstants.ID
import fit.asta.health.feature.profile.ProfileConstants.INJURIES_SINCE
import fit.asta.health.feature.profile.ProfileConstants.JENDTIME
import fit.asta.health.feature.profile.ProfileConstants.JSTARTTIME
import fit.asta.health.feature.profile.ProfileConstants.NAME
import fit.asta.health.feature.profile.ProfileConstants.PHONE
import fit.asta.health.feature.profile.ProfileConstants.PREGNANCY_WEEK
import fit.asta.health.feature.profile.ProfileConstants.USER_IMG
import fit.asta.health.feature.profile.ProfileConstants.WAKEUPTIME
import fit.asta.health.feature.profile.ProfileConstants.WEIGHT
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys.GENDER
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys.ISONPERIOD
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys.ISPREG
import fit.asta.health.feature.profile.create.vm.ComposeIndex
import fit.asta.health.feature.profile.create.vm.HPropState
import fit.asta.health.feature.profile.create.vm.ProfileEvent
import fit.asta.health.feature.profile.create.vm.ProfileSubmitState
import fit.asta.health.feature.profile.create.vm.ThreeRadioBtnSelections
import fit.asta.health.feature.profile.create.vm.TwoRadioBtnSelections
import fit.asta.health.feature.profile.show.vm.ProfileGetState.Empty
import fit.asta.health.feature.profile.show.vm.ProfileGetState.Loading
import fit.asta.health.feature.profile.show.vm.ProfileGetState.NoInternet
import fit.asta.health.feature.profile.show.vm.ProfileGetState.Success
import fit.asta.health.network.NetworkHelper
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.resources.strings.R
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

    //States
    private val _stateSubmit = MutableStateFlow<ProfileSubmitState>(ProfileSubmitState.Loading)
    val stateSubmit = _stateSubmit.asStateFlow()

    private val mutableHPropState = MutableStateFlow<HPropState>(HPropState.Loading)
    val stateHp = mutableHPropState.asStateFlow()

    private val _mutableState = MutableStateFlow<ProfileGetState>(Loading)
    val state = _mutableState.asStateFlow()

    //Details
    val name = savedState.getStateFlow(NAME, InputWrapper())
    val email = savedState.getStateFlow(
        EMAIL, InputWrapper()
    )

    val userImg = savedState.getStateFlow(
        USER_IMG, ProfileMedia(name = "user_img", title = "User Profile Image")
    )

    private val userID = savedState.getStateFlow(ID, "")

    //Physique
    val dob = savedState.getStateFlow(DOB, InputWrapper())
    val age = savedState.getStateFlow(AGE, InputWrapper())
    val weight = savedState.getStateFlow(
        WEIGHT, InputWrapper()
    )
    val height = savedState.getStateFlow(
        HEIGHT, InputWrapper()
    )
    val pregnancyWeek = savedState.getStateFlow(
        PREGNANCY_WEEK, InputWrapper()
    )
    private val bodyType = savedState.getStateFlow(
        BODY_TYPE, InputWrapper()
    )

    //Health
    val injuriesSince = savedState.getStateFlow(
        INJURIES_SINCE, InputWrapper()
    )

    //LifeStyle
    val wakeUpTime = savedState.getStateFlow(
        WAKEUPTIME, InputWrapper()
    )
    val bedTime = savedState.getStateFlow(
        BEDTIME, InputWrapper()
    )
    val jStartTime = savedState.getStateFlow(
        JSTARTTIME, InputWrapper()
    )
    val jEndTime = savedState.getStateFlow(
        JENDTIME, InputWrapper()
    )

    // multiple radio buttons selection handling
    private val _radioButtonSelections = MutableStateFlow<Map<String, Any?>>(emptyMap())
    val radioButtonSelections: StateFlow<Map<String, Any?>>
        get() = _radioButtonSelections

    // Function to update the selection for a specific radio button
    fun updateRadioButtonSelection(radioButtonName: String, selection: Any) {
        viewModelScope.launch {
            _radioButtonSelections.value = _radioButtonSelections.value.toMutableMap().apply {
                put(radioButtonName, selection)
            }
        }
    }

    // Function to get the selected values for a specific radio button group
    private inline fun <reified T> getSelectedValueForRadioButton(radioButtonName: String): T? {
        return radioButtonSelections.value[radioButtonName] as? T
    }

    private val _propertiesData = MutableStateFlow(
        mapOf(
            ComposeIndex.First to mapOf(
                0 to mutableStateListOf(),
                1 to mutableStateListOf(),
                2 to mutableStateListOf(),
                3 to mutableStateListOf(),
                4 to mutableStateListOf(),
                5 to mutableStateListOf(),
                6 to mutableStateListOf()
            ), ComposeIndex.Second to mapOf(
                0 to mutableStateListOf(), 1 to mutableStateListOf(), 2 to mutableStateListOf()
            ), ComposeIndex.Third to mapOf(
                0 to mutableStateListOf(),
                1 to mutableStateListOf(),
                2 to mutableStateListOf(),
                3 to mutableStateListOf(),
                4 to mutableStateListOf<HealthProperties>()
            )
        )
    )

    val propertiesData: StateFlow<Map<ComposeIndex, Map<Int, SnapshotStateList<HealthProperties>>>> =
        _propertiesData

    private fun modifyPropertiesData(
        cardViewIndex: Int,
        item: HealthProperties,
        composeIndex: ComposeIndex,
        add: Boolean,
    ) {
        val propertyData = _propertiesData.value.toMutableMap()
        val composeData = propertyData[composeIndex]?.toMutableMap() ?: mutableMapOf()
        val currentList = composeData[cardViewIndex] ?: mutableStateListOf()

        if (add) {
            if (!currentList.contains(item)) {
                currentList.add(item)
            }
        } else {
            currentList.remove(item)
        }

        composeData[cardViewIndex] = currentList
        propertyData[composeIndex] = composeData
        _propertiesData.value = propertyData
    }

    // Functions for adding and removing items from properties data
    private fun healthAdd(
        cardViewIndex: Int,
        item: HealthProperties,
        composeIndex: ComposeIndex,
    ) {
        modifyPropertiesData(cardViewIndex, item, composeIndex, add = true)
    }

    private fun healthRemove(
        cardViewIndex: Int,
        item: HealthProperties,
        composeIndex: ComposeIndex,
    ) {
        modifyPropertiesData(cardViewIndex, item, composeIndex, add = false)
    }

    private fun getValueAtIndex(
        composeIndex: ComposeIndex,
        cardViewIndex: Int,
    ): List<HealthProperties>? {
        val composeData = _propertiesData.value[composeIndex]
        return composeData?.get(cardViewIndex)
    }


    private fun modifyPropertiesData(
        cardViewIndex: Int,
        items: List<HealthProperties>,
        composeIndex: ComposeIndex,
        add: Boolean = true,
    ) {
        val propertyData = _propertiesData.value.toMutableMap()
        val composeData = propertyData[composeIndex]?.toMutableMap() ?: mutableMapOf()
        val currentList = composeData[cardViewIndex] ?: mutableStateListOf()

        if (add) {
            for (item in items) {
                if (!currentList.contains(item)) {
                    currentList.add(item)
                }
            }
        } else {
            currentList.removeAll(items)
        }

        composeData[cardViewIndex] = currentList
        propertyData[composeIndex] = composeData
        _propertiesData.value = propertyData
    }


    init {
        loadUserProfile()
    }

    fun loadUserProfile() {

        if (networkHelper.isConnected()) {
            authRepo.getUser()?.let {
                loadUserProfileResponse(userId = it.uid)
            }
        } else {
            _mutableState.value = NoInternet
        }

    }

    private fun loadUserProfileResponse(userId: String) {
        viewModelScope.launch {

            when (val result = profileRepo.getUserProfile(userId)) {
                is ApiResponse.Error -> {
                    // Handle error
                }

                is ApiResponse.HttpError -> {
                    _mutableState.value = Empty
                }

                is ApiResponse.Success -> {
                    handleSuccessResponse(result.data)
                }
            }
        }
    }

    private fun handleSuccessResponse(data: UserProfile) {

        //Profile Data Replica
//        savedState[PROFILE_DATA] = data

        //Access Data
        handleContactData(data.contact, data.id)
        handlePhysiqueData(
            data.physique, dob = InputWrapper(
                value = data.contact.dob
            )
        )
        handleHealthData(data.health)
        handleLifeStyleData(data.lifeStyle)
        handleDietData(data.diet)

        //Profile Data
        _mutableState.value = Success(data)
    }

    private fun handleContactData(contact: Contact, id: String) {
        savedState[NAME] = InputWrapper(value = contact.name)
        savedState[EMAIL] = InputWrapper(value = contact.email)
        savedState[PHONE] = InputWrapper(value = contact.phone)
        savedState[USER_IMG] = contact.url.toString()
        savedState[ADDRESS] = contact.address.toString()
        savedState[ID] = id
        savedState[DOB] = InputWrapper(value = contact.dob)
    }

    private fun handlePhysiqueData(
        physique: Physique,
        dob: InputWrapper,
    ) {
        // Handle physique data
        savedState[AGE] = InputWrapper(value = physique.age.toString())
        savedState[DOB] = dob
        savedState[WEIGHT] = InputWrapper(value = physique.weight.toString())
        savedState[HEIGHT] = InputWrapper(value = physique.height.toString())
        loadThreeRadioBtnSelection(physique.gender, GENDER.key)
        loadTwoRadioBtnSelection(
            result = physique.isPregnant, radioBtnName = ISPREG.key
        )
        loadTwoRadioBtnSelection(
            result = physique.onPeriod, radioBtnName = ISONPERIOD.key
        )
        savedState[PREGNANCY_WEEK] = InputWrapper(value = physique.pregnancyWeek.toString())
    }

    private fun handleHealthData(health: Health) {
        // Handle health data

        updateRadioButtonSelection(
            MultiRadioBtnKeys.HEALTHHIS.key,
            checkTwoRadioBtnSelections(health.healthHistory.isNullOrEmpty())
        )
        updateRadioButtonSelection(
            MultiRadioBtnKeys.INJURIES.key,
            checkTwoRadioBtnSelections(health.injuries.isNullOrEmpty())
        )
        updateRadioButtonSelection(
            MultiRadioBtnKeys.BODYPART.key,
            checkTwoRadioBtnSelections(health.bodyPart.isNullOrEmpty())
        )
        updateRadioButtonSelection(
            MultiRadioBtnKeys.AILMENTS.key,
            checkTwoRadioBtnSelections(health.ailments.isNullOrEmpty())
        )
        updateRadioButtonSelection(
            MultiRadioBtnKeys.MEDICATIONS.key,
            checkTwoRadioBtnSelections(health.medications.isNullOrEmpty())
        )
        updateRadioButtonSelection(
            MultiRadioBtnKeys.HEALTHTAR.key,
            checkTwoRadioBtnSelections(health.healthTargets.isNullOrEmpty())
        )
        updateRadioButtonSelection(
            MultiRadioBtnKeys.ADDICTION.key,
            checkTwoRadioBtnSelections(health.addiction.isNullOrEmpty())
        )

        savedState[INJURIES_SINCE] = InputWrapper(value = health.injurySince.toString())

        _propertiesData.value = _propertiesData.value.toMutableMap().apply {

            modifyPropertiesData(
                0, health.healthHistory ?: emptyList(), ComposeIndex.First, add = true
            )
            modifyPropertiesData(1, health.injuries ?: emptyList(), ComposeIndex.First, add = true)
            modifyPropertiesData(2, health.bodyPart ?: emptyList(), ComposeIndex.First, add = true)
            modifyPropertiesData(3, health.ailments ?: emptyList(), ComposeIndex.First, add = true)
            modifyPropertiesData(
                4, health.medications ?: emptyList(), ComposeIndex.First, add = true
            )
            modifyPropertiesData(
                5, health.healthTargets ?: emptyList(), ComposeIndex.First, add = true
            )
            modifyPropertiesData(6, health.addiction ?: emptyList(), ComposeIndex.First, add = true)
        }
    }

    private fun handleLifeStyleData(lifeStyle: LifeStyle) {
        // Handle lifestyle data
        savedState[WAKEUPTIME] = InputWrapper(value = lifeStyle.sleep.from.toString())
        savedState[BEDTIME] = InputWrapper(value = lifeStyle.sleep.to.toString())

        loadThreeRadioBtnSelection(
            lifeStyle.physicalActivity, MultiRadioBtnKeys.PHYACTIVE.key
        )

        loadTwoRadioBtnSelection(
            lifeStyle.workingEnv, MultiRadioBtnKeys.WORKINGENV.key
        )

        loadTwoRadioBtnSelection(
            lifeStyle.workStyle, MultiRadioBtnKeys.WORKINGSTYLE.key
        )

        loadThreeRadioBtnSelection(
            lifeStyle.workingHours, MultiRadioBtnKeys.WORKINGHRS.key
        )

        _propertiesData.value = _propertiesData.value.toMutableMap().apply {
            modifyPropertiesData(
                0, lifeStyle.curActivities ?: emptyList(), ComposeIndex.Second, add = true
            )
            modifyPropertiesData(
                1, lifeStyle.prefActivities ?: emptyList(), ComposeIndex.Second, add = true
            )
            modifyPropertiesData(
                2, lifeStyle.lifeStyleTargets ?: emptyList(), ComposeIndex.Second, add = true
            )
        }
    }

    private fun handleDietData(diet: Diet) {
        // Handle diet data

        _propertiesData.value = _propertiesData.value.toMutableMap().apply {
            modifyPropertiesData(0, diet.preference ?: emptyList(), ComposeIndex.Third, add = true)
            modifyPropertiesData(1, diet.nonVegDays ?: emptyList(), ComposeIndex.Third, add = true)
            modifyPropertiesData(2, diet.allergies ?: emptyList(), ComposeIndex.Third, add = true)
            modifyPropertiesData(3, diet.cuisines ?: emptyList(), ComposeIndex.Third, add = true)
            modifyPropertiesData(
                4, diet.foodRestrictions ?: emptyList(), ComposeIndex.Third, add = true
            )
        }

        updateRadioButtonSelection(
            selection = checkTwoRadioBtnSelections(diet.foodRestrictions.isNullOrEmpty()),
            radioButtonName = MultiRadioBtnKeys.DIETREST.key
        )
    }

    private fun checkTwoRadioBtnSelections(result: Boolean) = if (result) {
        TwoRadioBtnSelections.Second
    } else {
        TwoRadioBtnSelections.First
    }

    private fun loadTwoRadioBtnSelection(result: Int?, radioBtnName: String) {
        when (result) {
            1 -> TwoRadioBtnSelections.First
            2 -> TwoRadioBtnSelections.Second
            else -> null
        }?.let { updateRadioButtonSelection(radioBtnName, it) }
    }


    private fun loadThreeRadioBtnSelection(result: Int?, radioBtnName: String) {
        when (result) {
            1 -> ThreeRadioBtnSelections.First
            2 -> ThreeRadioBtnSelections.Second
            3 -> ThreeRadioBtnSelections.Third
            else -> null
        }?.let { updateRadioButtonSelection(radioBtnName, it) }
    }

    private fun uploadTwoRadioBtnSelection(value: TwoRadioBtnSelections?): Int {
        return when (value) {
            TwoRadioBtnSelections.First -> 1
            TwoRadioBtnSelections.Second -> 2
            null -> 0
        }
    }


    private fun uploadThreeRadioBtnSelection(value: ThreeRadioBtnSelections?): Int {
        return when (value) {
            ThreeRadioBtnSelections.First -> 1
            ThreeRadioBtnSelections.Second -> 2
            ThreeRadioBtnSelections.Third -> 3
            null -> 0
        }
    }


    private fun submit() {
        authRepo.getUser()?.let { user ->
            val userProfile = createUserProfile(user)
            updateProfile(userProfile)
        }
    }


    private fun createUserProfile(user: User): UserProfile {
        val contact = createContact()
        val physique = createPhysique()
        val health = createHealth()
        val lifeStyle = createLifeStyle()
        val diet = createDiet()

        return UserProfile(
            uid = user.uid, id = userID.value, contact, physique, health, lifeStyle, diet
        )
    }


    private fun createContact(): Contact {
        // Extract the contact creation logic here
        return Contact(
            dob = dob.value.value,
            email = email.value.value.trim(),
            name = name.value.value.trim(),
            url = ProfileMedia(url = userImg.value.toString()),
            localUrl = userImg.value.localUrl
        )
    }

    private fun safeToInt(value: String): Int {
        return value.toIntOrNull() ?: 0
    }


    private fun createPhysique(): Physique {
        // Extract the physique creation logic here
        return Physique(
            weight = weight.value.value.toFloat(),
            age = safeToInt(age.value.value),
            height = height.value.value.toFloat(),
            pregnancyWeek = safeToInt(pregnancyWeek.value.value),
            bodyType = bodyType.value.value.toInt(),
            gender = uploadThreeRadioBtnSelection(
                value = getSelectedValueForRadioButton(
                    GENDER.key
                )
            ),
            isPregnant = uploadTwoRadioBtnSelection(
                getSelectedValueForRadioButton(
                    ISPREG.key
                )
            ),
            bmi = userBMI(
                userWeight = weight.value.value.toFloat(), userHeight = height.value.value.toFloat()
            ),
            onPeriod = uploadTwoRadioBtnSelection(
                getSelectedValueForRadioButton(
                    ISONPERIOD.key
                )
            )
        )
    }

    private fun createHealth(): Health {
        // Extract the health creation logic here
        return Health(
            healthHistory = getValueAtIndex(ComposeIndex.First, 0)?.let { ArrayList(it) },
            injuries = getValueAtIndex(ComposeIndex.First, 1)?.let { ArrayList(it) },
            bodyPart = getValueAtIndex(ComposeIndex.First, 2)?.let { ArrayList(it) },
            ailments = getValueAtIndex(ComposeIndex.First, 3)?.let { ArrayList(it) },
            medications = getValueAtIndex(ComposeIndex.First, 4)?.let { ArrayList(it) },
            healthTargets = getValueAtIndex(ComposeIndex.First, 5)?.let { ArrayList(it) },
            addiction = getValueAtIndex(ComposeIndex.First, 6)?.let { ArrayList(it) },
            injurySince = if (injuriesSince.value.value == "") {
                0
            } else {
                injuriesSince.value.value.toInt()
            }
        )
    }

    private fun createLifeStyle(): LifeStyle {
        // Extract the lifestyle creation logic here
        return LifeStyle(
            curActivities = getValueAtIndex(ComposeIndex.Second, 0)?.let { ArrayList(it) },
            prefActivities = getValueAtIndex(ComposeIndex.Second, 1)?.let { ArrayList(it) },
            lifeStyleTargets = getValueAtIndex(ComposeIndex.Second, 2)?.let { ArrayList(it) },
            physicalActivity = uploadThreeRadioBtnSelection(
                getSelectedValueForRadioButton(MultiRadioBtnKeys.PHYACTIVE.key)
            ),
            workingEnv = uploadTwoRadioBtnSelection(
                getSelectedValueForRadioButton(
                    MultiRadioBtnKeys.WORKINGENV.key
                )
            ),
            workStyle = uploadTwoRadioBtnSelection(
                getSelectedValueForRadioButton(
                    MultiRadioBtnKeys.WORKINGSTYLE.key
                )
            ),
            workingHours = uploadThreeRadioBtnSelection(
                getSelectedValueForRadioButton(
                    MultiRadioBtnKeys.WORKINGHRS.key
                )
            ),
        )
    }

    private fun createDiet(): Diet {
        // Extract the diet creation logic here
        return Diet(preference = getValueAtIndex(ComposeIndex.Third, 0)?.let { ArrayList(it) },
            nonVegDays = getValueAtIndex(ComposeIndex.Third, 1)?.let { ArrayList(it) },
            allergies = getValueAtIndex(ComposeIndex.Third, 2)?.let { ArrayList(it) },
            cuisines = getValueAtIndex(ComposeIndex.Third, 3)?.let { ArrayList(it) },
            foodRestrictions = getValueAtIndex(ComposeIndex.Third, 4)?.let { ArrayList(it) })
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

    private fun onValidateDetailsText(value: String, min: Int = 1, max: Int): UiString {
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
            is ProfileEvent.OnEmailChange -> handleEmailChange(event.email)
            is ProfileEvent.OnNameChange -> handleNameChange(event.name)
            is ProfileEvent.OnUserImgChange -> handleUserImgChange(event.url)
            is ProfileEvent.OnUserHeightChange -> handleUserHeightChange(event.height)
            is ProfileEvent.OnUserWeightChange -> handleUserWeightChange(event.weight)
            is ProfileEvent.OnUserAGEChange -> handleUserAgeChange(event.age)
            is ProfileEvent.OnUserDOBChange -> handleUserDobChange(event.dob)
            is ProfileEvent.OnUserPregWeekChange -> handleUserPregWeekChange(event.week)
            is ProfileEvent.OnUserInjuryTimeChange -> handleUserInjuryTimeChange(event.time)
            is ProfileEvent.OnUserJEndTimeChange -> handleUserJEndTimeChange(event.jEndTime)
            is ProfileEvent.OnUserJStartTimeChange -> handleUserJStartTimeChange(event.jStartTime)
            is ProfileEvent.OnUserBedTimeChange -> handleUserBedTimeChange(event.bedTime)
            is ProfileEvent.OnUserWakeUpTimeChange -> handleUserWakeUpTimeChange(event.wakeUpTime)
            is ProfileEvent.OnProfilePicClear -> handleProfilePicClear()
            is ProfileEvent.OnSubmit -> submit()
            is ProfileEvent.GetHealthProperties -> getHealthProperties(event.propertyType)
            is ProfileEvent.SetSelectedAddItemOption -> healthAdd(
                event.index, event.item, event.composeIndex
            )

            is ProfileEvent.SetSelectedRemoveItemOption -> healthRemove(
                event.index, event.item, event.composeIndex
            )
        }
    }

    private fun handleProfilePicClear() {
        savedState[USER_IMG] = userImg.value.copy(localUrl = null, url = "")
    }

    private fun handleEmailChange(email: String) {
        savedState[EMAIL] = InputWrapper(
            value = email, error = onValidateDetailsEmail(email)
        )
    }

    private fun handleNameChange(name: String) {
        savedState[NAME] = InputWrapper(
            value = name, error = onValidateDetailsText(name, 1, 20)
        )
    }

    private fun handleUserImgChange(url: Uri?) {
        savedState[USER_IMG] = userImg.value.copy(localUrl = url)
    }

    private fun handleUserHeightChange(eventHeight: String) {
        savedState[HEIGHT] = height.value.copy(
            value = eventHeight,
            error = onValidatePhy(type = "Height", value = eventHeight, min = 54.64, max = 251.0)
        )
    }

    private fun handleUserWeightChange(eventWeight: String) {
        savedState[WEIGHT] = weight.value.copy(
            value = eventWeight,
            error = onValidatePhy(type = "Weight", value = eventWeight, min = 2.13, max = 635.0)
        )
    }

    private fun handleUserAgeChange(eventAge: String) {
        savedState[AGE] = age.value.copy(
            value = eventAge, error = onValidateAge(eventAge, min = 10)
        )
    }

    private fun handleUserDobChange(eventDob: String) {
        savedState[DOB] = dob.value.copy(
            value = eventDob
        )
    }

    private fun handleUserPregWeekChange(eventWeek: String) {
        savedState[PREGNANCY_WEEK] = pregnancyWeek.value.copy(
            value = eventWeek,
            error = onValidatePhy(type = "Pregnancy Week", value = eventWeek, min = 1.0, max = 55.0)
        )
    }

    private fun handleUserInjuryTimeChange(eventTime: String) {
        savedState[INJURIES_SINCE] = injuriesSince.value.copy(
            value = eventTime
        )
    }

    private fun handleUserJEndTimeChange(eventJEndTime: String) {
        savedState[JENDTIME] = jEndTime.value.copy(
            value = eventJEndTime
        )
    }

    private fun handleUserJStartTimeChange(eventJStartTime: String) {
        savedState[JSTARTTIME] = jStartTime.value.copy(
            value = eventJStartTime
        )
    }

    private fun handleUserBedTimeChange(eventBedTime: String) {
        savedState[BEDTIME] = bedTime.value.copy(
            value = eventBedTime
        )
    }

    private fun handleUserWakeUpTimeChange(eventWakeUpTime: String) {
        savedState[WAKEUPTIME] = wakeUpTime.value.copy(
            value = eventWakeUpTime
        )
    }

    //Profile Validations
    val areDetailsInputsValid = combine(name, email) { name, email ->
        name.value.isNotEmpty() && name.error is UiString.Empty && email.value.isNotEmpty() && email.error is UiString.Empty
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

}
