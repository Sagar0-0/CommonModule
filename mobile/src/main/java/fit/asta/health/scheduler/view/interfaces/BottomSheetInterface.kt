package fit.asta.health.scheduler.view.interfaces

import fit.asta.health.scheduler.model.net.scheduler.Wk


//private var alarmTitle: String

interface BottomSheetInterface {
    fun onAlarmNameListener(alarmTitle: String)

    fun onAlarmDescriptionListener(alarmDescription: String)

    fun onAlarmReminderModeListener(alarmReminderMode: String)

    fun onAlarmVibrationModeListener(alarmVibrationPoints: String)

    fun onAlarmWeekListener(alarmWeek: Wk)
}