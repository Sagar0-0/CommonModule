package fit.asta.health.feature.profile.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.feature.profile.profile.ui.UserProfileScreen
import fit.asta.health.feature.profile.profile.ui.UserProfileUiEvent
import fit.asta.health.feature.profile.profile.ui.rememberUserProfileState
import fit.asta.health.feature.profile.show.vm.ProfileViewModel

const val PROFILE_GRAPH_ROUTE = "graph_profile"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(PROFILE_GRAPH_ROUTE, navOptions)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun NavGraphBuilder.profileRoute(navController: NavController) {
    composable(route = PROFILE_GRAPH_ROUTE) {
        val profileViewModel: ProfileViewModel = hiltViewModel()
        val userProfileResponseState by profileViewModel.userProfileState.collectAsStateWithLifecycle()
        val submitProfileState by profileViewModel.submitProfileState.collectAsStateWithLifecycle()

        val userProfileState = rememberUserProfileState(
            navController = navController
        )
        UserProfileScreen(
            userProfileState = userProfileState,
            userProfileResponseState = userProfileResponseState,
            submitProfileState = submitProfileState
        ) {
            when (it) {
                UserProfileUiEvent.LoadUserProfile -> {
                    profileViewModel.loadUserProfile()
                }

                UserProfileUiEvent.OnBack -> {
                    navController.popBackStack()
                }
            }
        }
//        CreateProfileLayout {
//            navController.popBackStack()
//        }
    }
}