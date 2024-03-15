package fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen

import fit.asta.health.data.scheduler.local.model.AlarmInstance
import fit.asta.health.data.scheduler.util.Weekdays
import fit.asta.health.feature.scheduler.util.VibrationPattern
import java.util.Calendar


data class ASUiState(
    //time
    var timeHours: Int = 0,
    var timeMinutes: Int = 0,

    //week
    val week: Weekdays = Weekdays.NONE,

    //status
    var status: Boolean = true,

    //tag, label,description
    var alarmDescription: String = "",
    var alarmName: String = "",
    var tagName: String = "",
    var tagId: String = "",
    var tagUrl: String = "",

    //reminder mode
    var mode: String = "Notification",

    //tone
    var toneName: String = "default",
    var toneType: Int = 1,
    var toneUri: String = "",

    //important
    var important: Boolean = false,

    //vibration
    var vibration: String = "Select",
    var vibrationPattern: VibrationPattern = VibrationPattern.Short,
    var vibrationStatus: Boolean = false,


    //meta
    var cBy: Int = 1,
    var cDate: String = "2023-04-19 13:40:31.099528692 +0000 UTC m=+56311.057896229\"",
    var sync: Int = 1,
    var uDate: String = "1",

    //save
    val alarmList: List<AlarmInstance> = emptyList(),
    var selectedStartDateMillis: Long = Calendar.getInstance().timeInMillis,
    var selectedEndDateMillis: Long? = null,
)


data class IvlUiState(
    val advancedReminder: AdvUiState = AdvUiState(),
    val snoozeTime: Int = 5,
    val statusEnd: Boolean = false,
    val endAlarmTime: TimeUi = TimeUi()
)

data class TimeUi(
    var hours: Int = 0,
    var minutes: Int = 0
)

data class AdvUiState(
    val status: Boolean = true,
    val time: Int = 0
)

data class ToneUiState(
    val name: String = "",
    val type: Int = 1,
    val uri: String = ""
)


