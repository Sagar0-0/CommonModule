package fit.asta.health.meditation.nav

import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.common.utils.Constants
import fit.asta.health.common.utils.Constants.MEDITATION_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.SCHEDULER_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.TAG_NAME
import fit.asta.health.common.utils.Constants.getDataForSchedule
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.designsystem.molecular.other.SheetDataSelectionScreen
import fit.asta.health.meditation.view.home.MEvent
import fit.asta.health.meditation.view.home.MeditationHomeScreen
import fit.asta.health.meditation.viewmodel.MeditationViewModel
import fit.asta.health.player.presentation.UiState
import fit.asta.health.player.presentation.screens.player.PlayerScreen

fun NavController.navigateToMeditation(navOptions: NavOptions? = null) {
    this.navigate(MEDITATION_GRAPH_ROUTE, navOptions)
}

fun NavController.navigateToScheduleFromMeditation() {
    //if these values are getting from constants only we will fetch them directly in schedular by providing tool tag here
//    val list = getDataForSchedule("meditation")
//    val desc = list[0]
//    val label = list[1]
    this.currentBackStackEntry?.savedStateHandle?.set(
        key = TAG_NAME,
        value = Constants.ToolTag.MEDITATION
    )
    this.navigate(
        SCHEDULER_GRAPH_ROUTE
    )
}

fun NavGraphBuilder.meditationNavigation(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = MEDITATION_GRAPH_ROUTE,
        startDestination = MeditationScreen.MeditationHomeScreen.route
    ) {
        composable(MeditationScreen.MeditationHomeScreen.route) { navBackStackEntry ->
            val viewModel: MeditationViewModel = navBackStackEntry.sharedViewModel(navController)
            val uiState = viewModel.uiState.value
            val state by viewModel.state.collectAsStateWithLifecycle()
            val selectedData by viewModel.selectedData.collectAsStateWithLifecycle()
            val context = LocalContext.current
            viewModel.setBackgroundSound(context)
            MeditationHomeScreen(
                state = state,
                event = viewModel::event,
                uiState = uiState,
                selectedData = selectedData,
                onClickMusic = { navController.navigate(route = MeditationScreen.AudioMeditation.route) },
                goToList = { navController.navigate(route = MeditationScreen.SheetScreen.route + "/$it") },
                onBack = onBack,
                onDNDPermission = viewModel::checkDNDStatus,
                onClickSchedule = { navController.navigateToScheduleFromMeditation() }
            )
        }
        composable(MeditationScreen.SheetScreen.route + "/{id}") { navBackStack ->
            val index = navBackStack.arguments?.getString("id")?.toInt()
            val viewModel: MeditationViewModel = navBackStack.sharedViewModel(navController)
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

        composable(MeditationScreen.AudioMeditation.route) {
            val viewModel: MeditationViewModel = it.sharedViewModel(navController)
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
                    viewModel.event(MEvent.End(context))
                    navController.popBackStack()
                }
            )
        }
    }
}