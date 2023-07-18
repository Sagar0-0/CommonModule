package fit.asta.health.tools.sunlight.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.tools.sunlight.view.*
import fit.asta.health.tools.sunlight.view.age_selection.AgeRange
import fit.asta.health.tools.sunlight.view.home.StatedStateComposable
import fit.asta.health.tools.sunlight.view.home.SunlightHomeScreen
import fit.asta.health.tools.sunlight.view.skin_exposure.SkinExposureLayout
import fit.asta.health.tools.sunlight.view.skincolor_selection.SkinColorLayout
import fit.asta.health.tools.sunlight.view.spf_selection.SPFLevel
import fit.asta.health.tools.sunlight.viewmodel.SunlightViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SunlightNavigation(navController: NavHostController, homeViewModel: SunlightViewModel) {
    NavHost(navController, startDestination = SunlightScreen.SunlightHomeScreen.route) {

        composable(route = SunlightScreen.SunlightHomeScreen.route) {
            SunlightHomeScreen(navController = navController, homeViewModel)
        }
        composable(route = SunlightScreen.SkinExposureScreen.route) {
            SkinExposureLayout(navController = navController, homeViewModel)
        }
        composable(route = SunlightScreen.SkinColorScreen.route) {
            SkinColorLayout(navController = navController, homeViewModel)
        }
        composable(route = SunlightScreen.SPFSelectionScreen.route) {
            SPFLevel(navController = navController, homeViewModel)
        }
        composable(route = SunlightScreen.AgeSelectionScreen.route) {
            AgeRange(navController = navController, homeViewModel)
        }
        composable(route = SunlightScreen.StartedStateComposable.route) {
            StatedStateComposable(navController = navController, homeViewModel)
        }
    }
}