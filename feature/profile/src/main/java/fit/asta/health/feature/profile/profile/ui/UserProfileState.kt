@file:OptIn(ExperimentalFoundationApi::class)

package fit.asta.health.feature.profile.profile.ui

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.feature.profile.profile.utils.ProfileNavigationScreen
import fit.asta.health.feature.profile.profile.utils.UserProfileEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar


@Composable
fun rememberUserProfileState(
    userProfileResponse: UserProfileResponse,
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
        submitProfileState,
        pagerState,
        coroutineScope,
        navController,
        saver = UserProfileState
            .Saver(
                userProfileResponse,
                submitProfileState,
                pagerState,
                coroutineScope,
                navController,
                onEvent
            )
    ) {
        UserProfileState(
            submitProfileState = submitProfileState,
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

    var currentPageIndex: Int
        get() = pagerState.currentPage
        set(value) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(value)
            }
        }

    val profileDataPages: List<ProfileNavigationScreen>
        get() = ProfileNavigationScreen.entries


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
                    it.userDobErrorMessage
                )
            },
            restore = {
                UserProfileState(
                    submitProfileState = submitProfileState,
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
                }
            }
        )
    }
}