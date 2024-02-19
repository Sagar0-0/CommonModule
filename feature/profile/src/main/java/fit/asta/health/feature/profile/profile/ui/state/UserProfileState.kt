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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fit.asta.health.data.profile.remote.model.ProfileMedia
import fit.asta.health.data.profile.remote.model.UserDetail
import fit.asta.health.data.profile.remote.model.UserProfileAddress
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.feature.profile.profile.utils.ProfileNavigationScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun rememberUserProfileState(
    userProfileResponse: UserProfileResponse,
    pagerState: PagerState = rememberPagerState {
        ProfileNavigationScreen.entries.size
    },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavController = rememberNavController(),
    onEvent: (UserProfileEvent) -> Unit
): UserProfileState {
    val healthScreenState = rememberSaveable(
        saver = HealthScreenState
            .Saver(
                coroutineScope, onEvent
            )
    ) {
        HealthScreenState(
            health = userProfileResponse.health,
            coroutineScope = coroutineScope,
            onEvent = onEvent
        )
    }

    val lifestyleScreenState = rememberSaveable(
        saver = LifestyleScreenState
            .Saver(
                coroutineScope, onEvent
            )
    ) {
        LifestyleScreenState(
            lifeStyle = userProfileResponse.lifeStyle,
            coroutineScope = coroutineScope,
            onEvent = onEvent
        )
    }

    val physiqueScreenState = rememberSaveable(
        saver = PhysiqueScreenState
            .Saver(
                coroutineScope, onEvent
            )
    ) {
        PhysiqueScreenState(
            physique = userProfileResponse.physique,
            coroutineScope = coroutineScope,
            onEvent = onEvent
        )
    }

    return rememberSaveable(
        userProfileResponse,
        pagerState,
        coroutineScope,
        navController,
        onEvent,
        saver = UserProfileState.Saver(
            healthScreenState,
            lifestyleScreenState,
            physiqueScreenState,
            userProfileResponse,
            pagerState,
            coroutineScope,
            navController,
            onEvent
        )
    ) {
        UserProfileState(
            healthScreenState = healthScreenState,
            lifestyleScreenState = lifestyleScreenState,
            physiqueScreenState = physiqueScreenState,
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
    val physiqueScreenState: PhysiqueScreenState,
    private val userProfileResponse: UserProfileResponse,
    val pagerState: PagerState,
    private val coroutineScope: CoroutineScope,
    private val navController: NavController,
    private val onEvent: (UserProfileEvent) -> Unit,
) {

    val dietScreenState = DietScreenState(
        userProfileResponse.diet,
        coroutineScope, onEvent
    )

    init {
        Log.d(
            "UserProfileState", "init: \n" +
                    "userProfileResponse = $userProfileResponse\n" +
                    "pagerState = $pagerState\n" +
                    "coroutineScope = $coroutineScope\n" +
                    "navController = $navController\n" +
                    "onEvent = $onEvent"
        )
    }

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

    var userDob by mutableStateOf(userProfileResponse.userDetail.dob)
    fun onBackPressed() {
        saveData()
        navController.popBackStack()
    }

    fun forceBackPress() {
        navController.popBackStack()
    }

    fun saveData() {
        if (!physiqueScreenState.isValid()) {
            currentPageIndex = 1
        } else {
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
                physique = physiqueScreenState.getPhysiqueData(),
                health = healthScreenState.getHealthData(),
                lifeStyle = lifestyleScreenState.getLifestyleData(),
                diet = dietScreenState.getDietData()
            )
            onEvent(UserProfileEvent.UpdateUserProfileData(newProfileData))
        }
    }

    fun clearProfile() {
        profileImageLocalUri = null
        profileImageUrl = ""
    }

    companion object {
        fun Saver(
            healthScreenState: HealthScreenState,
            lifestyleScreenState: LifestyleScreenState,
            physiqueScreenState: PhysiqueScreenState,
            userProfileResponse: UserProfileResponse,
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
                    it.userDob,
                    it.bottomSheetSearchQuery,
                    it.profileImageUrl
                )
            },
            restore = {
                Log.d("SHEET", "Restore: ${it[0]}")
                UserProfileState(
                    healthScreenState = healthScreenState,
                    lifestyleScreenState = lifestyleScreenState,
                    physiqueScreenState = physiqueScreenState,
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
                    this.userDob = it[5] as String
                    this.bottomSheetSearchQuery = it[6] as String
                    this.profileImageUrl = it[7] as String
                }
            }
        )
    }

    data class ProfileBottomSheetPicker(
        val id: String,
        val name: String,
        val list: SnapshotStateList<UserProperties>
    )
}