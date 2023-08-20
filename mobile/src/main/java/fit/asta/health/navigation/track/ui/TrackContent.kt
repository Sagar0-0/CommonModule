package fit.asta.health.navigation.track.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.navigation.track.ui.navigation.TrackNavGraph
import fit.asta.health.navigation.track.ui.viewmodel.TrackViewModel

@Composable
fun TrackContent() {

    val viewModel: TrackViewModel = hiltViewModel()

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            // Nav Host For the Tracking Feature
            TrackNavGraph(
                navController = rememberNavController(),
                trackViewModel = viewModel
            )
        }
    }
}