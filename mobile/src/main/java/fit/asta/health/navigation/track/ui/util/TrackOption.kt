package fit.asta.health.navigation.track.ui.util

sealed class TrackOption(var trackStatus: TrackStatus) {

    data object WaterOption : TrackOption(TrackStatus.StatusDaily)
    data object StepsOption : TrackOption(TrackStatus.StatusDaily)
    data object MeditationOption : TrackOption(TrackStatus.StatusDaily)
    data object BreathingOption : TrackOption(TrackStatus.StatusDaily)
    data object SleepOption : TrackOption(TrackStatus.StatusDaily)
    data object SunlightOption : TrackOption(TrackStatus.StatusDaily)
    data object YogaOption : TrackOption(TrackStatus.StatusDaily)
    data object DanceOption : TrackOption(TrackStatus.StatusDaily)
    data object WorkoutOption : TrackOption(TrackStatus.StatusDaily)
    data object HiitOption : TrackOption(TrackStatus.StatusDaily)

    sealed class TrackStatus(val status: String) {

        data object StatusDaily : TrackStatus("daily")
        data object StatusWeekly : TrackStatus("weekly")
        data object StatusMonthly : TrackStatus("monthly")
        data object StatusYearly : TrackStatus("yearly")
    }
}