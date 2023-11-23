package fit.asta.health.tools.walking.nav

import android.content.Intent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.tools.walking.progress.ProgressViewModel
import fit.asta.health.tools.walking.progress.StepsActivityScreen
import fit.asta.health.tools.walking.progress.StepsPermissionScreen
import fit.asta.health.tools.walking.progress.StepsScreen
import fit.asta.health.tools.walking.service.StepCounterService

const val STEPS_GRAPH_ROUTE = "steps_graph_address"

fun NavController.navigateToStepsCounter(navOptions: NavOptions? = null) {
    this.navigate(STEPS_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.stepsCounterNavigation(
    navController: NavHostController, onBack: () -> Unit
) {

    navigation(
        route = STEPS_GRAPH_ROUTE,
        startDestination = StepsCounterScreen.StepsPermissionScreen.route
    ) {
        composable(route = StepsCounterScreen.StepsPermissionScreen.route) {
//            val progressViewModel: ProgressViewModel = it.sharedViewModel(navController)
            StepsPermissionScreen {
                navController.popBackStack()
                navController.navigate(StepsCounterScreen.StepsCounterHomeScreen.route)
            }
        }
        composable(route = StepsCounterScreen.StepsCounterHomeScreen.route) {
            val progressViewModel: ProgressViewModel = it.sharedViewModel(navController)
            val state by progressViewModel.progressHome.collectAsStateWithLifecycle()
            val list by progressViewModel.sheetDataList.collectAsStateWithLifecycle()
            val context = LocalContext.current
            LaunchedEffect(key1 = Unit, block = {
                progressViewModel.startProgressHome(context)
            })
            StepsScreen(state = state, list = list, onStart = {
                progressViewModel.startSession()
                context.startService(Intent(context, StepCounterService::class.java))
                navController.navigate(StepsCounterScreen.StepsProgressScreen.route)
            }) {
                onBack()
            }
        }
        composable(route = StepsCounterScreen.StepsProgressScreen.route) {
            val progressViewModel: ProgressViewModel = it.sharedViewModel(navController)
            val state by progressViewModel.progress.collectAsStateWithLifecycle()
            val context = LocalContext.current
            StepsActivityScreen(
                state = state,
                onPause = { progressViewModel.pause() },
                onResume = { progressViewModel.resume() },
                onStop = {
                    progressViewModel.stop()
                    context.stopService(Intent(context, StepCounterService::class.java))
                    navController.popBackStack()
                })
        }
    }
}