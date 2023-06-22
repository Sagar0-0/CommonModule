package fit.asta.health.navigation.track

import fit.asta.health.navigation.track.view.screens.TrackMenuScreen
import fit.asta.health.navigation.track.view.screens.TrackDetailScreen

/**
 * This contains all the Navigation Routes for the Tracking Feature
 *
 * @property Menu It is the Menu for all the tracking Options and directs to [TrackMenuScreen]
 * @property Detail It shows the detailed Tracking Statistics and directs to [TrackDetailScreen]
 */
sealed class TrackNavRoute(val route: String) {
    object Menu : TrackNavRoute("track-menu-route")
    object Detail : TrackNavRoute("track-detail-screen")
}