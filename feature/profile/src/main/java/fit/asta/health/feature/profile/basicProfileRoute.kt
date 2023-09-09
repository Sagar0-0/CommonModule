package fit.asta.health.feature.profile

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.feature.profile.ui.BasicProfileEvent
import fit.asta.health.feature.profile.ui.BasicProfileScreen
import fit.asta.health.feature.profile.vm.BasicProfileViewModel

const val BASIC_PROFILE_GRAPH_ROUTE = "graph_basic_profile"

fun NavController.navigateToBasicProfile(navOptions: NavOptions? = null) {
    if (navOptions == null) {
        this.navigate(BASIC_PROFILE_GRAPH_ROUTE) {
            popUpToTop(this@navigateToBasicProfile)
        }
    } else {
        this.navigate(BASIC_PROFILE_GRAPH_ROUTE, navOptions)
    }
}

fun NavGraphBuilder.basicProfileRoute() {
    composable(BASIC_PROFILE_GRAPH_ROUTE) {
        val basicProfileViewModel: BasicProfileViewModel = hiltViewModel()

        val checkReferralCodeState by basicProfileViewModel.checkReferralCodeState.collectAsStateWithLifecycle()
        val createBasicProfileState by basicProfileViewModel.createBasicProfileState.collectAsStateWithLifecycle()
        val autoFetchedReferralCode by basicProfileViewModel.referralCode.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            if (autoFetchedReferralCode.isNotEmpty()) {
                basicProfileViewModel.checkReferralCode(autoFetchedReferralCode)
            }
        }

        val user = basicProfileViewModel.getUser()

        BasicProfileScreen(
            user = user,
            checkReferralCodeState = checkReferralCodeState,
            createBasicProfileState = createBasicProfileState,
            autoFetchedReferralCode = autoFetchedReferralCode,
            onEvent = {
                when (it) {
                    is BasicProfileEvent.Link -> {
                        basicProfileViewModel.linkWithCredentials(it.cred)
                    }

                    is BasicProfileEvent.CheckReferralCode -> {
                        basicProfileViewModel.checkReferralCode(it.code)
                    }

                    is BasicProfileEvent.CreateBasicProfile -> {
                        basicProfileViewModel.createBasicProfile(it.basicProfileDTO)
                    }

                    is BasicProfileEvent.ResetCodeState -> {
                        basicProfileViewModel.resetCheckCodeState()
                    }

                    is BasicProfileEvent.NavigateToHome -> {
                        basicProfileViewModel.navigateToHome()
                    }

                    is BasicProfileEvent.ResetCreateProfileState -> {
                        basicProfileViewModel.resetCreateProfileState()
                    }
                }
            }
        )


    }
}



