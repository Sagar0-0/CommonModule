package fit.asta.health.scheduler.compose.screen.alarmsetingscreen


data class AlarmSettingUiState(
    val Label: String = "",
    val Description: String = "",
    // reminder mode
    val Choice: String = "",
    // vibration slider
    val Vibration: String = "",
    // custom tag
    val CustomTagName :TagUiState= TagUiState(),
    val Week:WkUiState= WkUiState()
)
data class TagUiState(
    val selected: Boolean=false,
    val meta: MetaUiState= MetaUiState(),// AlarmEntity
    val id: Int = 0
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
    val snoozeTime: Int=0,
    val staticIntervals: List<StatUiState> = listOf(StatUiState()),
    val status: Boolean=false,
    val variantIntervals: List<StatUiState> = listOf(StatUiState()),
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
    val type: Int=0,
    val uri: String=""
)

data class WkUiState(
    val friday: Boolean=false,
    val monday: Boolean=false,
    val saturday: Boolean=false,
    val sunday: Boolean=false,
    val thursday: Boolean=false,
    val tuesday: Boolean=false,
    val wednesday: Boolean=false,
    val recurring: Boolean=false
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


