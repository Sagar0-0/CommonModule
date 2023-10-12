package fit.asta.health.feature.settings

import android.widget.Toast
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
import fit.asta.health.designsystem.molecular.AppRetryCard
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.feature.settings.view.SettingsNotificationLayout
import fit.asta.health.feature.settings.view.SettingsNotificationsStatus
import fit.asta.health.feature.settings.view.SettingsScreenLayout
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
                    is UiState.ErrorRetry -> {
                        AppRetryCard(text = (deleteAccountState as UiState.ErrorRetry).resId.toStringFromResId()) {
                            settingsViewModel.deleteAccount()
                        }
                    }

                    is UiState.Loading -> {
                        AppCircularProgressIndicator()
                    }

                    is UiState.Success -> {
                        settingsViewModel.resetDeleteState()
                        onSettingsUiEvent(SettingsUiEvent.NavigateToAuth)
                    }

                    is UiState.ErrorMessage -> {
                        Toast.makeText(
                            context,
                            (deleteAccountState as UiState.ErrorMessage).resId.toStringFromResId(
                                context
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                        settingsViewModel.resetDeleteState()
                    }

                    else -> {}
                }

                val logoutState by settingsViewModel.logoutState.collectAsStateWithLifecycle()
                when (logoutState) {
                    is UiState.ErrorRetry -> {
                        AppRetryCard(text = (logoutState as UiState.ErrorRetry).resId.toStringFromResId()) {
                            settingsViewModel.logout()
                        }
                    }

                    is UiState.Loading -> {
                        AppCircularProgressIndicator()
                    }

                    is UiState.Success -> {
                        settingsViewModel.resetLogoutState()
                        onSettingsUiEvent(SettingsUiEvent.NavigateToAuth)
                    }

                    is UiState.ErrorMessage -> {
                        Toast.makeText(
                            context,
                            (logoutState as UiState.ErrorMessage).resId.toStringFromResId(context),
                            Toast.LENGTH_SHORT
                        ).show()
                        settingsViewModel.resetLogoutState()
                    }

                    else -> {}
                }
                val theme by settingsViewModel.theme.collectAsStateWithLifecycle()

                SettingsScreenLayout(
                    builtVersion = context.getCurrentBuildVersion(),
                    theme = theme,
                    onClickEvent = {
                        when (it) {
                            is SettingsUiEvent.SetTheme -> {
                                settingsViewModel.setTheme(it.theme)
                            }

                            SettingsUiEvent.SIGNOUT -> {
                                settingsViewModel.logout()
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
                val status by remember {
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
                    settingsNotificationsStatus = status,
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
    data object Notifications : SettingDestination("ss_notif")
}