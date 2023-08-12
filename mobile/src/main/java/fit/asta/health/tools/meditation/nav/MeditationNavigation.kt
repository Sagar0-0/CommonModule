package fit.asta.health.tools.meditation.nav

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import fit.asta.health.main.Graph
import fit.asta.health.main.deepLinkUrl
import fit.asta.health.main.sharedViewModel
import fit.asta.health.tools.meditation.view.audio_meditation.AudioMeditationScreen
import fit.asta.health.tools.meditation.view.home.MEvent
import fit.asta.health.tools.meditation.view.home.MeditationHomeScreen
import fit.asta.health.tools.meditation.view.instructor.InstructorScreen
import fit.asta.health.tools.meditation.view.language.LanguageScreen
import fit.asta.health.tools.meditation.view.level.LevelScreen
import fit.asta.health.tools.meditation.view.music.MusicScreen
import fit.asta.health.tools.meditation.viewmodel.MeditationViewModel

fun NavGraphBuilder.meditationNavigation(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = Graph.MeditationTool.route,
        startDestination = MeditationScreen.MeditationHomeScreen.route,
        deepLinks = listOf(navDeepLink {
            uriPattern = "$deepLinkUrl/${Graph.MeditationTool.route}"
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
        composable(MeditationScreen.Level.route) {
            val viewModel: MeditationViewModel = it.sharedViewModel(navController)
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
            val currentPosition by viewModel.currentPosition.collectAsStateWithLifecycle()
            AudioMeditationScreen(
                musicState = musicState,
                currentPosition = currentPosition,
                onAudioEvent = viewModel::onAudioEvent,
                onBackPressed = { navController.popBackStack() }
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