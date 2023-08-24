package fit.asta.health.navigation.track

/**
 * This contains all the Navigation Routes for the Tracking Feature
 */
sealed class TrackNavRoute(val route: String) {
    data object TrackMenu : TrackNavRoute("track-menu-route")
    data object WaterTrackDetail : TrackNavRoute("water-track-detail")
    data object StepsTrackDetail : TrackNavRoute("steps-track-detail")
    data object BreathingTrackDetail : TrackNavRoute("breathing-track-detail")
    data object MeditationTrackDetail : TrackNavRoute("meditation-track-detail")
    data object SleepTrackDetail : TrackNavRoute("sleep-track-detail")
    data object SunlightTrackDetail : TrackNavRoute("sunlight-track-detail")
    data object ExerciseTrackDetail : TrackNavRoute("exercise-track-detail")
}