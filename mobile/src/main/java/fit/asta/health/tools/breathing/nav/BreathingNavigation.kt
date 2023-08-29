package fit.asta.health.tools.breathing.nav

import android.content.Intent
import androidx.compose.runtime.getValue
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
        ) {
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
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
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
            ExerciseScreen(
                onClick = { viewModel.event(UiEvent.SetExercise(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.PaceScreen.route) {
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
            PaceScreen(
                onClick = { viewModel.event(UiEvent.SetPace(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.BreakTimeScreen.route) {
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
            BreakTimeScreen(
                onClick = { viewModel.event(UiEvent.SetBreakTime(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.GoalScreen.route) {
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
            GoalsScreen(
                onClick = { viewModel.event(UiEvent.SetGoals(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.LanguageScreen.route) {
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
            LanguageScreen(
                onClick = { viewModel.event(UiEvent.SetLanguage(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.CourseLevelScreen.route) {
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
            CourseLevelScreen(
                onClick = { viewModel.event(UiEvent.SetLevel(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.InstructorScreen.route) {
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
            InstructorScreen(
                onClick = { viewModel.event(UiEvent.SetInstructor(it)) },
                onBack = { navController.popBackStack() })
        }
        composable(BreathingScreen.MusicScreen.route) {
            val viewModel: BreathingViewModel = it.sharedViewModel(navController)
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