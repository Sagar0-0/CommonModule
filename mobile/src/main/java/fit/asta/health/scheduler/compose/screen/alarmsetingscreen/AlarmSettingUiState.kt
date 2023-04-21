package fit.asta.health.scheduler.compose.screen.alarmsetingscreen


data class AlarmSettingUiState(
    val Label: String = "",
    val Description: String = "",
    // reminder mode
    val Choice: String = "Notification",
    // vibration slider
    val Vibration: String = "Pattern 1",
    val Sound: VibUiState=VibUiState(),
    // custom tag
    val CustomTagName :TagUiState= TagUiState(),
    val Week:WkUiState= WkUiState()
)
data class TagUiState(
    val selected: Boolean=false,
    val meta: MetaUiState= MetaUiState(),// AlarmEntity
    val id: Int = 0
)
data class ASUiState(
    //time
    var time_hours: String = "",
    var time_midDay: Boolean = false,
    var time_minutes: String = "",

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
    var status: Boolean=false,

    //tag, label,description
    var alarm_description: String = "",
    var alarm_name: String = "",
    var tag_name: String = "",
    var tagId: String = "",
    var tag_url: String = "",

    //reminder mode
    var mode: String="Notification",

    //tone
    var tone_name: String = "default",
    var tone_type: Int = 1,
    var tone_uri: String = "",

   //important
    var important: Boolean=false,

    //vibration
    var vibration: String = "Pattern1",
    var vibration_percentage: String = "",
    var vibration_status: Boolean = false,

    //intervals
    var interval: String="Power Nap",

    //meta
    var cBy: Int = 1,
    var cDate: String = "2023-04-19 13:40:31.099528692 +0000 UTC m=+56311.057896229\"",
    var sync: Int = 1,
    var uDate: String = "1"
)

data class MetaUiState( // AlarmEntity
    val almId: Int=0, // 1
    val id: String="", // 6310c0912aeef2b4b3684a6f
    val important: Boolean=false, // true
    val info: InfoUiState=InfoUiState(),
    val interval: IvlUiState=IvlUiState(),
    val meta: MetaDataUiState=MetaDataUiState(),
    val mode: Int=0, // 2
    val repeat: Boolean=false, // true
    val status: Boolean=false, // true
    val time: TimeUiState=TimeUiState(),
    val tone: ToneUiState=ToneUiState(),
    val userId: String="", // 6309a9379af54f142c65fbff
    val vibration: VibUiState=VibUiState(),
    val week: WkUiState=WkUiState()
)

data class InfoUiState(
    val description: String="",
    val name: String="",
    val tag: String="",
    val tagId: String="",
    val url: String=""
)

data class IvlUiState(
    val advancedReminder: AdvUiState=AdvUiState(),
    val duration: Int=0,
    val isRemainderAtTheEnd: Boolean=false,
    val repeatableInterval: RepUiState=RepUiState(),
    val snoozeTime: Int=5,
    val staticIntervals: List<StatUiState> = emptyList(),
    val status: Boolean=false,
    val variantIntervals: List<StatUiState> = emptyList(),
    val isVariantInterval: Boolean=false
)

data class AdvUiState(
    val status: Boolean=false,
    val time: Int=0
)

data class RepUiState(
    val time: Int=0,
    val unit: String=""
)

data class MetaDataUiState(
    val cBy: Int=0,
    val cDate: String="",
    val sync: Int=0,
    val uDate: String=""
)

data class TimeUiState(
    val hours: String="",
    val midDay: Boolean=false,
    val minutes: String=""
)

data class ToneUiState(
    val name: String="",
    val type: Int=1,
    val uri: String=""
)

data class WkUiState(
    var friday: Boolean=false,
    var monday: Boolean=false,
    var saturday: Boolean=false,
    var sunday: Boolean=false,
    var thursday: Boolean=false,
    var tuesday: Boolean=false,
    var wednesday: Boolean=false,
    var recurring: Boolean=false
)

data class VibUiState(
    val percentage: String="",
    val status: Boolean=false
)

data class StatUiState(
    val hours: String="",
    val midDay: Boolean=false,
    val minutes: String="",
    val name: String="",
    val id: Int =0
)


