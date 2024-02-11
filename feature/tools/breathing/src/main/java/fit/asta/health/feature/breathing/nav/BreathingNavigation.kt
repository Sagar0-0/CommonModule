package fit.asta.health.feature.breathing.nav

import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.common.utils.Constants.BREATHING_GRAPH_ROUTE
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.designsystem.molecular.other.SheetDataSelectionScreen
import fit.asta.health.feature.breathing.view.exercise.ExerciseScreen
import fit.asta.health.feature.breathing.view.home.BreathingHomeScreen
import fit.asta.health.feature.breathing.viewmodel.BreathingViewModel
import fit.asta.health.player.presentation.UiState
import fit.asta.health.player.presentation.screens.player.PlayerScreen

fun NavController.navigateToBreathing(navOptions: NavOptions? = null) {
    this.navigate(BREATHING_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.breathingNavigation(
    navController: NavController, onBack: () -> Unit
) {
    navigation(
        route = BREATHING_GRAPH_ROUTE,
        startDestination = BreathingScreen.HomeScreen.route
    ) {
        composable(
            route = BreathingScreen.HomeScreen.route,
        ) { navBackStackEntry ->
            val viewModel: BreathingViewModel = navBackStackEntry.sharedViewModel(navController)
            val uiState = viewModel.uiState.value
            val state by viewModel.state.collectAsStateWithLifecycle()
            val selectedData by viewModel.selectedData.collectAsStateWithLifecycle()
            BreathingHomeScreen(
                state = state,
                event = viewModel::event,
                uiState = uiState,
                selectedData = selectedData,
                onClickExe = { navController.navigate(route = BreathingScreen.ExerciseScreen.route) },
                goToList = { navController.navigate(route = BreathingScreen.SheetScreen.route + "/$it") },
                onBack = onBack,
                onDNDPermission = { viewModel.checkDNDStatus() }
            )
        }
        composable(BreathingScreen.SheetScreen.route + "/{id}") { navBackStack ->
            val index = navBackStack.arguments?.getString("id")?.toInt()
            val viewModel: BreathingViewModel = navBackStack.sharedViewModel(navController)
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
        composable(BreathingScreen.ExerciseScreen.route) {
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
            ExerciseScreen(
                onClick = {
                    viewModel.event(
                        fit.asta.health.feature.breathing.view.home.UiEvent.SetExercise(
                            it
                        )
                    )
                },
                onBack = { navController.popBackStack() })
        }

        composable(BreathingScreen.MusicScreen.route) {
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
            val musicState by viewModel.musicState.collectAsStateWithLifecycle()
            val visibility by viewModel.visibility.collectAsStateWithLifecycle()
            val trackList by viewModel.trackList.collectAsStateWithLifecycle()
            val musicList by viewModel.musicList.collectAsStateWithLifecycle()
            val selectedTrack by viewModel.track.collectAsStateWithLifecycle()
            val context = LocalContext.current
            PlayerScreen(
                player = viewModel.getPlayer(),
                uiState = UiState(),
                trackList = trackList,
                musicList = musicList,
                selectedTrack = selectedTrack,
                musicState = musicState,
                visibility = visibility,
                onAudioEvent = viewModel::onAudioEvent,
                onVisibility = viewModel::setVisibility,
                onTrackChange = viewModel::onTrackChange,
                onBack = {
                    viewModel.event(fit.asta.health.feature.breathing.view.home.UiEvent.End(context))
                    navController.popBackStack()
                }
            )
        }
    }
}
