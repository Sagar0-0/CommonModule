package fit.asta.health.tools.meditation.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.tools.meditation.view.audio_meditation.AudioMaditationScreen
import fit.asta.health.tools.meditation.view.home.MEvent
import fit.asta.health.tools.meditation.view.home.MeditationHomeScreen
import fit.asta.health.tools.meditation.view.instructor.InstructorScreen
import fit.asta.health.tools.meditation.view.language.LanguageScreen
import fit.asta.health.tools.meditation.view.level.LevelScreen
import fit.asta.health.tools.meditation.view.music.MusicScreen
import fit.asta.health.tools.meditation.viewmodel.MeditationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
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
            MeditationHomeScreen(
                Event =viewModel::event,
                uiState =uiState,
                language=language,
                instructor = instructor,
                level = level,
                onClickMusic = { navController.navigate(route = MeditationScreen.Music.route)},
                onClickLanguage = { navController.navigate(route = MeditationScreen.Language.route) },
                onClickLevel = { navController.navigate(route = MeditationScreen.Level.route) },
                onClickInstructor = {navController.navigate(route = MeditationScreen.Instructor.route)}
            )
        }
        composable(MeditationScreen.Music.route) {
            MusicScreen()
        }
        composable(MeditationScreen.Level.route) {
            LevelScreen(onClick = {viewModel.event(MEvent.setLevel(it))}, onBack = {navController.popBackStack()})
        }
        composable(MeditationScreen.Instructor.route) {
            InstructorScreen(onClick = {viewModel.event(MEvent.setInstructor(it))}, onBack = {navController.popBackStack()})
        }
        composable(MeditationScreen.AudioMeditation.route) {
            AudioMaditationScreen()
        }
        composable(MeditationScreen.Language.route) {
            LanguageScreen(onClick = {viewModel.event(MEvent.setLanguage(it))}, onBack = {navController.popBackStack()})
        }


    }
}