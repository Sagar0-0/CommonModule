package fit.asta.health.feature.scheduler.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.common.utils.Constants.HourMinAmPmKey
import fit.asta.health.common.utils.Constants.SCHEDULER_GRAPH_ROUTE
import fit.asta.health.common.utils.HourMinAmPm
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AlarmSettingEvent
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AlarmSettingScreen
import fit.asta.health.feature.scheduler.ui.screen.tagscreen.TagsEvent
import fit.asta.health.feature.scheduler.ui.screen.tagscreen.TagsScreen
import fit.asta.health.feature.scheduler.ui.screen.timesettingscreen.TimeSettingEvent
import fit.asta.health.feature.scheduler.ui.screen.timesettingscreen.TimeSettingScreen
import fit.asta.health.feature.scheduler.ui.viewmodel.SchedulerViewModel

fun NavController.navigateToScheduler(navOptions: NavOptions? = null) {
    this.navigate(SCHEDULER_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.schedulerNavigation(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = SCHEDULER_GRAPH_ROUTE,
        startDestination = AlarmSchedulerScreen.AlarmSettingHome.route
    ) {
        composable(route = AlarmSchedulerScreen.AlarmSettingHome.route) {
            val schedulerViewModel: SchedulerViewModel = it.sharedViewModel(navController)
            val alarmSettingUiState by schedulerViewModel.alarmSettingUiState.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = it) {
                if (((alarmSettingUiState.timeHours * 60) + alarmSettingUiState.timeMinutes) <= 0) {
                    schedulerViewModel.setHourMin(
                        navController.previousBackStackEntry?.savedStateHandle?.get<HourMinAmPm>(key = HourMinAmPmKey)
                    )
                }
            }
            AlarmSettingScreen(
                alarmSettingUiState = alarmSettingUiState,
                aSEvent = { uiEvent ->
                    when (uiEvent) {
                        is AlarmSettingEvent.SetAlarmTime -> {
                            schedulerViewModel.setAlarmTime(uiEvent.time)
                        }

                        is AlarmSettingEvent.SetWeek -> {
                            schedulerViewModel.setWeek(uiEvent.week)
                        }

                        is AlarmSettingEvent.SetStatus -> {
                            schedulerViewModel.setStatus(uiEvent.status)
                        }

                        is AlarmSettingEvent.SetLabel -> {
                            schedulerViewModel.setLabel(uiEvent.label)
                        }

                        is AlarmSettingEvent.SetDescription -> {
                            schedulerViewModel.setDescription(uiEvent.description)
                        }

                        is AlarmSettingEvent.SetReminderMode -> {
                            schedulerViewModel.setReminderMode(uiEvent.choice)
                        }

                        is AlarmSettingEvent.SetVibration -> {
                            schedulerViewModel.setVibration(uiEvent.choice)
                        }

                        is AlarmSettingEvent.SetVibrationIntensity -> {
                            schedulerViewModel.setVibrationPattern(uiEvent.vibration)
                        }

                        is AlarmSettingEvent.SetSound -> {
                            schedulerViewModel.setSound(uiEvent.tone)
                        }

                        is AlarmSettingEvent.SetImportant -> {
                            schedulerViewModel.setImportant(uiEvent.important)
                        }

                        is AlarmSettingEvent.Save -> {
                            schedulerViewModel.setDataAndSaveAlarm(uiEvent.context)
                        }

                        is AlarmSettingEvent.ResetUi -> {
                            schedulerViewModel.resetUi()
                        }
                    }
                },
                navTagSelection = { navController.navigate(route = AlarmSchedulerScreen.TagSelection.route) },
                navTimeSetting = {
                    navController.navigate(route = AlarmSchedulerScreen.IntervalSettingsSelection.route)
                },
                navBack = onBack,
            )
        }

        composable(route = AlarmSchedulerScreen.TagSelection.route) {
            val schedulerViewModel: SchedulerViewModel = it.sharedViewModel(navController)
            val tagsList by schedulerViewModel.tagsList.collectAsStateWithLifecycle()
            val customTagList by schedulerViewModel.customTagList.collectAsStateWithLifecycle()
            TagsScreen(
                onNavBack = { navController.popBackStack() },
                tagsEvent = { uiEvent ->
                    when (uiEvent) {
                        is TagsEvent.DeleteTag -> {
                            schedulerViewModel.deleteTag(uiEvent.tag)
                        }

                        is TagsEvent.SelectedTag -> {
                            schedulerViewModel.selectedTag(uiEvent.tag)
                        }

                        is TagsEvent.UpdateTag -> {
                            schedulerViewModel.updateServerTag(uiEvent.label, uiEvent.url)
                        }

                        is TagsEvent.GetTag -> {
                            schedulerViewModel.getTagDataFromServer()
                        }
                    }
                },
                tagsList = tagsList,
                customTagList = customTagList
            )
        }
        composable(route = AlarmSchedulerScreen.IntervalSettingsSelection.route) {
            val schedulerViewModel: SchedulerViewModel = it.sharedViewModel(navController)
            val timeSettingUiState by schedulerViewModel.timeSettingUiState.collectAsStateWithLifecycle()
            TimeSettingScreen(
                timeSettingUiState = timeSettingUiState,
                tSEvent = { uiEvent ->
                    when (uiEvent) {
                        is TimeSettingEvent.SetSnooze -> {
                            schedulerViewModel.setSnoozeTime(uiEvent.time)
                        }

                        is TimeSettingEvent.SetAdvancedDuration -> {
                            schedulerViewModel.setPreNotificationDuration(uiEvent.time)
                        }

                        is TimeSettingEvent.SetAdvancedStatus -> {
                            schedulerViewModel.setPreNotificationStatus(uiEvent.choice)
                        }

                        is TimeSettingEvent.SetEndAlarm -> {
                            schedulerViewModel.setEndAlarm(uiEvent.time)
                        }

                        is TimeSettingEvent.SetStatusEndAlarm -> {
                            schedulerViewModel.setStatusEndAlarm(uiEvent.choice)
                        }

                        is TimeSettingEvent.DeleteEndAlarm -> {
                            schedulerViewModel.deleteEndAlarm()
                        }
                    }
                },
                navBack = { navController.popBackStack() }
            )
        }
    }
}