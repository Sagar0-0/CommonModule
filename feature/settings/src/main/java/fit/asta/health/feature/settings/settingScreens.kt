package fit.asta.health.feature.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.feature.settings.view.SettingsHomeScreen
import fit.asta.health.feature.settings.view.SettingsNotificationLayout
import fit.asta.health.feature.settings.view.SettingsNotificationsStatus
import fit.asta.health.feature.settings.view.SettingsUiEvent
import fit.asta.health.feature.settings.vm.SettingsViewModel

private const val SETTINGS_GRAPH_ROUTE = "graph_settings"
fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(SETTINGS_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.settingScreens(
    onSettingsUiEvent: (SettingsUiEvent) -> Unit
) {

    composable(route = SETTINGS_GRAPH_ROUTE) {
        val context = LocalContext.current
        val settingsViewModel: SettingsViewModel = hiltViewModel()
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            route = SETTINGS_GRAPH_ROUTE,
            startDestination = SettingDestination.Main.route
        ) {
            composable(route = SettingDestination.Main.route) {
                val deleteAccountState by settingsViewModel.deleteState.collectAsStateWithLifecycle()
                val selectedTheme by settingsViewModel.selectedTheme.collectAsStateWithLifecycle()
                val userLogoutState by settingsViewModel.logoutState.collectAsStateWithLifecycle()

                SettingsHomeScreen(
                    appVersionNumber = context.getCurrentBuildVersion(),
                    deleteAccountState = deleteAccountState,
                    userLogoutState = userLogoutState,
                    selectedTheme = selectedTheme,
                    onUiEvent = {
                        when (it) {
                            is SettingsUiEvent.SetTheme -> {
                                settingsViewModel.setSelectedTheme(it.theme)
                            }

                            SettingsUiEvent.Logout -> {
                                settingsViewModel.logoutUser()
                            }

                            SettingsUiEvent.DELETE -> {
                                settingsViewModel.deleteAccount()
                            }

                            SettingsUiEvent.NOTIFICATION -> {
                                navController.navigate(SettingDestination.Notifications.route)
                            }

                            SettingsUiEvent.ResetDeleteState -> {
                                settingsViewModel.resetDeleteState()
                            }

                            SettingsUiEvent.ResetLogoutState -> {
                                settingsViewModel.resetLogoutState()
                            }

                            else -> {
                                onSettingsUiEvent(it)
                            }
                        }
                    }
                )
            }

            composable(route = SettingDestination.Notifications.route) {
                val notificationPreferenceState by remember {
                    mutableStateOf(
                        SettingsNotificationsStatus(
                            isAllNotificationOn = settingsViewModel.isAllNotificationOn,
                            isReminderAlarmOn = settingsViewModel.isReminderAlarmOn,
                            isActivityTipsOn = settingsViewModel.isActivityTipsOn,
                            isGoalProgressTipsOn = settingsViewModel.isGoalProgressTipsOn,
                            isGoalAdjustmentOn = settingsViewModel.isGoalAdjustmentOn,
                            isGoalsCompletedOn = settingsViewModel.isGoalsCompletedOn,
                            isNewReleaseOn = settingsViewModel.isNewReleaseOn,
                            isHealthTipsOn = settingsViewModel.isHealthTipsOn,
                            isPromotionsOn = settingsViewModel.isPromotionsOn,
                        )
                    )
                }
                SettingsNotificationLayout(
                    settingsNotificationsStatus = notificationPreferenceState,
                    onBackPress = {
                        onSettingsUiEvent(SettingsUiEvent.BACK)
                    },
                    onSwitchToggle = settingsViewModel::onSwitchToggle
                )
            }
        }
    }
}

internal sealed class SettingDestination(val route: String) {
    data object Main : SettingDestination("ss_main")
    data object Notifications : SettingDestination("ss_notification")
}