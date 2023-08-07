package fit.asta.health.settings.view

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.R
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.common.utils.rateUs
import fit.asta.health.common.utils.sendBugReportMessage
import fit.asta.health.common.utils.shareApp
import fit.asta.health.common.utils.showUrlInBrowser
import fit.asta.health.main.Graph
import fit.asta.health.settings.data.SettingsNotificationsStatus
import fit.asta.health.settings.data.SettingsUiEvent
import fit.asta.health.settings.data.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.settingScreens(
    navController: NavHostController
) {
    navigation(
        route = Graph.Settings.route,
        startDestination = SettingScreens.Main.route
    ) {
        composable(route = SettingScreens.Main.route) {
            val context = LocalContext.current
            val authViewModel: AuthViewModel = hiltViewModel()

            val onUiClickEvent: (key: SettingsUiEvent) -> Unit = { key ->
                when (key) {
                    SettingsUiEvent.SUBSCRIBE -> {
                        navController.navigate(Graph.Subscription.route)
                    }

                    SettingsUiEvent.REFERRAL -> {
                        navController.navigate(Graph.Referral.route)
                    }

                    SettingsUiEvent.WALLET -> {
                        navController.navigate(Graph.Wallet.route)
                    }
                    SettingsUiEvent.ADDRESS -> {
                        navController.navigate(Graph.Address.route)
                    }

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
                        navController.navigate(Graph.Feedback.route + "/application")
                    }

                    SettingsUiEvent.SIGNOUT -> {
                        authViewModel.logout(
                            context = context,
                            onSuccess = {
                                navController.navigate(Graph.Authentication.route) {
                                    popUpToTop(navController)
                                }
                            },
                            onFailure = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                        )
                    }

                    SettingsUiEvent.DELETE -> {
                        authViewModel.deleteAccount(
                            onSuccess = {
                                navController.navigate(Graph.Authentication.route) {
                                    popUpToTop(navController)
                                }
                            },
                            onFailure = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

                    SettingsUiEvent.BUG -> {
                        context.sendBugReportMessage()
                    }

                    SettingsUiEvent.TERMS -> {
                        context.showUrlInBrowser(
                            getImgUrl(context.getString(R.string.url_terms_of_use))
                        )
                    }

                    SettingsUiEvent.PRIVACY -> {
                        context.showUrlInBrowser(
                            getImgUrl(context.getString(R.string.url_privacy_policy))
                        )
                    }

                    SettingsUiEvent.VERSION -> {
                        Toast.makeText(
                            context,
                            context.getCurrentBuildVersion(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }


            SettingsScreenLayout(
                builtVersion = context.getCurrentBuildVersion(),
                onClickEvent = onUiClickEvent
            )
        }
        composable(route = SettingScreens.Notifications.route) {
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
                onBackPress = navController::navigateUp,
                onSwitchToggle = settingsViewModel::onSwitchToggle
            )
        }
    }

}

sealed class SettingScreens(val route: String) {
    object Main : SettingScreens("ss_main")
    object Notifications : SettingScreens("ss_notif")
}