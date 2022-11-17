package fit.asta.health.scheduler.compose

sealed class SchedulerScreen(val route: String) {
    object AlarmSettingHome : SchedulerScreen(route = "alarm_setting_home")
    object TagSelection : SchedulerScreen(route = "tag_selection")
    object CustomTagCreation : SchedulerScreen(route = "custom_tag_creation")
    object LabelSelection : SchedulerScreen(route = "label_selection")
    object DescSelection : SchedulerScreen(route = "desc_selection")
    object IntervalSettingsSelection : SchedulerScreen(route = "interval_selection")
    object ReminderModeSelection : SchedulerScreen(route = "reminder_mode_selection")
    object VibrationSelection : SchedulerScreen(route = "vibration_selection")
    object SoundSelection : SchedulerScreen(route = "sound_selection")
    object SnoozeSelection : SchedulerScreen(route = "snooze_selection")
    object RepetitiveInterval : SchedulerScreen(route = "repetitive_interval")
}