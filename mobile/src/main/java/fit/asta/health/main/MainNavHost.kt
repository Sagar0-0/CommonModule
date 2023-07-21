package fit.asta.health.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import fit.asta.health.main.ui.MainActivityComp
import fit.asta.health.navigation.home.view.component.ErrorScreenLayout
import fit.asta.health.settings.ui.SettingsNavigation
import fit.asta.health.tools.breathing.nav.breathingNavigation
import fit.asta.health.tools.exercise.nav.exerciseNavigation
import fit.asta.health.tools.meditation.nav.meditationNavigation
import fit.asta.health.tools.sunlight.nav.sunlightNavigation
import fit.asta.health.tools.water.nav.waterToolNavigation

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
            onBreathing = { navController.navigate(Graph.BreathingTool.route) },
            onWater = { navController.navigate(Graph.WaterTool.route) },
            onMeditation = { navController.navigate(Graph.MeditationTool.route) },
            onDance = {
                navController.navigate(Graph.ExerciseTool.route + "?activity=dance")
            },
            onHiit = {
                navController.navigate(Graph.ExerciseTool.route + "?activity=HIIT")
            },
            onSleep = {
//                navController.navigate(Graph.SleepTool.route)
            },
            onSunlight = {
                navController.navigate(Graph.SunlightTool.route)
            },
            onWorkout = {
                navController.navigate(Graph.ExerciseTool.route + "?activity=workout")
            },
            onYoga = {
                navController.navigate(Graph.ExerciseTool.route + "?activity=yoga")
            },
        )
        SettingsNavigation(navController)
        breathingNavigation(navController, onBack = { navController.navigateUp() })
        waterToolNavigation(navController, onBack = { navController.navigateUp() })
        meditationNavigation(navController, onBack = { navController.navigateUp() })
        sunlightNavigation(navController, onBack = { navController.navigateUp() })
//        sleepNavGraph(navController,  onBack = { navController.navigateUp() })
        exerciseNavigation(navController, onBack = { navController.navigateUp() })
    }

}