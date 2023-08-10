package fit.asta.health.splash

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.main.Graph
import fit.asta.health.onboarding.ui.navigateToOnboarding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.splashScreen(navController: NavController) {
    composable(Graph.Splash.route) {
        val authViewModel: AuthViewModel = hiltViewModel()
        val context = LocalContext.current
        if (!PrefManager.getOnboardingShownStatus(context)) {
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