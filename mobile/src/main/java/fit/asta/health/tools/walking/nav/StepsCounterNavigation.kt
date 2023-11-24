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
import fit.asta.health.meditation.view.other.SheetDataSelectionScreen
import fit.asta.health.tools.walking.service.StepCounterService
import fit.asta.health.tools.walking.view.home.StepsScreen
import fit.asta.health.tools.walking.view.permission.StepsPermissionScreen
import fit.asta.health.tools.walking.view.session.StepsActivityScreen
import fit.asta.health.tools.walking.vm.ProgressViewModel

const val STEPS_GRAPH_ROUTE = "steps_graph_address"

fun NavController.navigateToStepsCounter(navOptions: NavOptions? = null) {
    this.navigate(STEPS_GRAPH_ROUTE, navOptions)
}

fun NavController.navigateToStepsCounterProgress(navOptions: NavOptions? = null) {
    this.navigate(StepsCounterScreen.StepsProgressScreen.route, navOptions)
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
        composable(route = StepsCounterScreen.StepsCounterHomeScreen.route) { navBackStackEntry ->
            val progressViewModel: ProgressViewModel =
                navBackStackEntry.sharedViewModel(navController)
            val state by progressViewModel.state.collectAsStateWithLifecycle()
            val list by progressViewModel.sessionList.collectAsStateWithLifecycle()
            val selectedData by progressViewModel.selectedData.collectAsStateWithLifecycle()
            val context = LocalContext.current
            LaunchedEffect(key1 = Unit, block = {
                progressViewModel.startProgressHome(context)
            })
            StepsScreen(
                state = state, list = list,
                onStart = {
                    progressViewModel.startSession()
                    context.startService(Intent(context, StepCounterService::class.java))
                    navController.navigate(StepsCounterScreen.StepsProgressScreen.route)
                },
                selectedData = selectedData,
                goToList = { int, str ->
                    progressViewModel.getSheetItemValue(str)
                    navController.navigate(route = StepsCounterScreen.StepsSheetScreen.route + "/$int")
                },
                setTarget = { dis, dur -> progressViewModel.setTarget(dis, dur) },
                onBack = onBack,
            )
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

        composable(StepsCounterScreen.StepsSheetScreen.route + "/{id}") { navBackStack ->
            val index = navBackStack.arguments?.getString("id")?.toInt()
            val viewModel: ProgressViewModel = navBackStack.sharedViewModel(navController)
            val list by viewModel.sheetDataList.collectAsStateWithLifecycle()
            val selectedData by viewModel.selectedData.collectAsStateWithLifecycle()

            SheetDataSelectionScreen(
                prc = selectedData[index ?: 1],
                list = list,
                onMClick = { viewModel.setMultiple(index ?: 1, it) },
                onSClick = { viewModel.setSingle(index ?: 1, it) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}