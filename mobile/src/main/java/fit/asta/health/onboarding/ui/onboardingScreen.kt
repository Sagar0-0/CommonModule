package fit.asta.health.onboarding.ui

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.main.Graph
import fit.asta.health.onboarding.vm.OnboardingViewModel

fun NavGraphBuilder.onboardingScreen(navController: NavController) {
    composable(Graph.Onboarding.route) {
        val onboardingViewModel: OnboardingViewModel = hiltViewModel()
        val authViewModel: AuthViewModel = hiltViewModel()
        val state = onboardingViewModel.state.collectAsStateWithLifecycle().value
        val context = LocalContext.current
        OnBoardingPager(
            state = state,
            onReload = onboardingViewModel::getData,
            onFinish = {
                PrefUtils.setOnboardingShownStatus(true, context)
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
        )
    }

}