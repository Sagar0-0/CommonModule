@file:OptIn(ExperimentalFoundationApi::class)

package fit.asta.health.feature.profile.profile.ui

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


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
    return remember(
        userProfileResponse,
        submitProfileState,
        pagerState,
        coroutineScope,
        navController,
//        saver = UserProfileState.Saver(userProfileResponse,submitProfileState,onEvent)
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

    var isConfirmDialogVisible by mutableStateOf(false)
    private var isAnythingChanged by mutableStateOf(false)
    var isImageCropperVisible by mutableStateOf(false)


//    var userProfileResponse: UserProfileResponse
//        get() = _userProfileResponse
//        set(value) {
//            if (_userProfileResponse != value) {
//                isAnythingChanged = true
//            }
//            onEvent(UserProfileEvent.UpdateUserProfileData(value))
//        }


    var currentPageIndex: Int
        get() = pagerState.currentPage
        set(value) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(value)
            }
        }

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
            onEvent(UserProfileEvent.SaveData)
        }
    }

    fun clearProfile() {
        profileImageLocalUri = null
        profileImageUrl = ""
    }

    val profileDataPages: List<ProfileNavigationScreen>
        get() = ProfileNavigationScreen.entries


//    companion object {
//        fun Saver(
//            userProfileResponse: UserProfileResponse,
//            submitProfileState: UiState<SubmitProfileResponse>,
//            onEvent: (UserProfileEvent) -> Unit
//        ) : Saver<UserProfileState, *> = listSaver(
//            save = {
//                listOf(
//                    it.isConfirmDialogVisible,
//                )
//            },
//            restore = {
//                UserProfileState(
//                    userProfileResponse = userProfileResponse
//                ).apply {
//                    this.isConfirmDialogVisible = it[0]
//                }
//            }
//        )
//    }
}


sealed interface UserProfileEvent {
    data object SaveData : UserProfileEvent
    data class UpdateUserProfileData(val userProfileResponse: UserProfileResponse) :
        UserProfileEvent
}

enum class ProfileNavigationScreen(
    val icon: ImageVector,
    val contentDescription: String,
    val labelId: Int
) {
    BASIC(
        icon = Icons.Outlined.AccountCircle,
        contentDescription = "Profile Screen 1",
        labelId = R.string.details
    ),

    Physique(
        icon = Icons.Outlined.Face,
        contentDescription = "Profile Screen 2",
        labelId = R.string.physique
    ),

    Health(
        icon = Icons.Outlined.Favorite,
        contentDescription = "Profile Screen 3",
        labelId = R.string.health
    ),

    Lifestyle(
        icon = Icons.Default.Emergency,
        contentDescription = "Profile Screen 4",
        labelId = R.string.lifestyle
    ),

    Diet(
        icon = Icons.Outlined.Egg,
        contentDescription = "Profile Screen 2",
        labelId = R.string.diet
    )
}
