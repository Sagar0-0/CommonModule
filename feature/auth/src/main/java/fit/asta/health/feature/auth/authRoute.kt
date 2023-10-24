package fit.asta.health.feature.auth

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.feature.auth.screens.AuthScreen
import fit.asta.health.feature.auth.screens.AuthUiEvent
import fit.asta.health.feature.auth.screens.OtpVerificationScreen
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
        val authViewModel: AuthViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            authViewModel.getOnboardingData()
        }

        val loginState by authViewModel.loginState.collectAsStateWithLifecycle()
        val onboardingState by authViewModel.onboardingDatState.collectAsStateWithLifecycle()

        AuthScreen(
            loginState = loginState,
            onboardingState = onboardingState,
            onNavigate = { navController.navigate(it) }
        ) {
            when (it) {
                AuthUiEvent.OnLoginFailed -> {
                    authViewModel.onLoginFailed()
                }

                is AuthUiEvent.NavigateToWebView -> {
                    navigateToWebView(it.url)
                }

                is AuthUiEvent.SignInWithCredentials -> {
                    authViewModel.signInAndNavigate(it.authCredential)
                }

                is AuthUiEvent.GetOnboardingData -> {
                    authViewModel.getOnboardingData()
                }
            }
        }
    }


    composable(AUTH_OTP_VERIFICATION_ROUTE) {
        OtpVerificationScreen()
    }
}

