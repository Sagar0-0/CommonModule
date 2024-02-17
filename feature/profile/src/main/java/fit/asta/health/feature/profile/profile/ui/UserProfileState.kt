@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package fit.asta.health.feature.profile.profile.ui

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
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
import fit.asta.health.data.profile.remote.model.HealthProperties
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
//        healthPropertiesState,
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

    var bottomSheetVisible by mutableStateOf(false)
        private set
    var bottomSheetSearchQuery by mutableStateOf("")

    private fun openBottomSheet(sheetState: SheetState) {
        bottomSheetVisible = true
        coroutineScope.launch { sheetState.expand() }
    }

    fun closeBottomSheet(sheetState: SheetState) {
        bottomSheetVisible = false
        coroutineScope.launch { sheetState.hide() }
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
    var profileImageUrl: String by mutableStateOf(userProfileResponse.userDetail.media.mailUrl.ifEmpty { userProfileResponse.userDetail.media.url })
        private set
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
    val injurySince by mutableStateOf(userProfileResponse.health.injurySince.toString())
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
        healthBottomSheetType: HealthBottomSheetType,
        index: Int
    ) {
        currentHealthBottomSheetTypeIndex = index
        // Should be before the event below, as it will reset the whole object
        // and bottomSheetVisible state when healthPropertiesState changes
        openBottomSheet(sheetState)
        onEvent(UserProfileEvent.GetHealthProperties(healthBottomSheetType.id))
    }

    //Lifestyle Page
    val sleepStartTime by mutableStateOf(userProfileResponse.lifeStyle.sleep.from.toString())
    val sleepEndTime by mutableStateOf(userProfileResponse.lifeStyle.sleep.to.toString())
    val jobStartTime by mutableStateOf(userProfileResponse.lifeStyle.workingTime.from.toString())
    val jobEndTime by mutableStateOf(userProfileResponse.lifeStyle.workingTime.to.toString())
    val currentActivities =
        (userProfileResponse.lifeStyle.curActivities ?: listOf()).toMutableStateList()
    val preferredActivities =
        (userProfileResponse.lifeStyle.curActivities ?: listOf()).toMutableStateList()
    val lifestyleActivities =
        (userProfileResponse.lifeStyle.curActivities ?: listOf()).toMutableStateList()

    // Diet Page
    val dietPreference = (userProfileResponse.diet.preference ?: listOf()).toMutableStateList()
    val nonVegDays = (userProfileResponse.diet.nonVegDays ?: listOf()).toMutableStateList()
    val dietAllergies = (userProfileResponse.diet.allergies ?: listOf()).toMutableStateList()
    val dietCuisines = (userProfileResponse.diet.cuisines ?: listOf()).toMutableStateList()
    val dietRestrictions = (userProfileResponse.diet.restrictions ?: listOf()).toMutableStateList()

    fun onBackPressed() {
        if (isAnythingChanged) {
            isConfirmDialogVisible = true
        } else {
            isConfirmDialogVisible = false
            navController.popBackStack()
        }
    }

    fun forceBackPress() {
        navController.popBackStack()
    }

    fun saveData() {
        if (isAnythingChanged) {
            onEvent(UserProfileEvent.UpdateUserProfileData(userProfileResponse))//TODO: PASS NEW DATA
        }
    }

    fun clearProfile() {
        profileImageLocalUri = null
        profileImageUrl = ""
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
                    it.bottomSheetVisible,
                    it.bottomSheetSearchQuery,
                    it.currentHealthBottomSheetTypeIndex
                )
            },
            restore = {
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
                    this.bottomSheetVisible = it[18] as Boolean
                    this.bottomSheetSearchQuery = it[19] as String
                    this.currentHealthBottomSheetTypeIndex = it[20] as Int
                }
            }
        )
    }
}