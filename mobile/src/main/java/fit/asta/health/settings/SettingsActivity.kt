package fit.asta.health.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.common.utils.getPublicStorageUrl
import fit.asta.health.common.utils.rateUs
import fit.asta.health.common.utils.sendBugReportMessage
import fit.asta.health.common.utils.sendFeedbackMessage
import fit.asta.health.common.utils.shareApp
import fit.asta.health.common.utils.showUrlInBrowser
import fit.asta.health.settings.data.SettingsUiEvent
import fit.asta.health.settings.ui.SettingsNotificationLayout
import fit.asta.health.settings.ui.SettingsScreenLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    companion object {
        fun launch(context: Context) {
            Intent(context, SettingsActivity::class.java)
                .apply {
                    context.startActivity(this)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val navController = rememberNavController()
                SettingsNavHost(navController)
            }

        }
    }

    @Composable
    fun SettingsNavHost(navHostController: NavHostController) {
        NavHost(navController = navHostController, startDestination = SettingScreens.Main.route) {
            composable(route = SettingScreens.Main.route) {
                SettingsScreenLayout(
                    builtVersion = getCurrentBuildVersion(),
                    onClickEvent = {
                        onUiClickEvent(it, navHostController)
                    }
                )
            }
            composable(route = SettingScreens.Notifications.route) {
                SettingsNotificationLayout(
                    onBackPress = { navHostController.navigateUp() },
                    onSwitchToggle = {
                        onSwitchToggle(it)
                    }
                )
            }
        }
    }

    private sealed class SettingScreens(val route: String) {
        object Main : SettingScreens("main")
        object Notifications : SettingScreens("notif")

    }

    private fun onSwitchToggle(key: String) {
        when (key) {
            resources.getString(R.string.user_pref_master_notification_key) -> {
                notifyChange()
            }

            resources.getString(R.string.user_pref_new_release_key) -> {

                if (PrefUtils.getNewReleaseNotification(this))
                    subscribeTopic(key)
                else
                    unSubscribeTopic(key)
            }

            resources.getString(R.string.user_pref_health_tips_key) -> {

                if (PrefUtils.getHealthTipsNotification(this))
                    subscribeTopic(key)
                else
                    unSubscribeTopic(key)
            }

            resources.getString(R.string.user_pref_promotions_key) -> {

                if (PrefUtils.getPromotionsNotification(this))
                    subscribeTopic(key)
                else
                    unSubscribeTopic(key)
            }
        }
    }

    private fun onUiClickEvent(key: SettingsUiEvent, navHostController: NavHostController) {
        when (key) {
            SettingsUiEvent.BACK -> {
                finish()
            }

            SettingsUiEvent.NOTIFICATION -> {
                navHostController.navigate(SettingScreens.Notifications.route)
            }

            SettingsUiEvent.SHARE -> {
                shareApp()
            }

            SettingsUiEvent.RATE -> {
                rateUs()
            }


            SettingsUiEvent.FEEDBACK -> {
                sendFeedbackMessage()
            }

            SettingsUiEvent.SIGNOUT -> {
                authViewModel.logout(this) {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }

            SettingsUiEvent.DELETE -> {
                authViewModel.deleteAccount(this) {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }

            SettingsUiEvent.BUG -> {
                sendBugReportMessage()
            }

            SettingsUiEvent.TERMS -> {
                showUrlInBrowser(
                    getPublicStorageUrl(
                        this, resources.getString(R.string.url_terms_of_use)
                    )
                )
            }

            SettingsUiEvent.PRIVACY -> {
                showUrlInBrowser(
                    getPublicStorageUrl(
                        this,
                        resources.getString(R.string.url_privacy_policy)
                    )
                )
            }

            SettingsUiEvent.VERSION -> {}
        }
    }

    private fun notifyChange() {
        setResult(Activity.RESULT_OK, null)
    }

    private fun subscribeTopic(topic: String) {

        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnFailureListener {

                /*val msg = getString(R.string.message_subscribe_failed)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()*/
                Log.e("subscribeTopic: ", topic + " - " + it.message)
            }
    }

    private fun unSubscribeTopic(topic: String) {

        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            .addOnFailureListener {

                /*val msg = getString(R.string.message_subscribe_failed)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()*/
                Log.e("unSubscribeTopic: ", topic + " - " + it.message)
            }
    }
}
