package fit.asta.health.tools.breathing.nav

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import fit.asta.health.common.utils.Constants.BREATHING_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.deepLinkUrl
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.meditation.view.other.SheetDataSelectionScreen
import fit.asta.health.player.presentation.UiState
import fit.asta.health.player.presentation.screens.player.PlayerScreen
import fit.asta.health.tools.breathing.view.exercise.ExerciseScreen
import fit.asta.health.tools.breathing.view.home.BreathingHomeScreen
import fit.asta.health.tools.breathing.view.home.UiEvent
import fit.asta.health.tools.breathing.viewmodel.BreathingViewModel

fun NavController.navigateToBreathing(navOptions: NavOptions? = null) {
    this.navigate(BREATHING_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.breathingNavigation(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = BREATHING_GRAPH_ROUTE,
        startDestination = BreathingScreen.HomeScreen.route,
        deepLinks = listOf(navDeepLink {
            uriPattern = "$deepLinkUrl/${BREATHING_GRAPH_ROUTE}"
            action = Intent.ACTION_VIEW
        })
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
                onClick = { viewModel.event(UiEvent.SetExercise(it)) },
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
                    viewModel.event(UiEvent.End(context))
                    navController.popBackStack()
                }
            )
        }
    }
}
