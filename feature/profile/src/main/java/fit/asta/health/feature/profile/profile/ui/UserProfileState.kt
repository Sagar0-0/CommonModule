@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package fit.asta.health.feature.profile.profile.ui

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.Diet
import fit.asta.health.data.profile.remote.model.Health
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.data.profile.remote.model.LifeStyle
import fit.asta.health.data.profile.remote.model.Physique
import fit.asta.health.data.profile.remote.model.ProfileMedia
import fit.asta.health.data.profile.remote.model.TimeSchedule
import fit.asta.health.data.profile.remote.model.UserDetail
import fit.asta.health.data.profile.remote.model.UserProfileAddress
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.feature.profile.profile.utils.ProfileNavigationScreen
import fit.asta.health.feature.profile.profile.utils.UserProfileEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar


@Composable
fun rememberUserProfileState(
    userProfileResponse: UserProfileResponse,
    healthPropertiesState: UiState<List<HealthProperties>>,
    submitProfileState: UiState<SubmitProfileResponse>,
    pagerState: PagerState = rememberPagerState {
        ProfileNavigationScreen.entries.size
    },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavController = rememberNavController(),
    onEvent: (UserProfileEvent) -> Unit
): UserProfileState {
    return rememberSaveable(
        userProfileResponse,
        healthPropertiesState,
        submitProfileState,
        pagerState,
        coroutineScope,
        navController,
        onEvent,
        saver = UserProfileState
            .Saver(
                userProfileResponse,
                healthPropertiesState,
                submitProfileState,
                pagerState,
                coroutineScope,
                navController,
                onEvent
            )
    ) {
        UserProfileState(
            submitProfileState = submitProfileState,
            healthPropertiesState = healthPropertiesState,
            userProfileResponse = userProfileResponse,
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            navController = navController,
            onEvent = onEvent
        )
    }
}

@Stable
class UserProfileState(
    val submitProfileState: UiState<SubmitProfileResponse>,
    val healthPropertiesState: UiState<List<HealthProperties>>,
    private val userProfileResponse: UserProfileResponse,
    val pagerState: PagerState,
    private val coroutineScope: CoroutineScope,
    private val navController: NavController,
    private val onEvent: (UserProfileEvent) -> Unit,
) {

    init {
        Log.d(
            "UserProfileState", "init: \n" +
                    "submitProfileState = $submitProfileState\n" +
                    "healthPropertiesState = $healthPropertiesState\n" +
                    "userProfileResponse = $userProfileResponse\n" +
                    "pagerState = $pagerState\n" +
                    "coroutineScope = $coroutineScope\n" +
                    "navController = $navController\n" +
                    "onEvent = $onEvent"
        )
    }

    val isScreenLoading by mutableStateOf(submitProfileState is UiState.Loading)

    //Parent level States
    var isConfirmDialogVisible by mutableStateOf(false)
    private var isAnythingChanged by mutableStateOf(false)
    var isImageCropperVisible by mutableStateOf(false)
    var currentPageIndex: Int
        get() = pagerState.currentPage
        set(value) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(value)
            }
        }
    val profileDataPages: List<ProfileNavigationScreen>
        get() = ProfileNavigationScreen.entries

    //    var bottomSheetVisible by mutableStateOf(false)
    var bottomSheetSearchQuery by mutableStateOf("")

    private fun openBottomSheet(sheetState: SheetState) {
//        bottomSheetVisible = true
        coroutineScope.launch { sheetState.expand() }
    }

    fun closeBottomSheet(sheetState: SheetState, bottomSheetVisible: MutableState<Boolean>) {
        coroutineScope.launch { sheetState.hide() }
        bottomSheetVisible.value = false
        bottomSheetSearchQuery = ""
    }

    //Contact Page
    var userName by mutableStateOf(userProfileResponse.userDetail.name)
    val email: String
        get() = userProfileResponse.userDetail.email
    val phoneNumber: String
        get() = userProfileResponse.userDetail.phoneNumber
    val address: String
        get() = userProfileResponse.userDetail.userProfileAddress.toString()
    var profileImageUrl by mutableStateOf(userProfileResponse.userDetail.media.mailUrl.ifEmpty { userProfileResponse.userDetail.media.url })
    var profileImageLocalUri by mutableStateOf<Uri?>(null)

    //Physique Page
    val calendar: Calendar = Calendar.getInstance()
    var userAge by mutableIntStateOf(userProfileResponse.physique.age)
    var userAgeErrorMessage by mutableStateOf<String?>(null)
        private set
    var userDob by mutableStateOf(userProfileResponse.userDetail.dob)
    var userDobErrorMessage by mutableStateOf<String?>(null)
        private set
    var userWeight by mutableStateOf(userProfileResponse.physique.weight.toString())
    var userWeightErrorMessage by mutableStateOf<String?>(null)
        private set
    var userHeight by mutableStateOf(userProfileResponse.physique.height.toString())
    var userHeightErrorMessage by mutableStateOf<String?>(null)
        private set
    var userGender by mutableIntStateOf(userProfileResponse.physique.gender)
    var isPregnant by mutableIntStateOf(userProfileResponse.physique.isPregnant)
    var onPeriod by mutableIntStateOf(userProfileResponse.physique.onPeriod)
    var userPregnancyWeek by mutableStateOf(userProfileResponse.physique.pregnancyWeek?.toString())
    var userPregnancyWeekErrorMessage by mutableStateOf<String?>(null)

    //Health Page
    val medications = (userProfileResponse.health.medications ?: listOf()).toMutableStateList()
    val targets = (userProfileResponse.health.targets ?: listOf()).toMutableStateList()
    val ailments = (userProfileResponse.health.ailments ?: listOf()).toMutableStateList()
    val healthHistory = (userProfileResponse.health.healthHistory ?: listOf()).toMutableStateList()
    val injuries = (userProfileResponse.health.injuries ?: listOf()).toMutableStateList()
    val bodyPart = (userProfileResponse.health.bodyPart ?: listOf()).toMutableStateList()
    val addiction = (userProfileResponse.health.addiction ?: listOf()).toMutableStateList()
    val injurySince by mutableStateOf(userProfileResponse.health.injurySince?.toString())
    var currentHealthBottomSheetTypeIndex by mutableIntStateOf(0)

    class HealthBottomSheetType(
        val id: String,
        val name: String,
        val list: List<HealthProperties>,
        val add: (HealthProperties) -> Unit,
        val remove: (HealthProperties) -> Unit,
    )

    val healthBottomSheetTypes: List<HealthBottomSheetType> = listOf(
        HealthBottomSheetType(
            "hh",
            "Health History",
            healthHistory.toList(),
            { healthHistory.add(it) },
            { healthHistory.remove(it) }),
        HealthBottomSheetType(
            "injury",
            "Injuries",
            injuries.toList(),
            { injuries.add(it) },
            { injuries.remove(it) }),
        HealthBottomSheetType(
            "ailment",
            "Ailments",
            ailments.toList(),
            { ailments.add(it) },
            { ailments.remove(it) }),
        HealthBottomSheetType(
            "med",
            "Medications",
            medications.toList(),
            { medications.add(it) },
            { medications.remove(it) }),
        HealthBottomSheetType(
            "tgt",
            "Targets",
            targets.toList(),
            { targets.add(it) },
            { targets.remove(it) }),
        HealthBottomSheetType(
            "bp",
            "Body parts",
            bodyPart.toList(),
            { bodyPart.add(it) },
            { bodyPart.remove(it) }),
        HealthBottomSheetType(
            "bp",
            "Addictions",
            addiction.toList(),
            { addiction.add(it) },
            { addiction.remove(it) }),
    )

    fun openHealthBottomSheet(
        sheetState: SheetState,
        index: Int,
        bottomSheetVisible: MutableState<Boolean>
    ) {
        currentHealthBottomSheetTypeIndex = index
        bottomSheetVisible.value = true
        coroutineScope.launch { sheetState.expand() }
        getHealthProperties()
    }

    private fun getHealthProperties() {
        onEvent(UserProfileEvent.GetHealthProperties(healthBottomSheetTypes[currentHealthBottomSheetTypeIndex].id))
    }

    //Lifestyle Page
    val sleepStartTime by mutableStateOf(userProfileResponse.lifeStyle.sleep.from.toString())
    val sleepEndTime by mutableStateOf(userProfileResponse.lifeStyle.sleep.to.toString())
    val jobStartTime by mutableStateOf(userProfileResponse.lifeStyle.workingTime.from.toString())
    val jobEndTime by mutableStateOf(userProfileResponse.lifeStyle.workingTime.to.toString())
    val currentActivities =
        (userProfileResponse.lifeStyle.curActivities ?: listOf()).toMutableStateList()
    val preferredActivities =
        (userProfileResponse.lifeStyle.prefActivities ?: listOf()).toMutableStateList()
    val lifestyleTargets =
        (userProfileResponse.lifeStyle.lifeStyleTargets ?: listOf()).toMutableStateList()

    // Diet Page
    val dietPreference = (userProfileResponse.diet.preference ?: listOf()).toMutableStateList()
    val nonVegDays = (userProfileResponse.diet.nonVegDays ?: listOf()).toMutableStateList()
    val dietAllergies = (userProfileResponse.diet.allergies ?: listOf()).toMutableStateList()
    val dietCuisines = (userProfileResponse.diet.cuisines ?: listOf()).toMutableStateList()
    val dietRestrictions = (userProfileResponse.diet.restrictions ?: listOf()).toMutableStateList()

    fun onBackPressed() {
//        if (isAnythingChanged) {
//            isConfirmDialogVisible = true
//        } else {
//            isConfirmDialogVisible = false
//            navController.popBackStack()
//        }
        saveData()
        navController.popBackStack()
    }

    fun forceBackPress() {
        navController.popBackStack()
    }

    fun saveData() {
        val newProfileData = UserProfileResponse(
            uid = userProfileResponse.uid,
            id = userProfileResponse.id,
            userDetail = UserDetail(
                userProfileAddress = UserProfileAddress(
                    address = address,
                    country = userProfileResponse.userDetail.userProfileAddress.country,
                    city = userProfileResponse.userDetail.userProfileAddress.city,
                    pin = userProfileResponse.userDetail.userProfileAddress.pin,
                    street = userProfileResponse.userDetail.userProfileAddress.street
                ),
                dob = userDob,
                email = email,
                name = userName,
                phoneNumber = phoneNumber,
                media = ProfileMedia(
                    url = userProfileResponse.userDetail.media.url,
                    mailUrl = userProfileResponse.userDetail.media.mailUrl,
                    localUri = profileImageLocalUri
                )
            ),
            physique = Physique(
                age = userAge,
                bodyType = userProfileResponse.physique.bodyType,
                bmi = userProfileResponse.physique.bmi,
                gender = userGender,
                height = userHeight.toFloat(),
                isPregnant = isPregnant,
                onPeriod = onPeriod,
                pregnancyWeek = userPregnancyWeek?.toIntOrNull(),
                weight = userWeight.toFloat()
            ),
            health = Health(
                medications = medications,
                targets = targets,
                ailments = ailments,
                healthHistory = healthHistory,
                injuries = injuries,
                bodyPart = bodyPart,
                addiction = addiction,
                injurySince = injurySince?.toIntOrNull()
            ),
            lifeStyle = LifeStyle(
                physicalActivity = userProfileResponse.lifeStyle.physicalActivity,
                workingEnv = userProfileResponse.lifeStyle.workingEnv,
                workStyle = userProfileResponse.lifeStyle.workStyle,
                workingHours = userProfileResponse.lifeStyle.workingHours,
                curActivities = currentActivities,
                prefActivities = preferredActivities,
                lifeStyleTargets = lifestyleTargets,
                workingTime = TimeSchedule(
                    from = jobStartTime.toFloat(),
                    to = jobEndTime.toFloat()
                ),
                sleep = TimeSchedule(
                    from = sleepStartTime.toFloat(),
                    to = sleepEndTime.toFloat()
                )
            ),
            diet = Diet(
                preference = dietPreference,
                nonVegDays = nonVegDays,
                allergies = dietAllergies,
                cuisines = dietCuisines,
                restrictions = dietRestrictions
            )
        )
        onEvent(UserProfileEvent.UpdateUserProfileData(newProfileData))
    }

    fun clearProfile() {
        profileImageLocalUri = null
        profileImageUrl = ""
    }

    fun resetHealthProperties() {
        onEvent(UserProfileEvent.ResetHealthProperties)
    }

    companion object {
        fun Saver(
            userProfileResponse: UserProfileResponse,
            healthPropertiesState: UiState<List<HealthProperties>>,
            submitProfileState: UiState<SubmitProfileResponse>,
            pagerState: PagerState,
            coroutineScope: CoroutineScope,
            navController: NavController,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<UserProfileState, *> = listSaver(
            save = {
                listOf(
                    it.userName,
                    it.profileImageLocalUri,
                    it.isConfirmDialogVisible,
                    it.isAnythingChanged,
                    it.isImageCropperVisible,
                    it.userAge,
                    it.userAgeErrorMessage,
                    it.userWeight,
                    it.userWeightErrorMessage,
                    it.userHeight,
                    it.userHeightErrorMessage,
                    it.userGender,
                    it.onPeriod,
                    it.isPregnant,
                    it.userPregnancyWeek,
                    it.userPregnancyWeekErrorMessage,
                    it.userDob,
                    it.userDobErrorMessage,
                    it.bottomSheetSearchQuery,
                    it.currentHealthBottomSheetTypeIndex,
                    it.profileImageUrl
                )
            },
            restore = {
                Log.d("SHEET", "Restore: ${it[0]}")
                UserProfileState(
                    submitProfileState = submitProfileState,
                    healthPropertiesState = healthPropertiesState,
                    userProfileResponse = userProfileResponse,
                    pagerState = pagerState,
                    coroutineScope = coroutineScope,
                    navController = navController,
                    onEvent = onEvent
                ).apply {
                    this.userName = it[0] as String
                    this.profileImageLocalUri = it[1] as Uri?
                    this.isConfirmDialogVisible = it[2] as Boolean
                    this.isAnythingChanged = it[3] as Boolean
                    this.isImageCropperVisible = it[4] as Boolean
                    this.userAge = it[5] as Int
                    this.userAgeErrorMessage = it[6] as String?
                    this.userWeight = it[7] as String
                    this.userWeightErrorMessage = it[8] as String?
                    this.userHeight = it[9] as String
                    this.userHeightErrorMessage = it[10] as String?
                    this.userGender = it[11] as Int
                    this.onPeriod = it[12] as Int
                    this.isPregnant = it[13] as Int
                    this.userPregnancyWeek = it[14] as String?
                    this.userPregnancyWeekErrorMessage = it[15] as String?
                    this.userDob = it[16] as String
                    this.userDobErrorMessage = it[17] as String?
                    this.bottomSheetSearchQuery = it[18] as String
                    this.currentHealthBottomSheetTypeIndex = it[19] as Int
                    this.profileImageUrl = it[20] as String
                }
            }
        )
    }
}