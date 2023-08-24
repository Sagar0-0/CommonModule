package fit.asta.health.feature.settings.view

data class SettingsNotificationsStatus(
    val isAllNotificationOn: Boolean,
    val isReminderAlarmOn: Boolean,
    val isActivityTipsOn: Boolean,
    val isGoalProgressTipsOn: Boolean,
    val isGoalAdjustmentOn: Boolean,
    val isGoalsCompletedOn: Boolean,
    val isNewReleaseOn: Boolean,
    val isHealthTipsOn: Boolean,
    val isPromotionsOn: Boolean
)
