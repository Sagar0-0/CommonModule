package fit.asta.health.feature.walking.nav

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.common.utils.Constants.SCHEDULER_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.getDataForSchedule
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.data.walking.service.StepCounterService
import fit.asta.health.designsystem.molecular.other.SheetDataSelectionScreen
import fit.asta.health.feature.walking.view.home.StepsScreen
import fit.asta.health.feature.walking.view.permission.StepsPermissionScreen
import fit.asta.health.feature.walking.view.session.StepsActivityScreen
import fit.asta.health.feature.walking.vm.StepsSessionViewModel
import fit.asta.health.feature.walking.vm.WalkingViewModel

const val STEPS_GRAPH_ROUTE = "steps_graph_address"

fun NavController.navigateToStepsCounter(navOptions: NavOptions? = null) {
    this.navigate(STEPS_GRAPH_ROUTE, navOptions)
}

fun NavController.navigateToScheduleFromWalking() {
    val list = getDataForSchedule("Walking")
    val desc = list[0]
    val label = list[1]
    this.navigate("${SCHEDULER_GRAPH_ROUTE}?desc=${desc}&label=${label}")
}

fun NavController.navigateToStepsCounterProgress(navOptions: NavOptions? = null) {
    this.navigate(
        StepsCounterScreen.StepsProgressScreen.route,
        navOptions
    )
}

fun NavGraphBuilder.stepsCounterNavigation(
    navController: NavHostController,
    sessionState: Boolean,
    onScheduler: () -> Unit,
    onBack: () -> Unit
) {

    navigation(
        route = STEPS_GRAPH_ROUTE,
        startDestination = if (sessionState) {
            StepsCounterScreen.StepsProgressScreen.route
        } else {
            StepsCounterScreen.StepsPermissionScreen.route
        }
    ) {
        composable(route = StepsCounterScreen.StepsPermissionScreen.route) { navBackStackEntry ->
            val walkingViewModel: WalkingViewModel =
                navBackStackEntry.sharedViewModel(navController)
            val stepsPermissionCount by walkingViewModel.stepsPermissionRejectedCount.collectAsStateWithLifecycle()
            StepsPermissionScreen(stepsPermissionCount = stepsPermissionCount,
                goToSteps = {
                    navController.popBackStack()
                    navController.navigate(StepsCounterScreen.StepsCounterHomeScreen.route)
                }, setPermissionCount = {
                    walkingViewModel.setStepsPermissionRejectedCount(it)
                })
        }
        composable(route = StepsCounterScreen.StepsCounterHomeScreen.route) { navBackStackEntry ->
            val context = LocalContext.current

            val walkingViewModel: WalkingViewModel =
                navBackStackEntry.sharedViewModel(navController)
            val state by walkingViewModel.state.collectAsStateWithLifecycle()
            val list by walkingViewModel.sessionList.collectAsStateWithLifecycle()
            val selectedData by walkingViewModel.selectedData.collectAsStateWithLifecycle()
            val availability by walkingViewModel.availability
            val permissionsGranted by walkingViewModel.permissionsGranted
            val sessionMetrics by walkingViewModel.sessionMetrics
            val permissions = walkingViewModel.permissions
            val onPermissionsResult = { walkingViewModel.initialLoad() }
            val permissionsLauncher =
                rememberLauncherForActivityResult(walkingViewModel.permissionsLauncher) {
                    onPermissionsResult()
                }
            StepsScreen(
                state = state, list = list,
                onStart = {
                    walkingViewModel.startSession()
                    context.startService(Intent(context, StepCounterService::class.java))
                    navController.navigate(StepsCounterScreen.StepsProgressScreen.route)
                },
                selectedData = selectedData,
                goToList = { int, str ->
                    walkingViewModel.getSheetItemValue(str)
                    navController.navigate(route = StepsCounterScreen.StepsSheetScreen.route + "/$int")
                },
                setTarget = { dis, dur -> walkingViewModel.setTarget(dis, dur) },
                onBack = onBack,
                onScheduler = {navController.navigateToScheduleFromWalking()},
                healthConnectAvailability = availability,
                onResumeAvailabilityCheck = {
                    walkingViewModel.checkAvailability()
                },
                permissions = permissions,
                permissionsGranted = permissionsGranted,
                sessionMetrics = sessionMetrics,
                uiState = walkingViewModel.healthUiState,
                onPermissionsResult = {
                    walkingViewModel.initialLoad()
                },
                onPermissionsLaunch = { values ->
                    permissionsLauncher.launch(values)
                }
            )
        }
        composable(route = StepsCounterScreen.StepsProgressScreen.route) {
            val viewModel: StepsSessionViewModel = hiltViewModel()
            val state by viewModel.progress.collectAsStateWithLifecycle()
            val context = LocalContext.current
            StepsActivityScreen(
                state = state,
                onPause = { viewModel.pause() },
                onResume = { viewModel.resume() },
                onStop = {
                    viewModel.stop()
                    context.stopService(Intent(context, StepCounterService::class.java))
                    navController.popBackStack()
                })
        }

        composable(StepsCounterScreen.StepsSheetScreen.route + "/{id}") { navBackStack ->
            val index = navBackStack.arguments?.getString("id")?.toInt()
            val viewModel: WalkingViewModel = navBackStack.sharedViewModel(navController)
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