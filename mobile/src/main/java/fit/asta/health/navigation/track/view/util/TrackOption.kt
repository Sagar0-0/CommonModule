package fit.asta.health.navigation.track.view.util

sealed class TrackOption(var trackStatus: TrackStatus) {

    object WaterOption : TrackOption(TrackStatus.StatusDaily)
    object StepsOption : TrackOption(TrackStatus.StatusDaily)
    object MeditationOption : TrackOption(TrackStatus.StatusDaily)
    object BreathingOption : TrackOption(TrackStatus.StatusDaily)
    object SleepOption : TrackOption(TrackStatus.StatusDaily)
    object SunlightOption : TrackOption(TrackStatus.StatusDaily)

    sealed class TrackStatus(val status: String) {

        object StatusDaily : TrackStatus("daily")
        object StatusWeekly : TrackStatus("weekly")
        object StatusMonthly : TrackStatus("monthly")
        object StatusYearly : TrackStatus("yearly")
    }
}