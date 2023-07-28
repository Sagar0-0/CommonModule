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
import fit.asta.health.main.ui.mainActivityComp
import fit.asta.health.navigation.home.view.component.ErrorScreenLayout
import fit.asta.health.profile.CreateProfileLayout
import fit.asta.health.profile.ProfileContent
import fit.asta.health.scheduler.navigation.schedulerNavigation
import fit.asta.health.settings.ui.SettingsNavigation
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
            ErrorScreenLayout {

            }
        }
    }

    NavHost(
        navController = navController,
        route = Graph.ROOT.route,
        startDestination = Graph.Home.route
    ) {

        mainActivityComp(navController=navController)
        composable(route = Graph.Profile.route) {
            ProfileContent(
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(Graph.CreateProfile.route) })
        }
        composable(route = Graph.CreateProfile.route) {
            CreateProfileLayout(onBack = { navController.popBackStack() })
        }
        SettingsNavigation(navController)
        breathingNavigation(navController, onBack = { navController.navigateUp() })
        waterToolNavigation(navController, onBack = { navController.navigateUp() })
        meditationNavigation(navController, onBack = { navController.navigateUp() })
        sunlightNavigation(navController, onBack = { navController.navigateUp() })
//        sleepNavGraph(navController,  onBack = { navController.navigateUp() })
        exerciseNavigation(navController, onBack = { navController.navigateUp() })
        testimonialsNavigation(navController)
        schedulerNavigation(navController, onBack = {navController.navigateUp()})
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