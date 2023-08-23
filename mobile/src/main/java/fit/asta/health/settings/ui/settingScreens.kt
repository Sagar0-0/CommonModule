package fit.asta.health.settings.ui

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.feature.auth.vm.AuthViewModel
import fit.asta.health.main.Graph
import fit.asta.health.settings.data.SettingsNotificationsStatus
import fit.asta.health.settings.ui.view.SettingsNotificationLayout
import fit.asta.health.settings.ui.view.SettingsScreenLayout
import fit.asta.health.settings.ui.view.SettingsUiEvent
import fit.asta.health.settings.ui.vm.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.settingScreens(
    onSettingsUiEvent: (SettingsUiEvent) -> Unit
) {
    navigation(
        route = Graph.Settings.route,
        startDestination = SettingDestination.Main.route
    ) {
        composable(route = SettingDestination.Main.route) {
            val context = LocalContext.current
            val authViewModel: AuthViewModel = hiltViewModel()
            val deleteAccountState by authViewModel.deleteState.collectAsStateWithLifecycle()
            LaunchedEffect(deleteAccountState) {
                when (deleteAccountState) {
                    is UiState.Success -> {
                        authViewModel.resetDeleteState()
                        onSettingsUiEvent(SettingsUiEvent.NavigateToAuth)
                    }

                    is UiState.Error -> {
                        Toast.makeText(
                            context,
                            (deleteAccountState as UiState.Error).resId.toStringFromResId(context),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }

            val logoutState by authViewModel.logoutState.collectAsStateWithLifecycle()
            LaunchedEffect(logoutState) {
                when (logoutState) {
                    is UiState.Success -> {
                        authViewModel.resetLogoutState()
                        onSettingsUiEvent(SettingsUiEvent.NavigateToAuth)
                    }

                    is UiState.Error -> {
                        Toast.makeText(
                            context,
                            (logoutState as UiState.Error).resId.toStringFromResId(context),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }

            SettingsScreenLayout(
                builtVersion = context.getCurrentBuildVersion(),
                onClickEvent = {
                    when (it) {
                        SettingsUiEvent.SIGNOUT -> {
                            authViewModel.logout()
                        }

                        SettingsUiEvent.DELETE -> {
                            authViewModel.deleteAccount()
                        }

                        else -> {
                            onSettingsUiEvent(it)
                        }
                    }
                }
            )
        }
        composable(route = SettingDestination.Notifications.route) {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
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

internal sealed class SettingDestination(val route: String) {
    data object Main : SettingDestination("ss_main")
    data object Notifications : SettingDestination("ss_notif")
}