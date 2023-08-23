package fit.asta.health.scheduler.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.main.Graph
import fit.asta.health.main.sharedViewModel
import fit.asta.health.navigation.today.ui.view.utils.HourMinAmPm
import fit.asta.health.navigation.today.ui.view.utils.Utils
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.AlarmSettingEvent
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.AlarmSettingScreen
import fit.asta.health.scheduler.ui.screen.tagscreen.TagsEvent
import fit.asta.health.scheduler.ui.screen.tagscreen.TagsScreen
import fit.asta.health.scheduler.ui.screen.timesettingscreen.TimeSettingEvent
import fit.asta.health.scheduler.ui.screen.timesettingscreen.TimeSettingScreen
import fit.asta.health.scheduler.ui.viewmodel.SchedulerViewModel

fun NavGraphBuilder.schedulerNavigation(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = Graph.Scheduler.route,
        startDestination = AlarmSchedulerScreen.AlarmSettingHome.route
    ) {
        composable(route = AlarmSchedulerScreen.AlarmSettingHome.route) {
            val schedulerViewModel: SchedulerViewModel = it.sharedViewModel(navController)
            LaunchedEffect(key1 = it) {
                schedulerViewModel.setHourMin(
                    navController.previousBackStackEntry?.savedStateHandle?.get<HourMinAmPm>(key = Utils.HourMinAmPmKey)
                )
            }
            val alarmSettingUiState by schedulerViewModel.alarmSettingUiState.collectAsStateWithLifecycle()
            val uiError by schedulerViewModel.uiError.collectAsStateWithLifecycle()
            val areInputsValid by schedulerViewModel.areInputsValid.collectAsStateWithLifecycle()
            AlarmSettingScreen(
                alarmSettingUiState = alarmSettingUiState,
                uiError = uiError,
                areInputsValid = areInputsValid,
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

                        is AlarmSettingEvent.GotoTagScreen -> {
                            schedulerViewModel.getTagData()
                        }

                        is AlarmSettingEvent.GotoTimeSettingScreen -> {

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
            val tagsUiState by schedulerViewModel.tagsUiState.collectAsStateWithLifecycle()
            TagsScreen(
                onNavBack = { navController.popBackStack() },
                tagsEvent = { uiEvent ->
                    when (uiEvent) {
                        is TagsEvent.DeleteTag -> {
                            schedulerViewModel.deleteTag(uiEvent.tag)
                        }

                        is TagsEvent.UndoTag -> {
                            schedulerViewModel.undoTag(uiEvent.tag)
                        }

                        is TagsEvent.SelectedTag -> {
                            schedulerViewModel.selectedTag(uiEvent.tag)
                        }

                        is TagsEvent.UpdateTag -> {
                            schedulerViewModel.updateServerTag(uiEvent.label, uiEvent.url)
                        }
                    }
                },
                tagsUiState = tagsUiState
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

                        is TimeSettingEvent.Save -> {
                        }
                    }
                },
                navBack = { navController.popBackStack() }
            )
        }
    }
}