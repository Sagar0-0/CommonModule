package fit.asta.health.tools.exercise.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.tools.exercise.view.body_parts.ExerciseBodyParts
import fit.asta.health.tools.exercise.view.body_stretch.ExerciseBodyStretch
import fit.asta.health.tools.exercise.view.challenges.ExerciseChallengesScreen
import fit.asta.health.tools.exercise.view.duration.ExerciseDurationScreen
import fit.asta.health.tools.exercise.view.equipments.ExerciseEquipment
import fit.asta.health.tools.exercise.view.goals.ExerciseGoalsScreen
import fit.asta.health.tools.exercise.view.home.ExerciseHomeScreen
import fit.asta.health.tools.exercise.view.home.HomeEvent
import fit.asta.health.tools.exercise.view.level.ExerciseLevelScreen
import fit.asta.health.tools.exercise.view.music.ExerciseMusic
import fit.asta.health.tools.exercise.view.style.ExerciseStyleScreen
import fit.asta.health.tools.exercise.viewmodel.ExerciseViewModel

@Composable
fun DanceNavigation(navController: NavHostController, activity: String, viewModel: ExerciseViewModel) {
    NavHost(
        navController = navController,
        startDestination = ExerciseScreen.HomeScreen.route
    ) {

        composable(ExerciseScreen.HomeScreen.route) {
//            viewModel.setScreen(value = activity)
            val uiState = viewModel.exerciseUiState.value
            val style by viewModel.selectedStyle.collectAsStateWithLifecycle()
            val music by viewModel.selectedMusic.collectAsStateWithLifecycle()
            val level by viewModel.selectedLevel.collectAsStateWithLifecycle()
            val challenge by viewModel.selectedChallenges.collectAsStateWithLifecycle()
            val bodyParts by viewModel.selectedBodyParts.collectAsStateWithLifecycle()
            val bodyStretch by viewModel.selectedBodyStretch.collectAsStateWithLifecycle()
            val duration by viewModel.selectedDuration.collectAsStateWithLifecycle()
            val equipment by viewModel.selectedEquipments.collectAsStateWithLifecycle()
            val quick by viewModel.selectedQuick.collectAsStateWithLifecycle()
            Log.d("subhash", "DanceNavigation: $style")
            ExerciseHomeScreen(
                screen = activity,
                uiState = uiState,
                style = style,
                duration = duration,
                equipment = equipment,
                music = music,
                level = level,
                quickDance = quick,
                bodyParts = bodyParts,
                bodyStretch = bodyStretch,
                challenges = challenge,
                onStyle = { navController.navigate(route = ExerciseScreen.Style.route) },
                onBodyParts = { navController.navigate(route = ExerciseScreen.BodyParts.route) },
                onBodyStretch = { navController.navigate(route = ExerciseScreen.BodyStretch.route) },
                onChallenges = { navController.navigate(route = ExerciseScreen.Challenges.route) },
                onLevel = { navController.navigate(route = ExerciseScreen.Level.route) },
                onQuickDance = { navController.navigate(route = ExerciseScreen.Goals.route) },
                onDuration = { navController.navigate(route = ExerciseScreen.Duration.route) },
                onEquipment = { navController.navigate(route = ExerciseScreen.Equipment.route) },
                onMusic = {navController.navigate(route = ExerciseScreen.Music.route) },
                event = viewModel::event
            )
        }
        composable(ExerciseScreen.BodyParts.route) {
            ExerciseBodyParts(
                onClick = { viewModel.event(HomeEvent.SetBodyParts(it)) },
                onBack = { navController.popBackStack()  },
                itemList = viewModel.bodyParts
            )
        }
        composable(ExerciseScreen.BodyStretch.route) {
            ExerciseBodyStretch(
                onClick = { viewModel.event(HomeEvent.SetBodyStretch(it)) },
                onBack = { navController.popBackStack()  },
                itemList = viewModel.bodyStretch
            )
        }
        composable(ExerciseScreen.Level.route) {
            ExerciseLevelScreen(
                onClick = { viewModel.event(HomeEvent.SetLevel(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.levelList
            )
        }
        composable(ExerciseScreen.Challenges.route) {
            ExerciseChallengesScreen(
                onClick = { viewModel.event(HomeEvent.SetChallenge(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.challengeList
            )
        }
        composable(ExerciseScreen.Goals.route) {
            ExerciseGoalsScreen(
                onClick = { viewModel.event(HomeEvent.SetQuick(it)) },
                onBack = { navController.popBackStack()  },
                itemList = viewModel.getGoalsList(code=activity)
            )
        }
        composable(ExerciseScreen.Style.route) {
            ExerciseStyleScreen(onClick = { viewModel.event(HomeEvent.SetStyle(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.getStyleList(code=activity)
            )
        }
        composable(ExerciseScreen.Duration.route) {
            ExerciseDurationScreen(
                onClick = { viewModel.event(HomeEvent.SetDuration(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.durationList
            )
        }
        composable(ExerciseScreen.Music.route){
            ExerciseMusic(
                onClick = {  },
                onBack = {navController.popBackStack()},
                itemList = viewModel.challengeList
            )
        }
        composable(ExerciseScreen.Equipment.route){
            ExerciseEquipment(
                onClick = { viewModel.event(HomeEvent.SetEquipment(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.equipmentList
            )
        }
    }
}