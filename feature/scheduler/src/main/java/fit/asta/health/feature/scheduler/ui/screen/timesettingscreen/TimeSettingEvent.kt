package fit.asta.health.feature.scheduler.ui.screen.timesettingscreen

import android.content.Context
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.TimeUi

sealed class TimeSettingEvent {
    data class SetSnooze(val time: Int) : TimeSettingEvent()
    data class SetAdvancedDuration(val time: Int, val context: Context) : TimeSettingEvent()
    data class SetAdvancedStatus(val choice: Boolean) : TimeSettingEvent()
    data class SetStatusEndAlarm(val choice: Boolean) : TimeSettingEvent()
    data class SetEndAlarm(val time: TimeUi) : TimeSettingEvent()

    data object DeleteEndAlarm : TimeSettingEvent()
    data object Save : TimeSettingEvent()
}
