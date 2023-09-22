package fit.asta.health.tools.meditation.nav

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
import fit.asta.health.common.utils.Constants.MEDITATION_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.deepLinkUrl
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.player.presentation.UiState
import fit.asta.health.player.presentation.screens.player.PlayerScreen
import fit.asta.health.tools.meditation.view.home.MEvent
import fit.asta.health.tools.meditation.view.home.MeditationHomeScreen
import fit.asta.health.tools.meditation.view.instructor.InstructorScreen
import fit.asta.health.tools.meditation.view.language.LanguageScreen
import fit.asta.health.tools.meditation.view.level.LevelScreen
import fit.asta.health.tools.meditation.view.music.MusicScreen
import fit.asta.health.tools.meditation.viewmodel.MeditationViewModel

fun NavController.navigateToMeditation(navOptions: NavOptions? = null) {
    this.navigate(MEDITATION_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.meditationNavigation(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = MEDITATION_GRAPH_ROUTE,
        startDestination = MeditationScreen.MeditationHomeScreen.route,
        deepLinks = listOf(navDeepLink {
            uriPattern = "$deepLinkUrl/${MEDITATION_GRAPH_ROUTE}"
            action = Intent.ACTION_VIEW
        })
    ) {
        composable(MeditationScreen.MeditationHomeScreen.route) {
            val viewModel: MeditationViewModel = it.sharedViewModel(navController)
            val uiState = viewModel.uiState.value
            val level by viewModel.selectedLevel.collectAsStateWithLifecycle()
            val language by viewModel.selectedLanguage.collectAsStateWithLifecycle()
            val instructor by viewModel.selectedInstructor.collectAsStateWithLifecycle()
            val music by viewModel.selectedMusic.collectAsStateWithLifecycle()
            val context = LocalContext.current
            viewModel.setBackgroundSound(context)
            MeditationHomeScreen(
                Event = viewModel::event,
                uiState = uiState,
                language = language,
                instructor = instructor,
                music = music,
                level = level,
                onClickMusic = { navController.navigate(route = MeditationScreen.Music.route) },
                onClickLanguage = { navController.navigate(route = MeditationScreen.Language.route) },
                onClickLevel = { navController.navigate(route = MeditationScreen.Level.route) },
                onClickInstructor = { navController.navigate(route = MeditationScreen.Instructor.route) },
                onBack = onBack
            )
        }
        composable(MeditationScreen.Music.route) {
            val viewModel: MeditationViewModel = it.sharedViewModel(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()
            val musicState by viewModel.musicState.collectAsStateWithLifecycle()
            val currentPosition by viewModel.currentPosition.collectAsStateWithLifecycle()
            MusicScreen(
                state = state,
                musicState = musicState,
                currentPosition = currentPosition,
                onMusicEvents = viewModel::onMusicEvent,
                navigateToPlayer = { navController.navigate(route = MeditationScreen.AudioMeditation.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(MeditationScreen.Level.route) { navBackStackEntry ->
            val viewModel: MeditationViewModel = navBackStackEntry.sharedViewModel(navController)
            LevelScreen(
                onClick = { viewModel.event(MEvent.SetLevel(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(MeditationScreen.Instructor.route) {
            val viewModel: MeditationViewModel = it.sharedViewModel(navController)
            InstructorScreen(
                onClick = { viewModel.event(MEvent.SetInstructor(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(MeditationScreen.AudioMeditation.route) {
            val viewModel: MeditationViewModel = it.sharedViewModel(navController)
            val musicState by viewModel.musicState.collectAsStateWithLifecycle()
            val visibility by viewModel.visibility.collectAsStateWithLifecycle()
            val trackList by viewModel.trackList.collectAsStateWithLifecycle()
            val selectedTrack by viewModel.track.collectAsStateWithLifecycle()
            PlayerScreen(
                player = viewModel.getPlayer(),
                uiState = UiState(),
                trackList = trackList,
                selectedTrack = selectedTrack,
                musicState = musicState,
                visibility = visibility,
                onAudioEvent = viewModel::onAudioEvent,
                onVisibility = viewModel::setVisibility,
                onTrackChange = viewModel::onTrackChange
            )
        }
        composable(MeditationScreen.Language.route) {
            val viewModel: MeditationViewModel = it.sharedViewModel(navController)
            LanguageScreen(
                onClick = { viewModel.event(MEvent.SetLanguage(it)) },
                onBack = { navController.popBackStack() })
        }
    }
}