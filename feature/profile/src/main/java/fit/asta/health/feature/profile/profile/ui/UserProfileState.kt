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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
    onSaveData: () -> Unit = {},
    onDataChange: (UserProfileResponse) -> Unit = {}
): UserProfileState {

    var isConfirmDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isAnythingChanged by rememberSaveable { mutableStateOf(false) }
    var profileImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    LaunchedEffect(submitProfileState) {
        if (submitProfileState is UiState.Success) {
            isAnythingChanged = false
            isConfirmDialogVisible = false
        }
    }

    return remember(
        userProfileResponse,
        submitProfileState,
        pagerState,
        coroutineScope,
        navController,
        isConfirmDialogVisible,
        isAnythingChanged,
        profileImageUri
    ) {
        UserProfileState(
            submitProfileState = submitProfileState,
            _userProfileResponse = userProfileResponse,
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            navController = navController,
            _isConfirmDialogVisible = isConfirmDialogVisible,
            _isAnythingChanged = isAnythingChanged,
            _profileImageUri = profileImageUri,
            onEvent = { event ->
                when (event) {
                    is UserProfileEvent.UpdateConfirmDialogState -> {
                        isConfirmDialogVisible = event.newValue
                    }

                    is UserProfileEvent.UpdateProfileUri -> {
                        profileImageUri = event.uri
                    }

                    is UserProfileEvent.UpdateAnythingChanged -> {
                        isAnythingChanged = event.newValue
                    }

                    is UserProfileEvent.UpdateUserProfileData -> {
                        onDataChange(event.userProfileResponse)
                    }

                    is UserProfileEvent.SaveData -> {
                        onSaveData()
                    }
                }
            }
        )
    }
}

@Stable
class UserProfileState(
    val submitProfileState: UiState<SubmitProfileResponse>,
    private val _userProfileResponse: UserProfileResponse,
    val pagerState: PagerState,
    private val coroutineScope: CoroutineScope,
    private val navController: NavController,
    private val _isAnythingChanged: Boolean,
    private val _profileImageUri: Uri?,
    private val _isConfirmDialogVisible: Boolean,
    private val onEvent: (UserProfileEvent) -> Unit,
) {

    var userProfileResponse: UserProfileResponse
        get() = _userProfileResponse
        set(value) {
            if (_userProfileResponse != value) {
                isAnythingChanged = true
            }
            onEvent(UserProfileEvent.UpdateUserProfileData(value))
        }

    var currentPageIndex: Int
        get() = pagerState.currentPage
        set(value) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(value)
            }
        }


    private var isAnythingChanged: Boolean
        get() = _isAnythingChanged
        set(value) {
            onEvent(UserProfileEvent.UpdateAnythingChanged(value))
        }

    var isConfirmDialogVisible: Boolean
        get() = _isConfirmDialogVisible
        set(value) {
            onEvent(UserProfileEvent.UpdateConfirmDialogState(value))
        }

    var profileImageUri: Uri?
        get() = _profileImageUri
        set(value) {
            onEvent(UserProfileEvent.UpdateProfileUri(value))
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
        TODO("Not yet implemented")
    }

    fun updateName(it: String) {
        TODO("Not yet implemented")
    }

    val profileDataPages: List<ProfileNavigationScreen>
        get() = ProfileNavigationScreen.entries

}


sealed interface UserProfileEvent {
    data class UpdateProfileUri(val uri: Uri?) : UserProfileEvent
    data class UpdateConfirmDialogState(val newValue: Boolean) : UserProfileEvent
    data class UpdateAnythingChanged(val newValue: Boolean) : UserProfileEvent
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
