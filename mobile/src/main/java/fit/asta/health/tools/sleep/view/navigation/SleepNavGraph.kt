package fit.asta.health.tools.sleep.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.tools.sleep.view.screens.SleepJetLagTipsScreen
import fit.asta.health.tools.sleep.view.screens.SleepDisturbanceScreen
import fit.asta.health.tools.sleep.view.screens.SleepFactorsScreen
import fit.asta.health.tools.sleep.view.screens.SleepGoalsScreen
import fit.asta.health.tools.sleep.view.screens.SleepHomeScreen
import fit.asta.health.tools.sleep.viewmodel.SleepToolViewModel

@Composable
fun SleepNavGraph(
    navController: NavHostController,
    sleepToolViewModel: SleepToolViewModel
) {

    NavHost(
        navController = navController,
        startDestination = SleepToolNavRoutes.SleepHomeRoute.routes,
        builder = {

            // Sleep Home Screen
            composable(
                SleepToolNavRoutes.SleepHomeRoute.routes,
                content = {
                    SleepHomeScreen(
                        navController = navController,
                        sleepToolViewModel = sleepToolViewModel
                    )
                }
            )

            // Sleep Factor Screen
            composable(
                SleepToolNavRoutes.SleepFactorRoute.routes,
                content = {

                    val sleepFactorState by sleepToolViewModel.sleepFactorsData
                        .collectAsStateWithLifecycle()

                    SleepFactorsScreen(
                        navController = navController,
                        sleepFactorState = sleepFactorState,
                        loadDataFunction = { sleepToolViewModel.getSleepFactorsData() }
                    )
                }
            )

            // Sleep Disturbance Screen
            composable(
                SleepToolNavRoutes.SleepDisturbanceRoute.routes,
                content = {

                    val sleepDisturbances by sleepToolViewModel.sleepDisturbancesData
                        .collectAsStateWithLifecycle()

                    SleepDisturbanceScreen(
                        navController = navController,
                        sleepDisturbanceState = sleepDisturbances,
                        loadDataFunction = { sleepToolViewModel.getDisturbancesData() }
                    )
                }
            )

            // Sleep jet lag tip Screen
            composable(
                SleepToolNavRoutes.SleepJetLagTipsRoute.routes,
                content = {
                    SleepJetLagTipsScreen(
                        navController = navController,
                        sleepToolViewModel = sleepToolViewModel
                    )
                }
            )

            // Sleep goals Screen
            composable(
                SleepToolNavRoutes.SleepGoalsRoute.routes,
                content = {
                    SleepGoalsScreen(
                        navController = navController,
                        sleepToolViewModel = sleepToolViewModel
                    )
                }
            )
        }
    )
}