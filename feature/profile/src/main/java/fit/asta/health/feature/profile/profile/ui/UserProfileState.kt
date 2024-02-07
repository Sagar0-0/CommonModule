@file:OptIn(ExperimentalFoundationApi::class)

package fit.asta.health.feature.profile.profile.ui

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun rememberUserProfileState(
    pagerState: PagerState = rememberPagerState {
        ProfileNavigationScreen.entries.size
    },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavController
): UserProfileState {

    var isConfirmDialogVisible by rememberSaveable { mutableStateOf(false) }

    return remember(
        pagerState,
        coroutineScope,
        navController,
        isConfirmDialogVisible
    ) {
        UserProfileState(
            pagerState,
            coroutineScope,
            navController,
            isConfirmDialogVisible,
            { isConfirmDialogVisible = it }
        )
    }
}

@Stable
class UserProfileState(
    val pagerState: PagerState,
    private val coroutineScope: CoroutineScope,
    private val navController: NavController,
    val isConfirmDialogVisible: Boolean,
    val changeConfirmDialogVisible: (Boolean) -> Unit
) {
    val currentPageIndex: Int
        get() = pagerState.currentPage


    fun updateCurrentPageIndex(index: Int) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(index)
        }
    }

    fun onBackPressed() {
        if (!isConfirmDialogVisible) {
            changeConfirmDialogVisible(true)
        } else {
            changeConfirmDialogVisible(false)
            navController.popBackStack()
        }
    }

    val profileDataPages: List<ProfileNavigationScreen>
        get() = ProfileNavigationScreen.entries

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
