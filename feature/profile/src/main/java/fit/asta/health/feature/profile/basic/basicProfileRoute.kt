package fit.asta.health.feature.profile.basic

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.feature.auth.screens.AuthPhoneSignInScreen
import fit.asta.health.feature.auth.screens.PhoneAuthUiEvent
import fit.asta.health.feature.profile.basic.ui.BasicProfileEvent
import fit.asta.health.feature.profile.basic.ui.BasicProfileScreenUi
import fit.asta.health.feature.profile.basic.vm.BasicProfileViewModel

const val BASIC_PROFILE_GRAPH_ROUTE = "graph_basic_profile"
const val BASIC_PROFILE_PHONE_AUTH_SCREEN = "basic_profile_phone_auth_screen"
const val BASIC_PROFILE_CREATE_SCREEN = "basic_profile_create_screen"

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
    composable(
        route = BASIC_PROFILE_GRAPH_ROUTE
    ) {
        val basicProfileViewModel: BasicProfileViewModel = hiltViewModel()

        val checkReferralCodeState by basicProfileViewModel.checkReferralCodeState.collectAsStateWithLifecycle()
        val createBasicProfileState by basicProfileViewModel.createBasicProfileState.collectAsStateWithLifecycle()
        val autoFetchedReferralCode by basicProfileViewModel.referralCode.collectAsStateWithLifecycle()
        val linkAccountState by basicProfileViewModel.linkAccountState.collectAsStateWithLifecycle()

        LaunchedEffect(autoFetchedReferralCode) {
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

        val navController = rememberNavController()
        NavHost(
            navController = navController,
            route = BASIC_PROFILE_GRAPH_ROUTE,
            startDestination = BASIC_PROFILE_CREATE_SCREEN
        ) {
            composable(BASIC_PROFILE_CREATE_SCREEN) {
                BasicProfileScreenUi(
                    user = user,
                    checkReferralCodeState = checkReferralCodeState,
                    createBasicProfileState = createBasicProfileState,
                    autoFetchedReferralCode = autoFetchedReferralCode,
                    onEvent = { event ->
                        when (event) {
                            BasicProfileEvent.NavigateToPhoneAuth -> {
                                navController.navigate(BASIC_PROFILE_PHONE_AUTH_SCREEN)
                            }

                            is BasicProfileEvent.ResetLinkAccountState -> {
                                basicProfileViewModel.resetLinkAccountState()
                            }

                            is BasicProfileEvent.Link -> {
                                basicProfileViewModel.linkWithCredentials(event.cred)
                            }

                            is BasicProfileEvent.CheckReferralCode -> {
                                basicProfileViewModel.checkReferralCode(event.code)
                            }

                            is BasicProfileEvent.CreateBasicProfile -> {
                                basicProfileViewModel.createBasicProfile(event.basicProfileDTO)
                            }

                            is BasicProfileEvent.ResetReferralCodeState -> {
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
            composable(BASIC_PROFILE_PHONE_AUTH_SCREEN) {
                AuthPhoneSignInScreen(
                    loginState = linkAccountState
                ) { event ->
                    when (event) {
                        PhoneAuthUiEvent.OnLoginFailed -> {
                            basicProfileViewModel.resetLinkAccountState()
                            navController.popBackStack()
                        }

                        is PhoneAuthUiEvent.SignInWithCredentials -> {
                            basicProfileViewModel.linkWithCredentials(event.authCredential)
                        }

                        is PhoneAuthUiEvent.OnAuthSuccess -> {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}