package fit.asta.health.scheduler.navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.main.Graph
import fit.asta.health.main.sharedViewModel
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AlarmSettingScreen
import fit.asta.health.scheduler.compose.screen.tagscreen.TagsScreen
import fit.asta.health.scheduler.compose.screen.timesettingscreen.TimeSettingScreen
import fit.asta.health.scheduler.viewmodel.SchedulerViewModel

fun NavGraphBuilder.schedulerNavigation(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = Graph.Scheduler.route,
        startDestination = AlarmSchedulerScreen.AlarmSettingHome.route
    ) {
//        composable(route = AlarmSchedulerScreen.AlarmHome.route) {
//            val schedulerViewModel:SchedulerViewModel= hiltViewModel()
//            val homeUiState = schedulerViewModel.homeUiState.value
//            val list by schedulerViewModel.alarmList.collectAsStateWithLifecycle()
//            HomeScreen(
//                list=list,
//                homeUiState = homeUiState,
//                hSEvent = schedulerViewModel::hSEvent,
//                navAlarmSettingHome = {
//                    navController.navigate(route = AlarmSchedulerScreen.AlarmSettingHome.route)
//                })
//        }
        composable(route = AlarmSchedulerScreen.AlarmSettingHome.route) {
            val schedulerViewModel: SchedulerViewModel = it.sharedViewModel(navController)
            val alarmSettingUiState by schedulerViewModel.alarmSettingUiState.collectAsStateWithLifecycle()
            val uiError by schedulerViewModel.uiError.collectAsStateWithLifecycle()
            val areInputsValid by schedulerViewModel.areInputsValid.collectAsStateWithLifecycle()
            AlarmSettingScreen(
                alarmSettingUiState = alarmSettingUiState,
                uiError = uiError,
                areInputsValid = areInputsValid,
                aSEvent = schedulerViewModel::aSEvent,
                navTagSelection = { navController.navigate(route = AlarmSchedulerScreen.TagSelection.route) },
                navTimeSetting = {
                    navController.navigate(route = AlarmSchedulerScreen.IntervalSettingsSelection.route)
                },
                navBack = onBack,
            )
        }

        composable(route = AlarmSchedulerScreen.TagSelection.route) {
            val schedulerViewModel: SchedulerViewModel = it.sharedViewModel(navController)
            val tagsUiState by schedulerViewModel.tagsUiState.collectAsStateWithLifecycle()
            Log.d(
                "manish",
                "schedulerNavigation: ${tagsUiState.tagsList} viewmodel${schedulerViewModel}"
            )
            TagsScreen(
                onNavBack = { navController.popBackStack() },
                tagsEvent = schedulerViewModel::tagsEvent,
                tagsUiState = tagsUiState
            )
        }
        composable(route = AlarmSchedulerScreen.IntervalSettingsSelection.route) {
            val schedulerViewModel: SchedulerViewModel = it.sharedViewModel(navController)
            val timeSettingUiState = schedulerViewModel.timeSettingUiState.value
            val list by schedulerViewModel.variantIntervalsList.collectAsStateWithLifecycle()
            Log.d("manish", "schedulerNavigation:viewmodel${schedulerViewModel}")
            TimeSettingScreen(
                list = list,
                timeSettingUiState = timeSettingUiState,
                tSEvent = schedulerViewModel::tSEvent,
                navBack = { navController.popBackStack() }
            )
        }
    }
}