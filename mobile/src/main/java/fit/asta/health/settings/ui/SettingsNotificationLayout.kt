package fit.asta.health.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import fit.asta.health.R
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.settings.data.SettingsNotificationsStatus

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

        LazyColumn(modifier = Modifier.padding(spacing.medium)) {
            item {
                SwitchItem(
                    painter = painterResource(id = R.drawable.ic_notifications),
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
                Text(
                    text = stringResource(id = R.string.title_notifications),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = spacing.medium, bottom = spacing.small)
                )

                SwitchItem(
                    painter = painterResource(id = R.drawable.messages),
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
                    painter = painterResource(id = R.drawable.messages),
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
                    painter = painterResource(id = R.drawable.messages),
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
                    painter = painterResource(id = R.drawable.messages),
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
                    painter = painterResource(id = R.drawable.messages),
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
                    painter = painterResource(id = R.drawable.ic_new_release),
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
                    painter = painterResource(id = R.drawable.ic_health_tips),
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
                    painter = painterResource(id = R.drawable.ic_offers),
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
    painter: Painter,
    text: String,
    subtitle: String? = null,
    isChecked: Boolean,
    isEnabled: Boolean = true,
    onChange: (newValue: Boolean) -> Unit
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.padding(spacing.extraSmall),
            painter = painter,
            contentDescription = ""
        )

        Column(
            Modifier
                .weight(1f)
                .padding(top = spacing.small, bottom = spacing.medium, start = spacing.extraSmall)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        Switch(
            checked = isChecked,
            onCheckedChange = { onChange(it) },
            modifier = Modifier.padding(bottom = spacing.small),
            enabled = isEnabled
        )
    }
}
