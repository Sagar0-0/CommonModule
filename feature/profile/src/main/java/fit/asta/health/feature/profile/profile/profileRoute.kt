package fit.asta.health.feature.profile.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.feature.profile.profile.ui.ProfileScreenState
import fit.asta.health.feature.profile.profile.ui.UserProfileUiEvent
import fit.asta.health.feature.profile.show.vm.ProfileViewModel

const val PROFILE_GRAPH_ROUTE = "graph_profile"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(PROFILE_GRAPH_ROUTE, navOptions)
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.profileRoute(navController: NavController) {
    composable(route = PROFILE_GRAPH_ROUTE) {
        val profileViewModel: ProfileViewModel = hiltViewModel()
        val userProfileState by profileViewModel.userProfileState.collectAsStateWithLifecycle()
        val submitProfileState by profileViewModel.submitProfileState.collectAsStateWithLifecycle()
        ProfileScreenState(
            userProfileState = userProfileState,
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
    }
}