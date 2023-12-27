package fit.asta.health.feature.settings

import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
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
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
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

                when (deleteAccountState) {
                    is UiState.Loading -> {
                        AppCircularProgressIndicator()
                    }

                    is UiState.Success -> {
                        settingsViewModel.resetDeleteState()
                        onSettingsUiEvent(SettingsUiEvent.NavigateToAuthScreen)
                    }

                    is UiState.ErrorRetry -> {
                        AppErrorScreen(text = (deleteAccountState as UiState.ErrorRetry).resId.toStringFromResId()) {
                            settingsViewModel.deleteAccount()
                        }
                    }

                    is UiState.NoInternet -> {
                        AppInternetErrorDialog {
                            settingsViewModel.deleteAccount()
                        }
                    }

                    is UiState.ErrorMessage -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(
                                context,
                                (deleteAccountState as UiState.ErrorMessage).resId.toStringFromResId(
                                    context
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
                            settingsViewModel.resetDeleteState()
                        }
                    }

                    else -> {}
                }

                val userLogoutState by settingsViewModel.logoutState.collectAsStateWithLifecycle()
                when (userLogoutState) {
                    is UiState.Loading -> {
                        AppCircularProgressIndicator()
                    }

                    is UiState.Success -> {
                        settingsViewModel.resetLogoutState()
                        onSettingsUiEvent(SettingsUiEvent.NavigateToAuthScreen)
                    }

                    is UiState.ErrorRetry -> {
                        AppErrorScreen(text = (userLogoutState as UiState.ErrorRetry).resId.toStringFromResId()) {
                            settingsViewModel.logoutUser()
                        }
                    }

                    is UiState.NoInternet -> {
                        AppInternetErrorDialog {
                            settingsViewModel.logoutUser()
                        }
                    }

                    is UiState.ErrorMessage -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(
                                context,
                                (userLogoutState as UiState.ErrorMessage).resId.toStringFromResId(
                                    context
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
                            settingsViewModel.resetLogoutState()
                        }

                    }

                    else -> {}
                }
                val selectedTheme by settingsViewModel.selectedTheme.collectAsStateWithLifecycle()

                SettingsHomeScreen(
                    appVersionNumber = context.getCurrentBuildVersion(),
                    selectedTheme = selectedTheme,
                    onUiEvent = {
                        when (it) {
                            is SettingsUiEvent.SetTheme -> {
                                settingsViewModel.setSelectedTheme(it.theme)
                            }

                            SettingsUiEvent.SIGNOUT -> {
                                settingsViewModel.logoutUser()
                            }

                            SettingsUiEvent.DELETE -> {
                                settingsViewModel.deleteAccount()
                            }

                            SettingsUiEvent.NOTIFICATION -> {
                                navController.navigate(SettingDestination.Notifications.route)
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