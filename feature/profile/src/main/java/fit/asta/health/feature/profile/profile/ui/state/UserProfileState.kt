@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
        userProfileResponse,
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
        userProfileResponse,
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
        userProfileResponse,
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

    val dietScreenState = rememberSaveable(
        userProfileResponse,
        saver = DietScreenState
            .Saver(
                coroutineScope, onEvent
            )
    ) {
        DietScreenState(
            diet = userProfileResponse.diet,
            coroutineScope = coroutineScope,
            onEvent = onEvent
        )
    }

    val basicDetailScreenState = rememberSaveable(
        userProfileResponse,
        saver = BasicDetailScreenState.Saver(onEvent)
    ) {
        BasicDetailScreenState(
            basicDetail = userProfileResponse.basicDetail,
            onEvent = onEvent
        )
    }

    return rememberSaveable(
        pagerState,
        coroutineScope,
        navController,
        onEvent,
//        saver = UserProfileState.Saver(
//            basicDetailScreenState,
//            healthScreenState,
//            lifestyleScreenState,
//            physiqueScreenState,
//            dietScreenState,
//            pagerState,
//            coroutineScope,
//            navController,
//            onEvent
//        )
    ) {
        UserProfileState(
            basicDetailScreenState = basicDetailScreenState,
            healthScreenState = healthScreenState,
            lifestyleScreenState = lifestyleScreenState,
            physiqueScreenState = physiqueScreenState,
            dietScreenState = dietScreenState,
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            navController = navController,
            onEvent = onEvent
        )
    }
}

@Stable
class UserProfileState(
    val basicDetailScreenState: BasicDetailScreenState,
    val healthScreenState: HealthScreenState,
    val lifestyleScreenState: LifestyleScreenState,
    val physiqueScreenState: PhysiqueScreenState,
    val dietScreenState: DietScreenState,
    val pagerState: PagerState,
    private val coroutineScope: CoroutineScope,
    private val navController: NavController,
    private val onEvent: (UserProfileEvent) -> Unit,
) {
    var currentPageIndex: Int
        get() = pagerState.currentPage
        set(value) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(value)
            }
        }
    val profileDataPages: List<ProfileNavigationScreen>
        get() = ProfileNavigationScreen.entries


    @OptIn(ExperimentalMaterial3Api::class)
    fun openSheet(bottomSheetState: SheetState, bottomSheetVisible: MutableState<Boolean>) {
        bottomSheetVisible.value = true
        coroutineScope.launch {
            bottomSheetState.expand()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun closeSheet(bottomSheetState: SheetState, bottomSheetVisible: MutableState<Boolean>) {
        bottomSheetVisible.value = false
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    fun onBackPressed() {
        navController.popBackStack()
    }

    fun navigateToOrders() {
        onEvent(UserProfileEvent.NavigateToOrders)
    }

    fun navigateToWallet() {
        onEvent(UserProfileEvent.NavigateToWallet)
    }

    fun navigateToSubscriptions() {
        onEvent(UserProfileEvent.NavigateToSubscription)
    }

    companion object {
//        fun Saver(
//            basicDetailScreenState: BasicDetailScreenState,
//            healthScreenState: HealthScreenState,
//            lifestyleScreenState: LifestyleScreenState,
//            physiqueScreenState: PhysiqueScreenState,
//            dietScreenState: DietScreenState,
//            pagerState: PagerState,
//            coroutineScope: CoroutineScope,
//            navController: NavController,
//            onEvent: (UserProfileEvent) -> Unit
//        ): Saver<UserProfileState, *> = listSaver(
//            save = {
//                listOf()
//            },
//            restore = {
//                UserProfileState(
//                    basicDetailScreenState = basicDetailScreenState,
//                    healthScreenState = healthScreenState,
//                    lifestyleScreenState = lifestyleScreenState,
//                    physiqueScreenState = physiqueScreenState,
//                    dietScreenState = dietScreenState,
//                    pagerState = pagerState,
//                    coroutineScope = coroutineScope,
//                    navController = navController,
//                    onEvent = onEvent
//                )
//            }
//        )
    }

    data class ProfileBottomSheetPicker(
        val fieldName: String,
        val getQueryParam: String,
        val name: String,
        val list: SnapshotStateList<UserProperties>
    )
}