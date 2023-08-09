package fit.asta.health.navigation.track.view.navigation

/**
 * This contains all the Navigation Routes for the Tracking Feature
 */
sealed class TrackNavRoute(val route: String) {
    object TrackMenu : TrackNavRoute("track-menu-route")
    object WaterTrackDetail : TrackNavRoute("water-track-detail")
    object StepsTrackDetail : TrackNavRoute("steps-track-detail")
    object BreathingTrackDetail : TrackNavRoute("breathing-track-detail")
    object MeditationTrackDetail : TrackNavRoute("meditation-track-detail")
    object SleepTrackDetail : TrackNavRoute("sleep-track-detail")
    object SunlightTrackDetail : TrackNavRoute("sunlight-track-detail")
}