package fit.asta.health.tools.sunlight.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.main.Graph
import fit.asta.health.main.sharedViewModel
import fit.asta.health.tools.sunlight.view.*
import fit.asta.health.tools.sunlight.view.age_selection.AgeRange
import fit.asta.health.tools.sunlight.view.home.StatedStateComposable
import fit.asta.health.tools.sunlight.view.home.SunlightHomeScreen
import fit.asta.health.tools.sunlight.view.skin_exposure.SkinExposureLayout
import fit.asta.health.tools.sunlight.view.skincolor_selection.SkinColorLayout
import fit.asta.health.tools.sunlight.view.spf_selection.SPFLevel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.sunlightNavigation(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = Graph.SunlightTool.route,
        startDestination = SunlightScreen.SunlightHomeScreen.route
    ) {
        composable(route = SunlightScreen.SunlightHomeScreen.route) {
            SunlightHomeScreen(navController = navController, it.sharedViewModel(navController))
        }
        composable(route = SunlightScreen.SkinExposureScreen.route) {
            SkinExposureLayout(navController = navController, it.sharedViewModel(navController))
        }
        composable(route = SunlightScreen.SkinColorScreen.route) {
            SkinColorLayout(navController = navController, it.sharedViewModel(navController))
        }
        composable(route = SunlightScreen.SPFSelectionScreen.route) {
            SPFLevel(navController = navController, it.sharedViewModel(navController))
        }
        composable(route = SunlightScreen.AgeSelectionScreen.route) {
            AgeRange(navController = navController, it.sharedViewModel(navController))
        }
        composable(route = SunlightScreen.StartedStateComposable.route) {
            StatedStateComposable(navController = navController, it.sharedViewModel(navController))
        }
    }
}