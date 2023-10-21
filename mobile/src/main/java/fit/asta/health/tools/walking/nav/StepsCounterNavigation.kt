package fit.asta.health.tools.walking.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.tools.walking.view.goals.GoalsScreen
import fit.asta.health.tools.walking.view.home.StepsHomeScreen
import fit.asta.health.tools.walking.view.steps_counter.StepsCounterScreen
import fit.asta.health.tools.walking.view.walking_types.WalkingTypesScreen
import fit.asta.health.tools.walking.viewmodel.WalkingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun  StepsCounterNavigation(navController: NavHostController,homeViewModel: WalkingViewModel) {

    NavHost(navController, startDestination = StepsCounterScreen.StepsCounterHomeScreen.route) {

        composable(StepsCounterScreen.StepsCounterHomeScreen.route) {
            StepsHomeScreen(navController = navController, homeViewModel)
        }
        composable(StepsCounterScreen.TypesScreen.route) {
            WalkingTypesScreen(navController = navController, homeViewModel)
        }
        composable(StepsCounterScreen.GoalScreen.route) {
            GoalsScreen(navController = navController, homeViewModel)
        }
        composable(StepsCounterScreen.DistanceScreen.route) {
            StepsCounterScreen(navController = navController, homeViewModel)
        }
    }
}