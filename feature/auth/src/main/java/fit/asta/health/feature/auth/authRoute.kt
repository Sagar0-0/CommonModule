package fit.asta.health.feature.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.auth.model.domain.User
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.feature.auth.screens.AuthEvent
import fit.asta.health.feature.auth.screens.AuthScreen
import fit.asta.health.feature.auth.vm.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val AUTH_GRAPH_ROUTE = "graph_auth"
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
    navigateToWebView: (String) -> Unit
) {
    composable(AUTH_GRAPH_ROUTE) {
        val context = LocalContext.current
        val authViewModel: AuthViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            if (authViewModel.isAuthenticated()) {
                Toast.makeText(context, "Complete Basic Profile to continue!", Toast.LENGTH_SHORT)
                    .show()
                authViewModel.navigateToBasicProfile()
            }
        }

        val loginState by authViewModel.loginState.collectAsStateWithLifecycle()
        val isProfileAvailable by authViewModel.isProfileAvailable.collectAsStateWithLifecycle()

        when (isProfileAvailable) {
            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    if ((isProfileAvailable as UiState.Success<Boolean>).data) {
                        Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show()
                        authViewModel.navigateToHome()
                    } else {
                        Toast.makeText(
                            context,
                            "Complete Basic Profile to continue!",
                            Toast.LENGTH_SHORT
                        ).show()
                        authViewModel.navigateToBasicProfile()
                    }
                }
            }

            is UiState.Loading -> {
                LoadingAnimation()
            }

            is UiState.ErrorMessage -> {
                Log.e(
                    "TAG",
                    "authRoute: ${(isProfileAvailable as UiState.ErrorMessage).resId.toStringFromResId()}"
                )
            }

            else -> {}
        }

        val checkProfileAndNavigate: (User) -> Unit = { authViewModel.isProfileAvailable(it.uid) }


        AuthScreen(
            loginState = loginState
        ) {
            when (it) {
                AuthEvent.ResetLoginState -> {
                    authViewModel.resetLoginState()
                }

                is AuthEvent.NavigateToWebView -> {
                    navigateToWebView(it.url)
                }

                is AuthEvent.SignInWithCredentials -> {
                    authViewModel.signInWithGoogleCredentials(it.authCredential)
                }

                is AuthEvent.CheckProfileAndNavigate -> {
                    checkProfileAndNavigate(it.user)
                }
            }
        }
    }
}

