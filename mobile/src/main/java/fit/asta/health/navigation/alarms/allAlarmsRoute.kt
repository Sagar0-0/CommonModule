package fit.asta.health.navigation.alarms

import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.Constants
import fit.asta.health.main.view.checkPermissionAndLaunchScheduler
import fit.asta.health.navigation.today.ui.view.AlarmEvent
import fit.asta.health.navigation.today.ui.view.AllAlarms


private const val ALL_ALARMS_ROUTE = "all_alarms"

fun NavController.navigateToAllAlarms() {
    this.navigate(ALL_ALARMS_ROUTE)
}

fun NavGraphBuilder.allAlarmsRoute(navController: NavController) {
    composable(ALL_ALARMS_ROUTE) {
        val vm: AllAlarmViewModel = hiltViewModel()
        val list by vm.alarmList.collectAsStateWithLifecycle()
        val context = LocalContext.current
        val checkPermissionAndLaunchScheduler =
            checkPermissionAndLaunchScheduler(context, navController)
        AllAlarms(
            list = list,
            onEvent = {
                when (it) {
                    is AlarmEvent.SetAlarmState -> {
                        vm.changeAlarmState(
                            state = it.state,
                            alarm = it.alarm,
                            context = it.context
                        )
                    }

                    is AlarmEvent.SetAlarm -> {
                        vm.setAlarmPreferences(999)
                    }

                    is AlarmEvent.EditAlarm -> {
                        vm.setAlarmPreferences(it.alarmId)
                        checkPermissionAndLaunchScheduler()
                    }

                    is AlarmEvent.NavSchedule -> {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = Constants.HourMinAmPmKey,
                            value = it.hourMinAmPm
                        )
                        checkPermissionAndLaunchScheduler()
                    }

                    is AlarmEvent.OnBack -> {
                        navController.popBackStack()
                    }
                }
            }
        )
    }
}