package fit.asta.health.navigation.track.view.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fit.asta.health.navigation.track.viewmodel.TrackViewModel

@Composable
fun TrackStatisticsUI(
    trackViewModel: TrackViewModel
) {

    Text(text = trackViewModel.currentSelectedTrackingOption.displayText)

    // TODO :- To be implemented Later
}