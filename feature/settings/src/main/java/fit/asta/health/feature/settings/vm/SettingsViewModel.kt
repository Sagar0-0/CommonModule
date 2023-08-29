package fit.asta.health.feature.settings.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResourcesProvider
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.datastore.PrefManager
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val prefManager: PrefManager,
    private val authRepo: AuthRepo,
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

    private val _logoutState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val logoutState = _logoutState.asStateFlow()

    private val _deleteState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val deleteState = _deleteState.asStateFlow()

    val theme = prefManager.userData
        .map { it.theme }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = "system",
        )


    init {
        getAllNotificationsStatus()
    }

    fun setTheme(newTheme: String) = viewModelScope.launch {
        prefManager.setTheme(newTheme)
    }

    fun logout() {
        _logoutState.value = UiState.Loading
        viewModelScope.launch {
            _logoutState.value = authRepo.signOut().toUiState()
            if (_logoutState.value is UiState.Success) {
                authRepo.setLogoutDone()
            }
        }

    }

    fun resetLogoutState() {
        _logoutState.value = UiState.Idle
    }

    fun deleteAccount() {
        _deleteState.value = UiState.Loading
        viewModelScope.launch {
            authRepo.deleteAccount().collect {
                _deleteState.value = it.toUiState()
                if (_deleteState.value is UiState.Success) {
                    authRepo.setLogoutDone()
                }
            }
        }
    }

    fun resetDeleteState() {
        _deleteState.value = UiState.Idle
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
