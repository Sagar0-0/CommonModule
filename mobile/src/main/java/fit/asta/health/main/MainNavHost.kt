package fit.asta.health.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fit.asta.health.auth.view.authScreens
import fit.asta.health.common.maps.view.addressScreens
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.feedback.ui.feedbackRoute
import fit.asta.health.main.view.homeScreen
import fit.asta.health.onboarding.ui.onboardingRoute
import fit.asta.health.payments.referral.view.referralScreens
import fit.asta.health.payments.sub.view.subscriptionScreens
import fit.asta.health.payments.wallet.view.walletScreen
import fit.asta.health.profile.CreateProfileLayout
import fit.asta.health.profile.ProfileContent
import fit.asta.health.scheduler.navigation.schedulerNavigation
import fit.asta.health.settings.view.settingScreens
import fit.asta.health.settings.webView
import fit.asta.health.splash.splashScreen
import fit.asta.health.testimonials.testimonialsNavigation
import fit.asta.health.tools.breathing.nav.breathingNavigation
import fit.asta.health.tools.exercise.nav.exerciseNavigation
import fit.asta.health.tools.meditation.nav.meditationNavigation
import fit.asta.health.tools.sunlight.nav.sunlightNavigation
import fit.asta.health.tools.water.nav.waterToolNavigation
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun MainNavHost(isConnected: Boolean) {
    val navController = rememberNavController()
    if (!isConnected) {
        Box(modifier = Modifier.fillMaxSize()) {
            AppErrorScreen {}
        }
    }

    NavHost(
        navController = navController,
        route = Graph.ROOT.route,
        startDestination = Graph.Splash.route
    ) {
        splashScreen(navController)
        onboardingRoute(navController)
        authScreens(navController)
        homeScreen(navController)

        composable(route = Graph.Profile.route) {
            ProfileContent(onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(Graph.CreateProfile.route) })
        }
        composable(route = Graph.CreateProfile.route) {
            CreateProfileLayout(onBack = { navController.popBackStack() })
        }

        breathingNavigation(navController, onBack = { navController.navigateUp() })
        waterToolNavigation(navController, onBack = { navController.navigateUp() })
        meditationNavigation(navController, onBack = { navController.navigateUp() })
        sunlightNavigation(navController, onBack = { navController.navigateUp() })
        //sleepNavGraph(navController,  onBack = { navController.navigateUp() })
        exerciseNavigation(navController, onBack = { navController.navigateUp() })
        testimonialsNavigation(navController)
        schedulerNavigation(navController, onBack = { navController.navigateUp() })

        settingScreens(navController)
        feedbackRoute(navController)
        addressScreens(navController)

        subscriptionScreens(navController)
        referralScreens(navController)
        walletScreen(navController)
        webView()
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}