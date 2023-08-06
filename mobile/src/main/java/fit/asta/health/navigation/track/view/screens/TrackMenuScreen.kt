package fit.asta.health.navigation.track.view.screens


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import fit.asta.health.navigation.track.view.navigation.TrackNavRoute
import fit.asta.health.navigation.track.viewmodel.TrackViewModel

/**
 * This Screen shows all the Statistics Options that are there in the App for the user to choose
 * the specific Option they want to see
 *
 * @param navController Controller which lets us navigate to a different Screen
 * @param trackViewModel This is the [TrackViewModel] class object which contains all the
 * business logic for the Tracking Feature
 */
@Composable
fun TrackMenuScreen(
    navController: NavController,
    trackViewModel: TrackViewModel
) {

    navController.navigate(TrackNavRoute.Detail.route)
}