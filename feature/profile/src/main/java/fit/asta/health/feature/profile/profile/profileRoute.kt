package fit.asta.health.feature.profile.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.feature.profile.profile.ui.UserProfileContent
import fit.asta.health.feature.profile.profile.ui.rememberUserProfileState
import fit.asta.health.feature.profile.profile.utils.UserProfileEvent
import fit.asta.health.feature.profile.show.vm.ProfileViewModel

const val PROFILE_GRAPH_ROUTE = "graph_profile"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(PROFILE_GRAPH_ROUTE, navOptions)
}

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.profileRoute(navController: NavController) {
    composable(route = PROFILE_GRAPH_ROUTE) {
        val profileViewModel: ProfileViewModel = hiltViewModel()
        val userProfileResponseState by profileViewModel.userProfileState.collectAsStateWithLifecycle()
        val submitProfileState by profileViewModel.submitProfileState.collectAsStateWithLifecycle()
        val healthPropertiesState by profileViewModel.healthPropertiesState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = Unit) {
            if (userProfileResponseState !is UiState.Success) {
                profileViewModel.loadUserProfile()
            }
        }

        val userProfileState = rememberUserProfileState(
            userProfileResponse = (userProfileResponseState as? UiState.Success)?.data
                ?: UserProfileResponse(),
            healthPropertiesState = healthPropertiesState,
            submitProfileState = submitProfileState,
            navController = navController,
            onEvent = { event ->
                when (event) {
                    is UserProfileEvent.UpdateUserProfileData -> {
                        profileViewModel.saveProfileData(event.userProfileResponse)
                    }

                    is UserProfileEvent.GetHealthProperties -> {
                        profileViewModel.getHealthProperties(event.id)
                    }

                    is UserProfileEvent.ResetHealthProperties -> {
                        profileViewModel.resetHealthProperties()
                    }
                }
            }
        )

        AppUiStateHandler(
            uiState = userProfileResponseState,
            onErrorRetry = {
                profileViewModel.loadUserProfile()
            },
            onErrorMessage = {
                userProfileState.onBackPressed()
            }
        ) {
            UserProfileContent(
                userProfileState = userProfileState
            )
        }
    }
}