package fit.asta.health.navigation.track

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.navigation.track.view.screens.TrackDetailScreen
import fit.asta.health.navigation.track.view.screens.TrackMenuScreen
import fit.asta.health.navigation.track.viewmodel.TrackViewModel

/**
 * This is the navigation Host function for the Tracking Feature
 *
 * @param navController This is the navController for the Tracking Screens
 * @param trackViewModel This is the View Model for all the Tracking Screen
 */
@Composable
fun TrackNavGraph(
    navController: NavHostController,
    trackViewModel: TrackViewModel
) {

    NavHost(
        navController = navController,
        startDestination = TrackNavRoute.Menu.route,
        builder = {

            // Menu Screen for Tracking Stats
            composable(
                TrackNavRoute.Menu.route,
                content = {
                    TrackMenuScreen(
                        navController = navController,
                        trackViewModel = trackViewModel
                    )
                }
            )

            // Detail Screen for Tracking Details
            composable(
                TrackNavRoute.Detail.route,
                content = { TrackDetailScreen(trackViewModel = trackViewModel) }
            )
        }
    )
}