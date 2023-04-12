package fit.asta.health.scheduler.compose.screen.alarmsetingscreen

import fit.asta.health.scheduler.model.net.scheduler.Time
import fit.asta.health.scheduler.model.net.scheduler.Vib
import fit.asta.health.scheduler.model.net.scheduler.Wk

sealed class AlarmSettingEvent{
    data class SetAlarmTime(val Time:Time) : AlarmSettingEvent()
    data class SetWeek(val week:Wk) : AlarmSettingEvent()
    data class SetStatus(val status:Boolean) : AlarmSettingEvent()
    data class SetLabel(val label:String) : AlarmSettingEvent()
    data class SetDescription(val description:String) : AlarmSettingEvent()
    data class SetReminderMode(val choice: String) : AlarmSettingEvent()
    data class SetVibration(val vibration: Vib) : AlarmSettingEvent()
    data class SetSound(val sound:Vib) : AlarmSettingEvent()
    data class SetImportant(val important: Boolean) : AlarmSettingEvent()

}
