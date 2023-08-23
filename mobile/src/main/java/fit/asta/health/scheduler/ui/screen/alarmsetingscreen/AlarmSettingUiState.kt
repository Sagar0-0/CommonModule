package fit.asta.health.scheduler.ui.screen.alarmsetingscreen

import fit.asta.health.scheduler.data.db.entity.Weekdays
import fit.asta.health.scheduler.util.VibrationPattern


data class ASUiState(
    //time
    var timeHours: Int = 0,
    var timeMinutes: Int = 0,

    //week
    val week: Weekdays = Weekdays.NONE,

    //status
    var status: Boolean = false,

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
    val uiError: String = ""
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
    val status: Boolean = false,
    val time: Int = 0
)

data class ToneUiState(
    val name: String = "",
    val type: Int = 1,
    val uri: String = ""
)

data class AMPMHoursMin(
    val hours: Int = 1,
    val minutes: Int = 1,
    val dayTime: DayTime = DayTime.PM
) {
    enum class DayTime {
        AM,
        PM
    }
}

data class Time24hr(val hour: Int, val min: Int)
