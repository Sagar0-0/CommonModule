package fit.asta.health.settings

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.messaging.FirebaseMessaging
import fit.asta.health.R
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.common.utils.deleteAccount
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.common.utils.getPublicStorageUrl
import fit.asta.health.common.utils.rateUs
import fit.asta.health.common.utils.sendBugReportMessage
import fit.asta.health.common.utils.sendFeedbackMessage
import fit.asta.health.common.utils.shareApp
import fit.asta.health.common.utils.showUrlInBrowser
import fit.asta.health.common.utils.signOut


private const val TITLE_TAG = "settingsActivityTitle"

class SettingsActivity : AppCompatActivity(),
    PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private var snackbarMsg by mutableStateOf<String?>(null)

    companion object {

        const val NOTIFY_CHANGE = 578
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        setContent {
            AppTheme {
                val navController = rememberNavController()
                SettingsNavHost(navController)
            }

        }
    }

    @Composable
    fun SettingsNavHost(navHostController: NavHostController) {
        NavHost(navController = navHostController, startDestination = "main") {
            composable(route = "main") {
                SettingsScreenLayout(
                    snackbarMsg = snackbarMsg,
                    builtVersion = getCurrentBuildVersion(),
                    onClick = {
                        onUiClickEvent(it, navHostController)
                    }
                )
            }
            composable(route = "notif") {
                SettingsNotificationLayout(
                    onBackPress = { navHostController.navigateUp() },
                    onSwitchToggle = {
                        onSwitchToggle(it)
                    }
                )
            }
        }
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
                navHostController.navigate("notif")
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
                signOut(
                    showSnackBar = {
                        showSnackBar(it)
                    }
                )
            }
            SettingsUiEvent.DELETE -> {
                deleteAccount(
                    showSnackBar = {
                        showSnackBar(it)
                    }
                )
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
            SettingsUiEvent.VERSION -> {
                //TODO: OnVersionClick
            }
        }
    }

    private fun showSnackBar(msg: String) {
        snackbarMsg = msg
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {

        val args = pref.extras
        val fragment = supportFragmentManager.fragmentFactory
            .instantiate(classLoader, pref.fragment!!).apply {

                arguments = args
                setTargetFragment(caller, 0)
            }

        // Replace the existing Fragment with the new Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.settingsFragContainer, fragment)
            .addToBackStack(null)
            .commit()

        findViewById<MaterialToolbar>(R.id.settingsToolbar).title = pref.title
        return true
    }

    fun notifyChange() {
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
