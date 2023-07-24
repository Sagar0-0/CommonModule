package fit.asta.health.navigation.track.view

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.navigation.track.TrackNavGraph
import fit.asta.health.navigation.track.viewmodel.TrackViewModel
import fit.asta.health.thirdparty.spotify.SpotifyLoginActivity

@Composable
fun TrackContent() {

    val viewModel: TrackViewModel = hiltViewModel()

    val activity = LocalContext.current as Activity
    val intent = Intent(activity, SpotifyLoginActivity::class.java)
    activity.startActivity(intent)

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