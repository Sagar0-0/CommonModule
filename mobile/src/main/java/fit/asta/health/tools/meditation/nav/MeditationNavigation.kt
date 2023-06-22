package fit.asta.health.tools.meditation.nav

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.tools.meditation.view.audio_meditation.AudioMeditationScreen
import fit.asta.health.tools.meditation.view.home.MEvent
import fit.asta.health.tools.meditation.view.home.MeditationHomeScreen
import fit.asta.health.tools.meditation.view.instructor.InstructorScreen
import fit.asta.health.tools.meditation.view.language.LanguageScreen
import fit.asta.health.tools.meditation.view.level.LevelScreen
import fit.asta.health.tools.meditation.view.music.MusicScreen
import fit.asta.health.tools.meditation.viewmodel.MeditationViewModel

@OptIn(
    ExperimentalMaterialApi::class
)
@Composable
fun MeditationNavigation(navController: NavHostController, viewModel: MeditationViewModel) {
    NavHost(
        navController = navController,
        startDestination = MeditationScreen.MeditationHomeScreen.route
    ) {

        composable(MeditationScreen.MeditationHomeScreen.route) {
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
                onClickInstructor = { navController.navigate(route = MeditationScreen.Instructor.route) }
            )
        }
        composable(MeditationScreen.Music.route) {
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
            LevelScreen(
                onClick = { viewModel.event(MEvent.SetLevel(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(MeditationScreen.Instructor.route) {
            InstructorScreen(
                onClick = { viewModel.event(MEvent.SetInstructor(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(MeditationScreen.AudioMeditation.route) {
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
            LanguageScreen(
                onClick = { viewModel.event(MEvent.SetLanguage(it)) },
                onBack = { navController.popBackStack() })
        }
    }
}