package fit.asta.health.feature.sleep.view.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.feature.sleep.view.screens.SleepDisturbanceScreen
import fit.asta.health.feature.sleep.view.screens.SleepFactorsScreen
import fit.asta.health.feature.sleep.view.screens.SleepGoalsScreen
import fit.asta.health.feature.sleep.view.screens.SleepHomeScreen
import fit.asta.health.feature.sleep.view.screens.SleepJetLagTipsScreen
import fit.asta.health.feature.sleep.viewmodel.SleepToolViewModel

const val SLEEP_GRAPH_ROUTE = "SLEEP_graph_address"

fun NavController.navigateToSleep(navOptions: NavOptions? = null) {
    this.navigate(SLEEP_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.sleepNavGraph(
    navController: NavHostController,
    onBack: () -> Unit
) {

    navigation(
        route = SLEEP_GRAPH_ROUTE,
        startDestination = SleepToolNavRoutes.SleepHomeRoute.routes,
        builder = {

            // Sleep Home Screen
            composable(SleepToolNavRoutes.SleepHomeRoute.routes) {
                val sleepToolViewModel: SleepToolViewModel = it.sharedViewModel(navController)
                // Progress Data which is shown in the Home Screen
                val progressData = sleepToolViewModel.userUIDefaults.collectAsState().value
                    .data?.sleepData?.progressData
                // Bottom Sheet Details
                val bottomSheetData =
                    sleepToolViewModel.userUIDefaults.collectAsState()
                        .value.data?.sleepData?.toolData?.prc

                // Selected Disturbances which will be shown in the bottom sheet
                val selectedDisturbances =
                    sleepToolViewModel.userUIDefaults.collectAsState()
                        .value.data?.sleepData?.toolData?.prc?.find {
                            it.ttl == "disturbance"
                        }
                val timerStatus = sleepToolViewModel.timerStatus.collectAsState().value

                SleepHomeScreen(
                    progressData = progressData,
                    navController = navController,
                    bottomSheetData = bottomSheetData,
                    selectedDisturbances = selectedDisturbances,
                    timerStatus = timerStatus,
                    onStartStopClick = { sleepToolViewModel.setTimerStatus() },
                    onBack = onBack
                )
            }


            // Sleep goals Screen
            composable(
                SleepToolNavRoutes.SleepGoalsRoute.routes,
                content = {
                    val sleepToolViewModel: SleepToolViewModel = it.sharedViewModel(navController)

                    // Default Goals List fetched from the Server
                    val goalsList by sleepToolViewModel.goalsList.collectAsStateWithLifecycle()

                    // Selected Goals which are already selected by the User
                    val selectedGoals =
                        sleepToolViewModel.userUIDefaults.collectAsStateWithLifecycle().value
                            .data?.sleepData?.toolData?.prc?.find {
                                it.ttl == "goal"
                            }

                    // Goals Screen
                    SleepGoalsScreen(
                        navController = navController,
                        goalsList = goalsList,
                        selectedGoals = selectedGoals,
                        loadData = { sleepToolViewModel.getGoalsList() }
                    ) { type, newValue ->
                        sleepToolViewModel.updateToolData(toolType = type, newValue = newValue)
                    }
                }
            )

            // Sleep Factor Screen
            composable(
                SleepToolNavRoutes.SleepFactorRoute.routes,
                content = {
                    val sleepToolViewModel: SleepToolViewModel = it.sharedViewModel(navController)
                    // Default Sleep Factor List fetched from the Server
                    val sleepFactorState by sleepToolViewModel.sleepFactorsData
                        .collectAsStateWithLifecycle()

                    // Selected Sleep Factor which are already selected by the User
                    val selectedSleepFactor =
                        sleepToolViewModel.userUIDefaults.collectAsStateWithLifecycle().value
                            .data?.sleepData?.toolData?.prc?.find {
                                it.ttl == "factors"
                            }

                    // Sleep Factors Screen
                    SleepFactorsScreen(
                        navController = navController,
                        sleepFactorState = sleepFactorState,
                        selectedFactors = selectedSleepFactor,
                        loadDataFunction = { sleepToolViewModel.getSleepFactorsData() }
                    ) { type, newValue ->
                        sleepToolViewModel.updateToolData(toolType = type, newValue = newValue)
                    }
                }
            )

            // Sleep Disturbance Screen
            composable(
                SleepToolNavRoutes.SleepDisturbanceRoute.routes,
                content = {
                    val sleepToolViewModel: SleepToolViewModel = it.sharedViewModel(navController)
                    // Default Sleep Disturbances List fetched from the Server
                    val sleepDisturbances by sleepToolViewModel.sleepDisturbancesData
                        .collectAsStateWithLifecycle()

                    // Selected Sleep Disturbances which are already selected by the User
                    val selectedDisturbances =
                        sleepToolViewModel.userUIDefaults.collectAsStateWithLifecycle().value
                            .data?.sleepData?.toolData?.prc?.find {
                                it.ttl == "disturbance"
                            }

                    // Sleep Disturbances Screen
                    SleepDisturbanceScreen(
                        navController = navController,
                        sleepDisturbanceState = sleepDisturbances,
                        selectedDisturbance = selectedDisturbances,
                        loadDataFunction = { sleepToolViewModel.getDisturbancesData() }
                    ) { type, newValue ->
                        sleepToolViewModel.updateToolData(toolType = type, newValue = newValue)
                    }
                }
            )

            // Sleep jet lag tip Screen
            composable(
                SleepToolNavRoutes.SleepJetLagTipsRoute.routes,
                content = {
                    val sleepToolViewModel: SleepToolViewModel = it.sharedViewModel(navController)
                    // Default Jet Lag Details List fetched from the Server
                    val jetLagDetails =
                        sleepToolViewModel.jetLagDetails.collectAsStateWithLifecycle().value

                    SleepJetLagTipsScreen(
                        jetLagDetails = jetLagDetails,
                    ) {
                        sleepToolViewModel.getJetLagTips()
                    }
                }
            )
        }
    )
}