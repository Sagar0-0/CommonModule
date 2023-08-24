package fit.asta.health.navigation.track

/**
 * This contains all the Navigation Routes for the Tracking Feature
 */
sealed class TrackDestination(val route: String) {
    data object TrackMenu : TrackDestination("track-menu-route")
    data object WaterTrackDetail : TrackDestination("water-track-detail")
    data object StepsTrackDetail : TrackDestination("steps-track-detail")
    data object BreathingTrackDetail : TrackDestination("breathing-track-detail")
    data object MeditationTrackDetail : TrackDestination("meditation-track-detail")
    data object SleepTrackDetail : TrackDestination("sleep-track-detail")
    data object SunlightTrackDetail : TrackDestination("sunlight-track-detail")
    data object ExerciseTrackDetail : TrackDestination("exercise-track-detail")
}