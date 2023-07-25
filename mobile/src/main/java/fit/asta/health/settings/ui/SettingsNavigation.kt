package fit.asta.health.settings.ui

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.R
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.common.utils.getPublicStorageUrl
import fit.asta.health.common.utils.rateUs
import fit.asta.health.common.utils.sendBugReportMessage
import fit.asta.health.common.utils.sendFeedbackMessage
import fit.asta.health.common.utils.shareApp
import fit.asta.health.common.utils.showUrlInBrowser
import fit.asta.health.main.Graph
import fit.asta.health.main.sharedViewModel
import fit.asta.health.settings.data.SettingsNotificationsStatus
import fit.asta.health.settings.data.SettingsUiEvent
import fit.asta.health.settings.data.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.SettingsNavigation(
    navController: NavHostController
) {
    navigation(
        route = Graph.Settings.route,
        startDestination = SettingScreens.Main.route
    ) {
        composable(route = SettingScreens.Main.route) {
            val context = LocalContext.current
            val authViewModel: AuthViewModel = it.sharedViewModel(navController)

            val onUiClickEvent: (key: SettingsUiEvent) -> Unit = { key ->
                when (key) {
                    SettingsUiEvent.BACK -> {
                        navController.navigateUp()
                    }

                    SettingsUiEvent.NOTIFICATION -> {
                        navController.navigate(SettingScreens.Notifications.route)
                    }

                    SettingsUiEvent.SHARE -> {
                        context.shareApp()
                    }

                    SettingsUiEvent.RATE -> {
                        context.rateUs()
                    }


                    SettingsUiEvent.FEEDBACK -> {
                        context.sendFeedbackMessage()
                    }

                    SettingsUiEvent.SIGNOUT -> {
                        authViewModel.logout(context) {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }

                    SettingsUiEvent.DELETE -> {
                        authViewModel.deleteAccount(context) {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }

                    SettingsUiEvent.BUG -> {
                        context.sendBugReportMessage()
                    }

                    SettingsUiEvent.TERMS -> {
                        context.showUrlInBrowser(
                            getPublicStorageUrl(
                                context, context.getString(R.string.url_terms_of_use)
                            )
                        )
                    }

                    SettingsUiEvent.PRIVACY -> {
                        context.showUrlInBrowser(
                            getPublicStorageUrl(
                                context,
                                context.getString(R.string.url_privacy_policy)
                            )
                        )
                    }

                    SettingsUiEvent.VERSION -> {}
                }
            }

            SettingsScreenLayout(
                builtVersion = context.getCurrentBuildVersion(),
                onClickEvent = {
                    onUiClickEvent(it)
                }
            )
        }
        composable(route = SettingScreens.Notifications.route) {
            val settingsViewModel: SettingsViewModel = it.sharedViewModel(navController)
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
                onBackPress = { navController.navigateUp() },
                onSwitchToggle = settingsViewModel::onSwitchToggle
            )
        }
    }

}

sealed class SettingScreens(val route: String) {
    object Main : SettingScreens("main")
    object Notifications : SettingScreens("notif")
}