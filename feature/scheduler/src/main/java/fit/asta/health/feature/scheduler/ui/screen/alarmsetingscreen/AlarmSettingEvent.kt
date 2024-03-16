package fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen

import android.content.Context
import fit.asta.health.data.scheduler.remote.net.scheduler.Time
import fit.asta.health.feature.scheduler.util.VibrationPattern

sealed class AlarmSettingEvent {
    data class SetAlarmTime(val time: Time) :
        AlarmSettingEvent()

    data class SetWeek(val week: Int) : AlarmSettingEvent()
    data class SetStatus(val status: Boolean) : AlarmSettingEvent()
    data class SetLabel(val label: String) : AlarmSettingEvent()
    data class SetDescription(val description: String) : AlarmSettingEvent()
    data class SetReminderMode(val choice: String) : AlarmSettingEvent()
    data class SetVibrationIntensity(val vibration: VibrationPattern) : AlarmSettingEvent()
    data class SetVibration(val choice: Boolean) : AlarmSettingEvent()
    data class SetDateRange(val start: Long, val end: Long?) : AlarmSettingEvent()
    data class SetSound(val tone: ToneUiState) : AlarmSettingEvent()
    data class SetImportant(val important: Boolean) : AlarmSettingEvent()
    data class Save(val context: Context) : AlarmSettingEvent()
    data object ResetUi : AlarmSettingEvent()
    data object OnSound : AlarmSettingEvent()


}
