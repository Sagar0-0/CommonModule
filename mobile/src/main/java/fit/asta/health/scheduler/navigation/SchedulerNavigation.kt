package fit.asta.health.scheduler.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.scheduler.compose.components.*
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AlarmSettingScreen
import fit.asta.health.scheduler.compose.screen.homescreen.HomeScreen
import fit.asta.health.scheduler.compose.screen.tagscreen.TagsScreen
import fit.asta.health.scheduler.compose.screen.timesettingscreen.TimeSettingScreen
import fit.asta.health.scheduler.viewmodel.SchedulerViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SchedulerNavigation(navController: NavHostController, schedulerViewModel: SchedulerViewModel) {

    NavHost(
        navController = navController,
        startDestination = AlarmSchedulerScreen.AlarmHome.route
    ) {

        composable(route = AlarmSchedulerScreen.AlarmHome.route) {
            val homeUiState = schedulerViewModel.homeUiState.value
            HomeScreen(
                homeUiState = homeUiState,
                hSEvent = schedulerViewModel::hSEvent,
                navAlarmSettingHome = {
                    navController.navigate(route = AlarmSchedulerScreen.AlarmSettingHome.route)
                })
        }
        composable(route = AlarmSchedulerScreen.AlarmSettingHome.route) {
            val alarmSettingUiState = schedulerViewModel.alarmSettingUiState.value
            AlarmSettingScreen(
                alarmSettingUiState = alarmSettingUiState,
                aSEvent = schedulerViewModel::aSEvent,
                navTagSelection = { navController.navigate(route = AlarmSchedulerScreen.TagSelection.route) },
                navTimeSetting = { navController.navigate(route = AlarmSchedulerScreen.IntervalSettingsSelection.route) },
                navBack = {navController.popBackStack()}
            )
        }

        composable(route = AlarmSchedulerScreen.TagSelection.route) {
            val tagsUiState = schedulerViewModel.tagsUiState.value
            TagsScreen(
                onNavBack = { navController.popBackStack() },
                tagsEvent = schedulerViewModel::tagsEvent,
                tagsUiState = tagsUiState
            )
        }
        composable(route = AlarmSchedulerScreen.IntervalSettingsSelection.route) {
            val timeSettingUiState = schedulerViewModel.timeSettingUiState.value
            val list by schedulerViewModel.variantIntervalsList.collectAsStateWithLifecycle()
            TimeSettingScreen(
                list = list,
                timeSettingUiState = timeSettingUiState,
                isIntervalDataValid = schedulerViewModel::isIntervalDataValid,
                tSEvent = schedulerViewModel::tSEvent,
                navBack = { navController.popBackStack() }
            )
        }

    }
}