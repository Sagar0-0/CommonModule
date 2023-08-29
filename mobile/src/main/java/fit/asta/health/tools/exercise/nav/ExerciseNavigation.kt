package fit.asta.health.tools.exercise.nav

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import fit.asta.health.common.utils.Constants.EXERCISE_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.deepLinkUrl
import fit.asta.health.common.utils.sharedViewModel
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
import fit.asta.health.tools.exercise.view.video.VideoScreen
import fit.asta.health.tools.exercise.view.video_player.VideoPlayerScreen
import fit.asta.health.tools.exercise.viewmodel.ExerciseViewModel

fun NavController.navigateToExercise(navOptions: NavOptions? = null) {
    this.navigate(EXERCISE_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.exerciseNavigation(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = "$EXERCISE_GRAPH_ROUTE?activity={activity}",
        startDestination = ExerciseScreen.HomeScreen.route,
        deepLinks = listOf(navDeepLink {
            uriPattern = "${deepLinkUrl}/${EXERCISE_GRAPH_ROUTE}/{activity}"
            action = Intent.ACTION_VIEW
        }),
        arguments = listOf(navArgument("activity") {
            defaultValue = "dance"
            type = NavType.StringType
        })
    ) {
        composable(ExerciseScreen.HomeScreen.route) {
            val activity = it.arguments?.getString("activity") ?: "dance"
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            viewModel.setScreen(activity)
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
                onMusic = { navController.navigate(route = ExerciseScreen.Music.route) },
                onSchedule = {},
                onPlayer = { navController.navigate(route = ExerciseScreen.VideoPlayer.route) },
                event = viewModel::event,
                onBack = onBack
            )
        }
        composable(ExerciseScreen.BodyParts.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            ExerciseBodyParts(
                onClick = { viewModel.event(HomeEvent.SetBodyParts(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.bodyParts
            )
        }
        composable(ExerciseScreen.BodyStretch.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            ExerciseBodyStretch(
                onClick = { viewModel.event(HomeEvent.SetBodyStretch(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.bodyStretch
            )
        }
        composable(ExerciseScreen.Level.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            ExerciseLevelScreen(
                onClick = { viewModel.event(HomeEvent.SetLevel(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.levelList
            )
        }
        composable(ExerciseScreen.Challenges.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            ExerciseChallengesScreen(
                onClick = { viewModel.event(HomeEvent.SetChallenge(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.challengeList
            )
        }
        composable(ExerciseScreen.Goals.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            val activity = it.arguments?.getString("activity") ?: "dance"
            ExerciseGoalsScreen(
                onClick = { viewModel.event(HomeEvent.SetQuick(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.getGoalsList(code = activity)
            )
        }
        composable(ExerciseScreen.Style.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            val activity = it.arguments?.getString("activity") ?: "dance"
            ExerciseStyleScreen(
                onClick = { viewModel.event(HomeEvent.SetStyle(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.getStyleList(code = activity)
            )
        }
        composable(ExerciseScreen.Duration.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            ExerciseDurationScreen(
                onClick = { viewModel.event(HomeEvent.SetDuration(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.durationList
            )
        }
        composable(ExerciseScreen.Music.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            ExerciseMusic(
                onClick = { },
                onBack = { navController.popBackStack() },
                itemList = viewModel.challengeList
            )
        }
        composable(ExerciseScreen.Equipment.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            ExerciseEquipment(
                onClick = { viewModel.event(HomeEvent.SetEquipment(it)) },
                onBack = { navController.popBackStack() },
                itemList = viewModel.equipmentList
            )
        }
        composable(ExerciseScreen.VideoPlayer.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            val state by viewModel.videoList.collectAsStateWithLifecycle()
            VideoPlayerScreen(state = state, onMusicEvents = viewModel::eventVideoPlayer,
                navigateToPlayer = { navController.navigate(route = ExerciseScreen.Video.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(ExerciseScreen.Video.route) {
            val viewModel: ExerciseViewModel = it.sharedViewModel(navController)
            val uiState by viewModel.uiState
            VideoScreen(player = viewModel.player(), uiState = uiState,
                progress = viewModel.exerciseUiState.value.consume,
                event = viewModel::eventVideo,
                onBack = { navController.popBackStack() })
        }
    }
}