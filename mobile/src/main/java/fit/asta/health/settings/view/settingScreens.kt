package fit.asta.health.settings.view

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import fit.asta.health.R
import fit.asta.health.auth.ui.navigateToAuth
import fit.asta.health.auth.ui.vm.AuthViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.common.utils.rateUs
import fit.asta.health.common.utils.sendBugReportMessage
import fit.asta.health.common.utils.shareApp
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.feedback.ui.navigateToFeedback
import fit.asta.health.main.Graph
import fit.asta.health.settings.data.SettingsNotificationsStatus
import fit.asta.health.settings.data.SettingsUiEvent
import fit.asta.health.settings.data.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.settingScreens(
    navController: NavHostController
) {
    navigation(
        route = Graph.Settings.route,
        startDestination = SettingDestination.Main.route
    ) {
        composable(route = SettingDestination.Main.route) {
            val context = LocalContext.current
            val authViewModel: AuthViewModel = hiltViewModel()
            val deleteAccountState by authViewModel.deleteState.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = deleteAccountState){
                when(deleteAccountState){
                    is UiState.Success->{
                        navController.navigateToAuth()
                    }
                    is UiState.Error->{
                        Toast.makeText(context, (deleteAccountState as UiState.Error).resId.toStringFromResId(context), Toast.LENGTH_SHORT).show()
                    }
                    else->{}
                }
            }

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
                        navController.navigate(SettingDestination.Notifications.route)
                    }

                    SettingsUiEvent.SHARE -> {
                        context.shareApp()
                    }

                    SettingsUiEvent.RATE -> {
                        context.rateUs()
                    }


                    SettingsUiEvent.FEEDBACK -> {
                        navController.navigateToFeedback(context.getString(R.string.application_fid))
                    }

                    SettingsUiEvent.SIGNOUT -> {
                        authViewModel.logout(
                            context = context,
                            onSuccess = {
                                navController.navigateToAuth()
                            },
                            onFailure = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                        )
                    }

                    SettingsUiEvent.DELETE -> {
                        authViewModel.deleteAccount()
                    }

                    SettingsUiEvent.BUG -> {
                        context.sendBugReportMessage()
                    }

                    SettingsUiEvent.TERMS -> {
                        val url = URLEncoder.encode(
                            getImgUrl(context.getString(R.string.url_terms_of_use)),
                            StandardCharsets.UTF_8.toString()
                        )
                        navController.navigate(Graph.WebView.route + "/$url")
                    }

                    SettingsUiEvent.PRIVACY -> {
                        val url = URLEncoder.encode(
                            getImgUrl(context.getString(R.string.url_privacy_policy)),
                            StandardCharsets.UTF_8.toString()
                        )
                        navController.navigate(Graph.WebView.route + "/$url")
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
                onBackPress = navController::navigateUp,
                onSwitchToggle = settingsViewModel::onSwitchToggle
            )
        }
    }

}

sealed class SettingDestination(val route: String) {
    object Main : SettingDestination("ss_main")
    object Notifications : SettingDestination("ss_notif")
}