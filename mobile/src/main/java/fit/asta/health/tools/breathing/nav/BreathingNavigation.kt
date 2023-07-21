package fit.asta.health.tools.breathing.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.main.Graph
import fit.asta.health.settings.ui.SettingScreens
import fit.asta.health.tools.breathing.view.break_time.BreakTimeScreen
import fit.asta.health.tools.breathing.view.course_level.CourseLevelScreen
import fit.asta.health.tools.breathing.view.exercise.ExerciseScreen
import fit.asta.health.tools.breathing.view.goal.GoalsScreen
import fit.asta.health.tools.breathing.view.home.BreathingHomeScreen
import fit.asta.health.tools.breathing.view.home.UiEvent
import fit.asta.health.tools.breathing.view.instructor.InstructorScreen
import fit.asta.health.tools.breathing.view.language.LanguageScreen
import fit.asta.health.tools.breathing.view.music.MusicScreen
import fit.asta.health.tools.breathing.view.pace.PaceScreen
import fit.asta.health.tools.breathing.viewmodel.BreathingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


fun NavGraphBuilder.breathingNavigation(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = Graph.BreathingTool.route,
        startDestination = BreathingScreen.HomeScreen.route
    ) {
        composable(BreathingScreen.HomeScreen.route) {
            val viewModel: BreathingViewModel = hiltViewModel()
            val uiState by viewModel.homeUiState
            val exercise by viewModel.selectedExercise.collectAsStateWithLifecycle()
            val goals by viewModel.selectedGoals.collectAsStateWithLifecycle()
            val music by viewModel.selectedMusic.collectAsStateWithLifecycle()
            val pace by viewModel.selectedPace.collectAsStateWithLifecycle()
            val breakTime by viewModel.selectedBreakTime.collectAsStateWithLifecycle()
            val level by viewModel.selectedLevel.collectAsStateWithLifecycle()
            val language by viewModel.selectedLanguage.collectAsStateWithLifecycle()
            val instructor by viewModel.selectedInstructor.collectAsStateWithLifecycle()
            BreathingHomeScreen(
                uiState = uiState,
                exercise = exercise,
                goals = goals,
                music = music,
                pace = pace,
                break_time = breakTime,
                level = level,
                language = language,
                instructor = instructor,
                onExercise = { navController.navigate(route = BreathingScreen.ExerciseScreen.route) },
                onMusic = { navController.navigate(route = BreathingScreen.MusicScreen.route) },
                onLevel = { navController.navigate(route = BreathingScreen.CourseLevelScreen.route) },
                onLanguage = { navController.navigate(route = BreathingScreen.LanguageScreen.route) },
                onPace = { navController.navigate(route = BreathingScreen.PaceScreen.route) },
                onBreakTime = { navController.navigate(route = BreathingScreen.BreakTimeScreen.route) },
                onGoals = { navController.navigate(route = BreathingScreen.GoalScreen.route) },
                onInstructor = { navController.navigate(route = BreathingScreen.InstructorScreen.route) },
                onSchedule = { /*TODO*/ },
                onPlayer = { navController.navigate(route = BreathingScreen.MusicMeditationScreen.route) },
                event = viewModel::event, onBack = onBack
            )
        }
        composable(BreathingScreen.ExerciseScreen.route) {
            val viewModel: BreathingViewModel = hiltViewModel()
            ExerciseScreen(
                onClick = { viewModel.event(UiEvent.SetExercise(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.PaceScreen.route) {
            val viewModel: BreathingViewModel = hiltViewModel()
            PaceScreen(
                onClick = { viewModel.event(UiEvent.SetPace(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.BreakTimeScreen.route) {
            val viewModel: BreathingViewModel = hiltViewModel()
            BreakTimeScreen(
                onClick = { viewModel.event(UiEvent.SetBreakTime(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.GoalScreen.route) {
            val viewModel: BreathingViewModel = hiltViewModel()
            GoalsScreen(
                onClick = { viewModel.event(UiEvent.SetGoals(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.LanguageScreen.route) {
            val viewModel: BreathingViewModel = hiltViewModel()
            LanguageScreen(
                onClick = { viewModel.event(UiEvent.SetLanguage(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.CourseLevelScreen.route) {
            val viewModel: BreathingViewModel = hiltViewModel()
            CourseLevelScreen(
                onClick = { viewModel.event(UiEvent.SetLevel(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.InstructorScreen.route) {
            val viewModel: BreathingViewModel = hiltViewModel()
            InstructorScreen(
                onClick = { viewModel.event(UiEvent.SetInstructor(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.MusicScreen.route) {
            val viewModel: BreathingViewModel = hiltViewModel()
            MusicScreen(
                onClick = { viewModel.event(UiEvent.SetMusic(it)) },
                onBack = { navController.popBackStack() })
        }
//        composable(BreathingScreen.MusicMeditationScreen.route) {
//            MusicMeditationScreen()
//        }
//        composable(BreathingScreen.MusicPlayerScreen.route) {
//            MusicPlayerScreen()
//        }
    }
}