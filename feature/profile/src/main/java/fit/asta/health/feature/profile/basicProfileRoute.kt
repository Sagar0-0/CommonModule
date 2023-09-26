package fit.asta.health.feature.profile

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.feature.profile.basic.ui.BasicProfileEvent
import fit.asta.health.feature.profile.basic.ui.BasicProfileScreen
import fit.asta.health.feature.profile.basic.vm.BasicProfileViewModel

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
        val linkAccountState by basicProfileViewModel.linkAccountState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            if (autoFetchedReferralCode.isNotEmpty()) {
                basicProfileViewModel.checkReferralCode(autoFetchedReferralCode)
            }
        }
        var user by remember { mutableStateOf(basicProfileViewModel.getUser()) }

        LaunchedEffect(linkAccountState) {
            if (linkAccountState is UiState.Success) {
                user = basicProfileViewModel.getUser()
            }
        }

        BasicProfileScreen(
            user = user,
            checkReferralCodeState = checkReferralCodeState,
            linkAccountState = linkAccountState,
            createBasicProfileState = createBasicProfileState,
            autoFetchedReferralCode = autoFetchedReferralCode,
            onEvent = {
                when (it) {
                    is BasicProfileEvent.ResetLinkAccountState -> {
                        basicProfileViewModel.resetLinkAccountState()
                    }

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