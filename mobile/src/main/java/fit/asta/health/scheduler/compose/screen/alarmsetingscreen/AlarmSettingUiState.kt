package fit.asta.health.scheduler.compose.screen.alarmsetingscreen

import fit.asta.health.scheduler.util.VibrationPattern


data class ASUiState(
    //time
    var timeHours: String = "0",
    var timeMidDay: Boolean = false,
    var timeMinutes: String = "0",

    //week
    var friday: Boolean = false,
    var monday: Boolean = false,
    var saturday: Boolean = false,
    var sunday: Boolean = false,
    var thursday: Boolean = false,
    var tuesday: Boolean = false,
    var wednesday: Boolean = false,
    var recurring: Boolean = false,

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
    val duration: Int = 0,
    val isRemainderAtTheEnd: Boolean = false,
    val repeatableInterval: RepUiState = RepUiState(),
    val snoozeTime: Int = 5,
    val staticIntervals: List<StatUiState> = emptyList(),
    val status: Boolean = false,
    val variantIntervals: List<StatUiState> = emptyList(),
    val isVariantInterval: Boolean = false
)

data class AdvUiState(
    val status: Boolean = false,
    val time: Int = 0
)

data class RepUiState(
    val time: Int = 0,
    val unit: String = ""
)


data class ToneUiState(
    val name: String = "",
    val type: Int = 1,
    val uri: String = ""
)


data class StatUiState(
    val hours: String = "",
    val midDay: Boolean = false,
    val minutes: String = "",
    val name: String = "",
    val id: Int = 0
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
