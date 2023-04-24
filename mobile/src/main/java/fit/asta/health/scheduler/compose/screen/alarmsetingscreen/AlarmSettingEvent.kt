package fit.asta.health.scheduler.compose.screen.alarmsetingscreen

import android.content.Context
import fit.asta.health.scheduler.model.net.scheduler.Time

sealed class AlarmSettingEvent{
    data class SetAlarmTime(val Time:Time) : AlarmSettingEvent()
    data class SetWeek(val week:Int) : AlarmSettingEvent()
    data class SetStatus(val status:Boolean) : AlarmSettingEvent()
    data class SetLabel(val label:String) : AlarmSettingEvent()
    data class SetDescription(val description:String) : AlarmSettingEvent()
    data class SetReminderMode(val choice: String) : AlarmSettingEvent()
    data class SetVibrationIntensity(val vibration: Float) : AlarmSettingEvent()
    data class SetVibration(val choice: Boolean) : AlarmSettingEvent()
    data class SetSound(val tone: ToneUiState) : AlarmSettingEvent()
    data class SetImportant(val important: Boolean) : AlarmSettingEvent()
    data class Save(val context: Context):AlarmSettingEvent()
    object GotoTagScreen:AlarmSettingEvent()
    object GotoTimeSettingScreen:AlarmSettingEvent()


}
