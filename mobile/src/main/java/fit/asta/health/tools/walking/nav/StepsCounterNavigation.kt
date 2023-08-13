package fit.asta.health.tools.walking.nav

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import fit.asta.health.main.Graph
import fit.asta.health.main.deepLinkUrl
import fit.asta.health.tools.walking.view.goals.GoalsScreen
import fit.asta.health.tools.walking.view.home.StepsHomeScreen
import fit.asta.health.tools.walking.view.steps_counter.StepsCounterScreen
import fit.asta.health.tools.walking.view.walking_types.WalkingtypesScreen
import fit.asta.health.tools.walking.viewmodel.WalkingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun  StepsCounterNavigation(navController: NavHostController,homeViewModel: WalkingViewModel) {

    NavHost(navController, startDestination = StepsCounterScreen.StepsCounterHomeScreen.route) {

        composable(StepsCounterScreen.StepsCounterHomeScreen.route,
            deepLinks = listOf(navDeepLink {
                uriPattern = "$deepLinkUrl/${Graph.WalkingTool.route}"
                action = Intent.ACTION_VIEW
            })
        ) {
            StepsHomeScreen(navController = navController, homeViewModel)
        }
        composable(StepsCounterScreen.TypesScreen.route) {
            WalkingtypesScreen(navController = navController, homeViewModel)
        }
        composable(StepsCounterScreen.GoalScreen.route) {
            GoalsScreen(navController = navController, homeViewModel)
        }
        composable(StepsCounterScreen.DistanceScreen.route) {
            StepsCounterScreen(navController = navController, homeViewModel)
        }
    }
}