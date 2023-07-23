package fit.asta.health.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fit.asta.health.main.ui.MainActivityComp
import fit.asta.health.navigation.home.view.component.ErrorScreenLayout
import fit.asta.health.profile.CreateProfileLayout
import fit.asta.health.profile.ProfileContent
import fit.asta.health.settings.ui.SettingsNavigation
import fit.asta.health.testimonials.testimonialsNavigation
import fit.asta.health.tools.breathing.nav.breathingNavigation
import fit.asta.health.tools.exercise.nav.exerciseNavigation
import fit.asta.health.tools.meditation.nav.meditationNavigation
import fit.asta.health.tools.sunlight.nav.sunlightNavigation
import fit.asta.health.tools.water.nav.waterToolNavigation
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun MainNavHost(isConnected: Boolean) {
    val navController = rememberNavController()
    if (!isConnected) {
        Box(modifier = Modifier.fillMaxSize()) {
            ErrorScreenLayout {

            }
        }
    }

    NavHost(
        navController = navController,
        route = Graph.ROOT.route,
        startDestination = Graph.Home.route
    ) {

        MainActivityComp(
            navController,
            onNav = {
                when (it) {
                    Graph.Settings -> {
                        navController.navigate(Graph.Settings.route)
                    }

                    Graph.BreathingTool -> {
                        navController.navigate(Graph.BreathingTool.route)
                    }

                    Graph.WaterTool -> {
                        navController.navigate(Graph.WaterTool.route)
                    }

                    Graph.MeditationTool -> {
                        navController.navigate(Graph.MeditationTool.route)
                    }

                    Graph.SunlightTool -> {
                        navController.navigate(Graph.SunlightTool.route)
                    }

                    Graph.Profile -> {
                        navController.navigate(Graph.Profile.route)
                    }

                    Graph.Testimonials -> {
                        navController.navigate(Graph.Testimonials.route)
                    }

                    Graph.SleepTool -> {}
                    Graph.WalkingTool -> {}
                    Graph.Dance -> {
                        navController.navigate(Graph.ExerciseTool.route + "?activity=dance")
                    }

                    Graph.Hiit -> {
                        navController.navigate(Graph.ExerciseTool.route + "?activity=HIIT")
                    }

                    Graph.Workout -> {
                        navController.navigate(Graph.ExerciseTool.route + "?activity=workout")
                    }

                    Graph.Yoga -> {
                        navController.navigate(Graph.ExerciseTool.route + "?activity=yoga")
                    }

                    else -> {}
                }
            },
        )
        composable(route = Graph.Profile.route) {
            ProfileContent(
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(Graph.CreateProfile.route) })
        }
        composable(route = Graph.CreateProfile.route) {
            CreateProfileLayout(onBack = { navController.popBackStack() })
        }
        SettingsNavigation(navController)
        breathingNavigation(navController, onBack = { navController.navigateUp() })
        waterToolNavigation(navController, onBack = { navController.navigateUp() })
        meditationNavigation(navController, onBack = { navController.navigateUp() })
        sunlightNavigation(navController, onBack = { navController.navigateUp() })
//        sleepNavGraph(navController,  onBack = { navController.navigateUp() })
        exerciseNavigation(navController, onBack = { navController.navigateUp() })
        testimonialsNavigation(navController)
    }

}