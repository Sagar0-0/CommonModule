package fit.asta.health.settings.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.ResourcesProvider
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val prefManager: PrefManager,
    private val firebaseMessaging: FirebaseMessaging
) : ViewModel() {

    var isAllNotificationOn by mutableStateOf(true)
    var isReminderAlarmOn by mutableStateOf(true)
    var isActivityTipsOn by mutableStateOf(true)
    var isGoalProgressTipsOn by mutableStateOf(true)
    var isGoalAdjustmentOn by mutableStateOf(true)
    var isGoalsCompletedOn by mutableStateOf(true)
    var isNewReleaseOn by mutableStateOf(true)
    var isHealthTipsOn by mutableStateOf(true)
    var isPromotionsOn by mutableStateOf(true)


    init {
        getAllNotificationsStatus()
    }

    private fun getAllNotificationsStatus() {
//        isReminderAlarmOn = prefManager TODO: NEED ALL...
    }

    fun onSwitchToggle(key: String) {
        when (key) {
            resourcesProvider.getString(R.string.user_pref_notification_key) -> {
                isAllNotificationOn = !isAllNotificationOn
            }

            resourcesProvider.getString(R.string.user_pref_reminder_alarm_key) -> {
                isReminderAlarmOn = !isReminderAlarmOn
            }

            resourcesProvider.getString(R.string.user_pref_activity_tips_key) -> {
                isActivityTipsOn = !isActivityTipsOn
            }

            resourcesProvider.getString(R.string.user_pref_goal_progress_tips_key) -> {
                isGoalProgressTipsOn = !isGoalProgressTipsOn
            }

            resourcesProvider.getString(R.string.user_pref_goal_adjustment_key) -> {
                isGoalAdjustmentOn = !isGoalAdjustmentOn
            }

            resourcesProvider.getString(R.string.user_pref_goals_completed_key) -> {
                isGoalsCompletedOn = !isGoalsCompletedOn
            }

            resourcesProvider.getString(R.string.user_pref_new_release_key) -> {
                isNewReleaseOn = !isNewReleaseOn
                if (isNewReleaseOn)
                    subscribeTopic(key)
                else
                    unSubscribeTopic(key)
            }

            resourcesProvider.getString(R.string.user_pref_health_tips_key) -> {
                isHealthTipsOn = !isHealthTipsOn
                if (isHealthTipsOn)
                    subscribeTopic(key)
                else
                    unSubscribeTopic(key)
            }

            resourcesProvider.getString(R.string.user_pref_promotions_key) -> {
                isPromotionsOn = !isPromotionsOn
                if (isPromotionsOn)
                    subscribeTopic(key)
                else
                    unSubscribeTopic(key)
            }
        }
    }

    private fun subscribeTopic(topic: String) {

        firebaseMessaging.subscribeToTopic(topic)
            .addOnFailureListener {

                /*val msg = getString(R.string.message_subscribe_failed)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()*/
                Log.e("subscribeTopic: ", topic + " - " + it.message)
            }
    }

    private fun unSubscribeTopic(topic: String) {

        firebaseMessaging.unsubscribeFromTopic(topic)
            .addOnFailureListener {

                /*val msg = getString(R.string.message_subscribe_failed)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()*/
                Log.e("unSubscribeTopic: ", topic + " - " + it.message)
            }
    }

}
