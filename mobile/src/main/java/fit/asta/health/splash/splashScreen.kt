package fit.asta.health.splash

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.main.Graph

fun NavGraphBuilder.splashScreen(navController: NavController) {
    composable(Graph.Splash.route) {
        val authViewModel: AuthViewModel = hiltViewModel()
        val context = LocalContext.current
        if (!PrefUtils.getOnboardingShownStatus(context)) {
            navController.navigate(Graph.Onboarding.route) {
                popUpToTop(navController)
            }
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