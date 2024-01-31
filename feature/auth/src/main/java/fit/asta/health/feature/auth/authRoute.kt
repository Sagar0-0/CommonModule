package fit.asta.health.feature.auth

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.feature.auth.screens.AuthPhoneSignInScreen
import fit.asta.health.feature.auth.screens.AuthScreenControl
import fit.asta.health.feature.auth.screens.AuthUiEvent
import fit.asta.health.feature.auth.screens.PhoneAuthUiEvent
import fit.asta.health.feature.auth.vm.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val AUTH_GRAPH_ROUTE = "graph_auth"
const val AUTH_OTP_VERIFICATION_ROUTE = "auth_otp_verification_route"
fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    if (navOptions == null) {
        this.navigate(AUTH_GRAPH_ROUTE) {
            popUpToTop(this@navigateToAuth)
        }
    } else {
        this.navigate(AUTH_GRAPH_ROUTE, navOptions)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.authRoute(
    navController: NavController,
    navigateToWebView: (String) -> Unit
) {

    composable(AUTH_GRAPH_ROUTE) {
        val authViewModel: AuthViewModel = it.sharedViewModel(navController = navController)
        LaunchedEffect(Unit) {
            authViewModel.getOnboardingData()
        }

        val loginState by authViewModel.loginState.collectAsStateWithLifecycle()
        val onboardingState by authViewModel.onboardingDatState.collectAsStateWithLifecycle()

        AuthScreenControl(
            loginState = loginState,
            onboardingState = onboardingState,
            onNavigate = { destination -> navController.navigate(destination) }
        ) { event ->
            when (event) {
                AuthUiEvent.OnLoginFailed -> {
                    authViewModel.onLoginFailed()
                }

                is AuthUiEvent.NavigateToWebView -> {
                    navigateToWebView(event.url)
                }

                is AuthUiEvent.SignInWithCredentials -> {
                    authViewModel.signInAndNavigate(event.authCredential)
                }

                is AuthUiEvent.GetOnboardingData -> {
                    authViewModel.getOnboardingData()
                }
            }
        }
    }


    composable(AUTH_OTP_VERIFICATION_ROUTE) {
        val authViewModel: AuthViewModel = it.sharedViewModel(navController = navController)
        val loginState by authViewModel.loginState.collectAsStateWithLifecycle()
        AuthPhoneSignInScreen(loginState = loginState) { event ->
            when (event) {
                PhoneAuthUiEvent.OnLoginFailed -> {
                    authViewModel.onLoginFailed()
                }

                is PhoneAuthUiEvent.SignInWithCredentials -> {
                    authViewModel.signInAndNavigate(event.authCredential)
                }

                else -> {}
            }
        }
    }
}

