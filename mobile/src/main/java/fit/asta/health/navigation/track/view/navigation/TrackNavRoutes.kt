package fit.asta.health.navigation.track.view.navigation

/**
 * This contains all the Navigation Routes for the Tracking Feature
 */
sealed class TrackNavRoute(val route: String) {
    object Menu : TrackNavRoute("track-menu-route")
    object Detail : TrackNavRoute("track-detail-screen")
}