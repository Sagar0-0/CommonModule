package fit.asta.health.onboarding.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.auth.ui.navigateToAuth
import fit.asta.health.auth.ui.vm.AuthViewModel
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.main.Graph
import fit.asta.health.onboarding.ui.components.OnboardingScreen
import fit.asta.health.onboarding.ui.vm.OnboardingViewModel

const val ONBOARDING_GRAPH_ROUTE = "graph_onboarding"
fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) {
    if (navOptions == null) {
        this.navigate(ONBOARDING_GRAPH_ROUTE) {
            popUpToTop(this@navigateToOnboarding)
        }
    } else {
        this.navigate(ONBOARDING_GRAPH_ROUTE, navOptions)
    }
}

fun NavGraphBuilder.onboardingRoute(navController: NavController) {
    composable(ONBOARDING_GRAPH_ROUTE) {
        val onboardingViewModel: OnboardingViewModel = hiltViewModel()
        val onboardingShown by onboardingViewModel.onboardingShown.collectAsStateWithLifecycle()
        val state by onboardingViewModel.state.collectAsStateWithLifecycle()

        OnboardingScreen(
            state = state,
            onReload = onboardingViewModel::getData,
            onFinish = {
                onboardingViewModel.dismissOnboarding()
                navController.navigateToAuth()
            }
        )

        LaunchedEffect(onboardingShown){
            if(onboardingShown) {
                if (!onboardingViewModel.isAuthenticated()) {
                    navController.navigateToAuth()
                } else {
                    navController.navigate(Graph.Home.route) {
                        popUpToTop(navController)
                    }
                }
            }
        }

    }
}