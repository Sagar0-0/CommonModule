package fit.asta.health.splash

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.main.Graph
import fit.asta.health.onboarding.ui.navigateToOnboarding
import fit.asta.health.onboarding.ui.vm.OnboardingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.splashScreen(navController: NavController) {
    composable(Graph.Splash.route) {
        val authViewModel: AuthViewModel = hiltViewModel()
        val onboardingViewModel: OnboardingViewModel = hiltViewModel()
        val onboardingShown by onboardingViewModel.onboardingState.collectAsStateWithLifecycle()
        if (!onboardingShown) {
            navController.navigateToOnboarding(
                navOptions {
                    popUpToTop(navController)
                }
            )
        } else {
            if (!authViewModel.isAuthenticated()) {
                navController.navigate(Graph.Authentication.route) {
                    popUpToTop(navController)
                }
            } else {
                navController.navigate(Graph.Home.route) {
                    popUpToTop(navController)
                }
            }

        }

    }
}