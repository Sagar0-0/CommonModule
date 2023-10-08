package fit.asta.health.feature.settings.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.AppTopBar
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.strings.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsNotificationLayout(
    settingsNotificationsStatus: SettingsNotificationsStatus,
    onBackPress: () -> Unit,
    onSwitchToggle: (key: String) -> Unit = {}
) {
    var isAllNotificationOn = settingsNotificationsStatus.isAllNotificationOn
    var isReminderAlarmOn = settingsNotificationsStatus.isReminderAlarmOn
    var isActivityTipsOn = settingsNotificationsStatus.isActivityTipsOn
    var isGoalProgressTipsOn = settingsNotificationsStatus.isGoalProgressTipsOn
    var isGoalAdjustmentOn = settingsNotificationsStatus.isGoalAdjustmentOn
    var isGoalsCompletedOn = settingsNotificationsStatus.isGoalsCompletedOn
    var isNewReleaseOn = settingsNotificationsStatus.isNewReleaseOn
    var isHealthTipsOn = settingsNotificationsStatus.isHealthTipsOn
    var isPromotionsOn = settingsNotificationsStatus.isPromotionsOn

    val context = LocalContext.current
    Column {
        AppTopBar(
            title = stringResource(id = R.string.title_notifications),
            onBack = onBackPress
        )

        LazyColumn(modifier = Modifier.padding(AppTheme.spacing.level3)) {
            item {
                SwitchItem(
                    imageVector = Icons.Default.Notifications,
                    text = stringResource(
                        id = R.string.user_pref_notification_cat_title
                    ),
                    isChecked = isAllNotificationOn,
                    onChange = {
                        isAllNotificationOn = it
                        onSwitchToggle(context.getString(R.string.user_pref_notification_key))
                    }
                )
                Divider()
            }

            item {
                TitleTexts.Level2(
                    text = stringResource(id = R.string.title_notifications),
                    modifier = Modifier.padding(
                        top = AppTheme.spacing.level3,
                        bottom = AppTheme.spacing.level2
                    )
                )

                SwitchItem(
                    imageVector = Icons.Default.Message,
                    text = stringResource(
                        id = R.string.user_pref_reminder_alarm_title
                    ),
                    subtitle = stringResource(
                        id = R.string.user_pref_reminder_alarm_summary
                    ),
                    isChecked = isAllNotificationOn && isReminderAlarmOn,
                    isEnabled = isAllNotificationOn,
                    onChange = {
                        isReminderAlarmOn = it
                        onSwitchToggle(context.getString(R.string.user_pref_reminder_alarm_key))
                    }
                )

                SwitchItem(
                    imageVector = Icons.Default.Message,
                    text = stringResource(
                        id = R.string.user_pref_activity_tips_title
                    ),
                    subtitle = stringResource(
                        id = R.string.user_pref_activity_tips_summary
                    ),
                    isChecked = isAllNotificationOn && isActivityTipsOn,
                    isEnabled = isAllNotificationOn,
                    onChange = {
                        isActivityTipsOn = it
                        onSwitchToggle(context.getString(R.string.user_pref_activity_tips_key))
                    }
                )

                SwitchItem(
                    imageVector = Icons.Default.Message,
                    text = stringResource(
                        id = R.string.user_pref_goal_progress_tips_title
                    ),
                    subtitle = stringResource(
                        id = R.string.user_pref_goal_progress_tips_summary
                    ),
                    isChecked = isAllNotificationOn && isGoalProgressTipsOn,
                    isEnabled = isAllNotificationOn,
                    onChange = {
                        isGoalProgressTipsOn = it
                        onSwitchToggle(context.getString(R.string.user_pref_goal_progress_tips_key))
                    }
                )

                SwitchItem(
                    imageVector = Icons.Default.Message,
                    text = stringResource(
                        id = R.string.user_pref_goal_adjustment_title
                    ),
                    subtitle = stringResource(
                        id = R.string.user_pref_goal_adjustment_summary
                    ),
                    isChecked = isAllNotificationOn && isGoalAdjustmentOn,
                    isEnabled = isAllNotificationOn,
                    onChange = {
                        isGoalAdjustmentOn = it
                        onSwitchToggle(context.getString(R.string.user_pref_goal_adjustment_key))
                    }
                )

                SwitchItem(
                    imageVector = Icons.Default.Message,
                    text = stringResource(
                        id = R.string.user_pref_goals_completed_title
                    ),
                    subtitle = stringResource(
                        id = R.string.user_pref_goals_completed_summary
                    ),
                    isChecked = isAllNotificationOn && isGoalsCompletedOn,
                    isEnabled = isAllNotificationOn,
                    onChange = {
                        isGoalsCompletedOn = it
                        onSwitchToggle(context.getString(R.string.user_pref_goals_completed_key))
                    }
                )

                SwitchItem(
                    imageVector = Icons.Default.NewReleases,
                    text = stringResource(
                        id = R.string.user_pref_new_release_title
                    ),
                    subtitle = stringResource(
                        id = R.string.user_pref_new_release_summary
                    ),
                    isChecked = isAllNotificationOn && isNewReleaseOn,
                    isEnabled = isAllNotificationOn,
                    onChange = {
                        isNewReleaseOn = it
                        onSwitchToggle(context.getString(R.string.user_pref_new_release_key))
                    }
                )

                SwitchItem(
                    imageVector = Icons.Default.HealthAndSafety,
                    text = stringResource(
                        id = R.string.user_pref_health_tips_title
                    ),
                    subtitle = stringResource(
                        id = R.string.user_pref_health_tips_summary
                    ),
                    isChecked = isAllNotificationOn && isHealthTipsOn,
                    isEnabled = isAllNotificationOn,
                    onChange = {
                        isHealthTipsOn = it
                        onSwitchToggle(context.getString(R.string.user_pref_health_tips_key))
                    }
                )

                SwitchItem(
                    imageVector = Icons.Default.LocalOffer,
                    text = stringResource(
                        id = R.string.user_pref_promotions_title
                    ),
                    subtitle = stringResource(
                        id = R.string.user_pref_promotions_summary
                    ),
                    isChecked = isAllNotificationOn && isPromotionsOn,
                    isEnabled = isAllNotificationOn,
                    onChange = {
                        isPromotionsOn = it
                        onSwitchToggle(context.getString(R.string.user_pref_promotions_key))
                    }
                )
            }
        }
    }
}

@Composable
fun SwitchItem(
    imageVector: ImageVector,
    text: String,
    subtitle: String? = null,
    isChecked: Boolean,
    isEnabled: Boolean = true,
    onChange: (newValue: Boolean) -> Unit
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        AppIcon(
            modifier = Modifier.padding(AppTheme.spacing.level1),
            imageVector = imageVector,
            contentDescription = ""
        )

        Column(
            Modifier
                .weight(1f)
                .padding(
                    top = AppTheme.spacing.level2,
                    bottom = AppTheme.spacing.level3,
                    start = AppTheme.spacing.level1
                )
        ) {
            TitleTexts.Level2(
                text = text
            )
            if (subtitle != null) {
                TitleTexts.Level2(
                    text = subtitle
                )
            }
        }

        Switch(
            checked = isChecked,
            onCheckedChange = { onChange(it) },
            modifier = Modifier.padding(bottom = AppTheme.spacing.level2),
            enabled = isEnabled
        )
    }
}
