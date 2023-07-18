package fit.asta.health.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import fit.asta.health.R
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsNotificationLayout(
    onBackPress: () -> Unit,
    onSwitchToggle: (key: String) -> Unit = {}
) {
    var isAllNotificationOn by remember { mutableStateOf(true) }
    var isReminderAlarmOn by remember { mutableStateOf(true) }
    var isActivityTipsOn by remember { mutableStateOf(true) }
    var isGoalProgressTipsOn by remember { mutableStateOf(true) }
    var isGoalAdjustmentOn by remember { mutableStateOf(true) }
    var isGoalsCompletedOn by remember { mutableStateOf(true) }
    var isNewReleaseOn by remember { mutableStateOf(true) }
    var isHealthTipsOn by remember { mutableStateOf(true) }
    var isPromotionsOn by remember { mutableStateOf(true) }

    val context = LocalContext.current
    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.title_notifications),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBackPress() }) {
                    Icon(
                        Icons.Outlined.NavigateBefore,
                        "back",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ), modifier = Modifier.shadow(elevation = cardElevation.medium)
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
