@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package fit.asta.health.feature.profile.profile.ui.state

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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.Diet
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.data.profile.remote.model.Physique
import fit.asta.health.data.profile.remote.model.ProfileMedia
import fit.asta.health.data.profile.remote.model.UserDetail
import fit.asta.health.data.profile.remote.model.UserProfileAddress
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.feature.profile.profile.utils.ProfileNavigationScreen
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
    val healthScreenState = rememberSaveable(
        healthPropertiesState,
        saver = HealthScreenState
            .Saver(
                healthPropertiesState, coroutineScope, onEvent
            )
    ) {
        HealthScreenState(
            health = userProfileResponse.health,
            healthPropertiesState = healthPropertiesState,
            coroutineScope = coroutineScope,
            onEvent = onEvent
        )
    }

    val lifestyleScreenState = rememberSaveable(
        healthPropertiesState,
        saver = LifestyleScreenState
            .Saver(
                healthPropertiesState, coroutineScope, onEvent
            )
    ) {
        LifestyleScreenState(
            lifeStyle = userProfileResponse.lifeStyle,
            healthPropertiesState = healthPropertiesState,
            coroutineScope = coroutineScope,
            onEvent = onEvent
        )
    }
    return rememberSaveable(
        userProfileResponse,
        healthPropertiesState,
        submitProfileState,
        pagerState,
        coroutineScope,
        navController,
        onEvent,
        healthScreenState,
        lifestyleScreenState,
        saver = UserProfileState.Saver(
            healthScreenState,
            lifestyleScreenState,
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
            healthScreenState = healthScreenState,
            lifestyleScreenState = lifestyleScreenState,
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
    val healthScreenState: HealthScreenState,
    val lifestyleScreenState: LifestyleScreenState,
    val submitProfileState: UiState<SubmitProfileResponse>,
    private val healthPropertiesState: UiState<List<HealthProperties>>,
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

    var bottomSheetSearchQuery by mutableStateOf("")

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

    // Diet Page
    private val dietPreference =
        (userProfileResponse.diet.preference ?: listOf()).toMutableStateList()
    private val nonVegDays = (userProfileResponse.diet.nonVegDays ?: listOf()).toMutableStateList()
    private val dietAllergies =
        (userProfileResponse.diet.allergies ?: listOf()).toMutableStateList()
    private val dietCuisines = (userProfileResponse.diet.cuisines ?: listOf()).toMutableStateList()
    private val dietRestrictions =
        (userProfileResponse.diet.restrictions ?: listOf()).toMutableStateList()

    fun onBackPressed() {
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
            health = healthScreenState.getHealthData(),
            lifeStyle = lifestyleScreenState.getLifestyleData(),
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
            healthScreenState: HealthScreenState,
            lifestyleScreenState: LifestyleScreenState,
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
                    it.profileImageUrl
                )
            },
            restore = {
                Log.d("SHEET", "Restore: ${it[0]}")
                UserProfileState(
                    healthScreenState = healthScreenState,
                    lifestyleScreenState = lifestyleScreenState,
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
                    this.profileImageUrl = it[19] as String
                }
            }
        )
    }

    data class ProfileBottomSheetPicker(
        val id: String,
        val name: String,
        val list: SnapshotStateList<HealthProperties>
    )
}